package outworldmind.owme.unit;

import outworldmind.owme.math.Matrix4;

public class Camera extends SceneUnit {
	
	private Matrix4 View;
	private Matrix4 Projection;
	
	public Matrix4 getView() {
		return View;
	}
	
	public Matrix4 getProjection() {
		return Projection;
	}

}
