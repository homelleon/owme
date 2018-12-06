package outworldmind.owme;

import outworldmind.owme.core.Config;
import outworldmind.owme.core.Engine;
import outworldmind.owme.graphics.RenderState;
import outworldmind.owme.graphics.Renderer;
import outworldmind.owme.graphics.Viewport;
import outworldmind.owme.graphics.primitives.BoxGeometryBuilder;
import outworldmind.owme.maths.Color;
import outworldmind.owme.maths.Matrix4;
import outworldmind.owme.maths.Vector3;
import outworldmind.owme.shaders.EntityShader;
import outworldmind.owme.units.Camera;

public class App {
	
    public static void main(String[] args ) {
    	var config = new Config();
    	
    	config.setParam(Engine.PARAM_WINDOW_NAME, "OWME Alpha Test Window");
    	config.setParam(Engine.PARAM_WINDOW_WIDTH, 1024);
    	config.setParam(Engine.PARAM_WINDOW_HEIGHT, 768);
    	
    	var owme = new Engine(config);
    	
    	var renderer = new Renderer(
        		new RenderState()
        			.setClearColor(new Color(0f, 0f, 0))
        			.setDepthTestMode(RenderState.DEPTH_LESS)
        			.setCullFaceMode(RenderState.CULL_BACK)
        	);
    	
    	var width = (int) config.getParam(Engine.PARAM_WINDOW_WIDTH);
    	var height = (int) config.getParam(Engine.PARAM_WINDOW_HEIGHT);
    	var camera = new Camera(60f, 0.1f, 1000f, new Viewport(0, 0, width, height));
    	var Transformation = new Matrix4()
    		.translate(new Vector3(0f, 0f, -10.0f))
    		.rotate(0f, new Vector3(0, 1, 0))
    		.scale(2f);    	
    	
    	var shader = new EntityShader();
    	
    	shader
			.setValue(EntityShader.TRANSFORMATION_MATRIX, Transformation)
    		.setValue(EntityShader.PROJECTION_MATRIX, camera.getProjection())
			.setValue(EntityShader.VIEW_MATRIX, camera.getView());

    	owme.start();    	
    	shader.init();
    	renderer.setShader(shader);

    	var boxBuilder = new BoxGeometryBuilder();    	
    	var geometry = boxBuilder
    			.setWidth(1)
    			.setHeight(1)
    			.setDepth(1)
    			.build();
    	
    	while (true) {
    		if (owme.getWindow().getCloseRequest()) break;
    		camera.update();
    		shader.setValue(EntityShader.VIEW_MATRIX, camera.getView());
    		renderer.draw(geometry);
    		owme.update();
    	}
    	
    	owme.stop();
    }
}
