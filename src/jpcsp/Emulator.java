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
package jpcsp;

import static jpcsp.HLE.modules.SysMemUserForUser.USER_PARTITION_ID;

import java.io.IOException;
import java.nio.ByteBuffer;

import jpcsp.Allegrex.compiler.Compiler;
import jpcsp.Allegrex.compiler.Profiler;
import jpcsp.Allegrex.compiler.RuntimeContext;
import jpcsp.Allegrex.compiler.RuntimeContextLLE;
import jpcsp.Debugger.InstructionCounter;
import jpcsp.Debugger.StepLogger;
import jpcsp.GUI.IMainGUI;
import jpcsp.HLE.HLEModuleManager;
import jpcsp.HLE.HLEUidObjectMapping;
import jpcsp.HLE.Modules;
import jpcsp.HLE.TPointer;
import jpcsp.HLE.kernel.Managers;
import jpcsp.HLE.kernel.managers.SceUidManager;
import jpcsp.HLE.kernel.types.SceModule;
import jpcsp.HLE.modules.SysMemUserForUser;
import jpcsp.HLE.modules.SysMemUserForUser.SysMemInfo;
import jpcsp.graphics.GEProfiler;
import jpcsp.graphics.VertexCache;
import jpcsp.graphics.VideoEngine;
import jpcsp.graphics.RE.externalge.ExternalGE;
import jpcsp.graphics.RE.software.BasePrimitiveRenderer;
import jpcsp.graphics.RE.software.BaseRenderer;
import jpcsp.graphics.RE.software.RendererExecutor;
import jpcsp.graphics.textures.TextureCache;
import jpcsp.hardware.Battery;
import jpcsp.hardware.Model;
import jpcsp.hardware.Nand;
import jpcsp.hardware.Wlan;
import jpcsp.memory.MemorySections;
import jpcsp.network.proonline.ProOnlineNetworkAdapter;
import jpcsp.scheduler.Scheduler;
import jpcsp.settings.Settings;
import jpcsp.sound.SoundChannel;
import jpcsp.util.DurationStatistics;
import jpcsp.util.JpcspDialogManager;

import org.apache.log4j.Logger;

/*
 * TODO list:
 * 1. Cleanup initialization in initNewPsp():
 *  - UMD: calls setFirmwareVersion before initNewPsp (PSF is read separate from BOOT.BIN).
 *  - PBP: calls initNewPsp before setFirmwareVersion (PSF is embedded in PBP).
 *  - ELF/PRX: only calls initNewPsp (doesn't have a PSF).
 */
public class Emulator implements Runnable {

    private static Emulator instance;
    private static Processor processor;
    private static Clock clock;
    private static Scheduler scheduler;
    private boolean moduleLoaded;
    private Thread mainThread;
    public static boolean run = false;
    public static boolean pause = false;
    private static IMainGUI gui;
    private InstructionCounter instructionCounter;
    public static Logger log = Logger.getLogger("emu");
    private SceModule module;
    private int firmwareVersion = 999;
    private String[] bootModuleBlackList = {"Prometheus Loader"};

    public Emulator(IMainGUI gui) {
        Emulator.gui = gui;
        processor = new Processor();
        clock = new Clock();
        scheduler = Scheduler.getInstance();

        moduleLoaded = false;
        mainThread = new Thread(this, "Emu");

        instance = this;
    }

    public Thread getMainThread() {
        return mainThread;
    }

    public static void exit() {
        if (DurationStatistics.collectStatistics) {
            log.info(TextureCache.getInstance().statistics);
        }
        RendererExecutor.exit();
        VertexCache.getInstance().exit();
        Compiler.exit();
        RuntimeContext.exit();
        Profiler.exit();
        GEProfiler.exit();
        BaseRenderer.exit();
        BasePrimitiveRenderer.exit();
        ExternalGE.exit();
        if (DurationStatistics.collectStatistics && Modules.ThreadManForUserModule.statistics != null && Modules.sceDisplayModule.statistics != null) {
            long totalMillis = getClock().milliTime();
            long displayMillis = Modules.sceDisplayModule.statistics.cumulatedTimeMillis;
            long idleCpuMillis = RuntimeContext.idleDuration.getCpuDurationMillis();
            long compilationCpuMillis = Compiler.compileDuration.getCpuDurationMillis();
            long cpuMillis = Modules.ThreadManForUserModule.statistics.allCpuMillis - compilationCpuMillis - idleCpuMillis;
            long cpuCycles = Modules.ThreadManForUserModule.statistics.allCycles;
            double totalSecs = totalMillis / 1000.0;
            double displaySecs = displayMillis / 1000.0;
            double cpuSecs = cpuMillis / 1000.0;
            if (totalSecs != 0) {
                log.info("Total execution time: " + String.format("%.3f", totalSecs) + "s");
                log.info("     PSP CPU time: " + String.format("%.3f", cpuSecs) + "s (" + String.format("%.1f", cpuSecs / totalSecs * 100) + "%)");
                log.info("     Display time: " + String.format("%.3f", displaySecs) + "s (" + String.format("%.1f", displaySecs / totalSecs * 100) + "%)");
            }
            if (VideoEngine.getStatistics() != null) {
                long videoCalls = VideoEngine.getStatistics().numberCalls;
                if (videoCalls != 0) {
                    log.info("Elapsed time per frame: " + String.format("%.3f", totalSecs / videoCalls) + "s:");
                    log.info("    Display time: " + String.format("%.3f", displaySecs / videoCalls));
                    log.info("    PSP CPU time: " + String.format("%.3f", cpuSecs / videoCalls) + " (" + (cpuCycles / videoCalls) + " instr)");
                }
                if (totalSecs != 0) {
                    log.info("Display Speed: " + String.format("%.2f", videoCalls / totalSecs) + " FPS");
                }
            }
            if (cpuSecs != 0) {
                log.info("PSP CPU Speed: " + String.format("%.2f", cpuCycles / cpuSecs / 1000000.0) + "MHz (" + (long) (cpuCycles / cpuSecs) + " instructions per second)");
            }
        }
        SoundChannel.exit();
    }

    private boolean isBootModuleBad(String name) {
        for (String moduleName : bootModuleBlackList) {
            if (name.equals(moduleName)) {
                return true;
            }
        }
        return false;
    }

    public SceModule load(String pspfilename, ByteBuffer f) throws IOException, GeneralJpcspException {
        return load(pspfilename, f, false, false);
    }

    private TPointer getLoadAddress() {
    	Memory mem = Emulator.getMemory();
        SysMemInfo testInfo = Modules.SysMemUserForUserModule.malloc(USER_PARTITION_ID, "test-LoadAddress", SysMemUserForUser.PSP_SMEM_Low, 0x100, 0);
        if (testInfo == null) {
        	return new TPointer(mem, MemoryMap.START_USERSPACE + 0x4000);
        }

        int lowestAddress = testInfo.addr;
        Modules.SysMemUserForUserModule.free(testInfo);

        return new TPointer(mem, lowestAddress);
    }

    public SceModule load(String pspfilename, ByteBuffer f, boolean fromSyscall, boolean isSignChecked) throws IOException, GeneralJpcspException {
        initNewPsp(fromSyscall);

        HLEModuleManager.getInstance().loadAvailableFlash0Modules(fromSyscall);

        TPointer loadAddress = getLoadAddress();
    	module = Loader.getInstance().LoadModule(pspfilename, f, loadAddress, USER_PARTITION_ID, USER_PARTITION_ID, false, true, fromSyscall, isSignChecked);

        if ((module.fileFormat & Loader.FORMAT_ELF) != Loader.FORMAT_ELF) {
            throw new GeneralJpcspException("File format not supported!");
        }
        if (isBootModuleBad(module.modname)) {
            JpcspDialogManager.showError(null, java.util.ResourceBundle.getBundle("jpcsp/languages/jpcsp").getString("Emulator.strPrometheusLoader.text"));
        }

        moduleLoaded = true;
        initCpu(fromSyscall);

        // Delete breakpoints and reset to PC
        if (State.debugger != null) {
            State.debugger.resetDebugger();
        }

        // Update instruction counter dialog with the new app
        if (instructionCounter != null) {
            instructionCounter.setModule(module);
        }

        return module;
    }

    private void initCpu(boolean fromSyscall) {
    	String discId = module.psf != null ? module.psf.getString("DISC_ID") : State.discId;
        if ("MSTKUPDATE".equals(discId)) {
        	RuntimeContextLLE.createMMIO();
        }

        RuntimeContext.update();

        int entryAddr = module.entry_addr;
        if (Memory.isAddressGood(module.module_start_func)) {
            if (module.module_start_func != entryAddr) {
                log.warn(String.format("Using the module start function as module entry: 0x%08X instead of 0x%08X", module.module_start_func, entryAddr));
                entryAddr = module.module_start_func;
            }
        }

        HLEModuleManager.getInstance().startModules(fromSyscall);
        Modules.ThreadManForUserModule.Initialise(module, entryAddr, module.attribute, module.pspfilename, module.modid, module.gp_value, fromSyscall);

        if (State.memoryViewer != null) {
            State.memoryViewer.RefreshMemory();
        }
    }

    public void initNewPsp(boolean fromSyscall) {
        moduleLoaded = false;

        Model.setModel(Settings.getInstance().readInt("emu.model"));
        Nand.init();
        HLEModuleManager.getInstance().stopModules();
        NIDMapper.getInstance().unloadAll();
        RuntimeContext.reset();

        if (!fromSyscall) {
            // Do not reset the profiler if we have been called from sceKernelLoadExec
            Profiler.reset();
            GEProfiler.reset();
            // Do not reset the clock if we have been called from sceKernelLoadExec
            getClock().reset();
        }

        getProcessor().reset();
        getScheduler().reset();

        Memory mem = Memory.getInstance();
        if (!fromSyscall) {
            // Clear all memory, including VRAM.
            mem.Initialise();
        } else {
            // Clear all memory excepted VRAM.
            // E.g. screen is not cleared when executing syscall sceKernelLoadExec().
            mem.memset(MemoryMap.START_SCRATCHPAD, (byte) 0, MemoryMap.SIZE_SCRATCHPAD);
            mem.memset(MemoryMap.START_RAM, (byte) 0, MemoryMap.SIZE_RAM);
        }

        Battery.initialize();
        Wlan.initialize();
        jpcsp.HLE.kernel.types.SceModule.ResetAllocator();
        SceUidManager.reset();
        HLEUidObjectMapping.reset();
        ProOnlineNetworkAdapter.init();

        if (State.fileLogger != null) {
            State.fileLogger.resetLogging();
        }
        MemorySections.getInstance().reset();

        HLEModuleManager.getInstance().init();
        Managers.reset();
        Modules.SysMemUserForUserModule.start();
        Modules.SysMemUserForUserModule.setFirmwareVersion(firmwareVersion);
    	Modules.ThreadManForUserModule.start();
    }

    @Override
    public void run() {
        RuntimeContext.start();
        RuntimeContextLLE.start();
        GEProfiler.initialise();

        clock.resume();

        while (true) {
            if (pause) {
                clock.pause();
                try {
                    synchronized (this) {
                        while (pause) {
                            wait();
                        }
                    }
                } catch (InterruptedException e) {
                    // Ignore exception
                }
                clock.resume();
            }

            if (RuntimeContext.isCompilerEnabled()) {
                RuntimeContext.run();
            } else {
                processor.step();
                Modules.sceGe_userModule.step();
                Modules.ThreadManForUserModule.step();
                scheduler.step();
                Modules.sceDisplayModule.step();

                if (State.debugger != null) {
                    State.debugger.step();
                }
            }
        }

    }

    public synchronized void RunEmu() {
        if (!moduleLoaded) {
            Emulator.log.debug("Nothing loaded, can't run...");
            gui.RefreshButtons();
            return;
        }

        if (pause) {
            pause = false;
            notifyAll();
        } else if (!run) {
            run = true;
            mainThread.start();
        }

        Modules.sceDisplayModule.setGeDirty(true);

        gui.RefreshButtons();
        if (State.debugger != null) {
            State.debugger.RefreshButtons();
        }
    }

    private static void PauseEmu(boolean hasStatus, int status) {
        if (run && !pause) {
            pause = true;

            if (hasStatus) {
                StepLogger.setStatus(status);
            }

            gui.RefreshButtons();

            if (State.debugger != null) {
                State.debugger.RefreshButtons();
                State.debugger.SafeRefreshDebugger(true);
            }

            if (State.memoryViewer != null) {
                State.memoryViewer.SafeRefreshMemory();
            }

            if (State.imageViewer != null) {
                State.imageViewer.SafeRefreshImage();
            }

            StepLogger.flush();
        }
    }

    public static synchronized void PauseEmu() {
        PauseEmu(false, 0);
    }
    public static final int EMU_STATUS_OK = 0x00;
    public static final int EMU_STATUS_UNKNOWN = 0xFFFFFFFF;
    public static final int EMU_STATUS_WDT_IDLE = 0x01;
    public static final int EMU_STATUS_WDT_HOG = 0x02;
    public static final int EMU_STATUS_WDT_ANY = EMU_STATUS_WDT_IDLE | EMU_STATUS_WDT_HOG;
    public static final int EMU_STATUS_MEM_READ = 0x04;
    public static final int EMU_STATUS_MEM_WRITE = 0x08;
    public static final int EMU_STATUS_MEM_ANY = EMU_STATUS_MEM_READ | EMU_STATUS_MEM_WRITE;
    public static final int EMU_STATUS_BREAKPOINT = 0x10;
    public static final int EMU_STATUS_UNIMPLEMENTED = 0x20;
    public static final int EMU_STATUS_PAUSE = 0x40;
    public static final int EMU_STATUS_JUMPSELF = 0x80;
    public static final int EMU_STATUS_BREAK = 0x100;
    public static final int EMU_STATUS_HALT = 0x200;

    public static synchronized void PauseEmuWithStatus(int status) {
        PauseEmu(true, status);
    }

    public static void setFpsTitle(String fps) {
        gui.setMainTitle(fps);
    }

    public static Processor getProcessor() {
        return processor;
    }

    public static Processor setProcessor(Processor processor) {
    	Processor previousProcessor = Emulator.processor;
    	Emulator.processor = processor;

    	return previousProcessor;
    }

    public static Memory getMemory() {
        return Memory.getInstance();
    }

	public static Memory getMemory(int address) {
		if (!Memory.isAddressGood(address)) {
			Memory mmio = RuntimeContextLLE.getMMIO();
			if (mmio != null) {
				return mmio;
			}
		}

		return getMemory();
	}

    public static Clock getClock() {
        return clock;
    }

    private static void setClock(Clock clock) {
        Emulator.clock = clock;
    }

    public static Scheduler getScheduler() {
        return scheduler;
    }

    public static IMainGUI getMainGUI() {
        return gui;
    }

    public static Emulator getInstance() {
        return instance;
    }

    public void setInstructionCounter(InstructionCounter instructionCounter) {
        this.instructionCounter = instructionCounter;
        instructionCounter.setModule(module);
    }

    public int getFirmwareVersion() {
        return firmwareVersion;
    }

    /**
     * @param firmwareVersion : in this format: ABB, where A = major and B =
     * minor, for example 271
     */
    public void setFirmwareVersion(int firmwareVersion) {
        this.firmwareVersion = firmwareVersion;

        Modules.SysMemUserForUserModule.setFirmwareVersion(this.firmwareVersion);
        RuntimeContext.setFirmwareVersion(firmwareVersion);
    }

    /**
     * @param firmwareVersion : in this format: "A.BB", where A = major and B =
     * minor, for example "2.71"
     */
    public void setFirmwareVersion(String firmwareVersion) {
        setFirmwareVersion(HLEModuleManager.psfFirmwareVersionToInt(firmwareVersion));
    }

    public static void setVariableSpeedClock(int numerator, int denominator) {
        if (getClock() instanceof VariableSpeedClock) {
            // Update the speed of the current variable speed clock
            ((VariableSpeedClock) getClock()).setSpeed(numerator, denominator);
        } else if (numerator != 1 || denominator != 1) {
            // Change the clock to a variable speed clock with the given speed
            VariableSpeedClock variableSpeedClock = new VariableSpeedClock(clock, numerator, denominator);
            setClock(variableSpeedClock);
        }
    }

    public void setModuleLoaded(boolean moduleLoaded) {
    	this.moduleLoaded = moduleLoaded;
    }
}
