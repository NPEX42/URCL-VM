package np.urcl.app;

import np.urcl.io.STDIO;

public class App {
	public void Start() {
		STDIO io = new STDIO();
		String line = io.Readln();
		io.Println(""+Integer.parseInt(line));
	}
}