package outworldmind.owme.core;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import org.lwjgl.opengl.GL11;

import outworldmind.owme.shader.Shader;
import outworldmind.owme.tool.NumberGenerator;
import outworldmind.owme.unit.Geometry;

public class Renderer {
	
	private int id;
	private RenderState state;
	private Shader shader;
	private boolean needRebuild = true;
	
	public Renderer(RenderState state) {
		id = NumberGenerator.generateId();
		this.state = state;
	}
	
	public void prepare() {
		var color = state.getClearColor();
		glClearColor(color.r, color.b, color.g, color.a);
	}
	
	public int getId() {
		return id;
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
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		if (needRebuild) rebuild();
		geometry.bind();
		GL11.glDrawElements(GL11.GL_TRIANGLES, geometry.size(), GL11.GL_UNSIGNED_INT, 0);
		geometry.unbind();
	}

}
