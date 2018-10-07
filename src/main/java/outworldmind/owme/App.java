package outworldmind.owme;

import outworldmind.owme.core.Config;
import outworldmind.owme.core.Engine;
import outworldmind.owme.core.RenderState;
import outworldmind.owme.core.Renderer;
import outworldmind.owme.math.Color;
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
    			.setClearColor(new Color(1f, 0f, 0f))
    	);

    	owme.initialize();
    	renderer.prepare();
    	
    	var geometry = VAO.create();
    	
    	var size = 1.0f;
    	
    	geometry.bind();
    	geometry.createBuffer(new float[] {
			// front
			-size, size, size, size, size, size, size, -size, size, -size, -size, size,
			// back
			size, size, -size, -size, size, -size, -size, -size, -size, size, -size, -size,
			// top
			-size, size, -size, size, size, -size, size, size, size, -size, size, size,
			// bottom
			size, -size, -size, -size, -size, -size, -size, -size, size, size, -size, size,
			// left
			-size, size, -size, -size, size, size, -size, -size, size, -size, -size, -size,
			// right
			size, size, size, size, size, -size, size, -size, -size, size, -size, size 
    		
    	});
    	
    	while (true) {
    		renderer.draw(geometry);
    		if (owme.getWindow().getCloseRequest()) break;
    		owme.update();
    	}
    	
    	owme.stop();
    }
}
