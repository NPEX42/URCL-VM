package np.urcl.vm.roms;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
/**
 * @author george
 */
public class ROM {
	private int size;
	private int versionMaj, versionMin;
	private byte[] program;
	private final int CHECKSUM;
	private static int PRIME1 = 54139;
	private static int PRIME2 = 22817;
	public ROM(int size, int versionMaj, int versionMin, int expectedCheckSum, byte[] program) throws CheckSumException {
		super();
		this.size = size;
		this.versionMaj = versionMaj;
		this.versionMin = versionMin;
		this.program = program;
		
		
		
		int CHECK = 0;
		for(byte b : program) {
			CHECK += ((b * PRIME1) % PRIME2);
		}
		CHECKSUM = CHECK;
		
		if(CHECKSUM != expectedCheckSum) {
			throw new CheckSumException(CHECKSUM, expectedCheckSum);
		}
	}
	
	public byte GetProgramByte(int index) {
		if(index < 0) index = 0;
		index %= size;
		return program[index];
	}
	
	@Override
	public String toString() {
		String out = "ROM V"+versionMaj+"."+versionMin+",\n"
					+"Size: "+size+",\n"
					+"Checksum: "+CHECKSUM;
		
		return out;
	}
	
	
	public static void Save(String filePath, List<Byte> program) throws IOException {
		DataOutputStream stream = new DataOutputStream(new FileOutputStream(filePath));
		stream.writeBytes("URCL");
		stream.writeByte(1);
		stream.writeByte(0);
		stream.writeChar(program.size());
		int CHECK = 0;
		for(byte b : program) {
			CHECK += ((b * PRIME1) % PRIME2);
			stream.write(b);
		}
		stream.writeInt(CHECK);
	}

	public byte[] GetProgram() {
		return program;
	}
	
	
}
