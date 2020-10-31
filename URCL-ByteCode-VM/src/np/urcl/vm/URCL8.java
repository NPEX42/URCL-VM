package np.urcl.vm;
/**
 * @author george
 */
import java.util.Arrays;

import np.urcl.vm.roms.ROM;

public class URCL8 {
	private boolean isRunning = true;
	private int[] reg = new int[16];
	private int[] mem = new int[32];
	
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
		
		case 0x01: ADD_REG_REG(A, B, C); break;
		case 0x02: ADD_REG_IMM(A, B, C); break;
		case 0x03: ADD_IMM_REG(A, B, C); break;
		case 0x04: ADD_IMM_IMM(A, B, C); break;
		
		case 0x05: SUB_REG_REG(A, B, C); break;
		case 0x06: SUB_REG_IMM(A, B, C); break;
		case 0x07: SUB_IMM_REG(A, B, C); break;
		case 0x08: SUB_IMM_IMM(A, B, C); break;
		
		case 0x0B: reg[A] = reg[B]++; break; //INC REG
		case 0x26: reg[A] = reg[B]; break; //MOV A B
		case 0x2E: if( CARRY) { PC += A * 5; System.out.println("BRC JUMPED "+A); } break; //BRC A
		case 0x30: if(!CARRY) { PC += A * 5; System.out.println("BNC JUMPED "+A); } break; //BNC A
		case 0x7F: isRunning = false; break;
		}
		
	}
	
	public boolean IsRunning() { return isRunning; }
	
	@Override
	public String toString() {
		return "PC: "+PC+"| CARRY: "+((CARRY) ? 1 : 0)+"\n "+Arrays.toString(reg);
	}
	
	
	
	
	//CORE ISA INSTRUCTIONS
	public void ADD_REG_REG(int A, int B, int C) {
		int result = GetRegister(B) + GetRegister(C);
		UpdateFlags(result);
		SetRegister(A, result);
	}
	
	public void ADD_REG_IMM(int A, int B, int C) {
		int result = GetRegister(B) + C;
		UpdateFlags(result);
		SetRegister(A, result);
	}
	
	public void ADD_IMM_REG(int A, int B, int C) {
		int result = B + GetRegister(C);
		UpdateFlags(result);
		SetRegister(A, result);
	}
	
	public void ADD_IMM_IMM(int A, int B, int C) {
		int result = B + C;
		UpdateFlags(result);
		SetRegister(A, result);
	}
	
	public void SUB_REG_REG(int A, int B, int C) {
		int result = GetRegister(B) + GetRegister(C);
		UpdateFlags(result);
		SetRegister(A, result);
	}
	
	public void SUB_REG_IMM(int A, int B, int C) {
		int result = GetRegister(B) + C;
		UpdateFlags(result);
		SetRegister(A, result);
	}
	
	public void SUB_IMM_REG(int A, int B, int C) {
		int result = B + GetRegister(C);
		UpdateFlags(result);
		SetRegister(A, result);
	}
	
	public void SUB_IMM_IMM(int A, int B, int C) {
		int result = B + C;
		UpdateFlags(result);
		SetRegister(A,result);
	}
	
	public void RSH(int A, int B, int C) {
		int result = SetRegister(A,(GetRegister(A) << 1));
	}
	
	
	public int GetRegister(int index) { 
		if(index == 0) return 0;
		return reg[index]; 
	}
	
	public int SetRegister(int index, int value) {
		return reg[index] = value;
	}
	
	public void SetPC(int address) {
		PC = address * 5;
	}
	
	public void UpdateFlags(int result) {
		CARRY = result > 255;
	}
}
