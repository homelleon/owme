package outworldmind.owme.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Garbage collector for disposable objects.
 * 
 * @author homelleon
 *
 */
public class GC {
	
	private static List<Disposable> objects = new ArrayList<Disposable>();
	
	private static boolean disengaging = false;
	
	/**
	 * Release all following objects and return its number.
	 * 
	 * @return int count of objects released
	 */
	protected static int disengage() {
		if (objects.isEmpty()) return 0;
		disengaging = true;
		
		var count = objects.size();
		objects.stream().forEach(Disposable::dispose);
		objects.clear();
		
		disengaging = false;
		return count;
	}
	
	public static void follow(Disposable object) {
		objects.add(object);
	}
	
	public static boolean forget(Disposable object) {
		if (!objects.contains(object) || disengaging) return false;
		
		objects.remove(object);
		return true;
	}
	
}
