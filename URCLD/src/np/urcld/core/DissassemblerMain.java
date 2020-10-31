package np.urcld.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import np.core.IO;
import np.urcl.asm.core.Dissassembler;
import np.urcl.vm.roms.CheckSumException;
import np.urcl.vm.roms.MalformattedFileException;
import np.urcl.vm.roms.ROM;
import np.urcl.vm.roms.ROMLoader;

public class DissassemblerMain {

	public static void main(String[] args) throws CheckSumException, IOException, MalformattedFileException {
		if(args.length < 2) { System.err.println("Usage: URCLD <rom> <opcodeMap>"); return; }
		ROM rom = ROMLoader.LoadROM(args[0]);
		String[] file = IO.ReadLines(args[1]);
		Map<Byte, String> dasmMap = new HashMap<>();
		for(String line : file) {
			String[] parts = line.split("\\|");
			if(parts.length >= 3) {
				dasmMap.put(Byte.parseByte(parts[2], 16), parts[0]);
			}
		}
		
		System.out.println(Dissassembler.Dissassemble_URCL8(dasmMap, rom.GetProgram(), 0));
	}

}
