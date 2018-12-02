package outworldmind.owme;

import outworldmind.owme.core.Config;
import outworldmind.owme.core.Engine;
import outworldmind.owme.core.RenderState;
import outworldmind.owme.core.Renderer;
import outworldmind.owme.math.Color;
import outworldmind.owme.math.Matrix4;
import outworldmind.owme.shader.EntityShader;
import outworldmind.owme.unit.Geometry;
import outworldmind.owme.unit.VAO;

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
    	);

    	owme.initialize();
    	
    	var viewM4 = new Matrix4();
    	viewM4.scale(0.5f);
    	var projM4 = new Matrix4();
    	var transfM4 = new Matrix4();
    	
    	var shader = new EntityShader();
    	
    	shader
			.setValue(EntityShader.TRANSFORMATION_MATRIX, transfM4)
    		.setValue(EntityShader.PROJECTION_MATRIX, projM4)
			.setValue(EntityShader.VIEW_MATRIX, viewM4);
    	
    	Geometry geometry = VAO.create();
    	
    	geometry.bind();
    	
    	var size = 1.0f;
    	
    	((VAO) geometry).createFloatBuffer(0, 3, new float[] {
			// front
			-size, size, size, 
			size, size, size, 
			size, -size, size, 
			-size, -size, size,
			// back
			size, size, -size, 
			-size, size, -size, 
			-size, -size, -size, 
			size, -size, -size,
			// top
			-size, size, -size, 
			size, size, -size, 
			size, size, size, 
			-size, size, size,
			// bottom
			size, -size, -size, 
			-size, -size, -size, 
			-size, -size, size, 
			size, -size, size,
			// left
			-size, size, -size, 
			-size, size, size, 
			-size, -size, size, 
			-size, -size, -size,
			// right
			size, size, size, 
			size, size, -size, 
			size, -size, -size, 
			size, -size, size    		
    	});
    	
    	((VAO) geometry).createIndexBuffer(new int[] {
    		// front
    		0, 1, 2, 2, 1, 3,    		
    		// back
    		4, 5, 6, 6, 5, 7,    		
    		// top
    		8, 9, 10, 10, 9, 11,     		
    		// bottom
    		12, 13, 14, 14, 13, 15,    				   
    		// left
    		16, 17, 18, 18, 17, 19,    		
    		// right
    		20, 21, 22, 22, 21, 23
    	});
    	
    	geometry.unbind();
    	
    	renderer.setShader(shader);
    	
    	while (true) {
    		renderer.draw(geometry);
    		if (owme.getWindow().getCloseRequest()) break;
    		owme.update();
    	}
    	
    	owme.stop();
    }
}
