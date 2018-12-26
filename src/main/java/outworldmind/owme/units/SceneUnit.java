package outworldmind.owme.units;

import outworldmind.owme.maths.Matrix4;
import outworldmind.owme.maths.Rotation;
import outworldmind.owme.maths.Transform;
import outworldmind.owme.maths.Vector3;
import outworldmind.owme.tools.NumberGenerator;

public class SceneUnit extends Node {
	
	private int id;
	
	protected Transform localTransform = new Transform();
	protected Transform globalTransform = new Transform();
	
	protected SceneUnit() {
		super();
		setId(NumberGenerator.generateId());
	}

	public int getId() {
		return id;
	}
	
	private void setId(int id) {
		this.id = id;
	}
	
	public Matrix4 getLocalTransformMatrix() {
		return localTransform.getMatrix();
	}
	
	public Matrix4 getTransformMatrix() {
		return globalTransform.getMatrix();
	}
	
	public SceneUnit update() {
//		localTransform.update();
		globalTransform.update();
		
		return this;
	}
	
	public SceneUnit move(Vector3 distance) {
		globalTransform.offsetPosition(distance);
		
		return this;
	}
	
	public SceneUnit moveTo(Vector3 position) {
		globalTransform.setPosition(position);
		
		return this;
	}
	
	public SceneUnit rotate(Rotation rotation) {
		globalTransform.offsetRotation(rotation);
		
		return this;
	}
	
	public SceneUnit rotateTo(Rotation rotation) {
		globalTransform.setRotation(rotation);
		
		return this;
	}
	
	public SceneUnit scale(Vector3 scale) {
		globalTransform.offsetScale(scale);
		
		return this;
	}
	
	public SceneUnit scaleTo(Vector3 scale) {
		globalTransform.setScale(scale);
		
		return this;
	}
	
	public Vector3 getPosition() {
		return globalTransform.getPosition();
	}
	
	public Rotation getRotation() {
		return globalTransform.getRotation();
	}
	
	public Vector3 getScale() {
		return globalTransform.getScale();
	}
	
	public Vector3 getLocalPosition() {
		return localTransform.getPosition();
	}
	
	public Vector3 getLocalRotation() {
		return localTransform.getRotation();
	}
	
	public Vector3 getLocalScale() {
		return localTransform.getScale();
	}
	
	@Override
	public SceneUnit clone() {
		return new SceneUnit()
				.scaleTo(getScale())
				.moveTo(getPosition())
				.rotateTo(getRotation());
	}
}
