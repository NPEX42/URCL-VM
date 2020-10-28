package np.urcl.asm.core;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import np.urcl.asm.api.OpcodeMapper;

public class RegexOpcodeMapper<T extends Number> implements OpcodeMapper<T> {
	private static Logger logger = Logger.Create(RegexOpcodeMapper.class);
	private Map<String, T> regexes = new HashMap<>();
	private Map<String, String> defines = new HashMap<>();
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public T GetOpcode(String line) {
		for(String regex : regexes.keySet()) {
			if(line.matches(regex)) {
				logger.Log("Matched '",line,"' With '",regex,"'");
				return regexes.get(regex); 
			}
		}
		logger.Log("FOUND NO MATCH FOR '",line,"'");
		return (T) new Byte((byte) 0x00);
	}
	
	private void AddEntry(String key, T value) {
		regexes.put(key, value);
	} 
	
	private RegexOpcodeMapper() {}
	
	//Format: MNEM|REGEX|OPCODE
	public static RegexOpcodeMapper<Byte> CreateByteMapperFromStrings(String[] text, String split) {
		RegexOpcodeMapper<Byte> mapper = new RegexOpcodeMapper<>();
		int malformattedCount = 0;
		
		
		mapper = ScanForDefines(mapper, text);
		
		for(String line : text) {
			if(line.isEmpty() || line.startsWith("#")) continue;
			
			
			
			String[] sections = line.split(split);
			
			
			
			if(sections.length >= 3) {
				
				
				for(String key : mapper.defines.keySet()) {
					Pattern pattern = Pattern.compile("&{1}[A-z._-]+");
					Matcher matcher = pattern.matcher(sections[1]);
					while(matcher.find()) {
						String match = matcher.group();
						
						logger.Debug("Found Match to regex: ",match);
						String define = match.substring(1);
						
						logger.Debug("Does Defines Contain '",define,"'? ",mapper.defines.containsKey(define));
						
						sections[1] = sections[1].replaceFirst(
								match,
								mapper.defines.getOrDefault(define,""));
					}
				} 
				
				mapper.AddEntry(sections[1], Byte.parseByte(sections[2],16));
				logger.Log("Added Instruction '",sections[0],"', Regex: ",sections[1],", Opcode: ",sections[2]);
			} else {
				logger.Log("Malformatted Line: ",line);
				malformattedCount++;
			}
		}
		logger.Log(
				"Ran through ",text.length," Lines, ",
				"Loaded ",mapper.Size()," Instructions, ",
				malformattedCount," were Malformatted (",((float)malformattedCount/(float)text.length)*100,"%)...");
		return mapper;
	}

	private static RegexOpcodeMapper<Byte> ScanForDefines(RegexOpcodeMapper<Byte> mapper, String[] text) {
		
		for(String line : text) {
			if(line.startsWith("#")) {
					String[] sections = line.split("[\\s]+");
					switch(sections[0]) {
					case "#DEF":
						{ 
							logger.Debug("Found #Define...");
							String key = sections[1];
							String value = sections[2];
							mapper.defines.put(key,value);
						}
						continue;
					}
				}
			}
		
		return mapper;
	}

	private int Size() {
		return regexes.size();
	}
}
