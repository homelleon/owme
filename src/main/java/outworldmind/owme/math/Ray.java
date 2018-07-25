package outworldmind.owme.math;

public abstract class Ray<T extends Vector<T>> {
	
	public T start;
	public T direction;
	
	protected Ray() {}
	
	protected Ray(T start, T direction) {
		this.start = start;
		this.direction = direction;
	}

}
