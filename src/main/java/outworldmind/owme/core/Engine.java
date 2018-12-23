package outworldmind.owme.core;

public class Engine {
	
	public final static String PARAM_WINDOW_NAME = "window.name";
	public final static String PARAM_WINDOW_WIDTH = "window.width";
	public final static String PARAM_WINDOW_HEIGHT = "window.height";
	public final static String PARAM_INTEGER = "java.lang.Integer";
	public final static String PARAM_FLOAT = "java.lang.Float";
	public final static String PARAM_STRING = "java.lang.String";
	
	public final static String PARAM_CAMERA_FOV = "camera.fov";
	public final static String PARAM_CAMERA_NEAR_PLANE = "camera.nearplane";
	public final static String PARAM_CAMERA_FAR_PLANE = "camera.farplane";
	
	private Config config;
	private Window window;
	private ConfigValidator validator;
	
	public Engine(Config config) {
		this.config = config;
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
			.addCheck(PARAM_WINDOW_WIDTH, PARAM_INTEGER, false)
			.addCheck(PARAM_WINDOW_HEIGHT, PARAM_INTEGER, false)
			.addCheck(PARAM_CAMERA_FOV, PARAM_FLOAT, false, true)
			.addCheck(PARAM_CAMERA_NEAR_PLANE, PARAM_FLOAT, false, true)
			.addCheck(PARAM_CAMERA_FAR_PLANE, PARAM_FLOAT, false, true);
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
