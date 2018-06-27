package outworldmind.owme.math;

public class Vector4 {
	
	public float x, y, z, w;
	
	public Vector4() {
		x = y = z = w = 0;
	}
	
	public Vector4(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4(Vector4 vector) {
		copy(vector);
	}
	
	public Vector4(Vector3 vector) {
		x = vector.x;
		y = vector.y;
		z = vector.z;
		w = 1.0f;
	}
	
	public Vector4 copy(Vector4 vector) {
		x = vector.x;
		y = vector.y;
		z = vector.z;
		w = vector.w;
		
		return this;
	}
	
	public float length() {
		return (float) Math.sqrt(lengthSquared());
	}
	
	public float lengthSquared() {
		return x * x + y * y + z * z + w * w;
	}
	
	/**
	 * Negate a vector
	 * @return this
	 */
	public Vector4 negate() {
		x = -x;
		y = -y;
		z = -z;
		w = -w;
		
		return this;
	}
	
	public float dot(Vector4 vector) {
		return x * vector.x + y * vector.y + z * vector.z + w * vector.w;
	}
	
	public Vector4 scale(float scale) {

		x *= scale;
		y *= scale;
		z *= scale;
		w *= scale;

		return this;

	}
	
	public Vector4 normalize() {
		float length = this.length();
		
		x /= length;
		y /= length;
		z /= length;
		w /= length;
		
		return this;
	}
	
	public Vector4 add(Vector4 vector) {
		x += vector.x;
		y += vector.y;
		z += vector.z;
		w += vector.w;

		return this;
	}
	
	public Vector4 add(float value) {
		x += value;
		y += value;
		z += value;
		w += value;
		
		return this;
	}
	
	
	public Vector4 sub(Vector4 vector) {
		x -= vector.x;
		y -= vector.y;
		z -= vector.z;
		w -= vector.w;		
		
		return this;
	}
	
	public Vector4 sub(float value) {
		x -= value;
		y -= value;
		z -= value;
		w -= value;
		
		return this;
	}
	
	public Vector4 mul(Vector4 vector) {
		x *= vector.x;
		y *= vector.y;
		z *= vector.z;
		w *= vector.w;
		
		return this;
	}
	
	public Vector4 mul(float value) {
		x *= value;
		y *= value;
		z *= value;
		w *= value;
		
		return this;
	}
	
	public Vector4 mul(Matrix4 matrix) {		
		float x_ = matrix.m[0][0] * x + matrix.m[1][0] * y + matrix.m[2][0] * z + matrix.m[3][0] * w;
		float y_ = matrix.m[0][1] * x + matrix.m[1][1] * y + matrix.m[2][1] * z + matrix.m[3][1] * w;
		float z_ = matrix.m[0][2] * x + matrix.m[1][2] * y + matrix.m[2][2] * z + matrix.m[3][2] * w;
		float w_ = matrix.m[0][3] * x + matrix.m[1][3] * y + matrix.m[2][3] * z + matrix.m[3][3] * w;
		
		x = x_;
		y = y_;
		z = z_;
		w = w_;
		
		return this;
	}
	
	public Vector4 div(Vector4 vector) {
		x /= vector.x;
		y /= vector.y;
		z /= vector.z;
		w /= vector.w;
		
		return this;
	}
	
	public Vector4 div(float value) {
		x /= value;
		y /= value;
		z /= value;
		w /= value;
		
		return this;
	}
	
	public boolean equals(Vector4 vector) {		
		return (Maths.equal(x, vector.x) && Maths.equal(y, vector.y) && 
				Maths.equal(z, vector.z) && Maths.equal(w, vector.w));
	}
	
	public String toString() {
		return "[" + x + "," + y + "," + z + "," + w + "]";
	}
	
	@Override
	public Vector4 clone() {
		return new Vector4(this);		
	}

}
