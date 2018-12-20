package outworldmind.owme.graphics;

public class GeometryBuilder {
	
	private float[] vertices;
	private float[] normals;
	private float[] textureCoords;
	private float[] tangents;
	private int[] indices;
	
	public GeometryBuilder setIndices(int[] array) {
		indices = array;
		
		return this;
	}
	
	public GeometryBuilder setVertices(float[] array) {
		vertices = array;
		
		return this;
	}
	
	public GeometryBuilder setNormals(float[] array) {
		normals = array;
		
		return this;
	}
	
	public GeometryBuilder setTextureCoords(float[] array) {
		textureCoords = array;
		
		return this;
	}
	
	public GeometryBuilder setTangents(float[] array) {
		tangents = array;
		
		return this;
	}
	
	public Geometry build() {
		if (vertices == null)
			throw new NullPointerException(this.getClass().getSimpleName() + " - vertices have to be defined.");
		
		var geometry = VAO.create();
		
		geometry.bind();
		if (indices != null)
			geometry.createIndexBuffer(indices);
		
		geometry.createFloatBuffer(3, vertices);
		
		if (normals != null)
			geometry.createFloatBuffer(3, normals);
		
		if (textureCoords != null)
			geometry.createFloatBuffer(2, textureCoords);
		
		if (tangents != null)
			geometry.createFloatBuffer(3, tangents);
		
		geometry.unbind();
		
		return geometry;		
	}

}
