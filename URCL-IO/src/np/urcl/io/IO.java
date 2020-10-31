package np.urcl.io;
/**
 * @author george
 */
public interface IO {
	public void Println (String msg);
	public void Print   (String msg);
	public String Readln(          );
	
	public String   ReadUntilEOF();
	public String[] ReadLines();
	
	public char[] ReadUnsignedShorts();
}
