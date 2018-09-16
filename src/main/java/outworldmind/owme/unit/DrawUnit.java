package outworldmind.owme.unit;

public class DrawUnit extends SceneUnit {
	
	private Model model = null;
	
	public Model getModel() {
		return model;
	}
	
	public DrawUnit setModel(Model model) {
		this.model = model;
		
		return this;
	}

}
