/* This autogenerated file is part of jpcsp. */
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

package jpcsp.HLE.modules150;

import jpcsp.HLE.Modules;
import jpcsp.HLE.modules.HLEModule;
import jpcsp.HLE.modules.HLEModuleFunction;
import jpcsp.HLE.modules.HLEModuleManager;

import jpcsp.Memory;
import jpcsp.Processor;

import jpcsp.Allegrex.CpuState; // New-Style Processor

public class sceMeWrapper_driver implements HLEModule {
	@Override
	public String getName() { return "sceMeWrapper_driver"; }
	
	@Override
	public void installModule(HLEModuleManager mm, int version) {
		if (version >= 150) {
		
			mm.addFunction(sceMeWrapperInitFunction, 0x63463F66);
			mm.addFunction(sceMeWrapperEndFunction, 0xC3A1D6B2);
			mm.addFunction(sceMeWrapper_driver_B655AD4EFunction, 0xB655AD4E);
			mm.addFunction(sceMeWrapper_driver_06F0236AFunction, 0x06F0236A);
			mm.addFunction(sceMeWrapper_driver_176BBE07Function, 0x176BBE07);
			mm.addFunction(sceMeWrapper_driver_DE2E7E89Function, 0xDE2E7E89);
			mm.addFunction(sceMeWrapper_driver_E67F4E0AFunction, 0xE67F4E0A);
			mm.addFunction(sceMeWrapper_driver_1C6A60E1Function, 0x1C6A60E1);
			mm.addFunction(sceMeWrapper_driver_C00D873BFunction, 0xC00D873B);
			mm.addFunction(sceMeWrapper_driver_FEE0A97DFunction, 0xFEE0A97D);
			mm.addFunction(sceMeWrapper_driver_BAC7C2BEFunction, 0xBAC7C2BE);
			mm.addFunction(sceMeWrapper_driver_39F06790Function, 0x39F06790);
			mm.addFunction(sceMeMallocFunction, 0xA177798D);
			mm.addFunction(sceMeCallocFunction, 0xC86500E3);
			mm.addFunction(sceMeFreeFunction, 0x5AD660FA);
			mm.addFunction(sceMeWrapper_driver_D1EA3DFDFunction, 0xD1EA3DFD);
			mm.addFunction(sceMeWrapper_driver_3D5F109CFunction, 0x3D5F109C);
			mm.addFunction(sceMeRpcLockFunction, 0x04AFF68E);
			mm.addFunction(sceMeRpcUnlockFunction, 0xB97B15D7);
			mm.addFunction(sceMeEnableFunctionsFunction, 0x4794C05C);
			mm.addFunction(sceMeWrapper_driver_55F11AF6Function, 0x55F11AF6);
			mm.addFunction(sceMeWrapper_driver_8EBBBBA3Function, 0x8EBBBBA3);
			mm.addFunction(sceMeWrapper_driver_B6FE3E62Function, 0xB6FE3E62);
			mm.addFunction(sceMePowerControlAvcPowerFunction, 0x05B2C420);
			mm.addFunction(sceMePowerSelectAvcClockFunction, 0x579ACEA9);
			
		}
	}
	
	@Override
	public void uninstallModule(HLEModuleManager mm, int version) {
		if (version >= 150) {
		
			mm.removeFunction(sceMeWrapperInitFunction);
			mm.removeFunction(sceMeWrapperEndFunction);
			mm.removeFunction(sceMeWrapper_driver_B655AD4EFunction);
			mm.removeFunction(sceMeWrapper_driver_06F0236AFunction);
			mm.removeFunction(sceMeWrapper_driver_176BBE07Function);
			mm.removeFunction(sceMeWrapper_driver_DE2E7E89Function);
			mm.removeFunction(sceMeWrapper_driver_E67F4E0AFunction);
			mm.removeFunction(sceMeWrapper_driver_1C6A60E1Function);
			mm.removeFunction(sceMeWrapper_driver_C00D873BFunction);
			mm.removeFunction(sceMeWrapper_driver_FEE0A97DFunction);
			mm.removeFunction(sceMeWrapper_driver_BAC7C2BEFunction);
			mm.removeFunction(sceMeWrapper_driver_39F06790Function);
			mm.removeFunction(sceMeMallocFunction);
			mm.removeFunction(sceMeCallocFunction);
			mm.removeFunction(sceMeFreeFunction);
			mm.removeFunction(sceMeWrapper_driver_D1EA3DFDFunction);
			mm.removeFunction(sceMeWrapper_driver_3D5F109CFunction);
			mm.removeFunction(sceMeRpcLockFunction);
			mm.removeFunction(sceMeRpcUnlockFunction);
			mm.removeFunction(sceMeEnableFunctionsFunction);
			mm.removeFunction(sceMeWrapper_driver_55F11AF6Function);
			mm.removeFunction(sceMeWrapper_driver_8EBBBBA3Function);
			mm.removeFunction(sceMeWrapper_driver_B6FE3E62Function);
			mm.removeFunction(sceMePowerControlAvcPowerFunction);
			mm.removeFunction(sceMePowerSelectAvcClockFunction);
			
		}
	}
	
	
	public void sceMeWrapperInit(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMeWrapperInit [0x63463F66]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceMeWrapperEnd(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMeWrapperEnd [0xC3A1D6B2]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceMeWrapper_driver_B655AD4E(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMeWrapper_driver_B655AD4E [0xB655AD4E]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceMeWrapper_driver_06F0236A(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMeWrapper_driver_06F0236A [0x06F0236A]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceMeWrapper_driver_176BBE07(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMeWrapper_driver_176BBE07 [0x176BBE07]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceMeWrapper_driver_DE2E7E89(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMeWrapper_driver_DE2E7E89 [0xDE2E7E89]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceMeWrapper_driver_E67F4E0A(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMeWrapper_driver_E67F4E0A [0xE67F4E0A]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceMeWrapper_driver_1C6A60E1(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMeWrapper_driver_1C6A60E1 [0x1C6A60E1]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceMeWrapper_driver_C00D873B(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMeWrapper_driver_C00D873B [0xC00D873B]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceMeWrapper_driver_FEE0A97D(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMeWrapper_driver_FEE0A97D [0xFEE0A97D]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceMeWrapper_driver_BAC7C2BE(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMeWrapper_driver_BAC7C2BE [0xBAC7C2BE]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceMeWrapper_driver_39F06790(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMeWrapper_driver_39F06790 [0x39F06790]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceMeMalloc(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMeMalloc [0xA177798D]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceMeCalloc(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMeCalloc [0xC86500E3]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceMeFree(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMeFree [0x5AD660FA]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceMeWrapper_driver_D1EA3DFD(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMeWrapper_driver_D1EA3DFD [0xD1EA3DFD]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceMeWrapper_driver_3D5F109C(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMeWrapper_driver_3D5F109C [0x3D5F109C]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceMeRpcLock(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMeRpcLock [0x04AFF68E]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceMeRpcUnlock(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMeRpcUnlock [0xB97B15D7]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceMeEnableFunctions(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMeEnableFunctions [0x4794C05C]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceMeWrapper_driver_55F11AF6(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMeWrapper_driver_55F11AF6 [0x55F11AF6]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceMeWrapper_driver_8EBBBBA3(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMeWrapper_driver_8EBBBBA3 [0x8EBBBBA3]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceMeWrapper_driver_B6FE3E62(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMeWrapper_driver_B6FE3E62 [0xB6FE3E62]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceMePowerControlAvcPower(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMePowerControlAvcPower [0x05B2C420]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceMePowerSelectAvcClock(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMePowerSelectAvcClock [0x579ACEA9]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public final HLEModuleFunction sceMeWrapperInitFunction = new HLEModuleFunction("sceMeWrapper_driver", "sceMeWrapperInit") {
		@Override
		public final void execute(Processor processor) {
			sceMeWrapperInit(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMeWrapper_driverModule.sceMeWrapperInit(processor);";
		}
	};
    
	public final HLEModuleFunction sceMeWrapperEndFunction = new HLEModuleFunction("sceMeWrapper_driver", "sceMeWrapperEnd") {
		@Override
		public final void execute(Processor processor) {
			sceMeWrapperEnd(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMeWrapper_driverModule.sceMeWrapperEnd(processor);";
		}
	};
    
	public final HLEModuleFunction sceMeWrapper_driver_B655AD4EFunction = new HLEModuleFunction("sceMeWrapper_driver", "sceMeWrapper_driver_B655AD4E") {
		@Override
		public final void execute(Processor processor) {
			sceMeWrapper_driver_B655AD4E(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMeWrapper_driverModule.sceMeWrapper_driver_B655AD4E(processor);";
		}
	};
    
	public final HLEModuleFunction sceMeWrapper_driver_06F0236AFunction = new HLEModuleFunction("sceMeWrapper_driver", "sceMeWrapper_driver_06F0236A") {
		@Override
		public final void execute(Processor processor) {
			sceMeWrapper_driver_06F0236A(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMeWrapper_driverModule.sceMeWrapper_driver_06F0236A(processor);";
		}
	};
    
	public final HLEModuleFunction sceMeWrapper_driver_176BBE07Function = new HLEModuleFunction("sceMeWrapper_driver", "sceMeWrapper_driver_176BBE07") {
		@Override
		public final void execute(Processor processor) {
			sceMeWrapper_driver_176BBE07(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMeWrapper_driverModule.sceMeWrapper_driver_176BBE07(processor);";
		}
	};
    
	public final HLEModuleFunction sceMeWrapper_driver_DE2E7E89Function = new HLEModuleFunction("sceMeWrapper_driver", "sceMeWrapper_driver_DE2E7E89") {
		@Override
		public final void execute(Processor processor) {
			sceMeWrapper_driver_DE2E7E89(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMeWrapper_driverModule.sceMeWrapper_driver_DE2E7E89(processor);";
		}
	};
    
	public final HLEModuleFunction sceMeWrapper_driver_E67F4E0AFunction = new HLEModuleFunction("sceMeWrapper_driver", "sceMeWrapper_driver_E67F4E0A") {
		@Override
		public final void execute(Processor processor) {
			sceMeWrapper_driver_E67F4E0A(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMeWrapper_driverModule.sceMeWrapper_driver_E67F4E0A(processor);";
		}
	};
    
	public final HLEModuleFunction sceMeWrapper_driver_1C6A60E1Function = new HLEModuleFunction("sceMeWrapper_driver", "sceMeWrapper_driver_1C6A60E1") {
		@Override
		public final void execute(Processor processor) {
			sceMeWrapper_driver_1C6A60E1(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMeWrapper_driverModule.sceMeWrapper_driver_1C6A60E1(processor);";
		}
	};
    
	public final HLEModuleFunction sceMeWrapper_driver_C00D873BFunction = new HLEModuleFunction("sceMeWrapper_driver", "sceMeWrapper_driver_C00D873B") {
		@Override
		public final void execute(Processor processor) {
			sceMeWrapper_driver_C00D873B(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMeWrapper_driverModule.sceMeWrapper_driver_C00D873B(processor);";
		}
	};
    
	public final HLEModuleFunction sceMeWrapper_driver_FEE0A97DFunction = new HLEModuleFunction("sceMeWrapper_driver", "sceMeWrapper_driver_FEE0A97D") {
		@Override
		public final void execute(Processor processor) {
			sceMeWrapper_driver_FEE0A97D(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMeWrapper_driverModule.sceMeWrapper_driver_FEE0A97D(processor);";
		}
	};
    
	public final HLEModuleFunction sceMeWrapper_driver_BAC7C2BEFunction = new HLEModuleFunction("sceMeWrapper_driver", "sceMeWrapper_driver_BAC7C2BE") {
		@Override
		public final void execute(Processor processor) {
			sceMeWrapper_driver_BAC7C2BE(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMeWrapper_driverModule.sceMeWrapper_driver_BAC7C2BE(processor);";
		}
	};
    
	public final HLEModuleFunction sceMeWrapper_driver_39F06790Function = new HLEModuleFunction("sceMeWrapper_driver", "sceMeWrapper_driver_39F06790") {
		@Override
		public final void execute(Processor processor) {
			sceMeWrapper_driver_39F06790(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMeWrapper_driverModule.sceMeWrapper_driver_39F06790(processor);";
		}
	};
    
	public final HLEModuleFunction sceMeMallocFunction = new HLEModuleFunction("sceMeWrapper_driver", "sceMeMalloc") {
		@Override
		public final void execute(Processor processor) {
			sceMeMalloc(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMeWrapper_driverModule.sceMeMalloc(processor);";
		}
	};
    
	public final HLEModuleFunction sceMeCallocFunction = new HLEModuleFunction("sceMeWrapper_driver", "sceMeCalloc") {
		@Override
		public final void execute(Processor processor) {
			sceMeCalloc(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMeWrapper_driverModule.sceMeCalloc(processor);";
		}
	};
    
	public final HLEModuleFunction sceMeFreeFunction = new HLEModuleFunction("sceMeWrapper_driver", "sceMeFree") {
		@Override
		public final void execute(Processor processor) {
			sceMeFree(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMeWrapper_driverModule.sceMeFree(processor);";
		}
	};
    
	public final HLEModuleFunction sceMeWrapper_driver_D1EA3DFDFunction = new HLEModuleFunction("sceMeWrapper_driver", "sceMeWrapper_driver_D1EA3DFD") {
		@Override
		public final void execute(Processor processor) {
			sceMeWrapper_driver_D1EA3DFD(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMeWrapper_driverModule.sceMeWrapper_driver_D1EA3DFD(processor);";
		}
	};
    
	public final HLEModuleFunction sceMeWrapper_driver_3D5F109CFunction = new HLEModuleFunction("sceMeWrapper_driver", "sceMeWrapper_driver_3D5F109C") {
		@Override
		public final void execute(Processor processor) {
			sceMeWrapper_driver_3D5F109C(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMeWrapper_driverModule.sceMeWrapper_driver_3D5F109C(processor);";
		}
	};
    
	public final HLEModuleFunction sceMeRpcLockFunction = new HLEModuleFunction("sceMeWrapper_driver", "sceMeRpcLock") {
		@Override
		public final void execute(Processor processor) {
			sceMeRpcLock(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMeWrapper_driverModule.sceMeRpcLock(processor);";
		}
	};
    
	public final HLEModuleFunction sceMeRpcUnlockFunction = new HLEModuleFunction("sceMeWrapper_driver", "sceMeRpcUnlock") {
		@Override
		public final void execute(Processor processor) {
			sceMeRpcUnlock(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMeWrapper_driverModule.sceMeRpcUnlock(processor);";
		}
	};
    
	public final HLEModuleFunction sceMeEnableFunctionsFunction = new HLEModuleFunction("sceMeWrapper_driver", "sceMeEnableFunctions") {
		@Override
		public final void execute(Processor processor) {
			sceMeEnableFunctions(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMeWrapper_driverModule.sceMeEnableFunctions(processor);";
		}
	};
    
	public final HLEModuleFunction sceMeWrapper_driver_55F11AF6Function = new HLEModuleFunction("sceMeWrapper_driver", "sceMeWrapper_driver_55F11AF6") {
		@Override
		public final void execute(Processor processor) {
			sceMeWrapper_driver_55F11AF6(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMeWrapper_driverModule.sceMeWrapper_driver_55F11AF6(processor);";
		}
	};
    
	public final HLEModuleFunction sceMeWrapper_driver_8EBBBBA3Function = new HLEModuleFunction("sceMeWrapper_driver", "sceMeWrapper_driver_8EBBBBA3") {
		@Override
		public final void execute(Processor processor) {
			sceMeWrapper_driver_8EBBBBA3(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMeWrapper_driverModule.sceMeWrapper_driver_8EBBBBA3(processor);";
		}
	};
    
	public final HLEModuleFunction sceMeWrapper_driver_B6FE3E62Function = new HLEModuleFunction("sceMeWrapper_driver", "sceMeWrapper_driver_B6FE3E62") {
		@Override
		public final void execute(Processor processor) {
			sceMeWrapper_driver_B6FE3E62(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMeWrapper_driverModule.sceMeWrapper_driver_B6FE3E62(processor);";
		}
	};
    
	public final HLEModuleFunction sceMePowerControlAvcPowerFunction = new HLEModuleFunction("sceMeWrapper_driver", "sceMePowerControlAvcPower") {
		@Override
		public final void execute(Processor processor) {
			sceMePowerControlAvcPower(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMeWrapper_driverModule.sceMePowerControlAvcPower(processor);";
		}
	};
    
	public final HLEModuleFunction sceMePowerSelectAvcClockFunction = new HLEModuleFunction("sceMeWrapper_driver", "sceMePowerSelectAvcClock") {
		@Override
		public final void execute(Processor processor) {
			sceMePowerSelectAvcClock(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMeWrapper_driverModule.sceMePowerSelectAvcClock(processor);";
		}
	};
    
};
