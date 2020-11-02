package np.urcl.interpreter.core;

import np.urcl.interpreter.api.Interpreter;

public class URCL extends Interpreter {
	
	private URCL(String[] program) {
		super(256, 65536, 31);
		prog = program;
	}
	
	private URCL(URCL.Header header, String[] source) {
		super(header.minReg, header.minMem, header.bits);
		prog = source;
	}
	
	public static URCL BuildInterpreter(String[] source) {
		URCL.Header header = LoadHeader(source);
		return new URCL(header, source);
	}
	
	private static URCL.Header LoadHeader(String[] source) {
		int minReg = 256, minMem = 65536, bits = 31;
		for(String line : source) {
			line = line.trim();
			String[] parts = line.split(WS);
			switch(parts[0]) {
			case "BITS": bits = Integer.parseInt(parts[parts.length - 1]); break;
			case "MINREG": minReg = Integer.parseInt(parts[parts.length - 1]); break;
			case "MINMEM": minMem = Integer.parseInt(parts[parts.length - 1]); break;
			}
		}
		
		return new URCL.Header(minReg, minMem, bits);
	}
	
	public static class Header {
		public final int minReg, minMem, bits;

		public Header(int minReg, int minMem, int bits) {
			super();
			this.minReg = minReg;
			this.minMem = minMem;
			this.bits = bits;
		}
		
	}
	
	@Override
	public boolean Exec(String line, String[] parts) {
		if(DEBUG) System.out.println(line
				+ " | " + GetReg(1)
				+ " | CARRY: " + CARRY
				+ " | NEGATIVE: " + NEGATIVE
				+ " | ZERO: " + ZERO
				+ " | MaxValue: " +  maxValue
				+ " | Register Count " + GetRegCount()
		);
		
		
		if(parts[0].equals("IMPORT")) {
			for(int i = 1; i < parts.length; i++) {
				ImportLibrary(parts[i]);
			}
			return true;
		}
		
			   if(line.matches("ADD" + WS + REGISTER + WS + REGISTER + WS + REGISTER)) {
			int result = UpdateFlags( GetReg(parts[2]) + GetReg(parts[3]) );
			SetReg(parts[1], result);
		} else if(line.matches("ADD" + WS + REGISTER + WS + IMMEDIATE + WS + REGISTER)) {
			int result = UpdateFlags( ParseInt(parts[2]) + GetReg(parts[3]) );
			SetReg(parts[1], result);
		} else if(line.matches("ADD" + WS + REGISTER + WS + REGISTER + WS + IMMEDIATE)) {
			int result = UpdateFlags( GetReg(parts[2]) + ParseInt(parts[3]) );
			SetReg(parts[1], result);
		} else if(line.matches("ADD" + WS + REGISTER + WS + IMMEDIATE + WS + IMMEDIATE)) {
			int result = UpdateFlags( ParseInt(parts[2]) + ParseInt(parts[3]) );
			SetReg(parts[1], result);
			
			
		} else if(line.matches("SUB" + WS + REGISTER + WS + REGISTER + WS + REGISTER)) {
			int result = UpdateFlags( GetReg(parts[2]) - GetReg(parts[3]) );
			SetReg(parts[1], result);
		} else if(line.matches("SUB" + WS + REGISTER + WS + IMMEDIATE + WS + REGISTER)) {
			int result = UpdateFlags( ParseInt(parts[2]) - GetReg(parts[3]) );
			SetReg(parts[1], result);
		} else if(line.matches("SUB" + WS + REGISTER + WS + REGISTER + WS + IMMEDIATE)) {
			int result = UpdateFlags( GetReg(parts[2]) - ParseInt(parts[3]) );
			SetReg(parts[1], result);
		} else if(line.matches("SUB" + WS + REGISTER + WS + IMMEDIATE + WS + IMMEDIATE)) {
			int result = UpdateFlags( ParseInt(parts[2]) - ParseInt(parts[3]) );
			SetReg(parts[1], result);
			
			
		} else if(line.matches("IMM" + WS + )) {
			
		} else if(line.matches("OUT" + WS + IMMEDIATE + WS + REGISTER)) {
			       if(ParseInt(parts[1]) == 0) {
				System.out.print(GetReg(parts[2]));
			} else if (ParseInt(parts[1]) == 1) {
				System.out.print( (char) GetReg(parts[2]));
			}
		} else if(line.matches("HLT")) {
			return false;
		}
		
		return true;
	}
	
	
	
	

}
