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
import java.util.Collections;
import java.util.List;

import np.core.IO;
import np.urcl.asm.api.Assembler;
import np.urcl.asm.core.Logger;
import np.urcl.asm.core.RegexOpcodeMapper;
import np.urcl.vm.URCL8;
import np.urcl.vm.roms.ROM;
import np.urcl.vm.roms.ROMLoader;


public class App {
	public void Start(String[] args) throws Exception {
		if(args.length < 2) {
			System.err.println("Usage: URCLA <Input> <Output>");
			return;
		}
		Logger.EnableInfo();
		Logger logger = Logger.Create(App.class);
		logger.Log("Loaded File '",args[0],"'");
		
		RegexOpcodeMapper<Byte> mapper = RegexOpcodeMapper.CreateByteMapperFromStrings(IO.ReadLines("asm/urcl8.txt"), "\\|");
		String[] source = IO.ReadLines(args[0]);
		
		Assembler<Byte> urcl = new Assembler<Byte>(mapper) {

			@Override
			protected List<Byte> AssembleLine(String line) {
				if(!line.startsWith("IMPORT")) {
					List<Byte> bytes = new ArrayList<Byte>();
					String[] parts = line.split("[\\s]+");
					bytes.add((byte)0);
					bytes.add(GetOpcode(line));
					bytes.addAll(ParseNumbersAndPad(parts, 1, 3));
					return bytes; 
				} else {
					String[] libs = line.split("[\\s]+");
					for(int i = 1; i < libs.length; i++) {
						if(!IncludeFile(libs[i])) {
							logger.Log("Unable To Locate Library '",libs[i],"'");
							break; 
						}
					}
					return Collections.emptyList();
				}
			}

			@Override
			protected Byte UserParseValue(char first, char last, String value) {
				if(first != '-') {
					return Byte.parseByte(value.substring(1)); 
				} else {
					return Byte.parseByte(value);
				}
			}

			@Override
			protected String[] GetArgs(String line) {
				return line.split("[\\s]+");
			}

			@Override
			protected void ProcessHashDir(String line) {
				// TODO Auto-generated method stub
				
			}
		};
		
		List<Byte> binary = urcl.Assemble(source);
		
		logger.Log("Binary: ",binary.toString());
		
		DataOutputStream stream = new DataOutputStream(new FileOutputStream(args[1]));
		//Write Header Info
		stream.write("URCL".getBytes());
		stream.write((byte) 1);
		stream.write(0);
		stream.writeChar(binary.size());
		//Write Program
		int PRIME1 = 54139;
		int PRIME2 = 22817;
		int SUM = 0;
		for(byte b : binary) {
			stream.writeByte(b);
			SUM += ((b * PRIME1) % PRIME2);
		}
		//Write Program Checksum
		stream.writeInt(SUM);
		stream.close();
		
		ROM rom = ROMLoader.LoadROM(args[1]);
		logger.Log(rom);
	
		URCL8 vm = new URCL8(rom);
		while(vm.IsRunning()) {
			vm.Clock();
			logger.Log(vm);
		}
	}
}
