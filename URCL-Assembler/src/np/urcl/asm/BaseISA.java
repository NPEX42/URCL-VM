package np.urcl.asm;

public class BaseISA {
	public static final byte
	ADD   = 0x01, 
	SUB   = 0x02,
	RSH   = 0x03,
	LSH   = 0x04,
	INC   = 0x05,
	DEC   = 0x06,
	
	XOR   = 0x07,
	AND   = 0x08,
	OR    = 0x09,
	NOR   = 0x0A,
	NAND  = 0x0B,
	XNOR  = 0x0C,
	NOT   = 0x0D,
	
	MOV   = 0x0E,
	IMM   = 0x0F,
	LOAD  = 0x10,
	STORE = 0x11,
	
	BRA   = 0x12,
	BRC   = 0x13,
	BNC   = 0x14,
	BRZ   = 0x15,
	BNZ   = 0x15,
	BRN   = 0x17,
	BRP   = 0x18,
	
	NOP   = 0x00,
	HLT   = (byte) 0xFF;
}
