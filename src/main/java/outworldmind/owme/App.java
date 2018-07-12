package outworldmind.owme;

import outworldmind.owme.core.Config;
import outworldmind.owme.core.Engine;

public class App {
    public static void main(String[] args ) {
    	  	
    	var config = new Config();
    	config.setParam(Engine.PARAM_WINDOW_NAME, "OWME Alpha Test Window");
    	config.setParam(Engine.PARAM_WINDOW_WIDTH, 1024);
    	config.setParam(Engine.PARAM_WINDOW_HEIGHT, 768);
    	
    	var engine = new Engine(config);
    	engine.initialize();
    	
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
