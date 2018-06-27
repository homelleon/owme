package outworldmind.owme.unit;

import outworldmind.owme.math.Transform;
import outworldmind.owme.tool.NumberGenerator;

public abstract class SceneUnit extends Node {
	
	private int id;
	private String name;
	
	protected Transform localTransform;
	protected Transform globalTransform;
	protected Transform currentTransform;
	
	protected SceneUnit(int id) {
		super();
		setId(id);
	}
	
	protected SceneUnit(int id, String name) {
		this(id);
		setName(name);
	}
	
	protected SceneUnit(String name) {
		this();
		setName(name);
	}
	
	protected SceneUnit() {
		this(NumberGenerator.generateId());
	}

	public int getId() {
		return id;
	}
	
	private void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	private void setName(String name) {
		this.name = name;
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
