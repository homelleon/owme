package outworldmind.owme.tools;

public class NumberGenerator {
	
	private static int count = 0;
	
	
	public static int generateId() {
		return ++count;
	}

}
