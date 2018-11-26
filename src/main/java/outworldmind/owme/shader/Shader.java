package outworldmind.owme.shader;

import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

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
	private Map<String, ShaderStage> stages;
	
	protected Shader() {
		variables = new HashMap<String, ShaderVariable>();
		stages = new HashMap<String, ShaderStage>();
		
		setId(GL20.glCreateProgram());
		if (id != 0) return;
		
		var message = getClass().getSimpleName() + " creation failed";
		Tools.getLogger().log(message);
		throw new IllegalStateException(message);	
	}
	
	private void setId(int id) {
		this.id = id;
	}
	
	public void initialize() {
		bindAttributes();
		
		if (stages.isEmpty())
			throw new IllegalArgumentException(getClass().getSimpleName() + " no stage detected");
		
		glLinkProgram(id);

		if (glGetProgrami(id, GL_LINK_STATUS) == 0)
			throw new IllegalStateException(this.getClass().getName() + " linking failed: " + glGetProgramInfoLog(id, 1024));
		
		glValidateProgram(id);
		
		if (glGetProgrami(id, GL_VALIDATE_STATUS) == 0)
			throw new IllegalStateException(this.getClass().getName() +  " validation failed: " + glGetProgramInfoLog(id, 1024));
		
		loadUniformLocations();
	}
	
	protected abstract void bindAttributes();
	
	private void loadUniformLocations() {
		variables.values().forEach(this::addUniform);
	}
	
	private void addUniform(ShaderVariable variable) {
		int uniformLocation = this.getUniformLocation(variable.getName());
			
		if (uniformLocation == 0xFFFFFFFF)
			System.err.println(this.getClass().getSimpleName() + " Error: Could not find uniform: " + variable.getName());
		
		variable.setId(uniformLocation);
	}
	
	private int getUniformLocation(String uniformName) {
		return GL20.glGetUniformLocation(id, uniformName);
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
		
		stages.values()
			.forEach(stage -> GL20.glDetachShader(id, stage.getId()));
		
		stages.values().stream()
			.map(stage -> stage.getId())
			.forEach(GL20::glDeleteShader);
		
		GL20.glDeleteProgram(id);
		
		variables.clear();
		stages.clear();
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
		stages.put(stage.getTypeName(), stage);
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
