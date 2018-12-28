package outworldmind.owme.graphics;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.util.ArrayList;
import java.util.List;

import outworldmind.owme.core.Tools;

public class VAO implements Geometry {

	private final int id;
	private List<VBO> vbos;
	private VBO indicies = null;
	private boolean initialized = false;
	
	private VAO(int id) {
		this.id = id;
		vbos = new ArrayList<VBO>();
		Tools.getGrabageCollector().follow(this);
	}
	
	public static VAO create() {
		var id = glGenVertexArrays();
		return new VAO(id);
	}
	
	public List<VBO> getBuffers() {
		return vbos;
	}
	
	public VBO getIndicies() {
		return indicies;
	}
	
	public void createIndexBuffer(int[] indexData) {
		indicies = VBO.create(GL_ELEMENT_ARRAY_BUFFER, indexData);
	}
	
	/**
	 * Create float buffer binded to GPU by creation order.
	 * 
	 * @param dimension
	 * @param float[] bufferData
	 */
	public void createFloatBuffer(int dimension, float[] bufferData) {
		var vbo = VBO.create(GL_ARRAY_BUFFER, bufferData);
		glVertexAttribPointer(vbos.size(), dimension, GL_FLOAT, false, dimension * Float.BYTES, 0);
		vbos.add(vbo);
	}
	
	@Override
	public void bind() {
		glBindVertexArray(id);
		for (var i = 0; i < vbos.size(); i++)
			glEnableVertexAttribArray(i);
	}
	
	@Override
	public void unbind() {
		if (initialized)
			for (var i = 0; i < vbos.size(); i++)
				glDisableVertexAttribArray(i);
		glBindVertexArray(0);
		initialized = true;
	}
	
	@Override
	public int size() {
		return indicies.size();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		var result = 1;
		result = prime * result + id;
		result = prime * result + vbos.hashCode();
		result = prime * result + indicies.hashCode();
		result = prime * result + (initialized ? 1 : 0);
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		if (!(obj instanceof VAO)) return false;
		
		VAO other = (VAO) obj;
		if (id != other.id) return false;
		return true;
	}
	
	@Override
	public void dispose() {
		unbind();
		Tools.getGrabageCollector().forget(this);
		if (!initialized) return;
		glDeleteVertexArrays(id);
		for (var vbo : vbos)
			vbo.dispose();
		
		if (indicies!= null)
			indicies.dispose();
	}
}
