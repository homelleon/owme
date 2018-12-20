package outworldmind.owme.units;

import outworldmind.owme.graphics.Model;
import outworldmind.owme.maths.Rotation;
import outworldmind.owme.maths.Vector3;

public class DrawUnit extends SceneUnit {
	
	private Model model = null;
	
	public Model getModel() {
		return model;
	}
	
	public DrawUnit setModel(Model model) {
		this.model = model;
		
		return this;
	}
	
	@Override
	public DrawUnit move(Vector3 distance) {
		super.move(distance);
		
		return this;
	}
	
	@Override
	public DrawUnit moveTo(Vector3 position) {
		super.moveTo(position);
		
		return this;
	}
	
	@Override
	public DrawUnit rotate(Rotation rotation) {
		super.rotate(rotation);
		
		return this;
	}
	
	@Override
	public DrawUnit rotateTo(Rotation rotation) {
		super.rotateTo(rotation);
		
		return this;
	}	
	
	
	@Override
	public DrawUnit scale(Vector3 scale) {
		super.scale(scale);
		
		return this;
	}
	
	@Override
	public DrawUnit scaleTo(Vector3 scale) {
		super.scaleTo(scale);
		
		return this;
	}
	
	@Override
	public DrawUnit clone() {
		return new DrawUnit()
				.scaleTo(getScale())
				.moveTo(getPosition())
				.rotateTo(getRotation())
				.setModel(model);
	}

}
