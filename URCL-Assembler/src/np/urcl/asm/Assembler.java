package np.urcl.asm;
import java.util.*;
public interface Assembler {
	String[] ProcessImports(String[] source);
	String[] ProcessLabels(String[] source);
	String[] ProcessDefines(String[] source);
	List<Byte> Assemble(String[] source);
}
