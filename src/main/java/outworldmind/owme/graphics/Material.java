package outworldmind.owme.graphics;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import outworldmind.owme.maths.Color;
import outworldmind.owme.tools.NumberGenerator;

public class Material {
	
	public static final String AMBIENT = "ambient";
	public static final String DIFFUSE = "diffuse";
	public static final String SPECULAR = "specular";
	public static final String NORMAL = "normal";
	public static final String BUMP = "bump";
	public static final String ILLUMINATION = "illumination";
	public static final String SHINISESS = "shinisess";
	
	private Map<String, Texture> textures;
	private Map<String, Color> colors;
	private Map<String, Float> values;
	private String name;
	private int id;
	
	public Material(String name) {
		textures = new HashMap<String, Texture>();
		colors = new HashMap<String, Color>();
		values = new HashMap<String, Float>();
		id = NumberGenerator.generateId();
		this.name = name;
	}
	
	public Material() {
		this("noname");
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Material addTexture(String name, Texture texture) {
		textures.put(name, texture);
		
		return this;
	}
	
	public Texture getTexture(String name) {
		return textures.get(name);
	}
	
	public Collection<Texture> getTextures() {
		return textures.values();
	}
	
	public Material addColor(String name, Color color) {
		colors.put(name, color);
		
		return this;
	}
	
	public Color getColor(String name) {
		return colors.get(name);
	}
	
	public Collection<Color> getColors() {
		return colors.values();
	}
	
	public Material addValue(String name, float value) {
		values.put(name, value);
		
		return this;
	}
	
	public float getValue(String name) {
		return values.get(name);
	}
	
	public void clear() {
		textures.values().forEach(Texture::destroy);
		textures.clear();
		colors.clear();
		values.clear();
	}

}
