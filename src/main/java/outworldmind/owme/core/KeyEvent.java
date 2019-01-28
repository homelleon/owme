package outworldmind.owme.core;

public class KeyEvent {
	
	private long window;
	private int button;
	private int scancode;
	private int action;
	private int mods;
	
	public KeyEvent(long window, int button, int scancode, int action, int mods) {
		this.window = window;
		this.button = button;
		this.scancode = scancode;
		this.action = action;
		this.mods = mods;
	}

	public int getKey() {
		return button;
	}

	public int getAction() {
		return action;
	}

	public int getMods() {
		return mods;
	}
	
	public long getWindow() {
		return window;
	}

	public int getScancode() {
		return scancode;
	}

}
