package outworldmind.owme.graphics;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

public class TextureBuilder {
	
	private String path;
	private String name;
	private int type = GL_TEXTURE_2D;
	private int width = 1;
	private int height = 1;
	private int bindLocation = -1;
	
	public TextureBuilder setPath(String path) {
		this.path = path;
		
		return this;
	}
	
	public TextureBuilder setName(String name) {
		this.name = name;
		
		return this;
	}
	
	public TextureBuilder setWidth(int width) {
		this.width = width;
		
		return this;
	}
	
	public TextureBuilder setHeight(int height) {
		this.height = height;
		
		return this;
	}
	
	public TextureBuilder setType(int type) {
		this.type = type;
		
		return this;
	}
	
	public TextureBuilder setBindLocation(int location) {
		bindLocation = location;
		
		return this;
	}
	
	public Texture build() {
		var texture = new Texture(name, type, width, height);
		texture.load(path);
		if (bindLocation > -1) 
			texture.setBindLocation(bindLocation);
		return texture;
	}	
}
