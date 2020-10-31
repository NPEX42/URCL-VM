package np.urcl.asm.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import np.urcl.asm.api.Assembler;
import np.urcl.asm.api.OpcodeMapper;
/**
 * @author george
 */
public class URCL8_ASM extends Assembler<Byte> {
	Logger logger = Logger.Create(getClass());
	public URCL8_ASM(OpcodeMapper<Byte> opcodes) {
		super(opcodes);
	}

	@Override
	protected List<Byte> AssembleLine(String line) {
		if(!line.startsWith("IMPORT")) {
			List<Byte> bytes = new ArrayList<Byte>();
			String[] parts = line.split("[\\s]+");
			bytes.add((byte)0);
			bytes.add(GetOpcode(line));
			bytes.addAll(ParseNumbersAndPad(parts, 1, 3));
			
			logger.Debug("Parsed Line '",line,"'");
			
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
	protected void ProcessHashDir(String line) {}
}
