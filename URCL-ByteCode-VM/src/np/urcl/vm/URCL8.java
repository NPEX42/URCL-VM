package np.urcl.vm;

import java.util.Arrays;

import np.urcl.vm.roms.ROM;

public class URCL8 {
	private boolean isRunning = true;
	private byte[] reg = new byte[256];
	private byte[] mem = new byte[127];
	
	private boolean CARRY = false;
	
	private int PC = 0;
	
	private ROM rom;
	
	public URCL8(ROM rom) {
		this.rom = rom;
	}

	public void Clock() {
		byte ISA = rom.GetProgramByte(PC++);
		byte opcode = rom.GetProgramByte(PC++);
		byte A = rom.GetProgramByte(PC++);
		byte B = rom.GetProgramByte(PC++);
		byte C = rom.GetProgramByte(PC++);
		
		switch(opcode) {
		case 0x00: break;
		case 0x01: {
			int old = reg[A];
			reg[A] = (byte) (reg[B] + reg[C]);
			CARRY = reg[A] < old;
			break; //ADD REG REG REG
		}
		case 0x02: reg[A] = (byte) (reg[B] + C); break; //ADD REG REG IMM
		case 0x03: reg[A] = (byte) (B + reg[C]); break; //ADD REG IMM REG
		case 0x0B: reg[A] = (byte) (reg[B]++); break; //INC REG
		case 0x26: reg[A] = reg[B]; break; //MOV A B
		case 0x2E: if( CARRY) { PC += A * 5; System.out.println("BRC JUMPED "+A); } break; //BRC A
		case 0x30: if(!CARRY) { PC += A * 5; System.out.println("BNC JUMPED "+A); } break; //BNC A
		case 0x7F: isRunning = false; break;
		}
		
	}
	
	public boolean IsRunning() { return isRunning; }
	
	@Override
	public String toString() {
		return "PC: "+PC+" | "+Dissassemble(PC - 5)+" | "+Arrays.toString(reg);
	}
	
	public String Dissassemble(int index) {
		String asm = "";
		index++;
		switch(rom.GetProgramByte(index++)) {
		case 0x00: asm += "NOP"; break;
		case 0x01: asm += "ADD_REG_REG"; break;
		case 0x02: asm += "ADD_REG_IMM"; break;
		case 0x03: asm += "ADD_IMM_REG"; break;
		case 0x04: asm += "ADD_IMM_IMM"; break;
		case 0x05: asm += "SUB_REG_REG"; break;
		case 0x06: asm += "SUB_REG_IMM"; break;
		case 0x07: asm += "SUB_IMM_REG"; break;
		case 0x08: asm += "SUB_IMM_IMM"; break;
		case 0x09: asm += "RSH_REG"; break;
		case 0x0A: asm += "LSH_REG"; break;
		case 0x0B: asm += "INC_REG_REG"; break;
		case 0x0C: asm += "DEC_REG_REG"; break;
		case 0x26: asm += "MOV_REG_REG"; break;
		case 0x2E: asm += "BRC_IMM"; break;
		case 0x2F: asm += "BRC_REG"; break;
		case 0x30: asm += "BNC_IMM"; break;
		case 0x31: asm += "BNC_REG"; break;
		case 0x7F: asm += "HLT"; break;
		}
		
		asm += " " + rom.GetProgramByte(index++);
		asm += " " + rom.GetProgramByte(index++);
		asm += " " + rom.GetProgramByte(index++);
		
		return asm;
	}
}
