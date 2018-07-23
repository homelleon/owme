//package outworldmind.owme.math;
//
//import java.nio.FloatBuffer;
//
//public class Matrix3 {
//	private final int size = 3;
//	public float[][] m;
//	
//	public Matrix3() {
//		copy(new float[size][size]);
//		setIdentity();
//	}
//	
//	public Matrix3(Matrix3 matrix) {
//		copy(matrix.m.clone());
//	}
//	
//	
//	public Matrix3 copy(float[][] m) {
//		if (m.length != size || m[0].length != size)
//			throw new IllegalArgumentException("Matrix4.copy - size of array must be " + size);
//		
//		this.m = m.clone();
//		
//		return this;
//	}
//	
//	
//	public Matrix3 copy(Matrix3 matrix) {
//		m = matrix.m.clone();
//		
//		return this;
//	}
//	
//	public Matrix3 zero() {
//		for (int i = 0; i < size; i++)
//			for (int j = 0; j < size; j++)
//				m[i][j] = 0.0f;
//	
//		return this;
//	}
//	
//	public Matrix3 setIdentity() {
//		zero();
//		for (int i = 0; i < size; i++)
//			m[i][i] = 1.0f;
//	
//		return this;
//	}
//	
//	public Matrix4 invert() {
//		float determinant = determinant();
//		
//		if (determinant == 0)
//			return null;
//		
//		/*
//		 * m00 m01 m02 m03
//		 * m10 m11 m12 m13
//		 * m20 m21 m22 m23
//		 * m30 m31 m32 m33
//		 */
//		float determinant_inv = 1f / determinant;
//
//		// first row
//		float t00 =  determinant3x3(m[1][1], m[1][2], m[1][3], m[2][1], m[2][2], m[2][3], m[3][1], m[3][2], m[3][3]);
//		float t01 = -determinant3x3(m[1][0], m[1][2], m[1][3], m[2][0], m[2][2], m[2][3], m[3][0], m[3][2], m[3][3]);
//		float t02 =  determinant3x3(m[1][0], m[1][1], m[1][3], m[2][0], m[2][1], m[2][3], m[3][0], m[3][1], m[3][3]);
//		float t03 = -determinant3x3(m[1][0], m[1][1], m[1][2], m[2][0], m[2][1], m[2][2], m[3][0], m[3][1], m[3][2]);
//		// second row
//		float t10 = -determinant3x3(m[0][1], m[0][2], m[0][3], m[2][1], m[2][2], m[2][3], m[3][1], m[3][2], m[3][3]);
//		float t11 =  determinant3x3(m[0][0], m[0][2], m[0][3], m[2][0], m[2][2], m[2][3], m[3][0], m[3][2], m[3][3]);
//		float t12 = -determinant3x3(m[0][0], m[0][1], m[0][3], m[2][0], m[2][1], m[2][3], m[3][0], m[3][1], m[3][3]);
//		float t13 =  determinant3x3(m[0][0], m[0][1], m[0][2], m[2][0], m[2][1], m[2][2], m[3][0], m[3][1], m[3][2]);
//		// third row
//		float t20 =  determinant3x3(m[0][1], m[0][2], m[0][3], m[1][1], m[1][2], m[1][3], m[3][1], m[3][2], m[3][3]);
//		float t21 = -determinant3x3(m[0][0], m[0][2], m[0][3], m[1][0], m[1][2], m[1][3], m[3][0], m[3][2], m[3][3]);
//		float t22 =  determinant3x3(m[0][0], m[0][1], m[0][3], m[1][0], m[1][1], m[1][3], m[3][0], m[3][1], m[3][3]);
//		float t23 = -determinant3x3(m[0][0], m[0][1], m[0][2], m[1][0], m[1][1], m[1][2], m[3][0], m[3][1], m[3][2]);
//		// fourth row
//		float t30 = -determinant3x3(m[0][1], m[0][2], m[0][3], m[1][1], m[1][2], m[1][3], m[2][1], m[2][2], m[2][3]);
//		float t31 =  determinant3x3(m[0][0], m[0][2], m[0][3], m[1][0], m[1][2], m[1][3], m[2][0], m[2][2], m[2][3]);
//		float t32 = -determinant3x3(m[0][0], m[0][1], m[0][3], m[1][0], m[1][1], m[1][3], m[2][0], m[2][1], m[2][3]);
//		float t33 =  determinant3x3(m[0][0], m[0][1], m[0][2], m[1][0], m[1][1], m[1][2], m[2][0], m[2][1], m[2][2]);
//
//		// transpose and divide by the determinant
//		m[0][0] = t00 * determinant_inv;
//		m[1][1] = t11 * determinant_inv;
//		m[2][2] = t22 * determinant_inv;
//		m[3][3] = t33 * determinant_inv;
//		m[0][1] = t10 * determinant_inv;
//		m[1][0] = t01 * determinant_inv;
//		m[2][0] = t02 * determinant_inv;
//		m[0][2] = t20 * determinant_inv;
//		m[1][2] = t21 * determinant_inv;
//		m[2][1] = t12 * determinant_inv;
//		m[0][3] = t30 * determinant_inv;
//		m[3][0] = t03 * determinant_inv;
//		m[1][3] = t31 * determinant_inv;
//		m[3][1] = t13 * determinant_inv;
//		m[3][2] = t23 * determinant_inv;
//		m[2][3] = t32 * determinant_inv;
//			
//		return this;
//	}
//	
//	private static float determinant3x3(float t00, float t01, float t02,
//		     float t10, float t11, float t12,
//		     float t20, float t21, float t22)
//	{
//		return   t00 * (t11 * t22 - t12 * t21)
//		      + t01 * (t12 * t20 - t10 * t22)
//		      + t02 * (t10 * t21 - t11 * t20);
//	}
//	
//	public float determinant() {
//		float f =
//			m[0][0]
//				* ((m[1][1] * m[2][2] * m[3][3] + m[1][2] * m[2][3] * m[3][1] + m[1][3] * m[2][1] * m[3][2])
//					- m[1][3] * m[2][2] * m[3][1]
//					- m[1][1] * m[2][3] * m[3][2]
//					- m[1][2] * m[2][1] * m[3][3]);
//		f -= m[0][1]
//			* ((m[1][0] * m[2][2] * m[3][3] + m[1][2] * m[2][3] * m[3][0] + m[1][3] * m[2][0] * m[3][2])
//				- m[1][3] * m[2][2] * m[3][0]
//				- m[1][0] * m[2][3] * m[3][2]
//				- m[1][2] * m[2][0] * m[3][3]);
//		f += m[0][2]
//			* ((m[1][0] * m[2][1] * m[3][3] + m[1][1] * m[2][3] * m[3][0] + m[1][3] * m[2][0] * m[3][1])
//				- m[1][3] * m[2][1] * m[3][0]
//				- m[1][0] * m[2][3] * m[3][1]
//				- m[1][1] * m[2][0] * m[3][3]);
//		f -= m[0][3]
//			* ((m[1][0] * m[2][1] * m[3][2] + m[1][1] * m[2][2] * m[3][0] + m[1][2] * m[2][0] * m[3][1])
//				- m[1][2] * m[2][1] * m[3][0]
//				- m[1][0] * m[2][2] * m[3][1]
//				- m[1][1] * m[2][0] * m[3][2]);
//		return f;
//	}
//	
//	public Matrix4 translate(Vector3 translation)	{
//		m[3][0] += m[0][0] * translation.x + m[1][0] * translation.y + m[2][0] * translation.z;
//		m[3][1] += m[0][1] * translation.x + m[1][1] * translation.y + m[2][1] * translation.z;
//		m[3][2] += m[0][2] * translation.x + m[1][2] * translation.y + m[2][2] * translation.z;
//		m[3][3] += m[0][3] * translation.x + m[1][3] * translation.y + m[2][3] * translation.z;
//	
//		return this;
//	}
//	
//	public Matrix4 translate(Vector2 translation) {		
//		m[3][0] += m[0][0] * translation.x + m[1][0] * translation.y;
//		m[3][1] += m[0][1] * translation.x + m[1][1] * translation.y;
//		m[3][2] += m[0][2] * translation.x + m[1][2] * translation.y;
//		m[3][3] += m[0][3] * translation.x + m[1][3] * translation.y;
//		
//		return this;
//	}
//	
//	public Matrix4 rotate(Vector3 rotation) {
//		Matrix4 rx = new Matrix4();
//		Matrix4 ry = new Matrix4();
//		Matrix4 rz = new Matrix4();
//		
//		float x = (float) Math.toRadians(rotation.x);
//		float y = (float) Math.toRadians(rotation.y);
//		float z = (float) Math.toRadians(rotation.z);
//		
//		rz.m[0][0] = (float)Math.cos(z); rz.m[0][1] = -(float)Math.sin(z); 	 rz.m[0][2] = 0; 				   rz.m[0][3] = 0;
//		rz.m[1][0] = (float)Math.sin(z); rz.m[1][1] = (float)Math.cos(z);  	 rz.m[1][2] = 0; 				   rz.m[1][3] = 0;
//		rz.m[2][0] = 0; 				 rz.m[2][1] = 0; 				   	 rz.m[2][2] = 1; 				   rz.m[2][3] = 0;
//		rz.m[3][0] = 0; 				 rz.m[3][1] = 0; 				   	 rz.m[3][2] = 0; 				   rz.m[3][3] = 1;
//		
//		rx.m[0][0] = 1; 				 rx.m[0][1] = 0;					 rx.m[0][2] = 0; 				   rx.m[0][3] = 0;
//		rx.m[1][0] = 0; 				 rx.m[1][1] = (float)Math.cos(x); 	 rx.m[1][2] = -(float)Math.sin(x); rx.m[1][3] = 0;
//		rx.m[2][0] = 0; 				 rx.m[2][1] = (float)Math.sin(x); 	 rx.m[2][2] = (float)Math.cos(x);  rx.m[2][3] = 0;
//		rx.m[3][0] = 0; 				 rx.m[3][1] = 0; 				 	 rx.m[3][2] = 0;				   rx.m[3][3] = 1;
//		
//		ry.m[0][0] = (float)Math.cos(y); ry.m[0][1] = 0; 					 ry.m[0][2] = (float)Math.sin(y);  ry.m[0][3] = 0;
//		ry.m[1][0] = 0; 				 ry.m[1][1] = 1; 				 	 ry.m[1][2] = 0; 				   ry.m[1][3] = 0;
//		ry.m[2][0] = -(float)Math.sin(y);ry.m[2][1] = 0;					 ry.m[2][2] = (float)Math.cos(y);  ry.m[2][3] = 0;
//		ry.m[3][0] = 0; 				 ry.m[3][1] = 0; 					 ry.m[3][2] = 0; 				   ry.m[3][3] = 1;
//	
//		m =  rz.mul(ry.mul(rx)).m;
//		
//		return this;
//	}
//	
//	public Matrix4 rotate(float angle, Vector3 axis) {		
//		float c = (float) Math.cos(angle);
//		float s = (float) Math.sin(angle);
//		float oneminusc = 1.0f - c;
//		float xy = axis.x * axis.y;
//		float yz = axis.y * axis.z;
//		float xz = axis.x * axis.z;
//		float xs = axis.x * s;
//		float ys = axis.y * s;
//		float zs = axis.z * s;
//
//		float f00 = axis.x * axis.x * oneminusc + c;
//		float f01 = xy * oneminusc + zs;
//		float f02 = xz * oneminusc - ys;
//		// n[3] not used
//		float f10 = xy * oneminusc - zs;
//		float f11 = axis.y * axis.y * oneminusc + c;
//		float f12 = yz * oneminusc + xs;
//		// n[7] not used
//		float f20 = xz * oneminusc + ys;
//		float f21 = yz * oneminusc - xs;
//		float f22 = axis.z * axis.z * oneminusc + c;
//
//		float t00 = m[0][0] * f00 + m[1][0] * f01 + m[2][0] * f02;
//		float t01 = m[0][1] * f00 + m[1][1] * f01 + m[2][1] * f02;
//		float t02 = m[0][2] * f00 + m[1][2] * f01 + m[2][2] * f02;
//		float t03 = m[0][3] * f00 + m[1][3] * f01 + m[2][3] * f02;
//		float t10 = m[0][0] * f10 + m[1][0] * f11 + m[2][0] * f12;
//		float t11 = m[0][1] * f10 + m[1][1] * f11 + m[2][1] * f12;
//		float t12 = m[0][2] * f10 + m[1][2] * f11 + m[2][2] * f12;
//		float t13 = m[0][3] * f10 + m[1][3] * f11 + m[2][3] * f12;
//		
//		
//		m[2][0] = m[0][0] * f20 + m[1][0] * f21 + m[2][0] * f22;
//		m[2][1] = m[0][1] * f20 + m[1][1] * f21 + m[2][1] * f22;
//		m[2][2] = m[0][2] * f20 + m[1][2] * f21 + m[2][2] * f22;
//		m[2][3] = m[0][3] * f20 + m[1][3] * f21 + m[2][3] * f22;
//		m[0][0] = t00;
//		m[0][1] = t01;
//		m[0][2] = t02;
//		m[0][3] = t03;
//		m[1][0] = t10;
//		m[1][1] = t11;
//		m[1][2] = t12;
//		m[1][3] = t13;
//		
//		return this;
//	}
//	
//	public Matrix4 scale(Vector3 scale) {
//		m[0][0] *= scale.x;
//		m[0][1] *= scale.x;
//		m[0][2] *= scale.x;
//		m[0][3] *= scale.x;
//		m[1][0] *= scale.y;
//		m[1][1] *= scale.y;
//		m[1][2] *= scale.y;
//		m[1][3] *= scale.y;
//		m[2][0] *= scale.z;
//		m[2][1] *= scale.z;
//		m[2][2] *= scale.z;
//		m[2][3] *= scale.z;
//		
//		return this;
//	}
//	
//
//	
//	public Matrix4 Orthographic2D(int width, int height) {
//		setIdentity();
//		m[0][0] = 2f / (float) width; 
//		m[1][1] = 2f / (float) height; 
//		
//		m[0][3] = -1;
//		m[1][3] = -1;
//		
//		return this;
//	}
//	
//	public Matrix4 OrthographicProjection(float l, float r, float b, float t, float n, float f) {
//		
//		m[0][0] = 2.0f/(r-l); 	m[0][1] = 0; 			m[0][2] = 0; 			m[0][3] = -(r+l)/(r-l);
//		m[1][0] = 0;			m[1][1] = 2.0f/(t-b); 	m[1][2] = 0; 			m[1][3] = -(t+b)/(t-b);
//		m[2][0] = 0; 			m[2][1] = 0; 			m[2][2] = 2.0f/(f-n); 	m[2][3] = -(f+n)/(f-n);
//		m[3][0] = 0; 			m[3][1] = 0; 			m[3][2] = 0; 			m[3][3] = 1;
//	
//		return this;
//	}
//	
//	public Matrix4 PerspectiveProjection(float fov, float width, float height, float nearPlane, float farPlane) {
//		setIdentity();
//		float tanFOV = (float) Math.tan(Math.toRadians(fov / 2));
//		float aspectRatio = width / height;
//		
//		m[0][0] = 1 / (tanFOV * aspectRatio);
//		m[1][1] = 1 / tanFOV;
//		m[2][2] = farPlane / (farPlane - nearPlane);
//		m[2][3] = farPlane * nearPlane / (farPlane - nearPlane);
//	
//		return this;
//	}
//	
//	public Matrix4 makeProjectionMatrix(float fov, float nearPlane, float farPlane, float width, float height) {
//		this.setIdentity();
//		float aspectRatio = width / height;
//		float y_scale = (float) (1f / Math.tan(Math.toRadians(fov / 2f)));
//		float x_scale = y_scale / aspectRatio;
//		float frustrum_length = farPlane - nearPlane;
//
//		this.m[0][0] = x_scale;
//		this.m[1][1] = y_scale;
//		this.m[2][2] = -((farPlane + nearPlane) / frustrum_length);
//		this.m[2][3] = -1;
//		this.m[3][2] = -((2 * nearPlane * farPlane) / frustrum_length);
//		this.m[3][3] = 0;
//		return this;
//	}
//	
//	public Matrix4 View(Vector3 forward, Vector3 up)	{
//		Vector3 f = forward;
//		Vector3 u = up;
//		Vector3 r = u.cross(f);
//		
//		m[0][0] = r.x; m[0][1] = r.y; m[0][2] = r.z; m[0][3] = 0;
//		m[1][0] = u.x; m[1][1] = u.y; m[1][2] = u.z; m[1][3] = 0;
//		m[2][0] = f.x; m[2][1] = f.y; m[2][2] = f.z; m[2][3] = 0;
//		m[3][0] = 0;   m[3][1] = 0;   m[3][2] = 0;   m[3][3] = 1;
//	
//		return this;
//	}
//	
//	public Matrix4 mul(Matrix4 matrix) {
//		Matrix4 result = new Matrix4();
//		
//		result.m[0][0] = m[0][0] * matrix.m[0][0] + m[1][0] * matrix.m[0][1] + m[2][0] * matrix.m[0][2] + m[3][0] * matrix.m[0][3];
//		result.m[0][1] = m[0][1] * matrix.m[0][0] + m[1][1] * matrix.m[0][1] + m[2][1] * matrix.m[0][2] + m[3][1] * matrix.m[0][3];
//		result.m[0][2] = m[0][2] * matrix.m[0][0] + m[1][2] * matrix.m[0][1] + m[2][2] * matrix.m[0][2] + m[3][2] * matrix.m[0][3];
//		result.m[0][3] = m[0][3] * matrix.m[0][0] + m[1][3] * matrix.m[0][1] + m[2][3] * matrix.m[0][2] + m[3][3] * matrix.m[0][3];
//		result.m[1][0] = m[0][0] * matrix.m[1][0] + m[1][0] * matrix.m[1][1] + m[2][0] * matrix.m[1][2] + m[3][0] * matrix.m[1][3];
//		result.m[1][1] = m[0][1] * matrix.m[1][0] + m[1][1] * matrix.m[1][1] + m[2][1] * matrix.m[1][2] + m[3][1] * matrix.m[1][3];
//		result.m[1][2] = m[0][2] * matrix.m[1][0] + m[1][2] * matrix.m[1][1] + m[2][2] * matrix.m[1][2] + m[3][2] * matrix.m[1][3];
//		result.m[1][3] = m[0][3] * matrix.m[1][0] + m[1][3] * matrix.m[1][1] + m[2][3] * matrix.m[1][2] + m[3][3] * matrix.m[1][3];
//		result.m[2][0] = m[0][0] * matrix.m[2][0] + m[1][0] * matrix.m[2][1] + m[2][0] * matrix.m[2][2] + m[3][0] * matrix.m[2][3];
//		result.m[2][1] = m[0][1] * matrix.m[2][0] + m[1][1] * matrix.m[2][1] + m[2][1] * matrix.m[2][2] + m[3][1] * matrix.m[2][3];
//		result.m[2][2] = m[0][2] * matrix.m[2][0] + m[1][2] * matrix.m[2][1] + m[2][2] * matrix.m[2][2] + m[3][2] * matrix.m[2][3];
//		result.m[2][3] = m[0][3] * matrix.m[2][0] + m[1][3] * matrix.m[2][1] + m[2][3] * matrix.m[2][2] + m[3][3] * matrix.m[2][3];
//		result.m[3][0] = m[0][0] * matrix.m[3][0] + m[1][0] * matrix.m[3][1] + m[2][0] * matrix.m[3][2] + m[3][0] * matrix.m[3][3];
//		result.m[3][1] = m[0][1] * matrix.m[3][0] + m[1][1] * matrix.m[3][1] + m[2][1] * matrix.m[3][2] + m[3][1] * matrix.m[3][3];
//		result.m[3][2] = m[0][2] * matrix.m[3][0] + m[1][2] * matrix.m[3][1] + m[2][2] * matrix.m[3][2] + m[3][2] * matrix.m[3][3];
//		result.m[3][3] = m[0][3] * matrix.m[3][0] + m[1][3] * matrix.m[3][1] + m[2][3] * matrix.m[3][2] + m[3][3] * matrix.m[3][3];
//		
//		copy(result);
//		
//		return this;
//	}
//	
//	public Quaternion mul(Quaternion q)	{
//		var result = new Quaternion(0, 0, 0, 0);
//		
//		result.x = m[0][0] * q.x + m[0][1] * q.y + m[0][2] * q.z + m[0][3] * q.w;
//		result.y = m[1][0] * q.x + m[1][1] * q.y + m[1][2] * q.z + m[1][3] * q.w;
//		result.z = m[2][0] * q.x + m[2][1] * q.y + m[2][2] * q.z + m[2][3] * q.w;
//		result.w = m[3][0] * q.x + m[3][1] * q.y + m[3][2] * q.z + m[3][3] * q.w;
//		
//		return result;
//	}
//	
//	public Matrix3 transpose() {
//		var result = new Matrix3();
//		
//		for (int i = 0; i < 4; i++)
//			for (int j = 0; j < 4; j++)
//				result.m[i][j] = m[j][i];
//		
//		copy(result);
//		
//		return this;
//	}
//	
//	public boolean equals(Matrix4 matrix) {
//		for (int i = 0; i < 3; i++)
//			for (int j = 0; j < 3; j++)
//				if (!Maths.equal(m[i][j], matrix.m[i][j])) return false;
//		
//		return true;	
//	}
//	
//	public Matrix3 load(FloatBuffer buf) {
//		
//		for (var i = 0; i < size; i++)
//			for (var j = 0; j < size; j++)
//				m[i][j] = buf.get();
//
//		return this;
//	}
//	
//	public Matrix3 store(FloatBuffer buf) {
//		for (var i = 0; i < size; i++)
//			for (var j = 0; j < size; j++)
//				buf.put(m[i][j]);
//		
//		return this;
//	}
//	
//	/**
//	 * Verticle visualization
//	 */
//	public String toString() {
//		
//		return 	"|" + m[0][0] + " " + m[1][0] + " " + m[2][0] + "|\n" +
//				"|" + m[0][1] + " " + m[1][1] + " " + m[2][1] + "|\n" +
//				"|" + m[0][2] + " " + m[1][2] + " " + m[2][2] + "|\n";
//	}
//	
//	public Matrix3 clone() {
//		return new Matrix3(this);
//	}
//}
