package np.urcl.asm.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import np.urcl.asm.Assembler;
import np.urcl.asm.AssemblerImpl;

class AssemblerTest {
	
	String[] source = {
			"INC R1 R0",
			"MOV R2 R0",
			"ADD R2 R1 R2",
			"BRC +3",
			"ADD R1 R1 R2",
			"BNC -3",
			"HLT"
	};
	List<Byte> expectedRom = Arrays.asList(
			(byte) 0x02, (byte) 0x10, (byte) 0x01, (byte) 0x00, (byte) 0x00,
			(byte) 0x03, (byte) 0x10, (byte) 0x02, (byte) 0x00, (byte) 0x00,
			(byte) 0x04, (byte) 0x10, (byte) 0x02, (byte) 0x01, (byte) 0x02,
			(byte) 0x10, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x00,
			(byte) 0x04, (byte) 0x10, (byte) 0x01, (byte) 0x01, (byte) 0x02,
			(byte) 0x11, (byte) 0x00, (byte) 0x3D, (byte) 0x00, (byte) 0x00,
			(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF
	);
	
	@Test
	void testAssemble() {
		Assembler asm = new AssemblerImpl();
		List<Byte> rom = asm.Assemble(source);
		assertEquals(expectedRom, rom, "Error! Roms Dont Match! \n"+"Expected: "+expectedRom+"\n"+" Got: "+rom);
	}

}
