package np.urcl.vm.roms;

public class CheckSumException extends Exception {
	public CheckSumException(int sum, int expectedCheckSum) {
		super("Checksums Dont Match: Calculated "+sum+" - Excepted "+expectedCheckSum);
	}

	private static final long serialVersionUID = 1L;

}
