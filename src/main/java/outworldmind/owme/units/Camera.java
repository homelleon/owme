package outworldmind.owme.units;

import outworldmind.owme.core.Config;
import outworldmind.owme.core.Engine;
import outworldmind.owme.graphics.Viewport;
import outworldmind.owme.maths.Matrix4;

public class Camera extends SceneUnit {
	
	private Matrix4 Projection = new Matrix4();
	private Matrix4 View = new Matrix4();
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
	
	public Camera(Config config) {
		this((float) config.getParam(Engine.PARAM_CAMERA_FOV),
				(float) config.getParam(Engine.PARAM_CAMERA_NEAR_PLANE),
				(float) config.getParam(Engine.PARAM_CAMERA_FAR_PLANE),
				new Viewport(0, 0, 
						(int) config.getParam(Engine.PARAM_WINDOW_WIDTH),
						(int) config.getParam(Engine.PARAM_WINDOW_HEIGHT)));
	}
	
	public Matrix4 getView() {		
		return View
				.setIdentity()
				.rotate(getRotation().getRadians())
				.translate(getPosition().negate());
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
