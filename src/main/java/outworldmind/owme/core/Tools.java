package outworldmind.owme.core;

public class Tools {
	
	private static final Tools INSTANCE = new Tools();
	private EventsManager events;
	private Logger logger;
	
	private Tools() {
		events = new EventsManager();
		logger = new Logger(Logger.CONSOLE_LOG_MODE);
	}
	
	public static Logger getLogger() {
		return INSTANCE.logger;
	}
	
	public static EventsManager getEvents() {
		return INSTANCE.events;
	}

}
