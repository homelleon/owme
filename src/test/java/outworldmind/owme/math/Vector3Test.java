package outworldmind.owme.math;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import outworldmind.owme.tool.TestUnit;

@DisplayName("Vector3")
public class Vector3Test {
	
	@Test
	@DisplayName("create") 
	public void testCreation() {
		var tester = new Vector3CreationTester();
		tester.testNoArgsCreation();
		tester.test3ArgsCreation();
		tester.testVec3ArgCreation();
		tester.testPlaneArgCreation();
	}
	
	@Test
	@DisplayName("service methods")
	public void testServiceMethods() {
		var tester = new Vector3ServiceTester();
		tester.testEquals();
		tester.testCopy();
		tester.testCompareTo();
		tester.testClone();
		tester.testToString();		
	}
	
	@Test
	@Disabled
	@DisplayName("operations")
	public void testOperatopns() {
		var tester = new Vector3OperationsTester();
//		tester.testLengthSquared();
//		tester.testLength();
//		tester.testNegate();
//		tester.testDot();
//		tester.testCross();
//		tester.testScale();
//		tester.testNormalize();
//		tester.testRotate();
//		tester.testAddVector();
//		tester.testAddValue();
//		tester.testSubVector();
//		tester.testSubValue();
//		tester.testMulVector();
//		tester.testMulValue();
//		tester.testDivVector();
//		tester.testDivValue();
//		tester.testAbs();		
	}

}

class Vector3CreationTester {
	public void testNoArgsCreation() {
		assertAll("With no arguments",
			() -> assertTrue(
					new Vector3().getClass().getName()
						.equals("outworldmind.owme.math.Vector3"), 
						"class is Vector3"),
			() -> noArgs().forEach(
					value -> assertEquals(
							value.getExpected(), value.getActual(), value.getMessage()))
		);
		
	}
	
	private Stream<TestUnit> noArgs() {
		var vector = new Vector3();
		return Stream.of(
			new TestUnit(0.0f, vector.x, "x: "),
			new TestUnit(0.0f, vector.y, "y: "),
			new TestUnit(0.0f, vector.z, "z: ")
		);
	}
	
	public void test3ArgsCreation() {
		var vector = new Vector3(-1, 0, 3);
		assertAll("With 3 arguments",
			() -> assertTrue(
					vector.getClass().getName().equals("outworldmind.owme.math.Vector3"), 
					"class is Vector3"),
			() -> args3().forEach(this::assertCreationValue)
		);
	}
	
	public static Stream<TestUnit> args3() {
		return Stream.of(
			new TestUnit(new float[]{0, 0, 0}, new Vector3(0, 0, 0), "(0, 0, 0)"),
			new TestUnit(new float[]{0, 1, 2}, new Vector3(0, 1, 2), "(0, 1, 2)"),
			new TestUnit(new float[]{-1, 0, 3}, new Vector3(-1, 0, 3), "(-1, 0, 3)"),
			new TestUnit(new float[]{-100, 500, -30}, new Vector3(-100, 500, -30), "(-100, 500, -30)")
		);
	}
	
	private void assertCreationValue(TestUnit value) {
		var expectedArray = (float[]) value.getExpected();
		var actualVector = (Vector3) value.getActual();
		var message = value.getMessage();
		assertEquals(expectedArray[0], actualVector.x, message + " x: ");
		assertEquals(expectedArray[1], actualVector.y, message + " y: ");
		assertEquals(expectedArray[2], actualVector.z, message + " z: ");
	}
	
	public void testVec3ArgCreation() {
		var vectorBase = new Vector3(-1, 0, 3);
		var vector = new Vector3(vectorBase);
		assertAll("With Vector3 argument",
			() -> assertTrue(
					vector.getClass().getName().equals("outworldmind.owme.math.Vector3"), 
					"class is Vector3"),
			() -> args3Vec().forEach(this::assertCreationValue)
		);
	}
	
	private Stream<TestUnit> args3Vec() {
		var vectors = Stream.of(
			new Vector3(0, 0, 0),
			new Vector3(0, 1, 2),
			new Vector3(-1, 0, 3),
			new Vector3(-100, 500, -30)
		);
		
		return vectors.map(vector -> 
			new TestUnit(
				new float[] {vector.x, vector.y, vector.z},
				new Vector3(vector),
				vector.toString()
			)					
		);
	}
	
	public void testPlaneArgCreation() {
		var plane = new Vector4(-1, 0, 3, 1);
		var vector = new Vector3(plane);
		assertAll("With plane argument",
			() -> assertTrue(
					vector.getClass().getName().equals("outworldmind.owme.math.Vector3"), 
					"class is Vector3"),
			() -> argsPlane().forEach(this::assertCreationValue)
		);
	}
	
	private Stream<TestUnit> argsPlane() {
		var planes = Stream.of(
			new Vector4(0, 0, 0, 1),
			new Vector4(0, 1, 2, 3),
			new Vector4(-1, 0, 3, -2),
			new Vector4(-100, 500, -30, 50)
		);
		
		return planes.map(plane -> 
			new TestUnit(
				new float[] {plane.x, plane.y, plane.z},
				new Vector3(plane),
				plane.toString()
			));
	}
}

class Vector3ServiceTester {
	
	public void testEquals() {
		var vectorA = new Vector3(0, 0, 0);
		var vectorB = new Vector3(1, 1, 1);
		var vectorC = new Vector3(0, 0, 0);
		assertAll("Equals",
			() -> assertTrue(vectorA.equals(vectorA), vectorA + "should be equal self"),
			() -> assertTrue(vectorA.equals(vectorC), vectorA + "should be equal to " + vectorC),
			() -> assertFalse(vectorA.equals(vectorB), vectorA + "should not be equal to " + vectorB)
		);
	}
	
	public void testCopy() {
		var baseVector = new Vector3(-1, 0, 3);
		var vector = new Vector3(2, -1, 3);
		assertAll("Copy",
			() -> assertTrue(vector.copy(baseVector).equals(baseVector), baseVector.toString())
		);
	}
	
	public void testCompareTo() {
		var vectorA = new Vector3(0, 0, 0);
		var vectorB = new Vector3(1, 1, 1);
		var vectorC = new Vector3(1, 1, 1);
		assertAll("CompareTo",
			() -> assertTrue(vectorB.compareTo(vectorB) == 0, vectorB + "should be equal to self"),
			() -> assertTrue(vectorB.compareTo(vectorC) == 0, vectorB + "should be equal to " + vectorC),
			() -> assertTrue(vectorA.compareTo(vectorB) == -1, vectorA + "should be less then " + vectorB),
			() -> assertTrue(vectorB.compareTo(vectorA) == 1, vectorB + "should be more then " + vectorA)
		);
	}
	
	public void testClone() {
		var vector = new Vector3(-1, 0, 3);
		assertAll("Clone",
			() -> assertTrue(vector.clone().equals(vector), vector.toString())
		);
	}
	
	public void testToString() {
		var vector1 = new Vector3();
		var vector2 = new Vector3(1, 0, -3);
		assertAll("ToString",
			() -> assertEquals(vector1.toString(), "(0.0, 0.0, 0.0)", "with no arguments"),
			() -> assertEquals(vector2.toString(), "(1.0, 0.0, -3.0)", "with arguments")
		);
	}
}

class Vector3OperationsTester {
//	public void testLengthSquared()
//	public void testLength()
//	public void testNegate()
//	public void testDot()
//	public void testCross()
//	public void testScale()
//	public void testNormalize()
//	public void testRotate()
//	public void testAddVector()
//	public void testAddValue()
//	public void testSubVector()
//	public void testSubValue()
//	public void testMulVector()
//	public void testMulValue()
//	public void testDivVector()
//	public void testDivValue()
//	public void testAbs()
}
