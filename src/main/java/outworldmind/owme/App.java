package outworldmind.owme;

import outworldmind.owme.core.Config;
import outworldmind.owme.core.Console;
import outworldmind.owme.core.Engine;
import outworldmind.owme.math.Maths;
import outworldmind.owme.math.Matrix4;
import outworldmind.owme.math.Vector3;

public class App {
    public static void main(String[] args ) {
    	var config = new Config();
    	config.setParam(Engine.PARAM_WINDOW_NAME, "OWME Alpha Test Window");
    	config.setParam(Engine.PARAM_WINDOW_WIDTH, 1024);
    	config.setParam(Engine.PARAM_WINDOW_HEIGHT, 768);
    	
    	var engine = new Engine(config);
    	engine.initialize();
    	
    	var m = new Matrix4();
    	m.rotate(new Vector3(-3f, -3f, -3f));
    	Console.log(m.toString());

    	
    	while (true) {
    		engine.update((owme) -> {
    			owme.getRenderer().draw();
    			return null;
    		});
    		if (engine.getWindow().getCloseRequest()) break;
    	}
    	engine.stop();
    }
}
