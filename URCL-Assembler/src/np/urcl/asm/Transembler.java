package np.urcl.asm;

import java.util.List;

public interface Transembler {
	String[] ProcessImports(String[] source);
	String[] ProcessLabels(String[] source);
	String[] ProcessDefines(String[] source);
	List<String> Assemble(String[] source);
}
