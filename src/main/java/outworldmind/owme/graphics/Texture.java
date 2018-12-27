package outworldmind.owme.graphics;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.awt.image.BufferedImage;

import outworldmind.owme.core.Console;
import outworldmind.owme.core.Disposable;
import outworldmind.owme.core.GC;
import outworldmind.owme.tools.ImageLoader;
import outworldmind.owme.tools.TextureUtil;

public class Texture implements Disposable{
	
	public static final int FILTER_NONE = 0;
	public static final int FILTER_BILINEAR = 1;
	
	public static final int WRAP_NONE = 0;
	public static final int WRAP_REPEAT = 1;
	public static final int WRAP_MIRROR_REPEAT = 2;
	
	private int id;
	private String name;
	private int type;
	private int bindLocation = 0;
	private boolean hasBindLocation = false;
	private int width;
	private int height;
	private int filterMode = FILTER_NONE;
	private int wrapMode = WRAP_NONE;
	private BufferedImage image;
	private boolean uploaded = false;
	
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
		GC.follow(this);
	}
	
	public void setBindLocation(int location) {
		bindLocation = location;
		
		hasBindLocation = true;
	}
	
	public int getBindLocation() {
		return bindLocation;
	}
	
	public boolean hasBindLocation() {
		return hasBindLocation;
	}
	
	public void bind() {
		if (hasBindLocation && uploaded)
			active(bindLocation);
		glBindTexture(type, id);
	}
	
	public void active(int location) {
		if (location < 0 || location >= 31) 
			throw new IndexOutOfBoundsException("Incorrect location at texture activation!");
		
		glActiveTexture(GL_TEXTURE0 + location);	
	}
	
	public static void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public void load(String path, boolean doUpload) {
		if (path == null) return;
		
		image = ImageLoader.INSTANCE.load(path);
		width = image.getWidth();
		height = image.getHeight();
		if (doUpload) upload();
	}
	
	public void load(String path) {
		if (path == null) return;
		
		image = ImageLoader.INSTANCE.load(path);
		width = image.getWidth();
		height = image.getHeight();
		upload();
	}
	
	public void upload() {
		if (uploaded) {
			Console.logErr(getClass().getSimpleName() + " " + name + " has already been uploaded");
			return;
		}
		
		generateTexture();
		bind();
		TextureUtil.uploadImage(image, type);
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
	
	public void dispose() {
		GC.forget(this);
		if (!uploaded) return;
		
		glDeleteTextures(id);
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
