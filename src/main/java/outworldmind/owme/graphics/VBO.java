package outworldmind.owme.graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import outworldmind.owme.core.Tools;

public class VBO implements Geometry {
	
	private final int id;
	private final int type;
	private int size;
	
	private VBO(int id, int type) {
		this.id = id;
		this.type = type;
		Tools.getGrabageCollector().follow(this);
	}
	
	public static VBO create(int type, int[] data) {
		var vbo = create(type);
		vbo.bind();
		vbo.storeData(data);
		return vbo;
	}
	
	public static VBO create(int type, float[] data) {
		var vbo = create(type);
		vbo.bind();
		vbo.storeData(data);
		return vbo;
	}
	
	private static VBO create(int type) {
		int id = GL15.glGenBuffers();
		return new VBO(id, type);
	}
	
	private void storeData(int[] data) {
		size = data.length;
		IntBuffer buffer = BufferUtils.createIntBuffer(size);
		buffer.put(data);
		buffer.flip();
		storeData(buffer);
	}
	
	private void storeData(float[] data) {
		size = data.length;
		FloatBuffer buffer = BufferUtils.createFloatBuffer(size);
		buffer.put(data);
		buffer.flip();
		storeData(buffer);
	}
	
	private void storeData(FloatBuffer data) {
		GL15.glBufferData(type, data, GL15.GL_STATIC_DRAW);
	}
	
	private void storeData(IntBuffer data) {
		GL15.glBufferData(type, data, GL15.GL_STATIC_DRAW);
	}
	
	@Override
	public void bind() {
		GL15.glBindBuffer(type, id);
	}
	
	@Override
	public void unbind() {
		GL15.glBindBuffer(type, 0);
	}
	
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public void dispose() {
		unbind();
		GL15.glDeleteBuffers(id);
		Tools.getGrabageCollector().forget(this);
	}
}
