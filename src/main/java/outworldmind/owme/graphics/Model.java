package outworldmind.owme.graphics;

import outworldmind.owme.tools.NumberGenerator;

public class Model {
	
	private int id;
	private String name;
	private Geometry geometry;
	private Shader shader;
	private Renderer renderer;
	private Material material;
	
	public Model(String name) {
		id = NumberGenerator.generateId();
		this.name = name;
	}
	
	public Model() {
		this("noname");
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
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
