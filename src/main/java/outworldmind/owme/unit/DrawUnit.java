package outworldmind.owme.unit;

public class DrawUnit extends SceneUnit {
	
	private Model model;
	
	protected DrawUnit() {
		super();
	}
	
	protected DrawUnit(int id) {
		super(id);
	}
	
	protected DrawUnit(int id, String name) {
		super(id, name);
	}
	
	protected DrawUnit(String name) {
		super(name);
	}
	
	public Model getModel() {
		return model;
	}
	
	public DrawUnit setModel(Model model) {
		this.model = model;
		
		return this;
	}

}
