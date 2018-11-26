package outworldmind.owme.shader;

public class EntityShader extends Shader {
	
	public EntityShader() {
		super();
		StringBuilder fakeSource = new StringBuilder();
		
		addVertexStage(fakeSource);
		addFragmentStage(fakeSource);
		initialize();
	}

	@Override
	protected void bindAttributes() {
		
	}
	
}
