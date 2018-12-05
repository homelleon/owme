package outworldmind.owme.shaders;

import java.util.List;

import outworldmind.owme.maths.Matrix4;
import outworldmind.owme.tools.FileLoader;

public class EntityShader extends Shader {
	
	private static final String VERTEX_SHADER = "/shader/entity_v.glsl";
	private static final String FRAGMENT_SHADER = "/shader/entity_f.glsl";
	
	public static final String TRANSFORMATION_MATRIX = "Transformation";
	public static final String PROJECTION_MATRIX = "Projection";
	public static final String VIEW_MATRIX = "View";
	
	private static final String POSITION_ATTRIBUTE = "Position";
	
	public EntityShader() {
		super();

		initialize();
	}
	
	@Override
	protected void initStages() {
		addVertexStage(FileLoader.INSTANCE.load(VERTEX_SHADER));
		addFragmentStage(FileLoader.INSTANCE.load(FRAGMENT_SHADER));
	}
	
	@Override
	protected void initVariables() {
		addVariables(List.of(
			new ShaderVariable(TRANSFORMATION_MATRIX, new Matrix4()),
			new ShaderVariable(PROJECTION_MATRIX, new Matrix4()),
			new ShaderVariable(VIEW_MATRIX, new Matrix4())
		));
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, POSITION_ATTRIBUTE);
//		bindFragOutput(0, "out_color");
	}
	
}
