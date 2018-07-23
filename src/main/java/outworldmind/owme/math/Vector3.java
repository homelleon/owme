package outworldmind.owme.math;

public class Vector3 extends Vector<Vector3> implements Comparable<Vector3> {
	
	public float x, y, z;
	
	public Vector3() {
		x = y = z = 0;
	}
	
	public Vector3(Float x, Float y, Float z) {
		set(x, y, z);
	}
	
	public Vector3(Double x, Double y, Double z) {
		set(x.floatValue(), y.floatValue(), z.floatValue());
	}
	
	public Vector3(Integer x, Integer y, Integer z) {
		set(x.floatValue(), y.floatValue(), z.floatValue());
	}
	
	public Vector3(Vector3 vector) {
		copy(vector);
	}	
	
	public Vector3(Vector4 plane) {
		copy(plane);
	}
	
	public Vector3(Quaternion q) {
		copy(q);
	}
	
	public Vector3 set(Float x, Float y, Float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		return this;
	}
	
	public Vector3 set(Float value) {
		this.x = value;
		this.y = value;
		this.z = value;
		
		return this;
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
		x *= scale;
		y *= scale;
		z *= scale;

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
	
	public Vector3 rotate(Rotation rotation) {
		rotate(rotation.x, new Rotation(1f, 0f, 0f));
		rotate(rotation.y, new Rotation(0f, 1f, 0f));
		rotate(rotation.z, new Rotation(0f, 0f, 1f));
		
		return this;
	}
	
	public Vector3 rotate(Float angle, Vector3 axis) {
		var rotation = new Matrix4().rotate(angle, axis);
		copy(rotation.mul(this));
//		var sinHalfAngle = (float) Math.sin(Math.toRadians(angle / 2f));
//		var cosHalfAngle = (float) Math.cos(Math.toRadians(angle / 2f));
//		
//		var rX = axis.x * sinHalfAngle;
//		var rY = axis.y * sinHalfAngle;
//		var rZ = axis.z * sinHalfAngle;
//		var rW = cosHalfAngle;
//		
//		var rotationQ = new Quaternion(rX, rY, rZ, rW);
//		var conjugateQ = rotationQ.clone().conjugate();
//		
//		copy(rotationQ.mul(this).mul(conjugateQ));
		
		return this;
	}
	
	public Vector3 add(Vector3 vector) {
		x += vector.x;
		y += vector.y;
		z += vector.z;
		
		return this;
	}
	
	public Vector3 add(Float value) {
		x += value;
		y += value;
		z += value;
		
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
		x -= value;
		y -= value;
		z -= value;
		
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
		x *= value;
		y *= value;
		z *= value;
		
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
		if (value == 0.0f)
			throw new NullPointerException("Vector3.div - divide by zero.");
		
		x /= value;
		y /= value;
		z /= value;
		
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
	
	@Override
	public Vector3 clone() {
		return new Vector3(this);		
	}
	
}
