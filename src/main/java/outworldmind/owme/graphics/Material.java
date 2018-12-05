package outworldmind.owme.graphics;

import java.util.HashMap;
import java.util.Map;

import outworldmind.owme.tools.NumberGenerator;

public class Material {
	
	private Map<String, Texture> textures;
	private int id;
	
	public Material() {
		textures = new HashMap<String, Texture>();
		setId(NumberGenerator.generateId());
	}
	
	private void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public Material addTexture(String name, Texture texture) {
		this.textures.put(name, texture);
		
		return this;
	}
	
	public Texture getTetxture(String name) {
		return textures.get(name);
	}
	
	public void clear() {
		textures.values().forEach(Texture::destroy);
		textures.clear();
	}

}
