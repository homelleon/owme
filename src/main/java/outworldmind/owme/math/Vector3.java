package outworldmind.owme.math;

public class Vector3 extends Vector<Vector3> implements Comparable<Vector3> {
	
	public float x, y, z;
	
	protected Vector3() {
		x = y = z = 0;
	}
	
	protected Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	protected Vector3(Vector3 vector) {
		copy(vector);
	}	
	
	protected Vector3(Vector4 plane) {
		x = plane.x;
		y = plane.y;
		z = plane.z;
	}	

	public Vector3 copy(Vector3 vector) {
		x = vector.x;
		y = vector.y;
		z = vector.z;
		
		return this;
	}
	
	public float length() {
		return (float) lengthSquared();
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
	
	public Vector3 scale(float scale) {
		x *= scale;
		y *= scale;
		z *= scale;

		return this;

	}
	
	public Vector3 normalize() {
		float length = this.length();
		
		x /= length;
		y /= length;
		z /= length;
		
		return this;
	}
	
	public Vector3 rotate(float angle, Vector3 axis) {
		float sinHalfAngle = (float) Math.sin(Math.toRadians(angle / 2));
		float cosHalfAngle = (float) Math.cos(Math.toRadians(angle / 2));
		
		float rX = axis.x * sinHalfAngle;
		float rY = axis.y * sinHalfAngle;
		float rZ = axis.z * sinHalfAngle;
		float rW = cosHalfAngle;
		
		Quaternion rotation = new Quaternion(rX, rY, rZ, rW);
		Quaternion conjugate = rotation.conjugate();
		
		Quaternion w = rotation.mul(this).mul(conjugate);
		
		x = w.x;
		y = w.y;
		z = w.z;
		
		return this;
	}
	
	public Vector3 add(Vector3 vector) {
		x += vector.x;
		y += vector.y;
		z += vector.z;
		
		return this;
	}
	
	public Vector3 add(float value) {
		x += value;
		y += value;
		z += value;
		
		return this;
	}
	
	
	public Vector3 sub(Vector3 vector) {
		x -= vector.x;
		y -= vector.y;
		z -= vector.z;
		
		return this;
	}
	
	public Vector3 sub(float value) {
		x -= value;
		y -= value;
		z -= value;
		
		return this;
	}
	
	public Vector3 mul(Vector3 vector) {
		x *= vector.x;
		y *= vector.y;
		z *= vector.z;
		
		return this;
	}
	
	public Vector3 mul(float value) {
		x *= value;
		y *= value;
		z *= value;
		
		return this;
	}
	
	public Vector3 div(Vector3 vector) {
		x /= vector.x;
		y /= vector.y;
		z /= vector.z;
		
		return this;
	}
	
	public Vector3 div(float value) {
		x /= value;
		y /= value;
		z /= value;
		
		return this;
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
