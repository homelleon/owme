package outworldmind.owme.unit;

import outworldmind.owme.math.Transform;
import outworldmind.owme.tool.NumberGenerator;

public abstract class SceneUnit extends Node {
	
	private int id;
	
	protected Transform localTransform;
	protected Transform globalTransform;
	protected Transform currentTransform;
	
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
	
	public Transform getCurrentTransform() {
		return currentTransform;
	}
	
	public SceneUnit loadGlobalTransform() {
		currentTransform = globalTransform;
		
		return this;
	}
	
	public SceneUnit loadLocalTransform() {
		currentTransform = localTransform;
		
		return this;
	}
}
