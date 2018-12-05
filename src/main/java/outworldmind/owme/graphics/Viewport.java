package outworldmind.owme.graphics;

public class Viewport {
	
	private float width;
	private float height;
	private float x;
	private float y;
	
	public Viewport(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
}
