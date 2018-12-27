package outworldmind.owme.core;

import java.util.HashMap;
import java.util.Map;


public class Config {
	
	public static final String CONSOLE_MODE = "console.mode";
	
	public final static String WINDOW_NAME = "window.name";
	public final static String WINDOW_WIDTH = "window.width";
	public final static String WINDOW_HEIGHT = "window.height";
	public final static String INTEGER = "java.lang.Integer";
	public final static String FLOAT = "java.lang.Float";
	public final static String STRING = "java.lang.String";
	
	public final static String CAMERA_FOV = "camera.fov";
	public final static String CAMERA_NEAR_PLANE = "camera.nearplane";
	public final static String CAMERA_FAR_PLANE = "camera.farplane";
	
	private Map<String, Object> params;
	
	public Config() {
		params = new HashMap<String, Object>();
	}
	
	public Object getParam(String name) {
		return params.get(name);
	}
	
	public Config setParam(String name, Object value) {
		params.put(name, value);
		
		return this;
	}
	
	protected Map<String, Object> getAllParams() {
		return params;
	}
	
	public void clear() {
		params.clear();
	}

}
