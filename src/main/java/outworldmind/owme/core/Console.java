package outworldmind.owme.core;

public class Console {
	
	public static final int NORMAL_MODE = 0;
	public static final int ERROR_MODE = 1;
	
	private static int mode = NORMAL_MODE;
	
	public static void setMode(int modeType) {
		mode = modeType;
	}
	
	public static void reset() {
		mode = NORMAL_MODE;
	}
	
	public static void log(String message) {
		if (mode == ERROR_MODE)
			System.err.println(message);
		else
			System.out.println(message);		
	}
	
	public static void logErr(String message) {
		setMode(ERROR_MODE);
		log(message);
		reset();
	}
	
	public static void log(Object object) {
		log(object.toString());
	}

}
