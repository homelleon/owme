package outworldmind.owme.core;

import org.lwjgl.glfw.GLFW;

public class Controls {
	
	public static final int ACTION_RELEASED = GLFW.GLFW_RELEASE;
	public static final int ACTION_PRESSED = GLFW.GLFW_PRESS;
	public static final int ACTION_REPEATED = GLFW.GLFW_REPEAT;
	
	private Mouse mouse;
	private Keyboard keyboard;
	
	protected Controls(Config config) {
		mouse = new Mouse(config);
		keyboard = new Keyboard(config);
	}
	
	public Mouse getMouse() {
		return mouse;
	}
	
	public Keyboard getKeyboard() {
		return keyboard;
	}
	
	

}
