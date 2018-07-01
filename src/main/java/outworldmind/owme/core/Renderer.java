package outworldmind.owme.core;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import outworldmind.owme.tool.NumberGenerator;

public class Renderer {
	
	private int id;
	
	public Renderer() {
		id = NumberGenerator.generateId();
	}
	
	public int getId() {
		return id;
	}
	
	public void draw() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); //?
	}

}
