package outworldmind.owme.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Vector3")
public class Vector3Test {
	
	@Test
	@DisplayName("create") 
	public void testCreation() {
		Vector3 v = createVector();
		assertEquals(0, v.x, "x: ");
		assertEquals(0, v.y, "y: ");
		assertEquals(1, v.z, "z: ");
	}
	
	private Vector3 createVector() {
		return new Vector3();
	}
	
	@Disabled
	@Test
	@DisplayName("create with args x, y, z")
	public void testCreationWithArgs() {
//		Vector3 v = new Vector3(x, y, z);
	}

}
