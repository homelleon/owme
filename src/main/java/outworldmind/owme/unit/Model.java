package outworldmind.owme.unit;

import outworldmind.owme.tool.NumberGenerator;

public class Model {
	
	private int id;
	private Mesh mesh;
	private Material material;
	
	public Model() {
		setId(NumberGenerator.generateId());
	}
	
	private void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public Model setMesh(Mesh mesh) {
		this.mesh = mesh;
		
		return this;
	}
	
	public Model setMaterial(Material material) {
		this.material = material;
		
		return this;
	}
	
	public Material getMaterial() {
		return material;
	}
	

}
