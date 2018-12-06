package outworldmind.owme.graphics;

import outworldmind.owme.tools.NumberGenerator;

public class Model {
	
	private int id;
	private Geometry geometry;
	private Shader shader;
	private Renderer renderer;
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
	public Model setGeometry(Geometry geometry) {
		this.geometry = geometry;
		
		return this;
	}
	
	public Geometry getGeometry() {
		return geometry;
	}
	
	
	public Shader getShader() {
		return shader;
	}

	public Model setShader(Shader shader) {
		this.shader = shader;
		
		return this;
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public Model setRenderer(Renderer renderer) {
		this.renderer = renderer;
		
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
