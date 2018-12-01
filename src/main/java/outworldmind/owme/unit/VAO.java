package outworldmind.owme.unit;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class VAO implements Geometry {

	private final int id;
	private List<VBO> vbos;
	private VBO indicies = null;
	private boolean initialized = false;
	
	private VAO(int id) {
		this.id = id;
		vbos = new ArrayList<VBO>();
	}
	
	public static VAO create() {
		var id = GL30.glGenVertexArrays();
		return new VAO(id);
	}
	
	public List<VBO> getBuffers() {
		return vbos;
	}
	
	public VBO getIndicies() {
		return indicies;
	}
	
	public void createIndexBuffer(int[] indexData) {
		var vbo = VBO.create(GL15.GL_ELEMENT_ARRAY_BUFFER);
		vbo.bind();
		vbo.storeData(indexData);
		indicies = vbo;
	}
	
	public void createFloatBuffer(int attribute, int dimention, float[] bufferData) {
		var vbo = VBO.create(GL15.GL_ARRAY_BUFFER);
		vbo.bind();
		vbo.storeData(bufferData);
		GL20.glVertexAttribPointer(attribute, dimention, GL11.GL_FLOAT, false, dimention * 4, 0);
		vbo.unbind();
		vbos.add(vbo);
	}
	
	@Override
	public void bind() {
		GL30.glBindVertexArray(id);
		for (var i = 0; i < vbos.size(); i++)
			GL20.glEnableVertexAttribArray(i);
	}
	
	@Override
	public void unbind() {
		if (initialized)
			for (var i = 0; i < vbos.size(); i++)
				GL20.glDisableVertexAttribArray(i);
		GL30.glBindVertexArray(0);
		initialized = true;
	}
	
	@Override
	public int size() {
		return indicies.size();
	}
	
	@Override
	public void delete() {
		GL30.glDeleteVertexArrays(id);
		for (var vbo : vbos)
			vbo.delete();
		
		if (indicies!= null)
			indicies.delete();
	}
}
