package np.urcl.asm.api;

/**
 * @author george
 * @param <T>
 */
public interface OpcodeMapper<T extends Number> {
	public T GetOpcode(String line);
}
