package outworldmind.owme.core;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import outworldmind.owme.tool.NumberGenerator;
import outworldmind.owme.unit.Geometry;

public class Renderer {
	
	private int id;
	private RenderState state;
	
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
	
	public void draw(Geometry geometry) {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		geometry.bind();
//		GL11.glDrawElements(GL11.GL_TRIANGLES, 16, type, indices);
	}

}
