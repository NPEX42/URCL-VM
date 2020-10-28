package np.urcl.vm.roms;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class ROMLoader {
	public static ROM LoadROM(String filePath) throws CheckSumException, IOException, MalformattedFileException {
		DataInputStream in = new DataInputStream(new FileInputStream(filePath));
		String MAGIC = new String(in.readNBytes(4));
		if(!MAGIC.equals("URCL")) { throw new MalformattedFileException(); }
		byte Maj = in.readByte(), Min = in.readByte();
		int size = (int)in.readChar();
		byte[] prog = in.readNBytes(size);
		int CHECKSUM = in.readInt();
		return new ROM(size, Maj, Min, CHECKSUM, prog);
	}
}
