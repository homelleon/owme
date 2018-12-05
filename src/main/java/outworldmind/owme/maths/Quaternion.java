package outworldmind.owme.maths;

public class Quaternion {

	public float x, y, z, w;
	
	public Quaternion() {
		x = y = z = w = 0;
	}
	
	public Quaternion(Quaternion quaternion) {
		copy(quaternion);
	}

	public Quaternion(float x, float y, float z, float w) {
		set(x, y, z, w);
		normalize();
	}

	public Quaternion(Matrix4 matrix) {
		float diagonal = matrix.m[0][0] + matrix.m[1][1] + matrix.m[2][2];
		if (diagonal > 0) {
			float w4 = (float) (Math.sqrt(diagonal + 1f) * 2f);
			w = w4 / 4f;
			x = (matrix.m[2][1] - matrix.m[1][2]) / w4;
			y = (matrix.m[0][2] - matrix.m[2][0]) / w4;
			z = (matrix.m[1][0] - matrix.m[0][1]) / w4;
		} else if ((matrix.m[0][0] > matrix.m[1][1]) && (matrix.m[0][0] > matrix.m[2][2])) {
			float x4 = (float) (Math.sqrt(1f + matrix.m[0][0] - matrix.m[1][1] - matrix.m[2][2]) * 2f);
			w = (matrix.m[2][1] - matrix.m[1][2]) / x4;
			x = x4 / 4f;
			y = (matrix.m[0][1] + matrix.m[1][0]) / x4;
			z = (matrix.m[0][2] + matrix.m[2][0]) / x4;
		} else if (matrix.m[1][1] > matrix.m[2][2]) {
			float y4 = (float) (Math.sqrt(1f + matrix.m[1][1] - matrix.m[0][0] - matrix.m[2][2]) * 2f);
			w = (matrix.m[0][2] - matrix.m[2][0]) / y4;
			x = (matrix.m[0][1] + matrix.m[1][0]) / y4;
			y = y4 / 4f;
			z = (matrix.m[1][2] + matrix.m[2][1]) / y4;
		} else {
			float z4 = (float) (Math.sqrt(1f + matrix.m[2][2] - matrix.m[0][0] - matrix.m[1][1]) * 2f);
			w = (matrix.m[1][0] - matrix.m[0][1]) / z4;
			x = (matrix.m[0][2] - matrix.m[2][0]) / z4;
			y = (matrix.m[1][2] - matrix.m[2][1]) / z4;
			z = z4 / 4f;
		}
	}
	
	public Quaternion copy(Quaternion quaternion) {
		x = quaternion.x;
		y = quaternion.y;
		z = quaternion.z;
		w = quaternion.w;
		
		return this;
	}

	public void normalize() {
		float mag = (float) Math.sqrt(w * w + x * x + y * y + z * z);
		w /= mag;
		x /= mag;
		y /= mag;
		z /= mag;
	}
	
	public Quaternion conjugate() {
		x = -x;
		y = -y;
		z = -z;
		
		return this;
	}
	
	private void set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Quaternion add(Quaternion q) {
		x += q.x;
		y += q.y;
		z += q.z;
		w += q.w;
		
		return this;
	}
	
	public Quaternion sub(Quaternion q) {
		x -= q.x;
		y -= q.y;
		z -= q.z;
		w -= q.w;
		
		return this;
	}
	
	public Quaternion mul(Quaternion q)	{
		float x_ = x * q.w + w * q.x + y * q.z - z * q.y;
		float y_ = y * q.w + w * q.y + z * q.x - x * q.z;
		float z_ = z * q.w + w * q.z + x * q.y - y * q.x;
		float w_ = w * q.w - x * q.x - y * q.y - z * q.z;
		
		set(x_, y_, z_, w_);

		return this;
	}

	public Quaternion mul(Vector3 vector) {
		float x_ =  w * vector.x + y * vector.z - z * vector.y;
		float y_ =  w * vector.y + z * vector.x - x * vector.z;
		float z_ =  w * vector.z + x * vector.y - y * vector.x;
		float w_ = -x * vector.x - y * vector.y - z * vector.z;
		
		set(x_, y_, z_, w_);

		return this;
	}
	
	public Quaternion mul(float value) {
		x *= value;
		y *= value;
		z *= value;
		w *= value;
		
		return this;
	}
	
	public Quaternion div(float value) {
		x /= value;
		y /= value;
		z /= value;
		w /= value;
		
		return this;
	}

	public Matrix4 toRotationMatrix() {
		Matrix4 matrix = new Matrix4();
		final float xy = x * y;
		final float xz = x * z;
		final float xw = x * w;
		final float yz = y * z;
		final float yw = y * w;
		final float zw = z * w;
		final float xSquared = x * x;
		final float ySquared = y * y;
		final float zSquared = z * z;
		matrix.m[0][0] = 1 - 2 * (ySquared + zSquared);
		matrix.m[0][1] = 2 * (xy - zw);
		matrix.m[0][2] = 2 * (yz + xw);
		matrix.m[0][3] = 0;
		matrix.m[1][0] = 2 * (xy + zw);
		matrix.m[1][1] = 1 - 2 * (xSquared + zSquared);
		matrix.m[1][2] = 2 * (yz - xw);
		matrix.m[1][3] = 0;
		matrix.m[2][0] = 2 * (xz - yw);
		matrix.m[2][1] = 2 * (yz + xw);
		matrix.m[2][2] = 1 - 2 * (xSquared + ySquared);
		matrix.m[2][3] = 0;
		matrix.m[3][0] = 0;
		matrix.m[3][1] = 0;
		matrix.m[3][2] = 0;
		matrix.m[3][3] = 1;
		return matrix;
	}

	public static Quaternion interpolate(Quaternion a, Quaternion b, float blend) {
		Quaternion result = new Quaternion(0, 0, 0, 1);
		float dot = a.w * b.w + a.x * b.x + a.y * b.y + a.z * b.z;
		float blendI = 1f - blend;
		if (dot < 0) {
			result.w = blendI * a.w + blend * -b.w;
			result.x = blendI * a.x + blend * -b.x;
			result.y = blendI * a.y + blend * -b.y;
			result.z = blendI * a.z + blend * -b.z;
		} else {
			result.w = blendI * a.w + blend * b.w;
			result.x = blendI * a.x + blend * b.x;
			result.y = blendI * a.y + blend * b.y;
			result.z = blendI * a.z + blend * b.z;
		}
		result.normalize();
		return result;
	}
	
	public Quaternion clone() {
		return new Quaternion(this);
	}

}
