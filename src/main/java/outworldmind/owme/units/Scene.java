package outworldmind.owme.units;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import outworldmind.owme.graphics.Model;
import outworldmind.owme.graphics.Shader;
import outworldmind.owme.graphics.Texture;

public class Scene {
	
	private Camera camera;
	private List<SceneUnit> units = new ArrayList<SceneUnit>();
	private Map<Integer, Texture> texturesById = new HashMap<Integer, Texture>();
	private boolean needProjUpdate = true;
	private boolean needViewUpdate = true;
	
	public Scene draw() {
		checkCameraUpdate();
		camera.update();
		units.forEach(SceneUnit::update);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		units.stream()
			.filter(unit -> {return unit instanceof DrawUnit;})
			.map(unit -> (DrawUnit) unit)
			.collect(Collectors.groupingBy(DrawUnit::getModel))
			.forEach(this::draw);
		
		return this;
	}
	
	private void checkCameraUpdate() {
		if (camera.doesNeedProjectionUpdate())
			needProjUpdate = true;
		if (camera.doesNeedViewUpdate())
			needViewUpdate = true;
	}
	
	private void draw(Model model, List<DrawUnit> units) {	
		var renderer = model.getRenderer();
		var shader = model.getShader();
		var material = model.getMaterial();
		var geometry = model.getGeometry();
		
		if (doProjectionUpdateOnRequired())
			shader.setValue(Shader.PROJECTION_MATRIX, camera.getProjection());
		
		if (doViewUpdateOnRequire())
			shader.setValue(Shader.VIEW_MATRIX, camera.getView());
		
		material.getTextures().forEach(this::tryToBindTexture);
		
		shader.start();
		units.forEach(unit -> {
			shader.setValue(Shader.TRANSFORMATION_MATRIX, unit.getTransformMatrix());
			shader.update();
			renderer.draw(geometry);
		});
	}
	
	private boolean doProjectionUpdateOnRequired() {
		if (!needProjUpdate) return false;
		needProjUpdate = false;
		
		return true;
	}
	
	private boolean doViewUpdateOnRequire() {
		if (!needViewUpdate) return false;
		needViewUpdate = false;
		
		return true;
	}
	
	private void tryToBindTexture(Texture texture) {
		if (texture == null) return;
		var location = texture.getBindLocation();
		if (texturesById.containsKey(location) && texturesById.get(location).equals(texture))
			return;
		
		texturesById.put(location, texture);
		texture.bind();
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public Scene setCamera(Camera camera) {
		this.camera = camera;
		needProjUpdate = true;
		needViewUpdate = true;
		
		return this;
	}
	
	public Scene add(SceneUnit unit) {
		units.add(unit);
		
		return this;
	}
	
	public Scene add(Collection<SceneUnit> units) {
		units.addAll(units);
		
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
