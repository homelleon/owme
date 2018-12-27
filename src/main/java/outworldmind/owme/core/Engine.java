package outworldmind.owme.core;

public class Engine {
	
	private Config config;
	private Window window;
	private ConfigValidator validator;
	
	public Engine(Config config) {
		this.config = config;
		Tools.init(config);
	}
	
	public Window getWindow() {
		return window;
	}
	
	public void start() {
		initValidator();
		validateConfig();
		initWindow();
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
	
	private void initWindow() {
		var width = config.getParam(Config.WINDOW_WIDTH);
		var height = config.getParam(Config.WINDOW_HEIGHT);
		var name = config.getParam(Config.WINDOW_NAME);
		window = new Window((String) name, (int) width, (int) height);
		window.initialize();
	}
	
	public void update() {
		window.update();
	}
	
	public void stop() {
		Console.log("released :" + Tools.getGrabageCollector().disengage());
		window.destroy();
	}

}
