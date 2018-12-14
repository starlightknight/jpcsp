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
package jpcsp.HLE.modules;

import static jpcsp.crypto.PreDecrypt.preDecrypt;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.log4j.Logger;

import jpcsp.HLE.BufferInfo;
import jpcsp.HLE.BufferInfo.LengthInfo;
import jpcsp.HLE.BufferInfo.Usage;
import jpcsp.HLE.CanBeNull;
import jpcsp.HLE.HLEFunction;
import jpcsp.HLE.HLEModule;
import jpcsp.HLE.HLEUnimplemented;
import jpcsp.HLE.Modules;
import jpcsp.HLE.TPointer;
import jpcsp.crypto.CryptoEngine;
import jpcsp.memory.IMemoryReader;
import jpcsp.memory.IMemoryWriter;
import jpcsp.memory.MemoryReader;
import jpcsp.memory.MemoryWriter;
import jpcsp.util.Utilities;

public class semaphore extends HLEModule {
	public static Logger log = Modules.getLogger("semaphore");
	private static int dumpIndex = 0;

    public int hleUtilsBufferCopyWithRange(byte[] out, int outOffset, int outSize, byte[] in, int inOffset, int inSize, int cmd) {
    	int result = 0;
    	if (preDecrypt(out, outOffset, outSize, in, inOffset, inSize, cmd)) {
    		if (log.isDebugEnabled()) {
    			log.debug(String.format("hleUtilsBufferCopyWithRange using pre-decrypted data"));
    		}
    	} else {
	    	// Call the KIRK engine to perform the given command
	    	ByteBuffer outBuffer = null;
	    	if (out != null) {
	    		outBuffer = ByteBuffer.wrap(out).order(ByteOrder.LITTLE_ENDIAN);
	    		outBuffer.position(outOffset);
	    	}

	    	ByteBuffer inBuffer = null;
	    	if (in != null) {
	    		inBuffer = ByteBuffer.wrap(in).order(ByteOrder.LITTLE_ENDIAN);
	    		inBuffer.position(inOffset);
	    	}
	    	int inSizeAligned = Utilities.alignUp(inSize, 15);

	    	CryptoEngine crypto = new CryptoEngine();
	    	result = crypto.getKIRKEngine().hleUtilsBufferCopyWithRange(outBuffer, outSize, inBuffer, inSizeAligned, inSize, cmd);
	    	if (result != 0) {
				String dumpFileName = String.format("dump.hleUtilsBufferCopyWithRange.%d", dumpIndex++);
				log.warn(String.format("hleUtilsBufferCopyWithRange returned error result=0x%X for command=0x%X, outputSize=0x%X, inputSize=0x%X, input dumped into file '%s'", result, cmd, outSize, inSize, dumpFileName));
				try {
					OutputStream dump = new FileOutputStream(dumpFileName);
					dump.write(in, inOffset, inSize);
					dump.close();
				} catch (IOException e) {
				}
	    	}
    	}

    	return result;
    }

    public int hleUtilsBufferCopyWithRange(TPointer outAddr, int outSize, TPointer inAddr, int inSize, int cmd) {
		int originalInSize = inSize;

		// The input size needs for some KIRK commands to be 16-bytes aligned
    	inSize = Utilities.alignUp(inSize, 15);

    	// Read the whole input buffer, including a possible header
    	// (up to 144 bytes, depending on the KIRK command)
    	byte[] inBytes = new byte[inSize + 144]; // Up to 144 bytes header
    	IMemoryReader memoryReaderIn = MemoryReader.getMemoryReader(inAddr, inSize, 1);
    	for (int i = 0; i < inSize; i++) {
    		inBytes[i] = (byte) memoryReaderIn.readNext();
    	}

    	// Some KIRK commands (e.g. PSP_KIRK_CMD_SHA1_HASH) only update a part of the output buffer.
    	// Read the whole output buffer so that it can be updated completely after the KIRK call.
    	byte[] outBytes = new byte[Utilities.alignUp(outSize, 15)];
    	IMemoryReader memoryReaderOut = MemoryReader.getMemoryReader(outAddr, outBytes.length, 1);
    	for (int i = 0; i < outBytes.length; i++) {
    		outBytes[i] = (byte) memoryReaderOut.readNext();
    	}

    	int result = hleUtilsBufferCopyWithRange(outBytes, 0, outSize, inBytes, 0, originalInSize, cmd);

    	// Write back the whole output buffer to the memory.
    	IMemoryWriter memoryWriter = MemoryWriter.getMemoryWriter(outAddr, outSize, 1);
    	for (int i = 0; i < outSize; i++) {
    		memoryWriter.writeNext(outBytes[i] & 0xFF);
    	}
    	memoryWriter.flush();

    	return result;
    }

    @HLEFunction(nid = 0x4C537C72, version = 150)
    public int sceUtilsBufferCopyWithRange(@CanBeNull @BufferInfo(lengthInfo=LengthInfo.nextParameter, usage=Usage.out, maxDumpLength=256) TPointer outAddr, int outSize, @CanBeNull @BufferInfo(lengthInfo=LengthInfo.nextParameter, usage=Usage.in, maxDumpLength=256) TPointer inAddr, int inSize, int cmd) {
    	hleUtilsBufferCopyWithRange(outAddr, outSize, inAddr, inSize, cmd);
    	// Fake a successful operation
    	return 0;
    }

    @HLEFunction(nid = 0x77E97079, version = 150)
    public int sceUtilsBufferCopyByPollingWithRange(TPointer outAddr, int outSize, TPointer inAddr, int inSize, int cmd) {
		return sceUtilsBufferCopyWithRange(outAddr, outSize, inAddr, inSize, cmd);
	}

	@HLEUnimplemented
	@HLEFunction(nid = 0x00EEC06A, version = 150)
	public int sceUtilsBufferCopy(TPointer outAddr, TPointer inAddr, int cmd) {
		return 0;
	}

	@HLEUnimplemented
	@HLEFunction(nid = 0x8EEB7BF2, version = 150)
	public int 	sceUtilsBufferCopyByPolling(TPointer outAddr, TPointer inAddr, int cmd) {
		return 0;
	}
}
