package outworldmind.owme.graphics;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import org.lwjgl.opengl.GL11;

import outworldmind.owme.core.Console;
import outworldmind.owme.shaders.Shader;
import outworldmind.owme.tools.NumberGenerator;

public class Renderer {
	
	private int id;
	private RenderState state;
	private Shader shader;
	private boolean needRebuild = true;
	private boolean needReset = true;
	
	public Renderer(RenderState state) {
		id = NumberGenerator.generateId();
		setState(state);
	}
	
	private void reset() {
		var color = state.getClearColor();
		glClearColor(color.r, color.b, color.g, color.a);
		var depthTestMode = state.getDepthTestMode();
		var cullFaceMode = state.getCullFaceMode();
		
		if (depthTestMode == RenderState.NONE)
			GL11.glDisable(GL11.GL_DEPTH_TEST);
		else {
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glDepthFunc(depthTestMode);
		}
		
		if (cullFaceMode == RenderState.NONE)
			GL11.glDisable(GL11.GL_CULL_FACE);
		else {
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glCullFace(cullFaceMode);
		}
		
		needReset = false;
	}
	
	public int getId() {
		return id;
	}
	
	public void setState(RenderState state) {
		this.state = state;
		needReset = true;
	}
	
	public void setShader(Shader shader) {
		if (this.shader != null) shader.stop();
		this.shader = shader;
		
		needRebuild = true;
	}
	
	private void rebuild() {
		try {
			shader.start();
		} catch(NullPointerException e) {
			Console.logErr("No shader attached to renderer!");
			e.printStackTrace();
			System.exit(-1);
		}
		
		needRebuild = false;
	}
	
	public void draw(Geometry geometry) {
		if (needReset) reset();
			
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		if (needRebuild) rebuild();
		geometry.bind();
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		GL11.glDrawElements(GL11.GL_TRIANGLES, geometry.size(), GL11.GL_UNSIGNED_INT, 0);
		geometry.unbind();
	}

}
