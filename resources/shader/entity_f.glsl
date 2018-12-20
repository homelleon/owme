in vec2 textureCoords;

uniform sampler2D diffuseMap;

void main(void) {
	
	gl_FragColor = texture(diffuseMap, textureCoords);
//	gl_FragColor = vec4(textureCoords.x * 1.0, textureCoords.y * 1.0, 0.0, 1.0);
//	gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);
	
}