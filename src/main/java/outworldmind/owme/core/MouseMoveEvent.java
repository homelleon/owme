package outworldmind.owme.core;

public class MouseMoveEvent {
	
	private long window;
	private double xPos;
	private double yPos;
	
	public MouseMoveEvent(long window, double xPos, double yPos) {
		this.window = window;
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public long getWindow() {
		return window;
	}

	public double getXPos() {
		return xPos;
	}

	public double getYPos() {
		return yPos;
	}
	
}
