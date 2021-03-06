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

public class sceMePower_driver implements HLEModule {
	@Override
	public String getName() { return "sceMePower_driver"; }
	
	@Override
	public void installModule(HLEModuleManager mm, int version) {
		if (version >= 150) {
		
			mm.addFunction(sceMePower_driver_55F11AF6Function, 0x55F11AF6);
			mm.addFunction(sceMePower_driver_8EBBBBA3Function, 0x8EBBBBA3);
			mm.addFunction(sceMePower_driver_B6FE3E62Function, 0xB6FE3E62);
			mm.addFunction(sceMePowerControlAvcPowerFunction, 0x05B2C420);
			mm.addFunction(sceMePowerSelectAvcClockFunction, 0x579ACEA9);
			
		}
	}
	
	@Override
	public void uninstallModule(HLEModuleManager mm, int version) {
		if (version >= 150) {
		
			mm.removeFunction(sceMePower_driver_55F11AF6Function);
			mm.removeFunction(sceMePower_driver_8EBBBBA3Function);
			mm.removeFunction(sceMePower_driver_B6FE3E62Function);
			mm.removeFunction(sceMePowerControlAvcPowerFunction);
			mm.removeFunction(sceMePowerSelectAvcClockFunction);
			
		}
	}
	
	
	public void sceMePower_driver_55F11AF6(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMePower_driver_55F11AF6 [0x55F11AF6]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceMePower_driver_8EBBBBA3(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMePower_driver_8EBBBBA3 [0x8EBBBBA3]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceMePower_driver_B6FE3E62(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceMePower_driver_B6FE3E62 [0xB6FE3E62]");

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
    
	public final HLEModuleFunction sceMePower_driver_55F11AF6Function = new HLEModuleFunction("sceMePower_driver", "sceMePower_driver_55F11AF6") {
		@Override
		public final void execute(Processor processor) {
			sceMePower_driver_55F11AF6(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMePower_driverModule.sceMePower_driver_55F11AF6(processor);";
		}
	};
    
	public final HLEModuleFunction sceMePower_driver_8EBBBBA3Function = new HLEModuleFunction("sceMePower_driver", "sceMePower_driver_8EBBBBA3") {
		@Override
		public final void execute(Processor processor) {
			sceMePower_driver_8EBBBBA3(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMePower_driverModule.sceMePower_driver_8EBBBBA3(processor);";
		}
	};
    
	public final HLEModuleFunction sceMePower_driver_B6FE3E62Function = new HLEModuleFunction("sceMePower_driver", "sceMePower_driver_B6FE3E62") {
		@Override
		public final void execute(Processor processor) {
			sceMePower_driver_B6FE3E62(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMePower_driverModule.sceMePower_driver_B6FE3E62(processor);";
		}
	};
    
	public final HLEModuleFunction sceMePowerControlAvcPowerFunction = new HLEModuleFunction("sceMePower_driver", "sceMePowerControlAvcPower") {
		@Override
		public final void execute(Processor processor) {
			sceMePowerControlAvcPower(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMePower_driverModule.sceMePowerControlAvcPower(processor);";
		}
	};
    
	public final HLEModuleFunction sceMePowerSelectAvcClockFunction = new HLEModuleFunction("sceMePower_driver", "sceMePowerSelectAvcClock") {
		@Override
		public final void execute(Processor processor) {
			sceMePowerSelectAvcClock(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceMePower_driverModule.sceMePowerSelectAvcClock(processor);";
		}
	};
    
};
