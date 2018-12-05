package outworldmind.owme.units;

import java.util.ArrayList;
import java.util.List;

public class Scene {
	
	private Camera camera;
	private List<SceneUnit> units = new ArrayList<SceneUnit>();
	
	public Camera getCamera() {
		return camera;
	}
	
	public Scene setCamera(Camera camera) {
		this.camera = camera;
		
		return this;
	}
	
	public Scene add(SceneUnit unit) {
		units.add(unit);
		
		return this;
	}
	
	public Scene remove(SceneUnit unit) {
		units.remove(unit);
		
		return this;
	}
	
	public void clean() {
		units.clear();
	}
}
