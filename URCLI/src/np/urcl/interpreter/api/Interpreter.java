package np.urcl.interpreter.api;

import java.lang.reflect.Array;
import java.util.HashMap;

import np.core.IO;

public abstract class Interpreter {
	public static boolean DEBUG = true;
	private HashMap<String, Integer> labels = new HashMap<>();
	private int[] registers, memory;
	protected int maxValue = Integer.MAX_VALUE;
	protected int PC, SP;
	protected String[] prog;
	protected boolean CARRY, ZERO, NEGATIVE;
	
	public static final String 
	REGISTER = "[\\$R][0-9]+",
	IMMEDIATE = "[+-]?[0-9]+",
	WS = "[\\s]+",
	LABEL = "[\\.][A-z0-9:_-]+";
	
	protected Interpreter(int minReg, int minMemory, int minBits) {
		registers = new int[minReg];
		memory = new int[minMemory];
		maxValue = (int) Math.pow(2, minBits);
	}
	
	protected int Load(int address) {
		return memory[address] % maxValue;
	}
	
	protected int Store(int address, int value) {
		return (memory[address] = value % maxValue);
	}
	
	protected int SetReg(int reg, int value) {
		if(reg > GetRegCount()) return 0;
		if(reg < 0) return 0;
		return (registers[reg] = value % maxValue);
	}
	
	protected int GetReg(int reg) {
		
		if(reg > GetRegCount()) return 0;
		if(reg < 0) return 0;
		
		if(reg == 0) {
			return 0;
		}
		
		return registers[reg] % maxValue;
	}
	
	protected int SetReg(String id, int value) {
		
		return SetReg(ParseInt(id), value);
	}
	
	protected int GetReg(String id) {
		
		return GetReg(ParseInt(id));
	}
	
	protected int Push(int value) {
		return (memory[SP++] = value);
	}
	
	protected int Pop(int value) {
		return (memory[--SP]);
	}
	
	protected int GetLabel(String label) {
		return labels.getOrDefault(label, 0);
	}
	
	protected int SetLabel(String label, int value) {
		labels.put(label, value);
		return value;
	}
	
	protected void ScanLabels() {
		int index = 0;
		for(String line : prog) {
			if(line.matches(LABEL) && !labels.containsKey(line)) {
				labels.put(line, index++);
			}
		}
	}
	
	public void RunSync() {
		while(Exec(prog[PC],prog[PC].split("[\\s]+"))) { PC++; }
	}
	
	public int UpdateFlags(int result) {
		if(DEBUG) System.out.println("Result Of Calc: "+result);
		CARRY = (result > maxValue - 1);
		ZERO  = (result % maxValue == 0);
		NEGATIVE = (result < 0);
		return result;
	}
	
	protected int ParseInt(String value) {
		char type = value.charAt(0);
		String v = value.substring(1);
		
		switch(type) {
		case '$':
		case 'R':
			return Integer.parseInt(v);
		
		case '+':
		case '-':
			return Integer.parseInt(value);
			
		case '.':
			return GetLabel(value);
			
		default:
			return Integer.parseInt(value);
		}
	} 
	
	protected String[] ImportLibrary(String path) {
		String[] include = IO.ReadLines(path);
		prog = Concatenate(prog, include);
		ScanLabels();
		return prog;
	}
	
	public <A> A[] Concatenate(A[] a, A[] b) {
	    int aLen = a.length;
	    int bLen = b.length;

	    @SuppressWarnings("unchecked")
	    A[] c = (A[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
	    System.arraycopy(a, 0, c, 0, aLen);
	    System.arraycopy(b, 0, c, aLen, bLen);

	    return c;
	}
	
	public int GetRegCount() { return registers.length; }
	
	public abstract boolean Exec(String line, String[] parts);
}
