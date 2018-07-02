package outworldmind.owme.unit;

import outworldmind.owme.tool.NumberGenerator;

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
