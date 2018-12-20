package outworldmind.owme.graphics;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;

import java.awt.image.BufferedImage;

import outworldmind.owme.tools.ImageLoader;
import outworldmind.owme.tools.TextureUtil;

public class Texture {
	
	public static final int FILTER_NONE = 0;
	public static final int FILTER_BILINEAR = 1;
	
	public static final int WRAP_NONE = 0;
	public static final int WRAP_REPEAT = 1;
	public static final int WRAP_MIRROR_REPEAT = 2;
	
	private int id;
	private String name;
	private int type;
	private int width;
	private int height;
	private int filterMode = FILTER_NONE;
	private int wrapMode = WRAP_NONE;
	private BufferedImage image;
	private boolean initialized = false;
	
	public Texture() {
		this(null);
	}
	
	public Texture(String name) {
		this(name, 1, 1);
	}
	
	public Texture(int width, int height) {
		this(null, width, height);
	}
	
	public Texture(int type, int width, int height) {
		this(null, type, width, height);
	}
	
	public Texture(String name, int width, int height) {
		this(name, GL_TEXTURE_2D, width, height);
	}
	
	public Texture(String name, int type, int width, int height) {
		this.name = name == null ? "noname" : name;
		this.type = type;
		this.width = width;
		this.height = height;
	}
	
	private void generateTexture() {
		id = glGenTextures();
	}
	
	public void init() {
		if (initialized) return;
		initialized = true;
		generateTexture();
		bind();
		TextureUtil.uploadImage(image, type);
	}
	
	public void bind() {
		if (!initialized) init();
		glBindTexture(type, id);
	}
	
	public static void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public void load(String path) {
		if (path == null) return;
		
		image = ImageLoader.INSTANCE.load(path);
		width = image.getWidth();
		height = image.getHeight();
	}
	
	public String getName() {
		return name;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean destroy() {
		if (!initialized) return false;
		
		glDeleteTextures(id);
		return true;
	}

	public int getWrapMode() {
		return wrapMode;
	}

	public void setWrapMode(int wrapMode) {
		this.wrapMode = wrapMode;
	}

	public int getFilterMode() {
		return filterMode;
	}

	public void setFilterMode(int filterMode) {
		this.filterMode = filterMode;
	}

}
