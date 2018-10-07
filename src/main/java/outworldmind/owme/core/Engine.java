package outworldmind.owme.core;

public class Engine {
	
	public final static String PARAM_WINDOW_NAME = "window.name";
	public final static String PARAM_WINDOW_WIDTH = "window.width";
	public final static String PARAM_WINDOW_HEIGHT = "window.height";
	public final static String PARAM_INTEGER = "java.lang.Integer";
	public final static String PARAM_STRING = "java.lang.String";
	
	private Config config;
	private Window window;
	private ConfigValidator validator;
	
	public Engine(Config config) {
		this.config = config;
	}
	
	public Window getWindow() {
		return window;
	}
	
	public void initialize() {
		initValidator();
		validateConfig();
		initWindow();
	}
	
	private void initValidator() {
		validator = new ConfigValidator(config);
		validator
			.addCheck(PARAM_WINDOW_WIDTH, PARAM_INTEGER, false)
			.addCheck(PARAM_WINDOW_HEIGHT, PARAM_INTEGER, false);
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
		var width = config.getParam(PARAM_WINDOW_WIDTH);
		var height = config.getParam(PARAM_WINDOW_HEIGHT);
		var name = config.getParam(PARAM_WINDOW_NAME);
		window = new Window((String) name, (int) width, (int) height);
	}
	
	public void update() {
		window.update();
	}
	
	public void stop() {
		window.destroy();
	}

}
