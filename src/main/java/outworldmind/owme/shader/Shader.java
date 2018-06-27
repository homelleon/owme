package outworldmind.owme.shader;

import java.util.ArrayList;
import java.util.List;

import outworldmind.owme.tool.NumberGenerator;

public class Shader {
	List<ShaderVariable> variables;
	private int id;
	
	protected Shader() {
		variables = new ArrayList<ShaderVariable>();
		setId(NumberGenerator.generateId());
	}
	
	private void setId(int id) {
		this.id = id;
	}
}
