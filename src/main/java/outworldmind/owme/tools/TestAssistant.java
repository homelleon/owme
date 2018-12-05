package outworldmind.owme.tools;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

import outworldmind.owme.maths.Maths;
import outworldmind.owme.maths.Vector;
import outworldmind.owme.maths.Vector2;
import outworldmind.owme.maths.Vector3;

public class TestAssistant {
	
	public static void testVector3Method(String testName, Stream<TestUnit> testStream) {
		assertAll(testName,
				() -> testStream.forEach(test -> assertTrue(
						((Vector3) test.getExpected()).equals((Vector3) test.getActual()), 
						test.getDebugMessage()))
			);
	}
	
	public static void testVector3MethodByName(String className, String name, Stream<DstSrcOutPipe> testPipeStream) {
		try {
			var objectClass = Class.forName(className);
			assertAll(objectClass.getSimpleName() + "." + name,
				() -> testPipeStream.forEach(pipe -> {
					try {
						
						var dest = (Vector<?>) pipe.getInputDst();
						var source = pipe.getInputSrc();
						var expected = pipe.getOutput();
						
						var actual = estimateActual(name, objectClass, pipe);
						
						var messageVerb = name.toUpperCase().substring(0, 1) + name.substring(1, name.length()); 
						
						assertTrue(
							compareResult(objectClass, actual, expected),
							messageVerb + " for " + dest + 
							(source == null ? "" : 
								(source instanceof Object[] ? 
										(" with arg: " + ((Object[]) source).toString()) :
										(" with arg: " + source))) + 
							"; Expected: " + expected + " Actual: " + actual 
						);
					} catch (IllegalAccessException | IllegalArgumentException
							| NoSuchMethodException | SecurityException | NullPointerException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						assertSame(pipe.getOutput(), e.getCause().getMessage());
					}
				})
			);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static Object estimateActual(String methodName, Class<?> objectClass, DstSrcOutPipe pipe)
	throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
	NoSuchMethodException, SecurityException {
		var dest = (Vector<?>) pipe.getInputDst();
		var source = pipe.getInputSrc();
		
		if (source == null) {
			return objectClass.getMethod(methodName).invoke(dest);
		} else if (source instanceof Object[]) {
			return objectClass.getMethod(
					methodName, 
					Stream.of(source).map(Object::getClass).toArray(Class<?>[]::new)
				)
				.invoke(dest.clone(), source);
		} else {
			return objectClass.getMethod(methodName, source.getClass())
				.invoke(dest.clone(), source);
		}
	}
	
	private static boolean compareResult(Class<?> objectClass, Object actual, Object expected) 
	throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
	NoSuchMethodException, SecurityException {
		if (actual instanceof Vector3 && expected instanceof Vector3)
			return (Boolean) objectClass.getMethod("equals", objectClass).invoke(actual, expected);
		else if (actual instanceof Float && expected instanceof Float)
			return Maths.equal((float) actual, (float) expected);
		else if (actual instanceof Exception)
			return actual.getClass() == expected.getClass();
		else
			return actual.equals(expected);
	}
	
	public static void testVector2Method(String name, Stream<TestUnit> testStream) {
		assertAll(name,
				() -> testStream.forEach(test -> assertTrue(
						((Vector2) test.getExpected()).equals((Vector2) test.getActual()), 
						test.getDebugMessage()))
			);
	}

}
