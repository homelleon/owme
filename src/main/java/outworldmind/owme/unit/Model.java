package outworldmind.owme.unit;

public class Model extends DrawUnit {
	
	private Mesh mesh;
	private Material material;
	
	public Model() {
		super("model");
	}
	
	public Model(String name) {
		super(name);
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
