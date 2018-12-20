package outworldmind.owme.shaders;

import java.util.List;

import outworldmind.owme.graphics.Shader;
import outworldmind.owme.graphics.ShaderVariable;
import outworldmind.owme.graphics.Texture;
import outworldmind.owme.maths.Matrix4;
import outworldmind.owme.tools.FileLoader;

public class EntityShader extends Shader {
	
	private static final String VERTEX_SHADER = "/shader/entity_v.glsl";
	private static final String FRAGMENT_SHADER = "/shader/entity_f.glsl";
	
	public static final String TRANSFORMATION_MATRIX = "Transformation";
	public static final String PROJECTION_MATRIX = "Projection";
	public static final String VIEW_MATRIX = "View";
	
	public static final String DIFFUSE_MAP = "diffuseMap";
	
	private static final String POSITION_ATTRIBUTE = "Position";
	private static final String NORMAL_ATTRIBUTE = "Normal";
	private static final String TEXCOORDS_ATTRIBUTE = "TexCoords";
	
	public EntityShader() {
		super();
		addStages();
		addVariables();
		init();
	}
	
	private void addStages() {
		addVertexStage(FileLoader.INSTANCE.load(VERTEX_SHADER));
		addFragmentStage(FileLoader.INSTANCE.load(FRAGMENT_SHADER));
	}
	
	private void addVariables() {
		addVariables(List.of(
			new ShaderVariable(DIFFUSE_MAP, new Texture()),
			new ShaderVariable(TRANSFORMATION_MATRIX, new Matrix4()),
			new ShaderVariable(PROJECTION_MATRIX, new Matrix4()),
			new ShaderVariable(VIEW_MATRIX, new Matrix4())
		));
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, POSITION_ATTRIBUTE);
		bindAttribute(1, NORMAL_ATTRIBUTE);
		bindAttribute(2, TEXCOORDS_ATTRIBUTE);
	}
	
}
