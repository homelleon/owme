package outworldmind.owme.core;

public class Controls {
	
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
