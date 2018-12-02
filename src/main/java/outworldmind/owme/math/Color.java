package outworldmind.owme.math;

public class Color {
	
	public static final float RGB_SIZE = 255;
	
	public float r = 0.0f;
	public float g = 0.0f;
	public float b = 0.0f;
	public float a = 1.0f;
	
	public Color() {}
	
	public Color(String hexColor) {
		int r = Integer.valueOf( hexColor.substring( 1, 3 ), 16 );
		int g = Integer.valueOf( hexColor.substring( 3, 5 ), 16 );
		int b = Integer.valueOf( hexColor.substring( 5, 7 ), 16 );
		
	    set(r, g, b);
	}
	
	public Color(float r, float g, float b, float a) {
		set(r, g, b, a);
	}
	
	public Color(float r, float g, float b) {
		set(r, g, b);
	}
	
	public Color(Color color) {
		copy(color);
	}
	
	public Color(Vector3 vector) {
		copy(vector);
	}
	
	public Color(Vector4 vector) {
		copy(vector);
	}
	
	public static Color red() {
		return new Color(RGB_SIZE, 0, 0);
	}
	
	public static Color green() {
		return new Color(0, RGB_SIZE, 0);
	}
	
	public static Color blue() {
		return new Color(0, 0, RGB_SIZE);
	}
	
	public static Color black() {
		return new Color(0, 0, 0);
	}
	
	public static Color white() {
		return new Color(RGB_SIZE, RGB_SIZE, RGB_SIZE);
	}
	
	public Vector3 getVector() {
		return new Vector3(r, g, b);
	}
	
	public Color set(float r, float g, float b, float a) {
		this.a = a;
		
		return set(r, g, b);
	}
	
	public Color set(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
		
		return this;
	}
	
	public Color copy(Color color) {
		return set(color.r, color.g, color.b, color.a);
	}
	
	public Color copy(Vector4 vector) {
		return set(vector.x, vector.y, vector.z, vector.w);
	}
	
	public Color copy(Vector3 vector) {
		return set(vector.x, vector.y, vector.z);
	}
	
	public Color rgbToOGL() {
		return new Color(r / RGB_SIZE, g / RGB_SIZE, b / RGB_SIZE);
	}
	
	public Vector3 getOGLVector() {
		return this.rgbToOGL().getVector();
	}
	
	public Color oglToRGB() {
		return new Color(r * RGB_SIZE, g * RGB_SIZE, b * RGB_SIZE);
	}
	
	public Color clone() {
		return new Color(this);
	}

}
