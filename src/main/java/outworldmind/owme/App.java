package outworldmind.owme;

import outworldmind.owme.core.WindowManager;

public class App {
    public static void main(String[] args ) {
    	
    	var window = new WindowManager(1024, 768);
    	window.update();
    	window.destroy();        
    }
}
