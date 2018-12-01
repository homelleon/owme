in vec3 Position;

uniform mat4 Transformation;
uniform mat4 Projection;
uniform mat4 View;

void main(void) {
	
	vec4 worldPosition = Transformation * vec4(Position, 1.0);
	vec4 positionRelativeToCam = View * worldPosition;
	gl_Position = Projection * positionRelativeToCam;
	
}