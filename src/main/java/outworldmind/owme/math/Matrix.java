package outworldmind.owme.math;

import java.nio.FloatBuffer;

public abstract class Matrix {
	
	protected int size;
	public float[][] m;
	
	protected Matrix(int size) {
		setSize(size);
		copy(new float[size][size]);
		setIdentity();
	}
	
	private void setSize(int size) {
		this.size = size;
	}
	
	protected Matrix copy(float[][] m) {
		if (m.length != size || m[0].length != size)
			throw new IllegalArgumentException(getClass().getSimpleName() + 
					".copy - size of array must be " + size);
		
		this.m = m.clone();
		
		return this;
	}
	
	protected Matrix zero() {
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				m[i][j] = 0.0f;
	
		return this;
	}
	
	protected Matrix setIdentity() {
		zero();
		for (int i = 0; i < size; i++)
			m[i][i] = 1.0f;
	
		return this;
	}
	
	protected Matrix transpose() {
		var result = new Matrix4();
		
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				result.m[i][j] = m[j][i];
		
		copy(result.m);
		
		return this;
	}
	
	protected Matrix store(FloatBuffer buf) {
		for (var i = 0; i < size; i++)
			for (var j = 0; j < size; j++)
				buf.put(m[i][j]);
		
		return this;
	}
	
	protected Matrix load(FloatBuffer buf) {		
		for (var i = 0; i < size; i++)
			for (var j = 0; j < size; j++)
				m[i][j] = buf.get();

		return this;
	}
	
	public boolean equals(Matrix matrix) {
		if (matrix == null || !getClass().equals(matrix.getClass())) return false;
		
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (!Maths.equal(m[i][j], matrix.m[i][j])) return false;
		
		return true;	
	}
	
	public abstract Matrix clone();
	public abstract String toString();

}
