package outworldmind.owme.maths;

public class Vector3 extends Vector<Vector3> implements Comparable<Vector3> {
	
	public float x, y, z;
	
	public Vector3() {
		super();
		x = y = z = 0;
	}
	
	public Vector3(Float x, Float y, Float z) {
		this();
		copy(x.floatValue(), y.floatValue(), z.floatValue());
	}
	
	public Vector3(Float x, Float y, Integer z) {
		this();
		copy(x.floatValue(), y.floatValue(), z.floatValue());
	}
	
	public Vector3(Float x, Integer y, Integer z) {
		this();
		copy(x.floatValue(), y.floatValue(), z.floatValue());
	}
	
	public Vector3(Float x, Integer y, Float z) {
		this();
		copy(x.floatValue(), y.floatValue(), z.floatValue());
	}
	
	public Vector3(Integer x, Integer y, Float z) {
		this();
		copy(x.floatValue(), y.floatValue(), z.floatValue());
	}
	
	public Vector3(Integer x, Float y, Integer z) {
		this();
		copy(x.floatValue(), y.floatValue(), z.floatValue());
	}
	
	public Vector3(Integer x, Float y, Float z) {
		this();
		copy(x.floatValue(), y.floatValue(), z.floatValue());
	}
	
	public Vector3(Double x, Double y, Double z) {
		this();
		copy(x.floatValue(), y.floatValue(), z.floatValue());
	}
	
	public Vector3(Integer x, Integer y, Integer z) {
		this();
		copy(x.floatValue(), y.floatValue(), z.floatValue());
	}
	
	public Vector3(Vector2 vector, boolean useYasZ) {
		this();
		copy(vector, useYasZ);
	}
	
	public Vector3(Vector2 vector) {
		this(vector, false);
	}
	
	public Vector3(Vector3 vector) {
		this();
		copy(vector);
	}	
	
	public Vector3(Vector4 plane) {
		this();
		copy(plane);
	}
	
	public Vector3(Quaternion q) {
		this();
		copy(q);
	}
	
	public Vector3(Float value) {
		this();
		copy(value);
	}
	
	public Vector3(Integer value) {
		this();
		copy(value);
	}
	
	public static Vector3 front() {
		return new Vector3(0, 0, 1);
	}
	
	public static Vector3 back() {
		return new Vector3(0, 0, -1);
	}
	
	public static Vector3 left() {
		return new Vector3(-1, 0, 0);
	}
	
	public static Vector3 right() {
		return new Vector3(1, 0, 0);
	}
	
	public static Vector3 top() {
		return new Vector3(0, 1, 0);
	}
	
	public static Vector3 bottom() {
		return new Vector3(0, -1, 0);
	}
	
	public Vector3 copy(Float x, Float y, Float z) {
		this.x = x.floatValue();
		this.y = y.floatValue();
		this.z = z.floatValue();
		
		return this;
	}
	
	public Vector3 copy(Integer value) {
		copy(value.floatValue());
		
		return this;
	}
	
	public Vector3 copy(Float value) {
		var floatValue = value.floatValue();
		this.x = floatValue;
		this.y = floatValue;
		this.z = floatValue;
		
		return this;
	}
	
	public Vector3 copy(Vector2 vector, boolean useYasZ) {
		x = vector.x;
		
		if (useYasZ) {
			y = 0;
			z = vector.y;
		} else {
			y = vector.y;
			z = 0;
		}
		
		return this;
	}
	
	public Vector3 copy(Vector2 vector) {
		return copy(vector, false);
	}

	public Vector3 copy(Vector3 vector) {
		x = vector.x;
		y = vector.y;
		z = vector.z;
		
		return this;
	}
	
	public Vector3 copy(Vector4 plane) {
		x = plane.x;
		y = plane.y;
		z = plane.z;
		
		return this;
	}
	
	public Vector3 copy(Quaternion q) {
		x = q.x;
		y = q.y;
		z = q.z;
		
		return this;
	}
	
	public float lengthSquared() {
		return x * x + y * y + z * z;
	}
	
	/**
	 * Negate a vector
	 * @return this
	 */
	public Vector3 negate() {
		x = -x;
		y = -y;
		z = -z;
		
		return this;
	}
	
	public float dot(Vector3 vector) {
		return x * vector.x + y * vector.y + z * vector.z;
	}
	
	public Vector3 cross(Vector3 vector) {
		float x = this.y * vector.z - this.z * vector.y;
		float y = this.z * vector.x - this.x * vector.z;
		float z = this.x * vector.y - this.y * vector.x;
		this.x = x;
		this.y = y;
		this.z = z;
		
		return this;
	}
	
	public Vector3 scale(Float scale) {
		var floatValue = scale.floatValue();
		x *= floatValue;
		y *= floatValue;
		z *= floatValue;

		return this;
	}
	
	public Vector3 normalize() {
		float length = this.length();
		
		if (length == 0) return this;
		
		x /= length;
		y /= length;
		z /= length;
		
		return this;
	}
	
	public Vector3 rotate(Vector3 rotation) {
		rotate(rotation.x, new Rotation(1f, 0f, 0f));
		rotate(rotation.y, new Rotation(0f, 1f, 0f));
		rotate(rotation.z, new Rotation(0f, 0f, 1f));
		
		return this;
	}
	
	public Vector3 rotate(Rotation rotation) {		
		return rotate((Vector3) rotation);
	}
	
	public Vector3 rotate(Float angle, Vector3 axis) {
//		var rotation = new Matrix4().rotate((float) Math.toRadians(angle), axis);
//		var rotation = new Matrix4().rotate(axis.clone().mul(angle));
//		Console.log(rotation.toString());
//		copy(rotation.mul(this));
		var sinHalfAngle = (float) Math.sin(Math.toRadians(angle / 2f));
		var cosHalfAngle = (float) Math.cos(Math.toRadians(angle / 2f));
		
		var rX = axis.x * sinHalfAngle;
		var rY = axis.y * sinHalfAngle;
		var rZ = axis.z * sinHalfAngle;
		var rW = cosHalfAngle;
//		
		var rotationQ = new Quaternion(rX, rY, rZ, rW);
		var conjugateQ = rotationQ.clone().conjugate();
		
		copy(rotationQ.mul(this).mul(conjugateQ));
//		
		return this;
	}
	
	public Vector3 add(Vector3 vector) {
		x += vector.x;
		y += vector.y;
		z += vector.z;
		
		return this;
	}
	
	public Vector3 add(Float value) {
		var floatValue = value.floatValue();
		
		x += floatValue;
		y += floatValue;
		z += floatValue;
		
		return this;
	}
	
	public Vector3 add(Double value) {
		return add(value.floatValue());
	}
	
	public Vector3 add(Integer value) {
		return add(value.floatValue());
	}
	
	
	public Vector3 sub(Vector3 vector) {
		x -= vector.x;
		y -= vector.y;
		z -= vector.z;
		
		return this;
	}
	
	public Vector3 sub(Float value) {
		var floatValue = value.floatValue();
		x -= floatValue;
		y -= floatValue;
		z -= floatValue;
		
		return this;
	}
	
	public Vector3 sub(Integer value) {
		return sub(value.floatValue());
	}
	
	public Vector3 sub(Double value) {
		return sub(value.floatValue());
	}
	
	public Vector3 mul(Vector3 vector) {
		x *= vector.x;
		y *= vector.y;
		z *= vector.z;
		
		return this;
	}
	
	public Vector3 mul(Float value) {
		var floatValue = value.floatValue();
		x *= floatValue;
		y *= floatValue;
		z *= floatValue;
		
		return this;
	}
	
	public Vector3 mul(Integer value) {
		return mul(value.floatValue());
	}
	
	public Vector3 mul(Double value) {
		return mul(value.floatValue());
	}
	
	public Vector3 div(Vector3 vector) {
		if (vector.x == 0.0f || vector.y == 0.0f || vector.z == 0.0f)
			throw new NullPointerException("Vector3.div - divide by zero.");
		
		x /= vector.x;
		y /= vector.y;
		z /= vector.z;
		
		return this;
	}
	
	public Vector3 div(Float value) {
		var floatValue = value.floatValue();
		if (floatValue == 0.0f)
			throw new NullPointerException("Vector3.div - divide by zero.");
		
		x /= floatValue;
		y /= floatValue;
		z /= floatValue;
		
		return this;
	}
	
	public Vector3 div(Integer value) {
		return div(value.floatValue());
	}
	
	public Vector3 div(Double value) {
		return div(value.floatValue());
	}
	
	public Vector3 abs() {
		return new Vector3(Math.abs(x), Math.abs(y), Math.abs(z));
	}
	
	public boolean equals(Vector3 vector) {
		return (Maths.equal(x, vector.x) && Maths.equal(y, vector.y) && 
				Maths.equal(z, vector.z));
	}
	
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}

	@Override
	public int compareTo(Vector3 vector) {
		if (this == vector || this.equals(vector))
			return 0;
		if (Maths.equal(this.x, this.x)) {
			if (Maths.equal(this.z, this.z))
				if (this.y < vector.y) return -1;
			if (Maths.equal(this.z, vector.z))
				return -1;
		} else if (this.x < vector.x) {
			return -1;
		}
		
		return 1;
	}
	
	public Vector2 getXY() {
		return new Vector2(x, y);
	}
	
	public Vector2 getXZ() {
		return new Vector2(x, z);
	}
	
	public Vector2 getYZ() {
		return new Vector2(y, z);
	}
	
	public float[] toArray() {
		return new float[] {x, y, z};
	}
	
	@Override
	public Vector3 clone() {
		return new Vector3(this);		
	}
	
}
