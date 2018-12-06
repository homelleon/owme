package outworldmind.owme.graphics.primitives;

import outworldmind.owme.graphics.Geometry;
import outworldmind.owme.graphics.VAO;

public class BoxGeometryBuilder {
	
	private float width;
	private float height;
	private float depth;
	
	public BoxGeometryBuilder setWidth(float width) {
		this.width = width;
		
		return this;
	}
	
	public BoxGeometryBuilder setHeight(float height) {
		this.height = height;
		
		return this;
	}
	
	public BoxGeometryBuilder setDepth(float depth) {
		this.depth = depth;
		
		return this;
	}
	
	public Geometry build() {
    	var geometry = VAO.create();
    	
    	geometry.bind();
    	
    	var x = width / 2;
    	var y = height / 2;
    	var z = depth / 2;
    	
    	((VAO) geometry).createFloatBuffer(0, 3, new float[] {
    			// front
    			-x, y, z, 
    			-x, -y, z, 
    			x, -y, z, 
    			x, y, z,
    			
    			// back 
    			-x, y, -z, 
    			-x, -y, -z, 
    			x, -y, -z,  
    			x, y, -z,
    			
    			// top
    			-x, y, -z,
    			-x, y, z,
    			x, y, z,
    			x, y, -z,
    			
    			// bottom
    			-x, -y, -z,
    			-x, -y, z,
    			x, -y, z,
    			x, -y, -z,
    			
    			// left
    			-x, y, z,
    			-x, -y, z,
    			-x, -y, -z,
    			-x, y, -z,
    			
    			// right
    			x, y, z,
    			x, -y, z,
    			x, -y, -z,
    			x, y, -z
        	});
    	
    	((VAO) geometry).createIndexBuffer(new int[] {
    		// front
    		0, 1, 3, 3, 1, 2,
    		// back
    		6, 5, 7, 7, 5, 4,
    		// top
    		8, 9 , 11, 11, 9, 10,
    		// bottom
    		12, 13, 15, 15, 13, 14,
    		// left
    		18, 17, 19, 19, 17, 16,
    		// right
    		20, 21, 23, 23, 21, 22
    	});
    	
    	geometry.unbind();
    	
    	return geometry;
	}

}
