package np.urcl.asm;

import java.util.*;
import static np.urcl.asm.BaseISA.*;

public class AssemblerImpl implements Assembler {

	Map<String, Byte> assemblyMap = new HashMap<String, Byte>();
	
	public AssemblerImpl() {
		assemblyMap.put("ADD", ADD);
		assemblyMap.put("SUB", SUB);
		assemblyMap.put("RSH", RSH);
		assemblyMap.put("LSH", LSH);
		assemblyMap.put("INC", INC);
		assemblyMap.put("DEC", DEC);
		assemblyMap.put("XOR", XOR);
		assemblyMap.put("AND", AND);
		assemblyMap.put("OR", OR);
		assemblyMap.put("NOR", NOR);
		assemblyMap.put("NAND", NAND);
		assemblyMap.put("XNOR", XNOR);
		assemblyMap.put("NOT", NOT);
		assemblyMap.put("MOV", MOV);
		assemblyMap.put("IMM", IMM);
		assemblyMap.put("LOAD", LOAD);
		assemblyMap.put("STORE", STORE);
		assemblyMap.put("BRA", BRA);
		assemblyMap.put("BRC", BRC);
		assemblyMap.put("BNC", BNC);
		assemblyMap.put("BRZ", BRZ);
		assemblyMap.put("BNZ", BNZ);
		assemblyMap.put("BRN", BRN);
		assemblyMap.put("BRP", BRP);
		assemblyMap.put("NOP", NOP);
		assemblyMap.put("HLT", HLT);
	}
	
	@Override
	public String[] ProcessImports(String[] source) {
		// TODO Auto-generated method stub
		return source;
	}

	@Override
	public String[] ProcessLabels(String[] source) {
		// TODO Auto-generated method stub
		return source;
	}

	@Override
	public String[] ProcessDefines(String[] source) {
		// TODO Auto-generated method stub
		return source;
	}

	@Override
	public List<Byte> Assemble(String[] source) {
		List<Byte> binary = new ArrayList<>();
		for(String line : source) {
			String[] sections = GetSections(line);
			binary.addAll(Parse(sections));
		}
		return binary;
	}

	private List<Byte> Parse(String[] parts) {
		if(assemblyMap.containsKey(parts[0])) {
			List<Byte> instruction = new ArrayList<>();
			instruction.add((byte) 0x00);
			instruction.add(assemblyMap.get(parts[0]));
			

			for(int i = 1; i < parts.length && instruction.size() <= 5; i++) {
				if(parts[i].charAt(0) == 'R' || parts[i].charAt(0) == '+') {
					instruction.add(Byte.parseByte(parts[i].substring(1)));
				} else {
					instruction.add(Byte.parseByte(parts[i]));
				}
			}
			
			for(int i = instruction.size(); i <= 5; i++) {
				instruction.add((byte) 0x00);
			}
			
			return instruction;
		}
		return Collections.emptyList();
	}

	private String[] GetSections(String line) {
		return line.split("[\\s]+");
	}

}
