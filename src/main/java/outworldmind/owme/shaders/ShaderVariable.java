package outworldmind.owme.shaders;

import java.nio.FloatBuffer;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import outworldmind.owme.graphics.Texture;
import outworldmind.owme.maths.Matrix4;
import outworldmind.owme.maths.Vector2;
import outworldmind.owme.maths.Vector3;
import outworldmind.owme.maths.Vector4;

public class ShaderVariable {
	
	private static final String INTEGER = "int";
	private static final String FLOAT = "float";
	private static final String BOOLEAN = "bool";
	private static final String VECTOR2 = "vec2";
	private static final String VECTOR3 = "vec3";
	private static final String VECTOR4 = "vec4";
//	private static final String MATRIX_2 = "mat2";
//	private static final String MATRIX_3 = "mat3";
	private static final String MATRIX_4 = "mat4";
	private static final String TEXTURE = "sampler2D";
	
	public static final Map<String, String> TYPE_MAP = Map.of(
		Integer.class.getSimpleName(), INTEGER,
		Float.class.getSimpleName(), FLOAT,
		Boolean.class.getSimpleName(), BOOLEAN,
		Vector2.class.getSimpleName(), VECTOR2,
		Vector3.class.getSimpleName(), VECTOR3,
		Vector4.class.getSimpleName(), VECTOR4,
//		Matrix2.class.getSimpleName(), MATRIX_2,
//		Matrix3.class.getSimpleName(), MATRIX_3,
		Matrix4.class.getSimpleName(), MATRIX_4,
		Texture.class.getSimpleName(), TEXTURE
	);
	
	private String type;
	private String name;
	private Object value;
	private int id = -2;
	
	private boolean needBinding = true;

//	private static FloatBuffer matrix2Buffer = BufferUtils.createFloatBuffer(4);
//	private static FloatBuffer matrix3Buffer = BufferUtils.createFloatBuffer(9);
	private static FloatBuffer matrix4Buffer = BufferUtils.createFloatBuffer(16);
	
	public ShaderVariable(String name, Object value) {
		this.name = name;
		setValue(value);
	}
	
	public void setValue(Object value) {
		if (type == null) defineValueType(value);
		else validateValue(value);
		
		this.value = value;		
		needBinding = true;
	}
	
	private void validateValue(Object value) {
		if (!TYPE_MAP.get(value.getClass().getSimpleName()).equals(type))
			throw new IllegalArgumentException("Class type mistmatch: using incorrect " + 
		value.getClass().getSimpleName() + " type for shader type " + type);
	}
	
	private void defineValueType(Object value) {
		var valueTypeName = value.getClass().getSimpleName();
		
		if (!TYPE_MAP.containsKey(valueTypeName))
			throw new IllegalArgumentException(this.getClass().getSimpleName() + " " + name + " : type " + valueTypeName + " is unsupported");
		
		type = TYPE_MAP.get(valueTypeName);
	}
	
	protected void bind() {
		switch (type) {
			case FLOAT: loadFloat((float) value); break;
			case INTEGER: loadInt((int) value); break;
			case BOOLEAN: loadBoolean((boolean) value); break;
			case VECTOR2: loadVector2((Vector2) value); break;
			case VECTOR3: loadVector3((Vector3) value); break;
			case VECTOR4: loadVector4((Vector4) value); break;
//			case MATRIX_2: loadMatrix2((Matrix2) value); break;
//			case MATRIX_3: loadMatrix3((Matrix3) value); break;
			case MATRIX_4: loadMatrix4((Matrix4) value); break;
		}
		
		needBinding = false;
	}
	
	private void loadInt(int value) {
		GL20.glUniform1i(id, value);
	}

	private void loadFloat(float value) {
		GL20.glUniform1f(id, value);
	}

	private void loadBoolean(boolean value) {
		GL20.glUniform1f(id, value ? 1 : 0);
	}

	private void loadVector3(Vector3 vector) {
		GL20.glUniform3f(id, vector.x, vector.y, vector.z);
	}
	private void loadVector2(Vector2 vector) {
		GL20.glUniform2f(id, vector.x, vector.y);
	}

	private void loadVector4(Vector4 vector) {
		GL20.glUniform4f(id, vector.x, vector.y, vector.z, vector.w);
	}
	
//	private void loadColor(Color color) {
//		Color oglColor = color.getOGL();
//		int uniformLocation = this.unfiroms.get(name);
//		GL20.glUniform3f(id, oglColor.r, oglColor.g, oglColor.b);
//	}
	
//	private void loadMatrix2(Matrix2 matrix) {
//	matrix.store(matrix2Buffer);
//	matrix2Buffer.flip();
//	GL20.glUniformMatrix2fv(id, false, matrix2Buffer);
//}
	
//	private void loadMatrix3(Matrix3 matrix) {
//		matrix.store(matrix3Buffer);
//		matrix3Buffer.flip();
//		GL20.glUniformMatrix3fv(id, false, matrix3Buffer);
//	}

	private void loadMatrix4(Matrix4 matrix) {
		matrix.store(matrix4Buffer);
		matrix4Buffer.flip();
		GL20.glUniformMatrix4fv(id, false, matrix4Buffer);
	}
	
	public boolean doesNeedBinding() {
		return needBinding;
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
