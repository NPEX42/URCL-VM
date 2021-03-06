package np.urcl.interpreter.core;

public class URCLI {

	public static void main(String[] args) {
		URCL.DEBUG = false;
		String[] source = {
				"BITS 16",
				"MINREG 16",
				"MINMEM 256",
				".STDOUT 0",
				"ADD R1 65 0",
				"OUT .STDOUT R1",
				"OUT 1 R1",
				"HLT"
		};
		URCL vm = URCL.BuildInterpreter(source);
		vm.RunSync();
	}

}
