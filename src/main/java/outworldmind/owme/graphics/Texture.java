package outworldmind.owme.graphics;

import outworldmind.owme.tools.NumberGenerator;

public class Texture {
	
	private int id;
	
	public Texture() {
		id = NumberGenerator.generateId();
	}
	
	public int getId() {
		return id;
	}
	
	public void destroy() {
		
	}

}
