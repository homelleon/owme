package outworldmind.owme.tools.modelUtils.objLoader;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import outworldmind.owme.maths.Quaternion;
import outworldmind.owme.maths.Vector2;
import outworldmind.owme.maths.Vector3;

public class OBJUtil {
	
	private static Collector<Float, ?, float[]> floatArrayCollector = Collectors.collectingAndThen(Collectors.toList(), floatList -> {
		float[] array = new float[floatList.size()];
		for (ListIterator<Float> iterator = floatList.listIterator(); iterator.hasNext();) {
			array[iterator.nextIndex()] = iterator.next();
		}
		return array;
	});
	
	public static Collector<Float, ?, float[]> getFloatArrayCollector() {
		return floatArrayCollector;
	}

	public static String [] removeEmptyStrings(String[] data) {
		ArrayList<String> result = new ArrayList<String>();
		
		for (int i = 0; i < data.length; i++) {
			if(!data[i].equals("")) {
				result.add(data[i]);
			}
		}
		
		String[] res = new String[result.size()];
		result.toArray(res);
		
		return res;
	}
	
	public static int[] toIntArray(Integer[] data) {
		int[] result = new int[data.length];
		
		for(int i=0; i < data.length; i++) {
			result[i] = data[i].intValue();
		}
		
		return result;
	}
	
	public static Vertex[] toVertexArray(FloatBuffer data) {
		Vertex[] vertices = new Vertex[data.limit() / Vertex.FLOATS];
		
		for(int i=0; i<vertices.length; i++) {
			vertices[i] = new Vertex();
			vertices[i].setPos(new Vector3(data.get(),data.get(),data.get()));
			vertices[i].setTextureCoord(new Vector2(data.get(),data.get()));
			vertices[i].setNormal(new Vector3(data.get(),data.get(),data.get()));
		}
		
		return vertices;
	}
	
	public static Vertex[] toVertexArray(ArrayList<Vertex> data) {
		Vertex[] vertices = new Vertex[data.size()];
		
		for(int i=0; i<vertices.length; i++) {
			vertices[i] = new Vertex();
			vertices[i].setPos(data.get(i).getPos());
			vertices[i].setTextureCoord(data.get(i).getTextureCoord());
			vertices[i].setNormal(data.get(i).getNormal());
		}
		
		return vertices;
	}
	
	public static void generateNormalsCW(Vertex[] vertices, int[] indices) {
	    for ( int i = 0; i < indices.length; i += 3 ) {
	    	Vector3 v0 = vertices[indices[i    ]].getPos();
	    	Vector3 v1 = vertices[indices[i + 1]].getPos();
	    	Vector3 v2 = vertices[indices[i + 2]].getPos();
	        
	    	Vector3 normal = v1.sub(v0).cross(v2.sub(v0)).normalize();
	        
	        vertices[indices[i	  ]].setNormal(vertices[indices[i    ]].getNormal().add(normal));
	        vertices[indices[i + 1]].setNormal(vertices[indices[i + 1]].getNormal().add(normal));
	        vertices[indices[i + 2]].setNormal(vertices[indices[i + 2]].getNormal().add(normal));
	    }

	    for ( int i = 0; i < vertices.length; ++i ) {	
	    	vertices[i].setNormal(vertices[i].getNormal().normalize());
	    }       
	}
	
	public static void generateNormalsCCW(Vertex[] vertices, int[] indices)	{
	    for ( int i = 0; i < indices.length; i += 3 ) {
	    	Vector3 v0 = vertices[indices[i    ]].getPos();
	    	Vector3 v1 = vertices[indices[i + 1]].getPos();
	    	Vector3 v2 = vertices[indices[i + 2]].getPos();
	        
	    	Vector3 normal = v2.sub(v0).cross(v1.sub(v0)).normalize();
	        
	        vertices[indices[i	  ]].setNormal(vertices[indices[i    ]].getNormal().add(normal));
	        vertices[indices[i + 1]].setNormal(vertices[indices[i + 1]].getNormal().add(normal));
	        vertices[indices[i + 2]].setNormal(vertices[indices[i + 2]].getNormal().add(normal));
	    }

	    for ( int i = 0; i < vertices.length; ++i ) {	
	    	vertices[i].setNormal(vertices[i].getNormal().normalize());
	    }       
	}
	
	public static void generateNormalsCW(ArrayList<Vertex> vertices, ArrayList<Integer> indices) {
	    for ( int i = 0; i < indices.size(); i += 3 )
	    {
	    	Vector3 v0 = vertices.get(indices.get(i)).getPos();
	    	Vector3 v1 = vertices.get(indices.get(i+1)).getPos();
	    	Vector3 v2 = vertices.get(indices.get(i+2)).getPos();
	        
	    	Vector3 normal = v1.sub(v0).cross(v2.sub(v0)).normalize();
	        
	        vertices.get(indices.get(i)).setNormal(vertices.get(indices.get(i)).getNormal().add(normal));
	        vertices.get(indices.get(i+1)).setNormal(vertices.get(indices.get(i+1)).getNormal().add(normal));
	        vertices.get(indices.get(i+2)).setNormal(vertices.get(indices.get(i+2)).getNormal().add(normal));
	    }

	    for ( int i = 0; i < vertices.size(); ++i ) {	
	    	vertices.get(i).setNormal(vertices.get(i).getNormal().normalize());
	    }       
	}
	
	public static void generateNormalsCCW(ArrayList<Vertex> vertices, ArrayList<Integer> indices) {
	    for ( int i = 0; i < indices.size(); i += 3 ) {
	    	Vector3 v0 = vertices.get(indices.get(i)).getPos();
	    	Vector3 v1 = vertices.get(indices.get(i+1)).getPos();
	    	Vector3 v2 = vertices.get(indices.get(i+2)).getPos();
	        
	    	Vector3 normal = v2.sub(v0).cross(v1.sub(v0)).normalize();
	        
	        vertices.get(indices.get(i)).setNormal(vertices.get(indices.get(i)).getNormal().add(normal));
	        vertices.get(indices.get(i+1)).setNormal(vertices.get(indices.get(i+1)).getNormal().add(normal));
	        vertices.get(indices.get(i+2)).setNormal(vertices.get(indices.get(i+2)).getNormal().add(normal));
	    }

	    for ( int i = 0; i < vertices.size(); ++i ) {	
	    	vertices.get(i).setNormal(vertices.get(i).getNormal().normalize());
	    }       
	}
	
	public static void generateNormalsCW(SmoothingGroup smoothingGroup)	{
	    for (Face face : smoothingGroup.getFaces()) {
	    	Vector3 v0 = smoothingGroup.getVertices().get(face.getIndices()[0]).getPos();
	    	Vector3 v1 = smoothingGroup.getVertices().get(face.getIndices()[1]).getPos();
	    	Vector3 v2 = smoothingGroup.getVertices().get(face.getIndices()[2]).getPos();
	        
	    	Vector3 normal = v1.sub(v0).cross(v2.sub(v0)).normalize();
	        
	    	smoothingGroup.getVertices().get(face.getIndices()[0]).setNormal(
	    			smoothingGroup.getVertices().get(face.getIndices()[0]).getNormal().add(normal));
	    	smoothingGroup.getVertices().get(face.getIndices()[1]).setNormal(
	    			smoothingGroup.getVertices().get(face.getIndices()[1]).getNormal().add(normal));
	    	smoothingGroup.getVertices().get(face.getIndices()[2]).setNormal(
	    			smoothingGroup.getVertices().get(face.getIndices()[2]).getNormal().add(normal));
	    }

	    for (Vertex vertex : smoothingGroup.getVertices()) {	
	    	vertex.setNormal(vertex.getNormal().normalize());
	    }       
	}
	
	public static void generateNormalsCCW(SmoothingGroup smoothingGroup) {
		  for (Face face : smoothingGroup.getFaces()) {
		    	Vector3 v0 = smoothingGroup.getVertices().get(face.getIndices()[0]).getPos();
		    	Vector3 v1 = smoothingGroup.getVertices().get(face.getIndices()[1]).getPos();
		    	Vector3 v2 = smoothingGroup.getVertices().get(face.getIndices()[2]).getPos();
		        
		    	Vector3 normal = v2.sub(v0).cross(v1.sub(v0)).normalize();
		        
		    	smoothingGroup.getVertices().get(face.getIndices()[0]).setNormal(
		    			smoothingGroup.getVertices().get(face.getIndices()[0]).getNormal().add(normal));
		    	smoothingGroup.getVertices().get(face.getIndices()[1]).setNormal(
		    			smoothingGroup.getVertices().get(face.getIndices()[1]).getNormal().add(normal));
		    	smoothingGroup.getVertices().get(face.getIndices()[2]).setNormal(
		    			smoothingGroup.getVertices().get(face.getIndices()[2]).getNormal().add(normal));
		    }

		    for (Vertex vertex : smoothingGroup.getVertices()) {	
		    	vertex.setNormal(vertex.getNormal().normalize());
		    }     
	}
	
	
	public static void generateTangentsBitangents(MeshData mesh) {
		for ( int i = 0; i < mesh.getIndices().length; i += 3 ) {
		    	Vector3 v0 = mesh.getVertices()[mesh.getIndices()[i]].getPos();
		    	Vector3 v1 = mesh.getVertices()[mesh.getIndices()[i+1]].getPos();
		    	Vector3 v2 = mesh.getVertices()[mesh.getIndices()[i+2]].getPos();
		        
		    	Vector2 uv0 = mesh.getVertices()[mesh.getIndices()[i]].getTextureCoord();
		    	Vector2 uv1 = mesh.getVertices()[mesh.getIndices()[i+1]].getTextureCoord();
		    	Vector2 uv2 = mesh.getVertices()[mesh.getIndices()[i+2]].getTextureCoord();
		    	
		    	Vector3 e1 = v1.sub(v0);
		    	Vector3 e2 = v2.sub(v0);
		    	
		    	Vector2 deltaUV1 = uv1.sub(uv0);
		    	Vector2 deltaUV2 = uv2.sub(uv0);
		    	
		    	float r = (1.0f / (deltaUV1.x * deltaUV2.y - deltaUV1.y * deltaUV2.x));
		    	
		    	Vector3 tangent = new Vector3();
		    	tangent.x = r * deltaUV2.y * e1.x - deltaUV1.y * e2.x;
		    	tangent.y = r * deltaUV2.y * e1.y - deltaUV1.y * e2.y;
		    	tangent.z = r * deltaUV2.y * e1.z - deltaUV1.y * e2.z;
		    	Vector3 bitangent = new Vector3();
		    	Vector3 normal = mesh.getVertices()[mesh.getIndices()[i]].getNormal().add(
		    				   mesh.getVertices()[mesh.getIndices()[i+1]].getNormal()).add(
		    				   mesh.getVertices()[mesh.getIndices()[i+2]].getNormal());
		    	normal = normal.normalize();
		    	
		    	bitangent = tangent.cross(normal);
		    	
		    	tangent = tangent.normalize();
		    	bitangent = bitangent.normalize();
		    	
		    	if (mesh.getVertices()[mesh.getIndices()[i]].getTangent() == null) 
		    		mesh.getVertices()[mesh.getIndices()[i]].setTangent(new Vector3(0,0,0));
		    	if (mesh.getVertices()[mesh.getIndices()[i]].getBitangent() == null) 
		    		mesh.getVertices()[mesh.getIndices()[i]].setBitangent(new Vector3(0,0,0));
		    	if (mesh.getVertices()[mesh.getIndices()[i+1]].getTangent() == null) 
		    		mesh.getVertices()[mesh.getIndices()[i+1]].setTangent(new Vector3(0,0,0));
		    	if (mesh.getVertices()[mesh.getIndices()[i+1]].getBitangent() == null) 
		    		mesh.getVertices()[mesh.getIndices()[i+1]].setBitangent(new Vector3(0,0,0));
		    	if (mesh.getVertices()[mesh.getIndices()[i+2]].getTangent() == null) 
		    		mesh.getVertices()[mesh.getIndices()[i+2]].setTangent(new Vector3(0,0,0));
		    	if (mesh.getVertices()[mesh.getIndices()[i+2]].getBitangent() == null) 
		    		mesh.getVertices()[mesh.getIndices()[i+2]].setBitangent(new Vector3(0,0,0));
		    	
		    	mesh.getVertices()[mesh.getIndices()[i]].setTangent(mesh.getVertices()[mesh.getIndices()[i]].getTangent().add(tangent));
		    	mesh.getVertices()[mesh.getIndices()[i]].setBitangent(mesh.getVertices()[mesh.getIndices()[i]].getBitangent().add(bitangent));
		    	mesh.getVertices()[mesh.getIndices()[i+1]].setTangent(mesh.getVertices()[mesh.getIndices()[i+1]].getTangent().add(tangent));
		    	mesh.getVertices()[mesh.getIndices()[i+1]].setBitangent(mesh.getVertices()[mesh.getIndices()[i+1]].getBitangent().add(bitangent));
		    	mesh.getVertices()[mesh.getIndices()[i+2]].setTangent(mesh.getVertices()[mesh.getIndices()[i+2]].getTangent().add(tangent));
		    	mesh.getVertices()[mesh.getIndices()[i+2]].setBitangent(mesh.getVertices()[mesh.getIndices()[i+2]].getBitangent().add(bitangent));	
		 }
		
		 for (Vertex vertex : mesh.getVertices()) {	
		    	vertex.setTangent(vertex.getTangent().normalize());
		    	vertex.setBitangent(vertex.getBitangent().normalize());
		    }
	}
	
	public static Quaternion normalizePlane(Quaternion plane) {
		float mag;
		mag = (float) Math.sqrt(plane.x * plane.x + plane.y * plane.y + plane.z * plane.z);
		plane.x = plane.x / mag;
		plane.y = plane.y / mag;
		plane.z = plane.z / mag;
		plane.w = plane.w / mag;
	
		return plane;
	}
	
	public static Vector2[] texCoordsFromFontMap(char x) {
		float x_ = (x%16)/16.0f;
		float y_ = (x/16)/16.0f;
		Vector2[] texCoords = new Vector2[4];
		texCoords[0] = new Vector2(x_, y_ + 1.0f/16.0f);
		texCoords[1] = new Vector2(x_, y_);
		texCoords[2] = new Vector2(x_ + 1.0f/16.0f, y_ + 1.0f/16.0f);
		texCoords[3] = new Vector2(x_ + 1.0f/16.0f, y_);
		
		return texCoords;
	}
}
