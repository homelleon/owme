package outworldmind.owme.maths;

public class Transform {

	private Vector3 position;
	private Vector3 scale;
	private Rotation rotation;
	private Matrix4 matrix = new Matrix4();
	
	private boolean needUpdate = true;
	
	public Transform() {
		this(new Vector3(), new Vector3(1), new Rotation());
	}
	
	public Transform(Vector3 position, Vector3 scale, Rotation rotation) {
		this.position = position.clone();
		this.scale = scale.clone();
		this.rotation = rotation.clone();
	}
	
	/**
	 * Gets position
	 * 
	 * @return {@link Vector3} copy of position vector
	 */
	public Vector3 getPosition() {
		return position.clone();
	}
	
	public Transform setPosition(Vector3 position) {
		this.position.copy(position);
		needUpdate = true;
		
		return this;
	}
	
	public Transform offsetPosition(Vector3 position) {
		this.position.add(position);
		needUpdate = true;
		
		return this;
	}
	
	/**
	 * Gets scale.
	 * 
	 * @return {@link Vector3} copy of scale vector
	 */
	public Vector3 getScale() {
		return scale.clone();
	}
	
	public Transform setScale(Vector3 scale) {
		this.scale.copy(scale);
		needUpdate = true;
		
		return this;
	}
	
	public Transform offsetScale(Vector3 scale) {
		this.scale.add(scale);
		needUpdate = true;
		
		return this;
	} 
	
	/**
	 * Gets rotation.
	 * 
	 * @return {@link Rotation} copy of Rotation object
	 */
	public Rotation getRotation() {
		return rotation.clone();
	}
	
	public Transform setRotation(Vector3 rotation) {
		this.rotation.copy(rotation);
		needUpdate = true;
		
		return this;
	}
	
	public Transform offsetRotation(Rotation rotation) {
		this.rotation.add(rotation);
		needUpdate = true;
		
		return this;
	}
	
	/**
	 * Gets current transformation matrix
	 * 
	 * @return {@link Matrix4} copy of 4x4 matrix
	 */
	public Matrix4 getMatrix() {
		return matrix.clone();
	}
	
	/**
	 * Updates transformation matrix.
	 * 
	 * @return {@link Transform} current Transform object
	 */
	public Transform update() {
		if (!needUpdate) return this;
		
		matrix
			.setIdentity()
			.translate(position)
			.rotate(rotation.getRadians())
			.scale(scale);
		
		needUpdate = false;
		
		return this;
	}
	
}
