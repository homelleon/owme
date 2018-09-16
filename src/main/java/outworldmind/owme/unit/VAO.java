package outworldmind.owme.unit;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class VAO implements Geometry {

	private final int id;
	private List<VBO> vbos;
	private VBO indicies = null;
	
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
	
	public void createIndices(int[] indexData) {
		var vbo = VBO.create(GL15.GL_ELEMENT_ARRAY_BUFFER);
		vbo.bind();
		vbo.storeData(indexData);
		indicies = vbo;
	}
	
	public void createBuffer(float[] bufferData) {
		var vbo = VBO.create(GL15.GL_ELEMENT_ARRAY_BUFFER);
		vbo.bind();
		vbo.storeData(bufferData);
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
		for (var i = 0; i < vbos.size(); i++)
			GL20.glDisableVertexAttribArray(i);
		GL30.glBindVertexArray(0);
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
