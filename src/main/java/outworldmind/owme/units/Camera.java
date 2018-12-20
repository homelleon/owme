package outworldmind.owme.units;

import outworldmind.owme.graphics.Viewport;
import outworldmind.owme.maths.Matrix4;

public class Camera extends SceneUnit {
	
	private Matrix4 Projection = new Matrix4();
	private Viewport viewport;
	private float fov;
	private float nearPlane;
	private float farPlane;
	
	public Camera(float fov, float nearPlane, float farPlane, Viewport viewport) {
		super();
		this.fov = fov;
		this.nearPlane = nearPlane;
		this.farPlane = farPlane;
		this.viewport = viewport;
		updateProjectionMatrix();
	}
	
	public Matrix4 getProjection() {
		return Projection;
	}
	
	private void updateProjectionMatrix() {
		Projection.makeProjectionMatrix(
				fov, nearPlane, farPlane, 
				viewport.getWidth(), viewport.getHeight());
	}

}
