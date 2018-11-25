package outworldmind.owme.shader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.lwjgl.opengl.GL20;

import outworldmind.owme.core.Tools;

public abstract class Shader {
	
	public static final String VERTEX_STAGE = "vertex";
	public static final String FRAGMENT_STAGE = "fragment";
	public static final String GEOMETRY_STAGE = "geometry";
	public static final String COMPUTE_STAGE = "compute";
	public static final String TESS_EVALUATION_STAGE = "tessellation evaluation";
	public static final String TESS_CONTROL_STAGE = "tessellation control";
	
	private int id;

	private Map<String, ShaderVariable> variables;
	private List<Integer> stageIds;
	
	protected Shader() {
		variables = new HashMap<String, ShaderVariable>();
		stageIds = new ArrayList<Integer>();
		setId(GL20.glCreateProgram());
		if (id == 0) {
			var message = getClass().getSimpleName() + " creation failed";
			Tools.getLogger().log(message);
			throw new IllegalStateException(message);
		}
	}
	
	private void setId(int id) {
		this.id = id;
	}
	
	public void initialize() {
		
	}
	
	public Shader start() {
		GL20.glUseProgram(id);
		
		return this;
	}
	
	public void stop() {
		GL20.glUseProgram(0);
	}
	
	public Shader addVariable(ShaderVariable variable) {
		variables.put(variable.getName(), variable);
		
		return this;
	}
	
	public Shader addVariables(List<ShaderVariable> variableList) {
		variableList.forEach(variable -> variables.put(variable.getName(), variable));
		
		return this;
	}
	
	public Shader setValue(String variableName, Object value) {
		variables.get(variableName).setValue(value);
		
		return this;
	}
	
	public void delete() {
		stop();
		stageIds.forEach(stageId -> GL20.glDetachShader(id, stageId));
		stageIds.forEach(GL20::glDeleteShader);
		GL20.glDeleteProgram(id);
		
		variables.clear();
		stageIds.clear();
	}
	
	protected void addVertexStage(StringBuilder code) {
		addStage(code, VERTEX_STAGE);
	}
	
	protected void addFragmentStage(StringBuilder code) {
		addStage(code, FRAGMENT_STAGE);
	}
	
	protected void addGeometryStage(StringBuilder code) {
		addStage(code, GEOMETRY_STAGE);
	}
	
	protected void addComputeStage(StringBuilder code) {
		addStage(code, COMPUTE_STAGE);
	}
	
	protected void addTessellationEvaluationStage(StringBuilder code) {
		addStage(code, TESS_EVALUATION_STAGE);
	}
	
	protected void addTessellationControlStage(StringBuilder code) {
		addStage(code, TESS_CONTROL_STAGE);
	}
	
	private void addStage(StringBuilder code, String typeName) {
		var stage = new ShaderStage(code, typeName);
		GL20.glAttachShader(id, stage.getId());
		stageIds.add(stage.getId());
	}
	
	@Override
	public String toString() {
		String variablesString = variables.values()
				.stream()
				.map(Object::toString)
				.collect(Collectors.joining("\n"));
		return getClass().getSimpleName() + " {\n" + variablesString + "\n}";
	}
}
