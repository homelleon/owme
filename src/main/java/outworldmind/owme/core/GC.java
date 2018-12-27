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
	
	private List<Disposable> objects;
	
	private boolean disengaging = false;
	
	protected GC() {
		objects = new ArrayList<Disposable>();
	}
	
	/**
	 * Release all following objects and return its number.
	 * 
	 * @return int count of objects released
	 */
	protected int disengage() {
		if (objects.isEmpty()) return 0;
		disengaging = true;
		
		var count = objects.size();
		objects.stream().forEach(Disposable::dispose);
		objects.clear();
		
		disengaging = false;
		return count;
	}
	
	public void follow(Disposable object) {
		objects.add(object);
	}
	
	public boolean forget(Disposable object) {
		if (!objects.contains(object) || disengaging) return false;
		
		objects.remove(object);
		return true;
	}
	
}
