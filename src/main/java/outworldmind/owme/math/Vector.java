package outworldmind.owme.math;

public abstract class Vector<T> {
	
	public abstract T add(T vector);
	public abstract T sub(T vector);
	public abstract T mul(T vector);
	public abstract T div(T vector);
	public abstract T add(Float value);
	public abstract T sub(Float value);
	public abstract T mul(Float value);
	public abstract T div(Float value);
	
	public final float length() {
		return (float) Math.sqrt(lengthSquared());
	}
	
	public abstract float lengthSquared();
	public abstract T clone();
}
