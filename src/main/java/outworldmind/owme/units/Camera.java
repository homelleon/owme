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
	private boolean needsProjUpdate = true;
	
	public Camera(float fov, float nearPlane, float farPlane, Viewport viewport) {
		super();
		this.fov = fov;
		this.nearPlane = nearPlane;
		this.farPlane = farPlane;
		this.viewport = viewport;
		update();
	}
	
	public Camera(Config config) {
		this((float) config.getParam(Config.CAMERA_FOV),
				(float) config.getParam(Config.CAMERA_NEAR_PLANE),
				(float) config.getParam(Config.CAMERA_FAR_PLANE),
				new Viewport(0, 0, 
						(int) config.getParam(Config.WINDOW_WIDTH),
						(int) config.getParam(Config.WINDOW_HEIGHT)));
	}
	
	@Override
	public Camera update() {
		if (needsProjUpdate)
			updateProjectionMatrix();
		
		if (globalTransform.doesNeedUpdate())
			updateViewMatrix();
		super.update();
		
		return this;
	}
	
	private void updateViewMatrix() {
		View
			.setIdentity()
			.rotate(getRotation().getRadians())
			.translate(getPosition().negate());
	}
	
	private void updateProjectionMatrix() {
		if (!needsProjUpdate) return;
		
		Projection.makeProjectionMatrix(
				fov, nearPlane, farPlane, 
				viewport.getWidth(), viewport.getHeight());
		needsProjUpdate = false;
	}
	
	public Matrix4 getView() {
		return View;
	}
	
	public Matrix4 getProjection() {
		return Projection;
	}

	public float getFov() {
		return fov;
	}

	public void setFov(float fov) {
		this.fov = fov;
		
		needsProjUpdate = true;
	}

	public float getNearPlane() {
		return nearPlane;
	}

	public void setNearPlane(float nearPlane) {
		this.nearPlane = nearPlane;
		
		needsProjUpdate = true;
	}

	public float getFarPlane() {
		return farPlane;
	}

	public void setFarPlane(float farPlane) {
		this.farPlane = farPlane;
		
		needsProjUpdate = true;
	}

	public Viewport getViewport() {
		return viewport;
	}

	public void setViewport(Viewport viewport) {
		this.viewport = viewport;
		
		needsProjUpdate = true;
	}
	
	public boolean doesNeedProjectionUpdate() {
		return needsProjUpdate;
	}
	
	public boolean doesNeedViewUpdate() {
		return globalTransform.doesNeedUpdate();
	}
	
	

}
