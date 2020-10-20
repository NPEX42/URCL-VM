package np.urcl.app;

import java.util.List;

import np.core.IO;
import np.urcl.asm.Assembler;
import np.urcl.asm.AssemblerImpl;

public class App {
	public void Start(String[] args) {
		if(args.length < 2) return;
		String[] source = IO.ReadLines(args[0]);
		Assembler asm = new AssemblerImpl();
		System.out.println("[URCLA/Info] Assembling Source...");
		List<Byte> binary = asm.Assemble(source);
		byte[] data = new byte[binary.size()];
		
		System.out.println("[URCLA/Info] Saving Binary...");
		for(int i = 0; i < data.length; i++) {
			data[i] = binary.get(i);
		}
		
		IO.SaveBytes(args[1], data);
	}
}
