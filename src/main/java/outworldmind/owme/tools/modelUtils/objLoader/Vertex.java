package outworldmind.owme.tools.modelUtils.objLoader;

import outworldmind.owme.maths.Vector2;
import outworldmind.owme.maths.Vector3;

public class Vertex {

	public static final int BYTES = 14 * Float.BYTES;
	public static final int FLOATS = 14;
	
	private Vector3 pos;
	private Vector3 normal;
	private Vector2 textureCoord;
	private Vector3 tangent;
	private Vector3 bitangent;
	private Vertex dublicateVertex = null;
	
	public Vertex(){}
	
	public Vertex(Vector3 pos)	{
		setPos(pos);
		setTextureCoord(new Vector2(0,0));
		setNormal(new Vector3(0,0,0));
	}
	
	public Vertex(Vector3 pos, Vector2 texture) {
		setPos(pos);
		setTextureCoord(texture);
		setNormal(new Vector3(0,0,0));
	}

	public Vector3 getPos() {
		return pos;
	}

	public void setPos(Vector3 pos) {
		this.pos = pos;
	}

	public Vector2 getTextureCoord() {
		return textureCoord;
	}

	public void setTextureCoord(Vector2 texture) {
		this.textureCoord = texture;
	}


	public Vector3 getNormal() {
		return normal;
	}

	public void setNormal(Vector3 normal) {
		this.normal = normal;
	}
	
	public void setDubilcateVertex(Vertex vertex) {
		this.dublicateVertex = vertex;
	}
	
	public Vertex getDublicateVertex() {
		return dublicateVertex;
	}

	public Vector3 getTangent() {
		return tangent;
	}

	public void setTangent(Vector3 tangent) {
		this.tangent = tangent;
	}

	public Vector3 getBitangent() {
		return bitangent;
	}

	public void setBitangent(Vector3 bitangent) {
		this.bitangent = bitangent;
	}
	
	public boolean equals(Object obj) {
		if (this == obj) return true;		
		if (obj == null || getClass() != obj.getClass() || this.hashCode() != obj.hashCode()) return false;
		
		Vertex other = (Vertex) obj;
		return (this.getPos().equals(other.getPos()) &&
				this.getNormal().equals(other.getNormal()) &&
				this.getTextureCoord().equals(other.getTextureCoord()));
		
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.getPos().hashCode();
		result = prime * result + this.getNormal().hashCode();
		result = prime * result + this.getTextureCoord().hashCode();
		return result;
	}
	
}