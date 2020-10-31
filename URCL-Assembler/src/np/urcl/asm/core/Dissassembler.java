package np.urcl.asm.core;

import java.util.Map;
/**
 * @author george
 */
public class Dissassembler {
	
	public static String Dissassemble_URCL8(Map<Byte, String> asmMap, byte[] rom, int offset) {
		String asm = "";
		int line = 0;
		for(int i = offset; i < rom.length; i += 5) {
			asm += line++;
			asm += " " + rom[i + 0];
			asm += " " + asmMap.getOrDefault((rom[i + 1]), "????");
			asm += " " + rom[i + 2];
			asm += " " + rom[i + 3];
			asm += " " + rom[i + 4];
			asm += "\n";
		}
		return asm;
	}
	
	private static String GetMnemonic(byte opcode) {
		switch(opcode) {
		case 0x00: return "NOP";
		case 0x01: return "ADD_REG_REG";
		case 0x02: return "ADD_REG_IMM";
		case 0x03: return "ADD_IMM_REG";
		case 0x04: return "ADD_IMM_IMM";
		case 0x05: return "SUB_REG_REG";
		case 0x06: return "SUB_REG_IMM";
		case 0x07: return "SUB_IMM_REG";
		case 0x08: return "SUB_IMM_IMM";
		case 0x09: return "RSH_REG";
		case 0x0A: return "LSH_REG";
		case 0x0B: return "INC_REG_REG";
		case 0x0C: return "DEC_REG_REG";
		case 0x26: return "MOV_REG_REG";
		case 0x2E: return "BRC_IMM";
		case 0x2F: return "BRC_REG";
		case 0x30: return "BNC_IMM";
		case 0x31: return "BNC_REG";
		case 0x7F: return "HLT";
		
		default:
			return "??? ("+opcode+")";
		}
	}
}
