package outworldmind.owme.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Engine {
	
	private Config config;
	private Map<String, Window> windows;
	private ConfigValidator validator;
	
	public Engine(Config config) {
		this.config = config;
		windows = new HashMap<String, Window>();
		Tools.init(config);
	}
	
	public Window getWindow(String name) {
		return windows.get(name);
	}
	
	public Collection<Window> getWindows() {
		return windows.values();
	}
	 
	public void start() {
		initValidator();
		validateConfig();
		
		addWindow(
				(String) config.getParam(Config.WINDOW_NAME), 
				(int) config.getParam(Config.WINDOW_WIDTH), 
				(int) config.getParam(Config.WINDOW_HEIGHT));
		windows.values().forEach(Window::initialize);
	}
	
	private void initValidator() {
		validator = new ConfigValidator(config);
		validator
			.addCheck(Config.WINDOW_WIDTH, Config.INTEGER, false)
			.addCheck(Config.WINDOW_HEIGHT, Config.INTEGER, false)
			.addCheck(Config.CAMERA_FOV, Config.FLOAT, false, true)
			.addCheck(Config.CAMERA_NEAR_PLANE, Config.FLOAT, false, true)
			.addCheck(Config.CAMERA_FAR_PLANE, Config.FLOAT, false, true);
	}
	
	private void validateConfig() {
		try {
			tryStrictConfigValidation();
		} catch (ParamValidationException e) {
			System.err.println(e.getMessage());
			stop();
		}
	}
	
	private void tryStrictConfigValidation() throws ParamValidationException {
		if (!validator.validate())
			throw new ParamValidationException("Engine.initialize", validator);
	}
	
	private void addWindow(String name, int width, int height) {
		windows.put(name, new Window((String) name, (int) width, (int) height));
	}
	
	public void update() {
		windows.values().forEach(Window::update);
	}
	
	public void stop() {
		Tools.getLogger().log("released: " + Tools.getGrabageCollector().disengage());
		windows.values().stream()
			.filter(window -> window.getCloseRequest())
			.forEach(Window::close);
		windows.values().forEach(Window::destroy);
	}

}
