package np.urcl.urcl8;

import java.io.IOException;

import np.urcl.vm.URCL8;
import np.urcl.vm.roms.CheckSumException;
import np.urcl.vm.roms.MalformattedFileException;
import np.urcl.vm.roms.ROM;
import np.urcl.vm.roms.ROMLoader;

public class Main {

	public static void main(String[] args) throws CheckSumException, IOException, MalformattedFileException {
		if(args.length < 1) {
			System.err.println("Usage: URCL8 <rom>");
			return;
		}
		
		ROM rom = ROMLoader.LoadROM(args[0]);
		URCL8 vm = new URCL8(rom);
		while(vm.IsRunning()) {
			vm.Clock();
			System.out.println(vm);
		}
	}

}
