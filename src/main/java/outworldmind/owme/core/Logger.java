package outworldmind.owme.core;

import java.util.Date;

public class Logger {
	
	public static final int NO_LOG_MODE = 0;
	public static final int CONSOLE_LOG_MODE = 1;
	public static final int RECORD_LOG_MODE = 2;
	public static final int CONSOLE_AND_RECORD_LOG_MODE = 3;
	
	private int mode = NO_LOG_MODE;
	private String loggs;
	
	Logger(int logMode) {
		mode = logMode;
	}
	
	public void log(String text) {
		if (mode == NO_LOG_MODE) return;
		if (mode == CONSOLE_LOG_MODE || mode == CONSOLE_AND_RECORD_LOG_MODE)
			Console.log(text);
		if (mode == RECORD_LOG_MODE || mode == CONSOLE_AND_RECORD_LOG_MODE)
			loggs += "/n" + new Date() + ": " + text;
	}
	
	public String getLoggs() {
		return loggs;
	}

}
