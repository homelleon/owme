package outworldmind.owme.tool;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Console;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

import outworldmind.owme.math.Maths;
import outworldmind.owme.math.Vector;
import outworldmind.owme.math.Vector2;
import outworldmind.owme.math.Vector3;

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
						
						var actual = source == null ?
								objectClass.getMethod(name).invoke(dest) :
								(source instanceof Object[] ?
										objectClass.getMethod(name, 
												((Object[]) source)[0].getClass(), 
												((Object[]) source)[1].getClass())
										.invoke(dest.clone(), 
												((Object[]) source)[0], 
												((Object[]) source)[1])
										: objectClass.getMethod(name, source.getClass()).invoke(dest.clone(), source));
						
						var messageVerb = name.toUpperCase().substring(0, 1) + name.substring(1, name.length()); 
						Boolean check = false;
						
						if (actual instanceof Vector3 && expected instanceof Vector3)
							check = (Boolean) objectClass.getMethod("equals", objectClass).invoke(actual, expected);
						else if (actual instanceof Float && expected instanceof Float)
							check = Maths.equal((float) actual, (float) expected);
						else if (actual instanceof Exception)
							check = actual.getClass() == expected.getClass();
						else
							check = actual.equals(expected);
						
						assertTrue(
							check,
							messageVerb + " for " + dest + 
							(source == null ? "" : 
								(source instanceof Object[] ? 
										(" with arg: " + ((Object[]) source)[0] + ", " +  ((Object[]) source)[1]) :
										(" with arg: " + source))) + 
							"; Expected: " + expected + " Actual: " + actual 
						);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
							| NoSuchMethodException | SecurityException | NullPointerException e) {
						e.printStackTrace();
						Console.log(e.getCause());
					}
				})
			);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testVector2Method(String name, Stream<TestUnit> testStream) {
		assertAll(name,
				() -> testStream.forEach(test -> assertTrue(
						((Vector2) test.getExpected()).equals((Vector2) test.getActual()), 
						test.getDebugMessage()))
			);
	}

}
