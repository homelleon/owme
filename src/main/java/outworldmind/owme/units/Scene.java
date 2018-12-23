package outworldmind.owme.units;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import outworldmind.owme.graphics.Material;
import outworldmind.owme.graphics.Model;
import outworldmind.owme.shaders.EntityShader;

public class Scene {
	
	private Camera camera;
	private List<SceneUnit> units = new ArrayList<SceneUnit>();
	
	public Scene draw() {
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
	
	private void draw(Model model, List<DrawUnit> units) {	
		var renderer = model.getRenderer();
		var shader = model.getShader();
		var material = model.getMaterial();
		var geometry = model.getGeometry();

		shader.setValue(EntityShader.VIEW_MATRIX, camera.getView());
		material.getTexture(Material.DIFFUSE).bind();
		
		shader.start();
		units.forEach(unit -> {
			shader.setValue(EntityShader.TRANSFORMATION_MATRIX, unit.getTransformMatrix());
			shader.update();
			renderer.draw(geometry);
		});
	}
	
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
