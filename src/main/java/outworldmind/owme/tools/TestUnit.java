package outworldmind.owme.tools;

public class TestUnit {
	
	private Object expected;
	private Object actual;
	private String message;
	
	public TestUnit(Object expected, Object actual) {
		this.expected = expected;
		this.actual = actual;
	}
	
	public TestUnit(Object expected, Object actual, String message) {
		this(expected, actual);
		this.message = message;
	}
	
	public Object getExpected() {
		return expected;
	}
	
	public Object getActual() {
		return actual;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getDebugMessage() {
		return message + " Expected: " + expected + ", Actual: " + actual;
	}

}
