package np.urcl.asm.core;

public class Logger {
	
	protected final String hostClassCallerName;
	
	private static boolean debug, info, fatal = true, error;
	
	public void Log(Object... items) {
		if(!info) return;
		System.out.print("["+hostClassCallerName+"/Info] ");
		for(Object obj : items) System.out.print(obj);
		System.out.println();
	}
	
	public void Err(Object... items) {
		if(!error) return;
		System.out.print("["+hostClassCallerName+"/Error] ");
		for(Object obj : items) System.out.print(obj);
		System.out.println();
	}
	
	public void Debug(Object... items) {
		if(!debug) return;
		System.out.print("["+hostClassCallerName+"/Debug] ");
		for(Object obj : items) System.out.print(obj);
		System.out.println();
	}
	
	public void Fatal(int statusCode, Object... items) {
		if(!fatal) return;
		System.out.print("["+hostClassCallerName+"/Fatal] ");
		for(Object obj : items) System.out.print(obj);
		System.out.println();
		System.exit(statusCode);
	}
	
	private Logger(String host) {
		this.hostClassCallerName = host;
	}
	
	public static Logger Create(Class<?> clazz) {
		return new Logger(clazz.getName());
	}
	
	public static void EnableDebug() { debug = true; }
	public static void EnableInfo() { info = true; }
}
