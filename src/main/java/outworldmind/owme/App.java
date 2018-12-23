package outworldmind.owme;

import java.util.stream.IntStream;

import org.lwjgl.opengl.GL11;

import outworldmind.owme.core.Config;
import outworldmind.owme.core.Engine;
import outworldmind.owme.graphics.Material;
import outworldmind.owme.graphics.Model;
import outworldmind.owme.graphics.RenderState;
import outworldmind.owme.graphics.Renderer;
import outworldmind.owme.graphics.TextureBuilder;
import outworldmind.owme.maths.Color;
import outworldmind.owme.maths.Rotation;
import outworldmind.owme.maths.Vector3;
import outworldmind.owme.shaders.EntityShader;
import outworldmind.owme.tools.modelUtils.BoxGeometryBuilder;
import outworldmind.owme.tools.modelUtils.objLoader.OBJModelLoader;
import outworldmind.owme.units.Camera;
import outworldmind.owme.units.DrawUnit;
import outworldmind.owme.units.Scene;

public class App {
	
    public static void main(String[] args ) {
    	var config = new Config();
    	
    	config.setParam(Engine.PARAM_WINDOW_NAME, "OWME Alpha Test Window");
    	config.setParam(Engine.PARAM_WINDOW_WIDTH, 1024);
    	config.setParam(Engine.PARAM_WINDOW_HEIGHT, 768);
    	
    	config.setParam(Engine.PARAM_CAMERA_FOV, 50f);
    	config.setParam(Engine.PARAM_CAMERA_NEAR_PLANE, 0.1f);
    	config.setParam(Engine.PARAM_CAMERA_FAR_PLANE, 1000f);
    	
    	var owme = new Engine(config);
    	owme.start();
    	
    	var renderer = new Renderer(
        		new RenderState()
        			.setClearColor(new Color(0f, 0f, 0))
        			.setDepthTestMode(RenderState.DEPTH_LESS)
        			.setCullFaceMode(RenderState.CULL_BACK)
        	);
    	
    	var geometry = new BoxGeometryBuilder()
    			.setWidth(1)
    			.setHeight(1)
    			.setDepth(1)
    			.build();

    	var material1 = new Material()
    			.addTexture(Material.DIFFUSE, 
    					new TextureBuilder()
	    	    			.setName(Material.DIFFUSE)
	    	    			.setPath("/image/grass.png")
	    	    			.setType(GL11.GL_TEXTURE_2D)
	    	    			.setBindLocation(0)
	    	    			.build());

    	var camera = new Camera(config);
    	
    	camera
    		.move(new Vector3(0, 1.0f, 0))
    		.rotate(new Rotation(-25, 0, 0));
    	
    	var shader = new EntityShader();
    	
    	var unit = new DrawUnit()
        		.move(new Vector3(0, 0, -3))
        		.scaleTo(new Vector3(1))
    			.setModel(new Model()
	    			.setGeometry(geometry)
	    			.setMaterial(material1)
	    			.setRenderer(renderer)
	    			.setShader(shader));
    	
    	var spartan = new DrawUnit()
        		.move(new Vector3(1, 0.1f, -1))
        		.scaleTo(new Vector3(0.005f))
    			.setModel(new OBJModelLoader().load("/model/", "spartan")[0]
	    			.setRenderer(renderer)
	    			.setShader(shader));

    	var scene = new Scene()
    			.setCamera(camera)
    			.add(unit);
    	
    	IntStream.range(0, 10)
    		.mapToObj(index -> spartan.clone().move(new Vector3(index * -0.2f, 0, 0)))
    		.forEach(scene::add);
    	
    	shader.setValue(EntityShader.PROJECTION_MATRIX, camera.getProjection());
    	
    	while (true) {
    		if (owme.getWindow().getCloseRequest()) break;
    		scene.draw();
    		owme.update();
    	}
    	
    	owme.stop();
    }
}
