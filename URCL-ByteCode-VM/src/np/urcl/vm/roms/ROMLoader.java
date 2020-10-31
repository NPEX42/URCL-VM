package np.urcl.vm.roms;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * @author george
 */
public class ROMLoader {
	
	private static int PRIME1 = 54139;
	private static int PRIME2 = 22817;
	
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
	
	public static ROM LoadRom(byte[] romData) throws CheckSumException, IOException, MalformattedFileException {
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(romData));
		String MAGIC = new String(in.readNBytes(4));
		if(!MAGIC.equals("URCL")) { throw new MalformattedFileException(); }
		byte Maj = in.readByte(), Min = in.readByte();
		int size = (int)in.readChar();
		byte[] prog = in.readNBytes(size);
		int CHECKSUM = in.readInt();
		return new ROM(size, Maj, Min, CHECKSUM, prog);
	}
	
	public static ROM LoadRom(List<Byte> rom) throws CheckSumException, IOException, MalformattedFileException {
		byte[] data = new byte[rom.size()];
		for(int i = 0; i < data.length; i++) {
			data[i] = rom.get(i);
		}
		
		
		
		return LoadRom(data);
	}

	public static ROM LoadRRG(List<Byte> binary) {
		List<Byte> header = new ArrayList<>();
		int size = binary.size();
		//Add Magic Number
		byte[] magic = "URCL".getBytes();
		for(byte b : magic) {
			header.add(b);
		}
		
		//Add Version
		header.add((byte)1);
		header.add((byte)0);
		
		//Add Program Size
		header.add((byte) ((size & 0xff00) >> 8));
		header.add((byte) ((size & 0x00ff) >> 0));
		
		//Add Program
		header.addAll(binary);
		
		//Calculate Checksum
		int CHECK = 0;
		for(byte b : binary) {
			CHECK += ((b * PRIME1) % PRIME2);
		}
		
		header.add((byte) ((CHECK & 0xff000000) >> 24));
		header.add((byte) ((CHECK & 0x00ff0000) >> 16));
		header.add((byte) ((CHECK & 0x0000ff00) >>  8));
		header.add((byte) ((CHECK & 0x000000ff) >>  0));
		
		try {
			return ROMLoader.LoadRom(header);
		} catch (Exception e) {
			return null;
		} 
	}
}
