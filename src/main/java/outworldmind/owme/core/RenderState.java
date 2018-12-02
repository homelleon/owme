package outworldmind.owme.core;

import org.lwjgl.opengl.GL11;

import outworldmind.owme.math.Color;

public class RenderState {
	
	public static final int NONE = -1;
	public static final int DEPTH_ALWAYS = GL11.GL_ALWAYS;
	public static final int DEPTH_NEVER = GL11.GL_NEVER;
	public static final int DEPTH_LESS = GL11.GL_LESS;
	public static final int DEPTH_GREATER = GL11.GL_GREATER;
	public static final int DEPTH_EQUAL = GL11.GL_EQUAL;
	
	public static final int CULL_FRONT = GL11.GL_FRONT;
	public static final int CULL_BACK = GL11.GL_BACK;
	public static final int CULL_ALL = GL11.GL_FRONT_AND_BACK;
	
	private Color clearColor = new Color();
	private int cullFaceMode = NONE;
	private int depthTestMode = NONE;

	public Color getClearColor() {
		return clearColor;
	}

	public RenderState setClearColor(Color clearColor) {
		this.clearColor = clearColor;
		
		return this;
	}
	
	public RenderState setDepthTestMode(int value) {
		this.depthTestMode = value;
		
		return this;
	}
	
	public int getDepthTestMode() {
		return depthTestMode;
	}
	
	public RenderState setCullFaceMode(int value) {
		this.cullFaceMode = value;
		
		return this;
	}
	
	public int getCullFaceMode() {
		return cullFaceMode;
	}

}
