package outworldmind.owme.shader;

import java.util.HashMap;
import java.util.Map;

import outworldmind.owme.math.Matrix4;
import outworldmind.owme.unit.Texture;

public class ShaderVariable {
	
	public static final String INTEGER = "int";
	public static final String FLOAT = "float";
	public static final String MATRIX_3 = "mat3";
	public static final String MATRIX_4 = "mat4";
	public static final String TEXTURE = "sampler2D";
	
	public static final Map<String, String> TYPE_MAP = Map.of(
		Integer.class.getSimpleName(), INTEGER,
		Float.class.getSimpleName(), FLOAT,
//		Matrix3.class.getSimpleName(), MATRIX_3,
		Matrix4.class.getSimpleName(), MATRIX_4,
		Texture.class.getSimpleName(), TEXTURE
	);
	
	private String type;
	private String name;
	private Object value;
	private int id;
	
	public ShaderVariable(String name, Object value) {
		this.name = name;
		setValue(value);
	}
	
	public void setValue(Object value) {
		if (type == null) defineValueType(value);
		else validateValue(value);
		this.value = value;
		
	}
	
	private void validateValue(Object value) {
		if (!TYPE_MAP.get(value.getClass().getSimpleName()).equals(type))
			throw new IllegalArgumentException("Class type mistmatch: using incorrect " + 
		value.getClass().getSimpleName() + " type for shader type " + type);
	}
	
	private void defineValueType(Object value) {
		var valueTypeName = value.getClass().getSimpleName();
		if (valueTypeName.equals(Float.class.getSimpleName()))			
			type = FLOAT;
		else if (valueTypeName.equals(Integer.class.getSimpleName()))
			type = INTEGER;
		else if (valueTypeName.equals(Matrix4.class.getSimpleName()))
			type = MATRIX_4;
	}
	
	public String getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	void setId(int id) {
		this.id = id;
	}
	
	int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " name: " + name + 
				", type: " + type + ", id: " + id + ", value:\n" + value;
	}
}
