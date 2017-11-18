/*
This file is part of jpcsp.

Jpcsp is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Jpcsp is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Jpcsp.  If not, see <http://www.gnu.org/licenses/>.
 */
package jpcsp.memory.mmio.dmac;

import static jpcsp.MemoryMap.END_IO_1;
import static jpcsp.MemoryMap.START_IO_0;

import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;

import jpcsp.Memory;
import jpcsp.Allegrex.compiler.RuntimeContextLLE;
import jpcsp.HLE.kernel.types.IAction;
import jpcsp.memory.mmio.MMIOHandlerDmac;

public class DmacThread extends Thread {
	private static Logger log = MMIOHandlerDmac.log;
	private static final int DMAC_MEMCPY_STEP2 = 0;
	private static final int DMAC_MEMCPY_STEP16 = 1;
	private static final int DMAC_MEMCPY_STEP8 = 2;
	private static final int DMAC_MEMCPY_STEP4 = 3;
	private static final int dmacMemcpyStepLength[] = new int[8];
	private final Semaphore job = new Semaphore(0);
	private volatile Memory mem;
	private volatile int src;
	private volatile int dst;
	private volatile int next;
	private volatile int attributes;
	private volatile IAction completedAction;
	private volatile boolean exit;

	public DmacThread() {
		dmacMemcpyStepLength[DMAC_MEMCPY_STEP2] = 2;
		dmacMemcpyStepLength[DMAC_MEMCPY_STEP4] = 4;
		dmacMemcpyStepLength[DMAC_MEMCPY_STEP8] = 8;
		dmacMemcpyStepLength[DMAC_MEMCPY_STEP16] = 16;
	}

	@Override
	public void run() {
		while (!exit) {
			try {
				job.acquire();
				if (!exit) {
					dmacMemcpy(dst, src, next, attributes);
				}
			} catch (InterruptedException e) {
				// Ignore exception
			}
		}
	}

	public void exit() {
		exit = true;
	}

	public void execute(Memory mem, int dst, int src, int next, int attributes, IAction completedAction) {
		this.mem = mem;
		this.dst = dst;
		this.src = src;
		this.next = next;
		this.attributes = attributes;
		this.completedAction = completedAction;

		job.release();
	}

	private static int normalizeAddress(int addr) {
		// Transform address 0x1nnnnnnn into 0xBnnnnnnn
		if (addr >= (START_IO_0 & Memory.addressMask) && addr <= (END_IO_1 & Memory.addressMask)) {
			addr |= (START_IO_0 & ~Memory.addressMask);
		}
		return addr;
	}

	private void dmacMemcpy(int dst, int src, int dstLength, int srcLength, int dstStep, int srcStep, boolean dstIncrement, boolean srcIncrement) {
		int srcStepLength = dmacMemcpyStepLength[srcStep];
		if (srcStepLength == 0) {
			log.error(String.format("dmacMemcpy with unknown srcStep=%d", srcStep));
			return;
		}

		int dstStepLength = dmacMemcpyStepLength[dstStep];
		if (dstStepLength == 0) {
			log.error(String.format("dmacMemcpy with unknown dstStep=%d", dstStep));
			return;
		}

		if (srcStepLength != dstStepLength) {
			log.error(String.format("dmacMemcpy with different steps: srcStepLength=%d, dstSteplength=%d", srcStepLength, dstStepLength));
			return;
		}

		if (srcLength != dstLength) {
			log.error(String.format("dmacMemcpy with different lengths: srcLength=0x%X, dstLength=0x%X", srcLength, dstLength));
			return;
		}

		if (log.isDebugEnabled()) {
			log.debug(String.format("dmacMemcpy dst=0x%08X, src=0x%08X, dstLength=0x%X, srcLength=0x%X, dstStepLength=%d, srcStepLength=%d, dstIncrement=%b, srcIncrement=%b", dst, src, dstLength, srcLength, dstStepLength, srcStepLength, dstIncrement, srcIncrement));
		}

		while (dstLength > 0 && srcLength > 0) {
			switch (srcStepLength) {
				case 1:
					if (log.isTraceEnabled()) {
						log.trace(String.format("memcpy dst=0x%08X, src=0x%08X, length=0x%X", dst, src, 1));
					}
					mem.write8(dst, (byte) mem.read8(src));
					break;
				case 2:
					if (log.isTraceEnabled()) {
						log.trace(String.format("memcpy dst=0x%08X, src=0x%08X, length=0x%X", dst, src, 2));
					}
					mem.write16(dst, (short) mem.read16(src));
					break;
				case 4:
					if (log.isTraceEnabled()) {
						log.trace(String.format("memcpy dst=0x%08X, src=0x%08X, length=0x%X", dst, src, 4));
					}
					mem.write32(dst, mem.read32(src));
					break;
				case 8:
					if (log.isTraceEnabled()) {
						log.trace(String.format("memcpy dst=0x%08X, src=0x%08X, length=0x%X", dst, src, 8));
					}
					mem.write32(dst, mem.read32(src));
					mem.write32(dst + 4, mem.read32(src + 4));
					break;
				case 16:
					if (log.isTraceEnabled()) {
						log.trace(String.format("memcpy dst=0x%08X, src=0x%08X, length=0x%X", dst, src, 16));
					}
					mem.write32(dst, mem.read32(src));
					mem.write32(dst + 4, mem.read32(src + 4));
					mem.write32(dst + 8, mem.read32(src + 8));
					mem.write32(dst + 12, mem.read32(src + 12));
					break;
			}
			dstLength -= dstStepLength;
			srcLength -= srcStepLength;

			if (dstIncrement) {
				dst += dstStepLength;
			}
			if (srcIncrement) {
				src += srcStepLength;
			}
		}
	}

	private void dmacMemcpy(int dst, int src, int next, int attr) {
		while (true) {
			int srcStep = (attr >> 12) & 0x7;
			int dstStep = (attr >> 15) & 0x7;
			int srcLengthShift = (attr >> 18) & 0x7;
			int srcLength = (attr & 0xFFF) << srcLengthShift;
			int dstLengthShift = (attr >> 21) & 0x7;
			int dstLength = (attr & 0xFFF) << dstLengthShift;
			boolean srcIncrement = (attr & 0x04000000) != 0;
			boolean dstIncrement = (attr & 0x08000000) != 0;

			src = normalizeAddress(src);
			dst = normalizeAddress(dst);

			// Check for most common case which can be implemented through a simple memcpy
			if (srcIncrement && dstIncrement && srcLength == dstLength) {
				if (log.isDebugEnabled()) {
					log.debug(String.format("dmacMemcpy dst=0x%08X, src=0x%08X, length=0x%X", dst, src, srcLength));
				}

				RuntimeContextLLE.getMMIO().memcpy(dst, src, srcLength);
			} else {
				dmacMemcpy(dst, src, dstLength, srcLength, dstStep, srcStep, dstIncrement, srcIncrement);
			}

			if ((attr & 0x80000000) != 0 || next == 0) {
				break;
			}

			src = mem.read32(next + 0);
			dst = mem.read32(next + 4);
			attr = mem.read32(next + 12);
			next = mem.read32(next + 8);
		}

		if (completedAction != null) {
			completedAction.execute();
		}
	}
}
