package np.urcl.asm.api;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author george
 * @param <T>
 */
public abstract class Assembler<T extends Number> {
	private HashMap<String, Number> defines = new HashMap<>();
	private final OpcodeMapper<T> opcodes;
	protected String[] sourceText;
	public List<T> Assemble(String... source) {
		sourceText = source;
		ScanForHashDirs();
		
		List<T> binary = new ArrayList<T>();
		for(String line : source) {
			binary.addAll(AssembleLine(line));
		}
		return binary;
	}
	
	private void ScanForHashDirs() {
		for(String line : sourceText) {
			if(line.trim().startsWith("#")) ProcessHashDir(line.trim());
		}
	}

	protected abstract List<T> AssembleLine(String line);
	
	protected T ParseNumber(String value) {
		char first, last;
		first = value.charAt(0);
		last = value.charAt(value.length()-1);
		return UserParseValue(first, last, value);
	}
	
	protected List<T> ParseNumbers(String[] values, int offset) {
		List<T> numbers = new ArrayList<T>();
		for(int i = offset; i < values.length; i++) {
			numbers.add(ParseNumber(values[i]));
		}
		return numbers;
	}
	
	protected List<T> ParseNumbersAndPad(String[] values, int offset, int target) {
		List<T> numbers = new ArrayList<T>();
		for(int i = offset; i < values.length && i <= target; i++) {
			numbers.add(ParseNumber(values[i]));
		}
		
		while(numbers.size() < target) {
			numbers.add((T)new Byte((byte) 0));
		}
		
		return numbers;
	}
	
	protected abstract T UserParseValue(char first, char last, String value);
	protected abstract String[] GetArgs(String line);
	protected abstract void ProcessHashDir(String line);
	
	public Assembler(OpcodeMapper<T> opcodes) {
		this.opcodes = opcodes;
	}
	
	protected synchronized void AddDefine(String name, Number value) {
		defines.put(name, value);
	}
	
	public synchronized Number GetDefine(String name) {
		return defines.getOrDefault(name, 0);
	}
	
	protected T GetOpcode(String line) {
		return opcodes.GetOpcode(line);
	}
	
	public synchronized boolean IncludeFile(String filePath) {
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			StringBuffer buffer = new StringBuffer();
			while((line = reader.readLine()) != null) {
				buffer.append(line).append('\n');
			}
			
			String[] include = buffer.toString().split("\n");
			
			sourceText = Concatenate(sourceText, include);
			
			return true;
		} catch (IOException e) {
			return false;
		}
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
}