package outworldmind.owme.maths;

public abstract class Transform {

	protected Vector3 position;
	protected Vector3 scale;
	protected Rotation rotation;
	protected Matrix4 transformation;
	
	public Transform(Vector3 position, Vector3 scale, Rotation rotation) {
		this.position = position;
		this.scale = scale;
		this.rotation = rotation;
	}
	
	public Transform() {
		this.position = new Vector3();
		this.scale = new Vector3();
		this.rotation = new Rotation();
	}
	
	public Vector3 getPosition() {
		return position;
	}
	
	public Transform setPosition(Vector3 position) {
		this.position = position;
		
		return this;
	}
	
	public Transform offsetPosition(Vector3 position) {
		this.position.add(position);
		
		return this;
	}
	
	public Vector3 getScale() {
		return scale;
	}
	
	public Transform setScale(Vector3 scale) {
		this.scale = scale;
		
		return this;
	}
	
	public Rotation getRotation() {
		return rotation;
	}
	
	public Transform setRotation(Rotation rotation) {
		this.rotation = rotation;
		
		return this;
	}
	
	public Transform offsetRotation(Rotation rotation) {
		this.rotation.add(rotation);
		
		return this;
	}
	
	public Matrix4 getMatrix() {
		return transformation;
	}
	
	public Transform update() {
		//TODO: impl update
		transformation.scale(scale);
		transformation.translate(position);
		
		return this;
	}
	
}
