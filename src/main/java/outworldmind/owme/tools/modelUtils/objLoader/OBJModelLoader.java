package outworldmind.owme.tools.modelUtils.objLoader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import outworldmind.owme.core.Console;
import outworldmind.owme.core.Logger;
import outworldmind.owme.core.Tools;
import outworldmind.owme.graphics.GeometryBuilder;
import outworldmind.owme.graphics.Material;
import outworldmind.owme.graphics.Model;
import outworldmind.owme.graphics.TextureBuilder;
import outworldmind.owme.maths.Color;
import outworldmind.owme.maths.Vector2;
import outworldmind.owme.maths.Vector3;

public class OBJModelLoader {

	private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	private ArrayList<Vector3> normals = new ArrayList<Vector3>();
	private ArrayList<Vector2> texCoords = new ArrayList<Vector2>();

	private Deque<MeshObject> objects = new ArrayDeque<MeshObject>();
	private HashMap<String, Material> materials = new HashMap<String, Material>();
	private int currentSmoothingGroup;
	private String materialname = null;
	
	private Frontface frontface = Frontface.CCW;
	private boolean generateNormals = true;
	private boolean generateTangents = false;
	
	private enum Frontface {
		CW,CCW
	}
	
	public OBJModelLoader() {}
	
	public OBJModelLoader(boolean generateTangents) {
		this.generateTangents = generateTangents;
	}
	
	private void loadMaterials(String path, String name) {
		BufferedReader mtlReader = null;
			try {
				InputStreamReader inputStream = new InputStreamReader(OBJModelLoader.class.getResourceAsStream(path + "/" + name + ".mtl"));
				mtlReader = new BufferedReader(inputStream);
				String line;
				String currentMtl = "";
				
				while ((line = mtlReader.readLine()) != null) {
					
					String[] tokens = line.split(" ");
					tokens = OBJUtil.removeEmptyStrings(tokens);
					
					if (tokens.length == 0)	continue;
					
					if (tokens[0].equals("newmtl")) {
						Material material = new Material(tokens[1]);
						materials.put(tokens[1], material);
						currentMtl = tokens[1];
					}
					
					if (tokens.length == 1) continue;
					
					if (tokens[0].equals("Ka")) {
						Color color = new Color(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3]));
						materials.get(currentMtl).addColor(Material.AMBIENT, color);
					}
					
					if (tokens[0].equals("Kd")) {
						Color color = new Color(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3]));
						materials.get(currentMtl).addColor(Material.DIFFUSE, color);
					}
					
					if (tokens[0].equals("Ks")) {
						Color color = new Color(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3]));
						materials.get(currentMtl).addColor(Material.SPECULAR, color);
					}
					
					if (tokens[0].equals("map_Kd"))
						materials.get(currentMtl).addTexture(Material.DIFFUSE, 
								new TextureBuilder()
								.setName(Material.DIFFUSE)
								.setPath(path + tokens[1])
								.build());
					
					if (tokens[0].equals("map_Ks"))
						materials.get(currentMtl).addTexture(Material.SPECULAR, 
								new TextureBuilder()
								.setName(Material.SPECULAR)
								.setPath(path + tokens[1])
								.build());

					if (tokens[0].equals("map_Ns"))
						//TODO: specular highlight component
						
					if (tokens[0].equals("map_d"))
						//TODO:
						
					if (tokens[0].equals("disp"))
						//TODO: displacement
						
					if (tokens[0].equals("decal"))
						//TODO: 
						
					if (tokens[0].equals("map_bump"))
						materials.get(currentMtl).addTexture(Material.BUMP, 
								new TextureBuilder()
								.setName(Material.BUMP)
								.setPath(path + tokens[1])
								.build());
					
					if (tokens[0].equals("bump"))
						//TODO: bump map with params
						
					if (tokens[0].equals("illum"))
						materials.get(currentMtl).addValue(Material.ILLUMINATION, Float.valueOf(tokens[1]));
						
					if (tokens[0].equals("Ns"))
						materials.get(currentMtl).addValue(Material.SHINISESS, Float.valueOf(tokens[1]));
				}
				mtlReader.close();
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
	}
	
	public Model[] load(String path, String objFile) {
		long time = System.currentTimeMillis();
		
			BufferedReader meshReader = null;
			// load .obj
			try {
				Tools.getLogger().log("Start loading " + path + objFile + ".obj...");
				InputStreamReader inputStream = new InputStreamReader(OBJModelLoader.class.getResourceAsStream(path + objFile + ".obj"));
				meshReader = new BufferedReader(inputStream);
				String line;
				while ((line = meshReader.readLine()) != null) {
					String[] tokens = line.split(" ");
					tokens = OBJUtil.removeEmptyStrings(tokens);
					
					if (tokens.length == 0 || tokens[0].equals("#"))
						continue;
					
					if (tokens[0].equals("v")) {
						vertices.add(new Vertex(new Vector3(Float.valueOf(tokens[1]),
														  Float.valueOf(tokens[2]),
														  Float.valueOf(tokens[3]))));
					}
					
					if (tokens[0].equals("vn")) {
						normals.add(new Vector3(Float.valueOf(tokens[1]),
											  Float.valueOf(tokens[2]),
											  Float.valueOf(tokens[3])));
					}
					
					if (tokens[0].equals("vt"))
						texCoords.add(new Vector2(Float.valueOf(tokens[1]), Float.valueOf(tokens[2])));

					
					if (tokens[0].equals("g")) {
						PolygonGroup polygonGroup = new PolygonGroup();	
						if (tokens.length > 1)
							polygonGroup.setName(tokens[1]);
						if (objects.isEmpty()) objects.add(new MeshObject());
						objects.peekLast().getPolygonGroups().add(polygonGroup);
					}
					
					if (tokens[0].equals("o")) {
						MeshObject object = new MeshObject();
						object.setName(tokens[1]);
						objects.add(new MeshObject());
					}
					
					if (tokens[0].equals("usemtl")) {
						Polygon polygon = new Polygon();
						materialname = tokens[1];
						polygon.setMaterial(tokens[1]);
						if (objects.peekLast().getPolygonGroups().isEmpty())
							objects.peekLast().getPolygonGroups().add(new PolygonGroup());
						objects.peekLast().getPolygonGroups().peekLast().getPolygons().add(polygon);
					}
					
					if (tokens[0].equals("s")) {
						if (objects.peekLast().getPolygonGroups().isEmpty())
							objects.peekLast().getPolygonGroups().add(new PolygonGroup());
						
						if (tokens[1].equals("off") || tokens[1].equals("0")) {
							currentSmoothingGroup = 0;
							if (!objects.peekLast().getPolygonGroups().peekLast().getSmoothingGroups().containsKey(0))
								objects.peekLast().getPolygonGroups().peekLast().getSmoothingGroups().put(currentSmoothingGroup, new SmoothingGroup());
						} else {
							currentSmoothingGroup = Integer.valueOf(tokens[1]);
							if (!objects.peekLast().getPolygonGroups().peekLast().getSmoothingGroups().containsKey(currentSmoothingGroup))
								objects.peekLast().getPolygonGroups().peekLast().getSmoothingGroups().put(currentSmoothingGroup, new SmoothingGroup());
						}
					}
					
					if (tokens[0].equals("f")) {
						if (objects.peekLast().getPolygonGroups().isEmpty())
							objects.peekLast().getPolygonGroups().add(new PolygonGroup());
						
						if (objects.peekLast().getPolygonGroups().peekLast().getSmoothingGroups().isEmpty()) {
							currentSmoothingGroup = 1;
							objects.peekLast().getPolygonGroups().peekLast().getSmoothingGroups().put(currentSmoothingGroup, new SmoothingGroup());
						}
						
						if (objects.peekLast().getPolygonGroups().peekLast().getPolygons().isEmpty())
							objects.peekLast().getPolygonGroups().peekLast().getPolygons().add(new Polygon());
						
						if (tokens.length == 4)
							parseTriangleFace(tokens);
						if (tokens.length == 5)
							parseQuadFace(tokens);
					}
				}
				meshReader.close();
					
				if (normals.isEmpty() && generateNormals) {
					for (MeshObject object : objects) {
						for (PolygonGroup polygonGroup : object.getPolygonGroups()) {
							for (Integer key : polygonGroup.getSmoothingGroups().keySet()) {
								if (frontface == Frontface.CW)
									OBJUtil.generateNormalsCW(polygonGroup.getSmoothingGroups().get(key));
								else
									OBJUtil.generateNormalsCCW(polygonGroup.getSmoothingGroups().get(key));
							}
						}
					}
				}					
					
				var models = new ArrayList<Model>();
				
				for (var object : objects) {
					for (var polygonGroup : object.getPolygonGroups()) {
						for (var polygon : polygonGroup.getPolygons()) {							
							generatePolygon(polygonGroup.getSmoothingGroups(), polygon);
							var geometry = convertToBuilder(polygon);
							var model = new Model()
									.setGeometry(geometry.build());
							var materialName = polygon.getMaterial();
							if (materialName != null) {
								loadMaterials(path, materialName);
								model.setMaterial(materials.get(materialName));
							}
							models.add(model);
						}
					}
				}
				
				Tools.getLogger().log("obj loading time : " + (System.currentTimeMillis() - time) + "ms");
				
				return models.toArray(Model[]::new);
				
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		
		return null;
	}
	
	private void parseTriangleFace(String[] tokens) {
		// vertex//normal
		if (tokens[1].contains("//")) {
			
			int[] vertexIndices = {Integer.parseInt(tokens[1].split("//")[0]) - 1,
					      		   Integer.parseInt(tokens[2].split("//")[0]) - 1,
					      		   Integer.parseInt(tokens[3].split("//")[0]) - 1};
			int[] normalIndices = {Integer.parseInt(tokens[1].split("//")[1]) - 1,
								   Integer.parseInt(tokens[2].split("//")[1]) - 1,
								   Integer.parseInt(tokens[3].split("//")[1]) - 1};
			
			Vertex v0 = new Vertex(vertices.get(vertexIndices[0]).getPos());
			Vertex v1 = new Vertex(vertices.get(vertexIndices[1]).getPos());
			Vertex v2 = new Vertex(vertices.get(vertexIndices[2]).getPos());
			v0.setNormal(normals.get(normalIndices[0]));
			v1.setNormal(normals.get(normalIndices[1]));
			v2.setNormal(normals.get(normalIndices[2]));
			if (generateTangents)
				generateTangents(v0, v1, v2);
			
			addToSmoothingGroup(objects.peekLast().getPolygonGroups().peekLast().getSmoothingGroups().get(currentSmoothingGroup),v0,v1,v2);
		
		} else if (tokens[1].contains("/")) {	
			if (tokens[1].split("/").length == 3) {
				// vertex/textureCoord/normal

				int[] vertexIndices = {Integer.parseInt(tokens[1].split("/")[0]) - 1,
						      		   Integer.parseInt(tokens[2].split("/")[0]) - 1,
						      		   Integer.parseInt(tokens[3].split("/")[0]) - 1};
				int[] texCoordIndices =  {Integer.parseInt(tokens[1].split("/")[1]) - 1,
						              	  Integer.parseInt(tokens[2].split("/")[1]) - 1,
						              	  Integer.parseInt(tokens[3].split("/")[1]) - 1};
				int[] normalIndices = {Integer.parseInt(tokens[1].split("/")[2]) - 1,
									   Integer.parseInt(tokens[2].split("/")[2]) - 1,
									   Integer.parseInt(tokens[3].split("/")[2]) - 1};
				
				Vertex v0 = new Vertex(vertices.get(vertexIndices[0]).getPos());
				Vertex v1 =  new Vertex(vertices.get(vertexIndices[1]).getPos());
				Vertex v2 =  new Vertex(vertices.get(vertexIndices[2]).getPos());
				v0.setNormal(normals.get(normalIndices[0]));
				v1.setNormal(normals.get(normalIndices[1]));
				v2.setNormal(normals.get(normalIndices[2]));
				v0.setTextureCoord(texCoords.get(texCoordIndices[0]));
				v1.setTextureCoord(texCoords.get(texCoordIndices[1]));
				v2.setTextureCoord(texCoords.get(texCoordIndices[2]));
				if (generateTangents)
					generateTangents(v0, v1, v2);
				
				addToSmoothingGroup(objects.peekLast().getPolygonGroups().peekLast().getSmoothingGroups().get(currentSmoothingGroup),v0,v1,v2);
			
			} else {
				// vertex/textureCoord
				int[] vertexIndices = {Integer.parseInt(tokens[1].split("/")[0]) - 1,
							  	 	   Integer.parseInt(tokens[2].split("/")[0]) - 1,
							  	 	   Integer.parseInt(tokens[3].split("/")[0]) - 1};
				int[] texCoordIndices =  {Integer.parseInt(tokens[1].split("/")[1]) - 1,
										  Integer.parseInt(tokens[2].split("/")[1]) - 1,
										  Integer.parseInt(tokens[3].split("/")[1]) - 1};
				
				Vertex v0 = new Vertex(vertices.get(vertexIndices[0]).getPos());
				Vertex v1 = new Vertex(vertices.get(vertexIndices[1]).getPos());
				Vertex v2 = new Vertex(vertices.get(vertexIndices[2]).getPos());
				v0.setTextureCoord(texCoords.get(texCoordIndices[0]));
				v1.setTextureCoord(texCoords.get(texCoordIndices[1]));
				v2.setTextureCoord(texCoords.get(texCoordIndices[2]));
				if (generateTangents)
					generateTangents(v0, v1, v2);
				
				addToSmoothingGroup(objects.peekLast().getPolygonGroups().peekLast().getSmoothingGroups().get(currentSmoothingGroup),v0,v1,v2);
			}		
		} else {
			// vertex
			
			int[] vertexIndices = {Integer.parseInt(tokens[1]) - 1,
					      Integer.parseInt(tokens[2]) - 1,
					      Integer.parseInt(tokens[3]) - 1};
			
			Vertex v0 = vertices.get(vertexIndices[0]);
			Vertex v1 = vertices.get(vertexIndices[1]);
			Vertex v2 = vertices.get(vertexIndices[2]);
			if (generateTangents)
				generateTangents(v0, v1, v2);
				
			addToSmoothingGroup(objects.peekLast().getPolygonGroups().peekLast().getSmoothingGroups().get(currentSmoothingGroup),v0,v1,v2);
		}
	}
	
	private void parseQuadFace(String[] tokens) {		
		if (tokens[1].contains("//")) {
			// vertex//normal
			
			int[] vertexIndices = {Integer.parseInt(tokens[1].split("//")[0]) - 1,
						  		   Integer.parseInt(tokens[2].split("//")[0]) - 1,
						  		   Integer.parseInt(tokens[3].split("//")[0]) - 1,
						  		   Integer.parseInt(tokens[4].split("//")[0]) - 1};
			int[] normalIndices = {Integer.parseInt(tokens[1].split("//")[1]) - 1,
								   Integer.parseInt(tokens[2].split("//")[1]) - 1,
								   Integer.parseInt(tokens[3].split("//")[1]) - 1,
								   Integer.parseInt(tokens[4].split("//")[1]) - 1};
			
			Vertex v0 = new Vertex(vertices.get(vertexIndices[0]).getPos());
			Vertex v1 = new Vertex(vertices.get(vertexIndices[1]).getPos());
			Vertex v2 = new Vertex(vertices.get(vertexIndices[2]).getPos());
			Vertex v3 = new Vertex(vertices.get(vertexIndices[3]).getPos());
			v0.setNormal(normals.get(normalIndices[0]));
			v1.setNormal(normals.get(normalIndices[1]));
			v2.setNormal(normals.get(normalIndices[2]));
			v3.setNormal(normals.get(normalIndices[3]));
			
			if (generateTangents) {
				generateTangents(v0, v1, v2);
				generateTangents(v2, v1, v3);
			}			
			addToSmoothingGroup(objects.peekLast().getPolygonGroups().peekLast().getSmoothingGroups().get(currentSmoothingGroup),v0,v1,v2,v3);
		
		} else if (tokens[1].contains("/")) {		
			// vertex/textureCoord/normal
			
			if (tokens[1].split("/").length == 3) {

				int[] vertexIndices = {Integer.parseInt(tokens[1].split("/")[0]) - 1,
									   Integer.parseInt(tokens[2].split("/")[0]) - 1,
									   Integer.parseInt(tokens[3].split("/")[0]) - 1,
									   Integer.parseInt(tokens[4].split("/")[0]) - 1};
				int[] texCoordIndices =  {Integer.parseInt(tokens[1].split("/")[1]) - 1,
										  Integer.parseInt(tokens[2].split("/")[1]) - 1,
										  Integer.parseInt(tokens[3].split("/")[1]) - 1,
										  Integer.parseInt(tokens[4].split("/")[1]) - 1};
				int[] normalIndices = {Integer.parseInt(tokens[1].split("/")[2]) - 1,
						               Integer.parseInt(tokens[2].split("/")[2]) - 1,
						               Integer.parseInt(tokens[3].split("/")[2]) - 1,
						               Integer.parseInt(tokens[4].split("/")[2]) - 1};
				
				Vertex v0 = new Vertex(vertices.get(vertexIndices[0]).getPos());
				Vertex v1 = new Vertex(vertices.get(vertexIndices[1]).getPos());
				Vertex v2 = new Vertex(vertices.get(vertexIndices[2]).getPos());
				Vertex v3 = new Vertex(vertices.get(vertexIndices[3]).getPos());
				v0.setNormal(normals.get(normalIndices[0]));
				v1.setNormal(normals.get(normalIndices[1]));
				v2.setNormal(normals.get(normalIndices[2]));
				v3.setNormal(normals.get(normalIndices[3]));			
				v0.setTextureCoord(texCoords.get(texCoordIndices[0]));
				v1.setTextureCoord(texCoords.get(texCoordIndices[1]));
				v2.setTextureCoord(texCoords.get(texCoordIndices[2]));
				v3.setTextureCoord(texCoords.get(texCoordIndices[3]));
				if (generateTangents) {
					generateTangents(v0, v1, v2);
					generateTangents(v2, v1, v3);
				}
				
				addToSmoothingGroup(objects.peekLast().getPolygonGroups().peekLast().getSmoothingGroups().get(currentSmoothingGroup),v0,v1,v2,v3);
			} else {
				// vertex/textureCoord

				int[] vertexIndices = {Integer.parseInt(tokens[1].split("/")[0]) - 1,
						      		   Integer.parseInt(tokens[2].split("/")[0]) - 1,
						      		   Integer.parseInt(tokens[3].split("/")[0]) - 1,
						      		   Integer.parseInt(tokens[4].split("/")[0]) - 1};
				int[] texCoordIndices =  {Integer.parseInt(tokens[1].split("/")[1]) - 1,
										  Integer.parseInt(tokens[2].split("/")[1]) - 1,
										  Integer.parseInt(tokens[3].split("/")[1]) - 1,
										  Integer.parseInt(tokens[4].split("/")[1]) - 1};
				
				Vertex v0 = new Vertex(vertices.get(vertexIndices[0]).getPos());
				Vertex v1 = new Vertex(vertices.get(vertexIndices[1]).getPos());
				Vertex v2 = new Vertex(vertices.get(vertexIndices[2]).getPos());
				Vertex v3 = new Vertex(vertices.get(vertexIndices[3]).getPos());				
				v0.setTextureCoord(texCoords.get(texCoordIndices[0]));
				v1.setTextureCoord(texCoords.get(texCoordIndices[1]));
				v2.setTextureCoord(texCoords.get(texCoordIndices[2]));
				v3.setTextureCoord(texCoords.get(texCoordIndices[3]));
				if (generateTangents) {
					generateTangents(v0, v1, v2);
					generateTangents(v2, v1, v3);
				}
				
				addToSmoothingGroup(objects.peekLast().getPolygonGroups().peekLast().getSmoothingGroups().get(currentSmoothingGroup),v0,v1,v2,v3);
			}		
		} else {
			// vertex
			
			int[] vertexIndices = {Integer.parseInt(tokens[1]) - 1,
					      		   Integer.parseInt(tokens[2]) - 1,
					      		   Integer.parseInt(tokens[3]) - 1,
					      		   Integer.parseInt(tokens[4]) - 1};
			
			Vertex v0 = new Vertex(vertices.get(vertexIndices[0]).getPos());
			Vertex v1 = new Vertex(vertices.get(vertexIndices[1]).getPos());
			Vertex v2 = new Vertex(vertices.get(vertexIndices[2]).getPos());
			Vertex v3 = new Vertex(vertices.get(vertexIndices[3]).getPos());
			if (generateTangents) {
				generateTangents(v0, v1, v2);
				generateTangents(v2, v1, v3);
			}
			
			addToSmoothingGroup(objects.peekLast().getPolygonGroups().peekLast().getSmoothingGroups().get(currentSmoothingGroup),v0,v1,v2,v3);
		}
	}
	
	private void addToSmoothingGroup(SmoothingGroup smoothingGroup, Vertex v0, Vertex v1, Vertex v2) {
		
		int index0 = processVertex(smoothingGroup, v0);
		int index1 = processVertex(smoothingGroup, v1);
		int index2 = processVertex(smoothingGroup, v2);
		
		Face face = new Face();
		face.getIndices()[0] = index0;
		face.getIndices()[1] = index1;
		face.getIndices()[2] = index2;
		face.setMaterial(materialname);
		
		smoothingGroup.getFaces().add(face);
	}
	
	private void addToSmoothingGroup(SmoothingGroup smoothingGroup, Vertex v0, Vertex v1, Vertex v2, Vertex v3) {
		int index0 = processVertex(smoothingGroup, v0);
		int index1 = processVertex(smoothingGroup, v1);
		int index2 = processVertex(smoothingGroup, v2);
		int index3 = processVertex(smoothingGroup, v3);
		
		Face face0 = new Face();
		face0.getIndices()[0] = index0;
		face0.getIndices()[1] = index1;
		face0.getIndices()[2] = index2;
		face0.setMaterial(materialname);
		
		Face face1 = new Face();
		face1.getIndices()[0] = index0;
		face1.getIndices()[1] = index2;
		face1.getIndices()[2] = index3;
		face1.setMaterial(materialname);
		
		smoothingGroup.getFaces().add(face0);
		smoothingGroup.getFaces().add(face1);
	}
	
	private int processVertex(SmoothingGroup smoothingGroup, Vertex previousVertex) {
		if (smoothingGroup.getVertices().contains(previousVertex)) {
			int index = smoothingGroup.getVertices().indexOf(previousVertex);
			Vertex nextVertex = smoothingGroup.getVertices().get(index);
			if (!hasSameNormalAndTexture(previousVertex, nextVertex)) {				
				if (nextVertex.getDublicateVertex() != null) {
					return processVertex(smoothingGroup, nextVertex.getDublicateVertex());
				} else {
					Vertex newVertex = new Vertex();
					newVertex.setPos(previousVertex.getPos());
					newVertex.setNormal(previousVertex.getNormal());
					newVertex.setTextureCoord(previousVertex.getTextureCoord());
					previousVertex.setDubilcateVertex(newVertex);
					smoothingGroup.getVertices().add(newVertex);
					return smoothingGroup.getVertices().indexOf(newVertex);
				}
			}
		}
		smoothingGroup.getVertices().add(previousVertex);
		return smoothingGroup.getVertices().indexOf(previousVertex);
	}
	
	private boolean hasSameNormalAndTexture(Vertex v1, Vertex v2) {
		return v1.getNormal().equals(v2.getNormal()) && v1.getTextureCoord().equals(v2.getTextureCoord());
	}
	
	private void generatePolygon(HashMap<Integer, SmoothingGroup> smoothingGroups, Polygon polygon) {
		
		for (Integer key : smoothingGroups.keySet()) {
			for (Face face : smoothingGroups.get(key).getFaces()) {
				if (face.getMaterial() == polygon.getMaterial()) {
					if (!polygon.getVertices().contains(smoothingGroups.get(key).getVertices().get(face.getIndices()[0])))
						polygon.getVertices().add(smoothingGroups.get(key).getVertices().get(face.getIndices()[0]));
					if (!polygon.getVertices().contains(smoothingGroups.get(key).getVertices().get(face.getIndices()[1])))
						polygon.getVertices().add(smoothingGroups.get(key).getVertices().get(face.getIndices()[1]));
					if (!polygon.getVertices().contains(smoothingGroups.get(key).getVertices().get(face.getIndices()[2])))
						polygon.getVertices().add(smoothingGroups.get(key).getVertices().get(face.getIndices()[2]));
					
					polygon.getIndices().add(polygon.getVertices().indexOf(smoothingGroups.get(key).getVertices().get(face.getIndices()[0])));
					polygon.getIndices().add(polygon.getVertices().indexOf(smoothingGroups.get(key).getVertices().get(face.getIndices()[1])));
					polygon.getIndices().add(polygon.getVertices().indexOf(smoothingGroups.get(key).getVertices().get(face.getIndices()[2])));
				}
			}
		}
	}
	
	private void generateTangents(Vertex v0, Vertex v1, Vertex v2) {
		Vector3 delatPos1 = v1.getPos().clone().sub(v0.getPos());
		Vector3 delatPos2 = v2.getPos().sub(v0.getPos());
		Vector2 uv0 = v0.getTextureCoord();
		Vector2 uv1 = v1.getTextureCoord();
		Vector2 uv2 = v2.getTextureCoord();
		Vector2 deltaUv1 = uv1.clone().sub(uv0);
		Vector2 deltaUv2 = uv2.clone().sub(uv0);

		float r = 1.0f / (deltaUv1.x * deltaUv2.y - deltaUv1.y * deltaUv2.x);
		delatPos1.scale(deltaUv2.y);
		delatPos2.scale(deltaUv1.y);
		Vector3 tangent = delatPos1.clone().sub(delatPos2);
		tangent.scale(r);
		v0.setTangent(tangent);
		v1.setTangent(tangent);
		v2.setTangent(tangent);
	}
	
	private GeometryBuilder convertToBuilder(Polygon polygon) {		
		int[] indices = polygon.getIndices().stream().mapToInt(i -> i).toArray();
		List<Vertex> vertices = polygon.getVertices();
		
		float[] positions = vertices.stream()
				.flatMap(vertex -> Stream.of(vertex.getPos().x, vertex.getPos().y, vertex.getPos().z))
				.collect(OBJUtil.getFloatArrayCollector());
		
		float[] normals = vertices.stream()
				.flatMap(vertex -> Stream.of(vertex.getNormal().x, vertex.getNormal().y, vertex.getNormal().z))
				.collect(OBJUtil.getFloatArrayCollector());
		
		float[] textureCoords = vertices.stream()
				.flatMap(vertex -> Stream.of(vertex.getTextureCoord().x, 1 - vertex.getTextureCoord().y))
				.collect(OBJUtil.getFloatArrayCollector());
		
		var builder = new GeometryBuilder()
				.setIndices(indices)
				.setVertices(positions)
				.setNormals(normals)
				.setTextureCoords(textureCoords);
		
		if (generateTangents) {
			float[] tangents = vertices.stream()
					.flatMap(vertex -> Stream.of(vertex.getTangent().x, vertex.getTangent().y, vertex.getTangent().z))
					.collect(OBJUtil.getFloatArrayCollector());
			
			builder.setTangents(tangents);
			
		}
		
		return builder;
	}
	
	public void clean() {
		vertices.clear();
		normals.clear();
		texCoords.clear();
	}
}
