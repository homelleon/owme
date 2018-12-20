package outworldmind.owme;

import org.lwjgl.opengl.GL11;

import outworldmind.owme.core.Config;
import outworldmind.owme.core.Engine;
import outworldmind.owme.graphics.Material;
import outworldmind.owme.graphics.Model;
import outworldmind.owme.graphics.RenderState;
import outworldmind.owme.graphics.Renderer;
import outworldmind.owme.graphics.TextureBuilder;
import outworldmind.owme.graphics.Viewport;
import outworldmind.owme.maths.Color;
import outworldmind.owme.maths.Rotation;
import outworldmind.owme.maths.Vector3;
import outworldmind.owme.shaders.EntityShader;
import outworldmind.owme.tools.modelUtils.BoxGeometryBuilder;
import outworldmind.owme.units.Camera;
import outworldmind.owme.units.DrawUnit;
import outworldmind.owme.units.Scene;

public class App {
	
    public static void main(String[] args ) {
    	var config = new Config();
    	
    	config.setParam(Engine.PARAM_WINDOW_NAME, "OWME Alpha Test Window");
    	config.setParam(Engine.PARAM_WINDOW_WIDTH, 2048);
    	config.setParam(Engine.PARAM_WINDOW_HEIGHT, 1024);
    	
    	var owme = new Engine(config);
    	owme.start();
    	
    	var renderer = new Renderer(
        		new RenderState()
        			.setClearColor(new Color(0f, 0f, 0))
        			.setDepthTestMode(RenderState.DEPTH_LESS)
        			.setCullFaceMode(RenderState.CULL_BACK)
        	);
    	
    	var width = (int) config.getParam(Engine.PARAM_WINDOW_WIDTH);
    	var height = (int) config.getParam(Engine.PARAM_WINDOW_HEIGHT);
  	
    	var geometry = new BoxGeometryBuilder()
    			.setWidth(1)
    			.setHeight(1)
    			.setDepth(1)
    			.build();

    	var material = new Material()
    			.addTexture(Material.DIFFUSE, 
    					new TextureBuilder()
	    	    			.setName(Material.DIFFUSE)
	    	    			.setPath("/image/grass.png")
	    	    			.setType(GL11.GL_TEXTURE_2D)
	    	    			.build());
    	

    	var camera = new Camera(50f, 0.1f, 1000f, new Viewport(0, 0, width, height));
    	
    	camera
    		.move(new Vector3(0f, 0f, 2f));
    	
    	var shader = new EntityShader();
    	
    	var unit = new DrawUnit()
        		.move(new Vector3(0, 0, -9))
        		.rotate(new Rotation(0f, 0f, 0f))
        		.scaleTo(new Vector3(0.5f))
    			.setModel(new Model()
	    			.setGeometry(geometry)
	    			.setMaterial(material)
	    			.setRenderer(renderer)
	    			.setShader(shader));

    	var scene = new Scene()
    			.setCamera(camera)
    			.add(unit);    			
    	
    	shader.setValue(EntityShader.PROJECTION_MATRIX, camera.getProjection());
    	
    	while (true) {
    		if (owme.getWindow().getCloseRequest()) break;
    		unit.rotate(new Rotation(0f, 0f, 10f));
    		scene.draw();
    		owme.update();
    	}
    	
    	owme.stop();
    }
}
