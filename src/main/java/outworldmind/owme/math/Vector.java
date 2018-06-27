package outworldmind.owme.math;

public abstract class Vector<T> {
	
	public abstract T clone();
	public abstract T add(T vector);
	public abstract T sub(T vector);
	public abstract T mul(T vector);
	public abstract T div(T vector);
	public abstract T add(float value);
	public abstract T sub(float value);
	public abstract T mul(float value);
	public abstract T div(float value);
}
