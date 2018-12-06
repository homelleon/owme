package outworldmind.owme.graphics;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_CONTROL_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_EVALUATION_SHADER;
import static org.lwjgl.opengl.GL43.GL_COMPUTE_SHADER;

import java.util.Map;

import org.lwjgl.opengl.GL20;

import outworldmind.owme.core.Tools;
import outworldmind.owme.tools.FileLoader;

public class ShaderStage {
	
	private static final Map<String, Integer> TYPES = Map.of(
		Shader.VERTEX_STAGE, GL_VERTEX_SHADER,
		Shader.FRAGMENT_STAGE, GL_FRAGMENT_SHADER,
		Shader.GEOMETRY_STAGE, GL_GEOMETRY_SHADER,
		Shader.COMPUTE_STAGE, GL_COMPUTE_SHADER,
		Shader.TESS_EVALUATION_STAGE, GL_TESS_EVALUATION_SHADER,
		Shader.TESS_CONTROL_STAGE, GL_TESS_CONTROL_SHADER
	);
	
	private int id = -1;
	private String typeName;
	private StringBuilder code;
	
	private static String BASE_SHADER = "/shader/base.glsl";
	private static final StringBuilder baseCode = FileLoader.INSTANCE.load(BASE_SHADER);
	
	public ShaderStage(StringBuilder code, String typeName) {
		if (code.length() < 1)
			throw new IllegalStateException(getClass().getSimpleName() + " empty code detected for type " +  typeName);
		this.code = code;
		this.typeName = typeName;
	}
	
	public void init() {
		var type = (int) TYPES.get(typeName);
		id = GL20.glCreateShader(type);
		
		code.insert(0, baseCode);
		GL20.glShaderSource(id, code);
		GL20.glCompileShader(id);
		
		if (GL20.glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
			var message = typeName + " stage: " + GL20.glGetShaderInfoLog(id, 500);
			Tools.getLogger().log(message);
			throw new IllegalStateException(message);
		}
	}
	
	public int getId() {
		return id;
	}
	
	public int getType() {
		return TYPES.get(typeName);
	}
	
	public String getTypeName() {
		return typeName;
	}

}
