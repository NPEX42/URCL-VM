package np.urcl.app;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import np.core.IO;
import np.urcl.asm.api.Assembler;
import np.urcl.asm.core.Logger;
import np.urcl.asm.core.RegexOpcodeMapper;
import np.urcl.asm.core.URCL8_ASM;
import np.urcl.vm.URCL8;
import np.urcl.vm.roms.ROM;
import np.urcl.vm.roms.ROMLoader;


public class App {
	public void Start(String[] args) throws Exception {
		if(args.length < 3) {
			System.err.println("Usage: URCLA <Input> <Output> <Log Mode>");
			return;
		}
		if(args[2].contains("i"))Logger.EnableInfo();
		if(args[2].contains("d"))Logger.EnableDebug();
		Logger logger = Logger.Create(App.class);
		logger.Log("Loaded File '",args[0],"'");
		
		RegexOpcodeMapper<Byte> mapper = RegexOpcodeMapper.CreateByteMapperFromStrings(IO.ReadLines("asm/urcl8.txt"), "\\|");
		String[] source = IO.ReadLines(args[0]);
		
		Assembler<Byte> urcl = new URCL8_ASM(mapper);
		
		List<Byte> binary = urcl.Assemble(source);

		ROM.Save(args[1], binary);
	}
}
