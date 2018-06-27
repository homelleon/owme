package outworldmind.owme.unit;

public class Scene extends SceneUnit {
	
	private Camera camera;
	
	public Camera getCamera() {
		return camera;
	}
	
	public Scene setCamera(Camera camera) {
		this.camera = camera;
		
		return this;
	}
}
