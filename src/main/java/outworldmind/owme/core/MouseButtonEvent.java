package outworldmind.owme.core;

public class MouseButtonEvent {
	
	private long window;
	private int button;
	private int action;
	private int mods;
	
	public MouseButtonEvent(long window, int button, int action, int mods) {
		this.window = window;
		this.button = button;
		this.action = action;
		this.mods = mods;
	}

	public long getWindow() {
		return window;
	}

	public int getButton() {
		return button;
	}

	public int getAction() {
		return action;
	}

	public int getMods() {
		return mods;
	}
	
}
