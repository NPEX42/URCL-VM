package np.urcl.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * @author george
 */
public class STDIO implements IO {

	@Override
	public void Println(String msg) {
		System.out.println(msg);
	}

	@Override
	public void Print(String msg) {
		System.out.print(msg);
	}

	@Override
	public String Readln() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			return reader.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public String ReadUntilEOF() {
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while((line = Readln()) != null) {
			buffer.append(line).append('\n');
		}
		return buffer.toString();
	}

	@Override
	public String[] ReadLines() {
		return ReadUntilEOF().split("\n");
	}

	@Override
	public char[] ReadUnsignedShorts() {
		return ReadUntilEOF().toCharArray();
	}
	
	
	
	

}
