package outworldmind.owme.math;

import java.nio.FloatBuffer;


public class Matrix4 {

	private final int size = 4;
	public float[][] m;
	
	public Matrix4() {
		copy(new float[size][size]);
		setIdentity();
	}
	
	public Matrix4(Matrix4 matrix) {
		copy(matrix.m.clone());
	}
	
	
	public Matrix4 copy(float[][] m) {
		if (m.length != size || m[0].length != size)
			throw new IllegalArgumentException("Matrix4.copy - size of array must be " + size);
		
		this.m = m.clone();
		
		return this;
	}
	
	
	public Matrix4 copy(Matrix4 matrix) {
		m = matrix.m.clone();
		
		return this;
	}
	
	public Matrix4 zero() {
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				m[i][j] = 0.0f;
	
		return this;
	}
	
	public Matrix4 setIdentity() {
		zero();
		for (int i = 0; i < size; i++)
			m[i][i] = 1.0f;
	
		return this;
	}
	
	public Matrix4 invert() {
		var determinant = determinant();
		
		if (determinant == 0)
			return null;
		
		/*
		 * m00 m01 m02 m03
		 * m10 m11 m12 m13
		 * m20 m21 m22 m23
		 * m30 m31 m32 m33
		 */
		var determinant_inv = 1f / determinant;

		// first row
		var t00 =  determinant3x3(m[1][1], m[1][2], m[1][3], m[2][1], m[2][2], m[2][3], m[3][1], m[3][2], m[3][3]);
		var t01 = -determinant3x3(m[1][0], m[1][2], m[1][3], m[2][0], m[2][2], m[2][3], m[3][0], m[3][2], m[3][3]);
		var t02 =  determinant3x3(m[1][0], m[1][1], m[1][3], m[2][0], m[2][1], m[2][3], m[3][0], m[3][1], m[3][3]);
		var t03 = -determinant3x3(m[1][0], m[1][1], m[1][2], m[2][0], m[2][1], m[2][2], m[3][0], m[3][1], m[3][2]);
		// second row
		var t10 = -determinant3x3(m[0][1], m[0][2], m[0][3], m[2][1], m[2][2], m[2][3], m[3][1], m[3][2], m[3][3]);
		var t11 =  determinant3x3(m[0][0], m[0][2], m[0][3], m[2][0], m[2][2], m[2][3], m[3][0], m[3][2], m[3][3]);
		var t12 = -determinant3x3(m[0][0], m[0][1], m[0][3], m[2][0], m[2][1], m[2][3], m[3][0], m[3][1], m[3][3]);
		var t13 =  determinant3x3(m[0][0], m[0][1], m[0][2], m[2][0], m[2][1], m[2][2], m[3][0], m[3][1], m[3][2]);
		// third row
		var t20 =  determinant3x3(m[0][1], m[0][2], m[0][3], m[1][1], m[1][2], m[1][3], m[3][1], m[3][2], m[3][3]);
		var t21 = -determinant3x3(m[0][0], m[0][2], m[0][3], m[1][0], m[1][2], m[1][3], m[3][0], m[3][2], m[3][3]);
		var t22 =  determinant3x3(m[0][0], m[0][1], m[0][3], m[1][0], m[1][1], m[1][3], m[3][0], m[3][1], m[3][3]);
		var t23 = -determinant3x3(m[0][0], m[0][1], m[0][2], m[1][0], m[1][1], m[1][2], m[3][0], m[3][1], m[3][2]);
		// fourth row
		var t30 = -determinant3x3(m[0][1], m[0][2], m[0][3], m[1][1], m[1][2], m[1][3], m[2][1], m[2][2], m[2][3]);
		var t31 =  determinant3x3(m[0][0], m[0][2], m[0][3], m[1][0], m[1][2], m[1][3], m[2][0], m[2][2], m[2][3]);
		var t32 = -determinant3x3(m[0][0], m[0][1], m[0][3], m[1][0], m[1][1], m[1][3], m[2][0], m[2][1], m[2][3]);
		var t33 =  determinant3x3(m[0][0], m[0][1], m[0][2], m[1][0], m[1][1], m[1][2], m[2][0], m[2][1], m[2][2]);

		// transpose and divide by the determinant
		m[0][0] = t00 * determinant_inv;
		m[1][1] = t11 * determinant_inv;
		m[2][2] = t22 * determinant_inv;
		m[3][3] = t33 * determinant_inv;
		m[0][1] = t10 * determinant_inv;
		m[1][0] = t01 * determinant_inv;
		m[2][0] = t02 * determinant_inv;
		m[0][2] = t20 * determinant_inv;
		m[1][2] = t21 * determinant_inv;
		m[2][1] = t12 * determinant_inv;
		m[0][3] = t30 * determinant_inv;
		m[3][0] = t03 * determinant_inv;
		m[1][3] = t31 * determinant_inv;
		m[3][1] = t13 * determinant_inv;
		m[3][2] = t23 * determinant_inv;
		m[2][3] = t32 * determinant_inv;
			
		return this;
	}
	
	private static float determinant3x3(float t00, float t01, float t02,
		     float t10, float t11, float t12,
		     float t20, float t21, float t22)
	{
		return   t00 * (t11 * t22 - t12 * t21)
		      + t01 * (t12 * t20 - t10 * t22)
		      + t02 * (t10 * t21 - t11 * t20);
	}
	
	public float determinant() {
		var f =
			m[0][0]
				* ((m[1][1] * m[2][2] * m[3][3] + m[1][2] * m[2][3] * m[3][1] + m[1][3] * m[2][1] * m[3][2])
					- m[1][3] * m[2][2] * m[3][1]
					- m[1][1] * m[2][3] * m[3][2]
					- m[1][2] * m[2][1] * m[3][3]);
		f -= m[0][1]
			* ((m[1][0] * m[2][2] * m[3][3] + m[1][2] * m[2][3] * m[3][0] + m[1][3] * m[2][0] * m[3][2])
				- m[1][3] * m[2][2] * m[3][0]
				- m[1][0] * m[2][3] * m[3][2]
				- m[1][2] * m[2][0] * m[3][3]);
		f += m[0][2]
			* ((m[1][0] * m[2][1] * m[3][3] + m[1][1] * m[2][3] * m[3][0] + m[1][3] * m[2][0] * m[3][1])
				- m[1][3] * m[2][1] * m[3][0]
				- m[1][0] * m[2][3] * m[3][1]
				- m[1][1] * m[2][0] * m[3][3]);
		f -= m[0][3]
			* ((m[1][0] * m[2][1] * m[3][2] + m[1][1] * m[2][2] * m[3][0] + m[1][2] * m[2][0] * m[3][1])
				- m[1][2] * m[2][1] * m[3][0]
				- m[1][0] * m[2][2] * m[3][1]
				- m[1][1] * m[2][0] * m[3][2]);
		return f;
	}
	
	public Matrix4 translate(Vector3 translation)	{
		m[3][0] += m[0][0] * translation.x + m[1][0] * translation.y + m[2][0] * translation.z;
		m[3][1] += m[0][1] * translation.x + m[1][1] * translation.y + m[2][1] * translation.z;
		m[3][2] += m[0][2] * translation.x + m[1][2] * translation.y + m[2][2] * translation.z;
		m[3][3] += m[0][3] * translation.x + m[1][3] * translation.y + m[2][3] * translation.z;
	
		return this;
	}
	
	public Matrix4 translate(Vector2 translation) {		
		m[3][0] += m[0][0] * translation.x + m[1][0] * translation.y;
		m[3][1] += m[0][1] * translation.x + m[1][1] * translation.y;
		m[3][2] += m[0][2] * translation.x + m[1][2] * translation.y;
		m[3][3] += m[0][3] * translation.x + m[1][3] * translation.y;
		
		return this;
	}
	
	public Matrix4 rotate(Vector3 rotation) {
		var rx = new Matrix4();
		var ry = new Matrix4();
		var rz = new Matrix4();
		
		var x = (float) Math.toRadians(rotation.x);
		var y = (float) Math.toRadians(rotation.y);
		var z = (float) Math.toRadians(rotation.z);
		
		rz.m[0][0] = (float)Math.cos(z); rz.m[0][1] = -(float)Math.sin(z); 	 rz.m[0][2] = 0; 				   rz.m[0][3] = 0;
		rz.m[1][0] = (float)Math.sin(z); rz.m[1][1] = (float)Math.cos(z);  	 rz.m[1][2] = 0; 				   rz.m[1][3] = 0;
		rz.m[2][0] = 0; 				 rz.m[2][1] = 0; 				   	 rz.m[2][2] = 1; 				   rz.m[2][3] = 0;
		rz.m[3][0] = 0; 				 rz.m[3][1] = 0; 				   	 rz.m[3][2] = 0; 				   rz.m[3][3] = 1;
		
		rx.m[0][0] = 1; 				 rx.m[0][1] = 0;					 rx.m[0][2] = 0; 				   rx.m[0][3] = 0;
		rx.m[1][0] = 0; 				 rx.m[1][1] = (float)Math.cos(x); 	 rx.m[1][2] = -(float)Math.sin(x); rx.m[1][3] = 0;
		rx.m[2][0] = 0; 				 rx.m[2][1] = (float)Math.sin(x); 	 rx.m[2][2] = (float)Math.cos(x);  rx.m[2][3] = 0;
		rx.m[3][0] = 0; 				 rx.m[3][1] = 0; 				 	 rx.m[3][2] = 0;				   rx.m[3][3] = 1;
		
		ry.m[0][0] = (float)Math.cos(y); ry.m[0][1] = 0; 					 ry.m[0][2] = (float)Math.sin(y);  ry.m[0][3] = 0;
		ry.m[1][0] = 0; 				 ry.m[1][1] = 1; 				 	 ry.m[1][2] = 0; 				   ry.m[1][3] = 0;
		ry.m[2][0] = -(float)Math.sin(y);ry.m[2][1] = 0;					 ry.m[2][2] = (float)Math.cos(y);  ry.m[2][3] = 0;
		ry.m[3][0] = 0; 				 ry.m[3][1] = 0; 					 ry.m[3][2] = 0; 				   ry.m[3][3] = 1;
	
		m =  rz.mul(ry.mul(rx)).m;
		
		return this;
	}
	
	public Matrix4 rotate(float angle, Vector3 axis) {		
		var c = (float) Math.cos(angle);
		var s = (float) Math.sin(angle);
		var oneminusc = 1.0f - c;
		var xy = axis.x * axis.y;
		var yz = axis.y * axis.z;
		var xz = axis.x * axis.z;
		var xs = axis.x * s;
		var ys = axis.y * s;
		var zs = axis.z * s;

		var f00 = axis.x * axis.x * oneminusc + c;
		var f01 = xy * oneminusc + zs;
		var f02 = xz * oneminusc - ys;
		// n[3] not used
		var f10 = xy * oneminusc - zs;
		var f11 = axis.y * axis.y * oneminusc + c;
		var f12 = yz * oneminusc + xs;
		// n[7] not used
		var f20 = xz * oneminusc + ys;
		var f21 = yz * oneminusc - xs;
		var f22 = axis.z * axis.z * oneminusc + c;

		var t00 = m[0][0] * f00 + m[1][0] * f01 + m[2][0] * f02;
		var t01 = m[0][1] * f00 + m[1][1] * f01 + m[2][1] * f02;
		var t02 = m[0][2] * f00 + m[1][2] * f01 + m[2][2] * f02;
		var t03 = m[0][3] * f00 + m[1][3] * f01 + m[2][3] * f02;
		var t10 = m[0][0] * f10 + m[1][0] * f11 + m[2][0] * f12;
		var t11 = m[0][1] * f10 + m[1][1] * f11 + m[2][1] * f12;
		var t12 = m[0][2] * f10 + m[1][2] * f11 + m[2][2] * f12;
		var t13 = m[0][3] * f10 + m[1][3] * f11 + m[2][3] * f12;
		
		
		m[2][0] = m[0][0] * f20 + m[1][0] * f21 + m[2][0] * f22;
		m[2][1] = m[0][1] * f20 + m[1][1] * f21 + m[2][1] * f22;
		m[2][2] = m[0][2] * f20 + m[1][2] * f21 + m[2][2] * f22;
		m[2][3] = m[0][3] * f20 + m[1][3] * f21 + m[2][3] * f22;
		m[0][0] = t00;
		m[0][1] = t01;
		m[0][2] = t02;
		m[0][3] = t03;
		m[1][0] = t10;
		m[1][1] = t11;
		m[1][2] = t12;
		m[1][3] = t13;
		
		return this;
	}
	
	public Matrix4 scale(Vector3 scale) {
		m[0][0] *= scale.x;
		m[0][1] *= scale.x;
		m[0][2] *= scale.x;
		m[0][3] *= scale.x;
		m[1][0] *= scale.y;
		m[1][1] *= scale.y;
		m[1][2] *= scale.y;
		m[1][3] *= scale.y;
		m[2][0] *= scale.z;
		m[2][1] *= scale.z;
		m[2][2] *= scale.z;
		m[2][3] *= scale.z;
		
		return this;
	}
	

	
	public Matrix4 Orthographic2D(int width, int height) {
		setIdentity();
		m[0][0] = 2f / (float) width; 
		m[1][1] = 2f / (float) height; 
		
		m[0][3] = -1;
		m[1][3] = -1;
		
		return this;
	}
	
	public Matrix4 OrthographicProjection(float l, float r, float b, float t, float n, float f) {
		
		m[0][0] = 2.0f/(r-l); 	m[0][1] = 0; 			m[0][2] = 0; 			m[0][3] = -(r+l)/(r-l);
		m[1][0] = 0;			m[1][1] = 2.0f/(t-b); 	m[1][2] = 0; 			m[1][3] = -(t+b)/(t-b);
		m[2][0] = 0; 			m[2][1] = 0; 			m[2][2] = 2.0f/(f-n); 	m[2][3] = -(f+n)/(f-n);
		m[3][0] = 0; 			m[3][1] = 0; 			m[3][2] = 0; 			m[3][3] = 1;
	
		return this;
	}
	
	public Matrix4 PerspectiveProjection(float fov, float width, float height, float nearPlane, float farPlane) {
		setIdentity();
		var tanFOV = (float) Math.tan(Math.toRadians(fov / 2));
		var aspectRatio = width / height;
		
		m[0][0] = 1 / (tanFOV * aspectRatio);
		m[1][1] = 1 / tanFOV;
		m[2][2] = farPlane / (farPlane - nearPlane);
		m[2][3] = farPlane * nearPlane / (farPlane - nearPlane);
	
		return this;
	}
	
	public Matrix4 makeProjectionMatrix(float fov, float nearPlane, float farPlane, float width, float height) {
		this.setIdentity();
		var aspectRatio = width / height;
		var y_scale = (float) (1f / Math.tan(Math.toRadians(fov / 2f)));
		var x_scale = y_scale / aspectRatio;
		var frustrum_length = farPlane - nearPlane;

		this.m[0][0] = x_scale;
		this.m[1][1] = y_scale;
		this.m[2][2] = -((farPlane + nearPlane) / frustrum_length);
		this.m[2][3] = -1;
		this.m[3][2] = -((2 * nearPlane * farPlane) / frustrum_length);
		this.m[3][3] = 0;
		return this;
	}
	
	public Matrix4 View(Vector3 forward, Vector3 up)	{
		var f = forward;
		var u = up;
		var r = u.cross(f);
		
		m[0][0] = r.x; m[0][1] = r.y; m[0][2] = r.z; m[0][3] = 0;
		m[1][0] = u.x; m[1][1] = u.y; m[1][2] = u.z; m[1][3] = 0;
		m[2][0] = f.x; m[2][1] = f.y; m[2][2] = f.z; m[2][3] = 0;
		m[3][0] = 0;   m[3][1] = 0;   m[3][2] = 0;   m[3][3] = 1;
	
		return this;
	}
	
	public Matrix4 mul(Matrix4 matrix) {
		var result = new Matrix4();
		
		result.m[0][0] = m[0][0] * matrix.m[0][0] + m[1][0] * matrix.m[0][1] + m[2][0] * matrix.m[0][2] + m[3][0] * matrix.m[0][3];
		result.m[0][1] = m[0][1] * matrix.m[0][0] + m[1][1] * matrix.m[0][1] + m[2][1] * matrix.m[0][2] + m[3][1] * matrix.m[0][3];
		result.m[0][2] = m[0][2] * matrix.m[0][0] + m[1][2] * matrix.m[0][1] + m[2][2] * matrix.m[0][2] + m[3][2] * matrix.m[0][3];
		result.m[0][3] = m[0][3] * matrix.m[0][0] + m[1][3] * matrix.m[0][1] + m[2][3] * matrix.m[0][2] + m[3][3] * matrix.m[0][3];
		result.m[1][0] = m[0][0] * matrix.m[1][0] + m[1][0] * matrix.m[1][1] + m[2][0] * matrix.m[1][2] + m[3][0] * matrix.m[1][3];
		result.m[1][1] = m[0][1] * matrix.m[1][0] + m[1][1] * matrix.m[1][1] + m[2][1] * matrix.m[1][2] + m[3][1] * matrix.m[1][3];
		result.m[1][2] = m[0][2] * matrix.m[1][0] + m[1][2] * matrix.m[1][1] + m[2][2] * matrix.m[1][2] + m[3][2] * matrix.m[1][3];
		result.m[1][3] = m[0][3] * matrix.m[1][0] + m[1][3] * matrix.m[1][1] + m[2][3] * matrix.m[1][2] + m[3][3] * matrix.m[1][3];
		result.m[2][0] = m[0][0] * matrix.m[2][0] + m[1][0] * matrix.m[2][1] + m[2][0] * matrix.m[2][2] + m[3][0] * matrix.m[2][3];
		result.m[2][1] = m[0][1] * matrix.m[2][0] + m[1][1] * matrix.m[2][1] + m[2][1] * matrix.m[2][2] + m[3][1] * matrix.m[2][3];
		result.m[2][2] = m[0][2] * matrix.m[2][0] + m[1][2] * matrix.m[2][1] + m[2][2] * matrix.m[2][2] + m[3][2] * matrix.m[2][3];
		result.m[2][3] = m[0][3] * matrix.m[2][0] + m[1][3] * matrix.m[2][1] + m[2][3] * matrix.m[2][2] + m[3][3] * matrix.m[2][3];
		result.m[3][0] = m[0][0] * matrix.m[3][0] + m[1][0] * matrix.m[3][1] + m[2][0] * matrix.m[3][2] + m[3][0] * matrix.m[3][3];
		result.m[3][1] = m[0][1] * matrix.m[3][0] + m[1][1] * matrix.m[3][1] + m[2][1] * matrix.m[3][2] + m[3][1] * matrix.m[3][3];
		result.m[3][2] = m[0][2] * matrix.m[3][0] + m[1][2] * matrix.m[3][1] + m[2][2] * matrix.m[3][2] + m[3][2] * matrix.m[3][3];
		result.m[3][3] = m[0][3] * matrix.m[3][0] + m[1][3] * matrix.m[3][1] + m[2][3] * matrix.m[3][2] + m[3][3] * matrix.m[3][3];
		
		copy(result);
		
		return this;
	}
	
	public Vector3 mul(Vector3 vector) {
		var x = m[0][0] * vector.x + m[0][1] * vector.y + m[0][2] * vector.z + m[0][3] * 1.0;
		var y = m[1][0] * vector.x + m[1][1] * vector.y + m[1][2] * vector.z + m[1][3] * 1.0; 
		var z = m[2][0] * vector.x + m[2][1] * vector.y + m[2][2] * vector.z + m[2][3] * 1.0;
		var w = m[3][0] * vector.x + m[3][1] * vector.y + m[3][2] * vector.z + m[3][3] * 1.0;
				
		return new Vector3(x / w, y / w, z / w);
	}
	
	public Quaternion mul(Quaternion q)	{
		var result = new Quaternion(0, 0, 0, 0);
		
		result.x = m[0][0] * q.x + m[0][1] * q.y + m[0][2] * q.z + m[0][3] * q.w;
		result.y = m[1][0] * q.x + m[1][1] * q.y + m[1][2] * q.z + m[1][3] * q.w;
		result.z = m[2][0] * q.x + m[2][1] * q.y + m[2][2] * q.z + m[2][3] * q.w;
		result.w = m[3][0] * q.x + m[3][1] * q.y + m[3][2] * q.z + m[3][3] * q.w;
		
		return result;
	}
	
	public Matrix4 transpose() {
		var result = new Matrix4();
		
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				result.m[i][j] = m[j][i];
		
		copy(result);
		
		return this;
	}
	
	public boolean equals(Matrix4 matrix) {
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (!Maths.equal(m[i][j], matrix.m[i][j])) return false;
		
		return true;	
	}
	
	public Matrix4 load(FloatBuffer buf) {
		
		for (var i = 0; i < size; i++)
			for (var j = 0; j < size; j++)
				m[i][j] = buf.get();

		return this;
	}
	
	public Matrix4 store(FloatBuffer buf) {
		for (var i = 0; i < size; i++)
			for (var j = 0; j < size; j++)
				buf.put(m[i][j]);
		
		return this;
	}
	
	/**
	 * Verticle visualization
	 */
	public String toString() {
		
		return 	"|" + m[0][0] + " " + m[1][0] + " " + m[2][0] + " " + m[3][0] + "|\n" +
				"|" + m[0][1] + " " + m[1][1] + " " + m[2][1] + " " + m[3][1] + "|\n" +
				"|" + m[0][2] + " " + m[1][2] + " " + m[2][2] + " " + m[3][2] + "|\n" +
				"|" + m[0][3] + " " + m[1][3] + " " + m[2][3] + " " + m[3][3] + "|";
	}
	
//	/**
//	 * Horizontal visualization
//	 */
//	public String toString() {
//		
//		return 	"|" + m[0][0] + " " + m[0][1] + " " + m[0][2] + " " + m[0][3] + "|\n" +
//				"|" + m[1][0] + " " + m[1][1] + " " + m[1][2] + " " + m[1][3] + "|\n" +
//				"|" + m[2][0] + " " + m[2][1] + " " + m[2][2] + " " + m[2][3] + "|\n" +
//				"|" + m[3][0] + " " + m[3][1] + " " + m[3][2] + " " + m[3][3] + "|";
//	}
	
	public Matrix4 clone() {
		return new Matrix4(this);
	}
}
