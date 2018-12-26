package outworldmind.owme.maths;

public class Vector2 extends Vector<Vector2> {
	
	public float x, y;
	
	public Vector2() {
		x = y = 0;
	}
	
	public Vector2(Float x, Float y) {
		this();
		copy(x, y);
	}
	
	public Vector2(Integer x, Integer y) {
		this();
		copy(x, y);
	}
	
	public Vector2(Vector3 vector) {
		this();
		copy(vector);
	}
	
	public Vector2(Vector2 vector) {
		this();
		copy(vector);
	}
	
	public static Vector2 up() {
		return new Vector2(0, 1);
	}
	
	public static Vector2 down() {
		return new Vector2(0, -1);
	}
	
	public static Vector2 left() {
		return new Vector2(-1, 0);
	}
	
	public static Vector2 right() {
		return new Vector2(1, 0);
	}
	
	public Vector2 copy(Float x, Float y) {
		this.x = x.floatValue();
		this.y = y.floatValue();
		
		return this;
	}
	
	public Vector2 copy(Integer x, Integer y) {
		this.x = x.floatValue();
		this.y = y.floatValue();
		
		return this;
	}
	
	public Vector2 copy(Vector2 vector) {
		x = vector.x;
		y = vector.y;
		
		return this;
	}
	
	public Vector2 copy(Vector3 vector) {
		x = vector.x;
		y = vector.z;
		
		return this;
	}
	
	public float lengthSquared() {
		return x * x + y * y;
	}
	
	public float dot(Vector2 vector) {
		return x * vector.x + y * vector.y;
	}
	
	public Vector2 normalize()	{
		float length = length();
		
		x /= length;
		y /= length;
		
		return this;
	}
	
	public Vector2 add(Vector2 vector) {
		x += vector.x;
		y += vector.y;
		
		return this;
	}
	
	public Vector2 add(Float value) {
		x += value;
		y += value;
		
		return this;
	}
	
	public Vector2 sub(Vector2 vector) {
		x -= vector.x;
		y -= vector.y;
		
		return this;
	}
	
	public Vector2 sub(Float value) {
		x -= value;
		y -= value;
		
		return this;
	}
	
	public Vector2 mul(Vector2 vector)	{
		x *= vector.x;
		y *= vector.y;
		
		return this;
	}
	
	public Vector2 mul(Float value) {
		x *= value;
		y *= value;
		
		return this;
	}
	
	public Vector2 div(Vector2 vector) {
		x /= vector.x;
		y /= vector.y;
		
		return this;
	}
	
	public Vector2 div(Float value) {
		x /= value;
		y /= value;
		
		return this;
	}
	
	public Vector3 getVector3() {
		return new Vector3(this);
	}
	
	public String toString() {
		return "[" + x + "," + y + "]";
	}
	
	@Override
	public Vector2 clone() {
		return new Vector2(this);		
	}
	
}
