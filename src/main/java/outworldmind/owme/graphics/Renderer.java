package outworldmind.owme.graphics;

import static org.lwjgl.opengl.GL11.glClearColor;

import org.lwjgl.opengl.GL11;

import outworldmind.owme.tools.NumberGenerator;

public class Renderer {
	
	private int id;
	private RenderState state;
	private boolean needReset = true;
	private Geometry currentGeometry = null; 
	
	public Renderer(RenderState state) {
		id = NumberGenerator.generateId();
		setState(state);
	}
	
	private void reset() {
		var color = state.getClearColor();
		glClearColor(color.r, color.b, color.g, color.a);
		var depthTestMode = state.getDepthTestMode();
		var cullFaceMode = state.getCullFaceMode();
		
		if (depthTestMode == RenderState.NONE)
			GL11.glDisable(GL11.GL_DEPTH_TEST);
		else {
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glDepthFunc(depthTestMode);
		}
		
		if (cullFaceMode == RenderState.NONE)
			GL11.glDisable(GL11.GL_CULL_FACE);
		else {
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glCullFace(cullFaceMode);
		}
		
		needReset = false;
	}
	
	public int getId() {
		return id;
	}
	
	public void setState(RenderState state) {
		this.state = state;
		needReset = true;
	}
	
	public void draw(Geometry geometry) {
		if (needReset) reset();
		bindGeometryIfChanged(geometry);
//		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		GL11.glDrawElements(GL11.GL_TRIANGLES, geometry.size(), GL11.GL_UNSIGNED_INT, 0);
	}
	
	private void bindGeometryIfChanged(Geometry geometry) {
		if (!needReset && currentGeometry != null && currentGeometry.equals(geometry)) return;
		currentGeometry = geometry;
		geometry.bind();		
	}

}
