package outworldmind.owme.core;

public class Tools {
	
	static final Tools INSTANCE = new Tools();
	private static boolean initialized = false;
	private Controls controls;
	private EventsManager events;
	private Logger logger;
	private GC gc;
	
	private Tools() {}
	
	protected static void init(Config config) {
		if (initialized) {
			Console.logErr(Tools.class.getSimpleName() + " multiple initialization");
			return;
		}

		Tools.INSTANCE.events = new EventsManager();
		Tools.INSTANCE.logger = new Logger(config);
		Tools.INSTANCE.gc = new GC();
		Tools.INSTANCE.controls = new Controls(config);
	}
	
	public static Logger getLogger() {
		return INSTANCE.logger;
	}
	
	public static EventsManager getEvents() {
		return INSTANCE.events;
	}
	
	public static GC getGrabageCollector() {
		return INSTANCE.gc;
	}
	
	public static Controls getControls() {
		return INSTANCE.controls;
	}

}
