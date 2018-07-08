package outworldmind.owme.math;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import outworldmind.owme.tool.DstSrcOutPipe;
import outworldmind.owme.tool.TestAssistant;
import outworldmind.owme.tool.TestUnit;

@DisplayName("Vector3")
public class Vector3Test {
	
	static final String CLASS_NAME = Vector3.class.getName();
	
	@Test
	@DisplayName("create") 
	public void testCreation() {
		var tester = new Vector3CreationTester(CLASS_NAME);
		tester.testNoArgsCreation();
		tester.test3ArgsCreation();
		tester.testVec3ArgCreation();
		tester.testPlaneArgCreation();
	}
	
	@Test
	@DisplayName("service methods")
	public void testServiceMethods() {
		var tester = new Vector3ServiceTester(CLASS_NAME);
		tester.testEquals();
		tester.testCopy();
		tester.testCompareTo();
		tester.testClone();
		tester.testToString();		
	}
	
	@Test
	@DisplayName("operations")
	public void testOperatopns() {
		var tester = new Vector3OperationsTester(CLASS_NAME);
		tester.testLengthSquared();
		tester.testLength();
		tester.testNegate();
		tester.testDot();
		tester.testCross();
		tester.testScale();
		tester.testNormalize();
		tester.testRotate();
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
	
	private String className;
	
	Vector3CreationTester(String className) {
		this.className = className;
	}
	
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
	
	private String className;
	
	Vector3ServiceTester(String className) {
		this.className = className;
	}
	
	public void testEquals() {
		var vectorA = new Vector3(0, 0, 0);
		var vectorB = new Vector3(1, 1, 1);
		var vectorC = new Vector3(0, 0, 0);
		
		var tests = Stream.of(
			new DstSrcOutPipe(vectorA, vectorA, true),
			new DstSrcOutPipe(vectorA, vectorC, true),
			new DstSrcOutPipe(vectorA, vectorB, false)
		);
		
		TestAssistant.testVector3MethodByName(className, "equals", tests);
	}
	
	public void testCopy() {
		var baseVector = new Vector3(-1, 0, 3);
		var vector = new Vector3(2, -1, 3);
		
		var tests = Stream.of(
			new DstSrcOutPipe(vector, baseVector, baseVector)
		);
		
		TestAssistant.testVector3MethodByName(className, "copy", tests);
	}
	
	public void testCompareTo() {
		var vectorA = new Vector3(0, 0, 0);
		var vectorB = new Vector3(1, 1, 1);
		var vectorC = new Vector3(1, 1, 1);
		
		var tests = Stream.of(
			new DstSrcOutPipe(vectorB, vectorB, 0),
			new DstSrcOutPipe(vectorB, vectorC, 0),
			new DstSrcOutPipe(vectorA, vectorB, -1),
			new DstSrcOutPipe(vectorB, vectorA, 1)
		);
		
		TestAssistant.testVector3MethodByName(className, "compareTo", tests);
	}
	
	public void testClone() {
		var vector = new Vector3(-1, 0, 3);
		
		var tests = Stream.of(
			new DstSrcOutPipe(vector, null, vector)
		);
		
		TestAssistant.testVector3MethodByName(className, "clone", tests);
	}
	
	public void testToString() {
		var tests = Stream.of(
			new DstSrcOutPipe(new Vector3(), null, "(0.0, 0.0, 0.0)"),
			new DstSrcOutPipe(new Vector3(1, 0, -3), null, "(1.0, 0.0, -3.0)")
		);
			
		TestAssistant.testVector3MethodByName(className, "toString", tests);
	}
}

class Vector3OperationsTester {
	
	private String className;
	
	Vector3OperationsTester(String className) {
		this.className = className;
	}
	
	public void testLengthSquared() {
		var tests = Stream.of(
			new DstSrcOutPipe(new Vector3(), null, 0.0f),
			new DstSrcOutPipe(new Vector3(1, 0, -3), null, 10f)
		);
			
		TestAssistant.testVector3MethodByName(className, "lengthSquared", tests);
	}
	
	public void testLength() {
		var tests = Stream.of(
			new DstSrcOutPipe(new Vector3(), null, 0.0f),
			new DstSrcOutPipe(new Vector3(1, 0, -3), null, 3.162277660168379f)
		);
		
		TestAssistant.testVector3MethodByName(className, "length", tests);
	}
	
	public void testNegate() {
		var tests = Stream.of(
			new DstSrcOutPipe(new Vector3(), null, new Vector3(0, 0, 0)),
			new DstSrcOutPipe(new Vector3(1, 0, -3), null, new Vector3(-1, 0, 3))
		);
		
		TestAssistant.testVector3MethodByName(className, "negate", tests);
	}
	
	public void testDot() {
		var vector1 = new Vector3();
		var vector2 = new Vector3(1, 0, -3);
		var vector3 = new Vector3(0, -2, 1);
		
		var tests = Stream.of(
			new DstSrcOutPipe(vector1, vector2, 0.0f),
			new DstSrcOutPipe(vector2, vector3, -3.0f),
			new DstSrcOutPipe(vector3, vector2, -3.0f)
		);
		
		TestAssistant.testVector3MethodByName(className, "dot", tests);
	}
	
	public void testCross() {
		var vector1 = new Vector3();
		var vector2 = new Vector3(1, 0, -3);
		var vector3 = new Vector3(0, -2, 1);
		
		var tests = Stream.of(
			new DstSrcOutPipe(vector1, vector2, new Vector3(0, 0, 0)),
			new DstSrcOutPipe(vector2, vector3, new Vector3(-6, -1, -2)),
			new DstSrcOutPipe(vector3, vector2, new Vector3(6, 1, 2))
		);
		
		TestAssistant.testVector3MethodByName(className, "cross", tests);
	}
	
	public void testScale() {
		var vector1 = new Vector3();
		var vector2 = new Vector3(1, 0, -3);
		
		var tests = Stream.of(
			new DstSrcOutPipe(vector1, 5.0f, new Vector3()),
			new DstSrcOutPipe(vector2, 5.0f, new Vector3(5, 0, -15)),
			new DstSrcOutPipe(vector2, -5.0f, new Vector3(-5, 0, 15))
		);
		
		TestAssistant.testVector3MethodByName(className, "scale", tests);
	}
	
	public void testNormalize() {
		var tests = Stream.of(
			new DstSrcOutPipe(new Vector3(), null, new Vector3(0, 0, 0)),
			new DstSrcOutPipe(new Vector3(1, 0, -3), null, new Vector3(0.3162277f, 0.0f, -0.948683298f))
		);
		
		TestAssistant.testVector3MethodByName(className, "normalize", tests);
	}
	
	public void testRotate() {
		var angle1 = new Object[] { 5.0f, new Vector3(1, 0, 0) };
		var angle2 = new Object[] { 5.0f, new Vector3(0, 1, 0) };
		var angle3 = new Object[] { 5.0f, new Vector3(0, 0, 1) };
		var angle4 = new Object[] { -3.0f, new Vector3(1, 1, 1) };
		var angle5 = new Object[] { 2.0f, new Vector3(-2, 0, 1) };
		
		// TODO: make it succeed
//		var tests = Stream.of(
//			new DstSrcOutPipe(new Vector3(), angle1 ,new Vector3(0, 0, 0)),
//			new DstSrcOutPipe(new Vector3(1, 2, 3), angle2, new Vector3(0, 0, 0)),
//			new DstSrcOutPipe(new Vector3(1, 2, 3), angle3, new Vector3(0, 0, 0)),
//			new DstSrcOutPipe(new Vector3(1, 2, 3), angle4, new Vector3(0, 0, 0)),
//			new DstSrcOutPipe(new Vector3(1, 2, 3), angle5, new Vector3(0, 0, 0))
//		);
//		
//		TestAssistant.testVector3MethodByName(className, "rotate", tests);
	}
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
