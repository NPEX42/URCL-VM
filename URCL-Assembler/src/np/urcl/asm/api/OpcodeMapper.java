package np.urcl.asm.api;

public interface OpcodeMapper<T extends Number> {
	public T GetOpcode(String line);
}
