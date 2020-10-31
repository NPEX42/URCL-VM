package np.urcl.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * @author george
 */
public class FILEIO implements IO {

	public static FILEIO OpenFile(String file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
			return new FILEIO(reader, writer);
		} catch (IOException ioex) {
			return null;
		}
	}
	
	private FILEIO(BufferedReader reader, BufferedWriter writer) {
		this.reader = reader;
		this.writer = writer;
	}
	
	private BufferedReader reader;
	private BufferedWriter writer;
	@Override
	public void Println(String msg) {
		try {
			writer.write(msg);
			writer.newLine();
		} catch (IOException e) {}
	}

	@Override
	public void Print(String msg) {
		try {
			writer.write(msg);
		} catch (IOException e) {}
	}

	@Override
	public String Readln() {
		try {
			return reader.readLine();
		} catch (IOException ioex) {
			return "";
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
