#version 430

// Tools
float saturate(float value) {
	return clamp(value, 0.0, 1.0);
}

// Crypting 
vec4 encodeFloat(float f) {
  vec4 enc = vec4(1.0, 255.0, 65025.0, 16581375.0) * f;
  enc = fract(enc);
  enc -= enc.yzww * vec4(1.0 / 255.0, 1.0 / 255.0, 1.0 / 255.0, 0.0);
  return enc;
}

float decodeFloat(vec4 rgba) {
  return dot(rgba, vec4(1.0, 1 / 255.0, 1 / 65025.0, 1 / 16581375.0));
}

// Lighting
vec4 phongLighting(vec4 ambient, vec4 diffuse, vec4 specular) {
	return ambient * diffuse + specular;
}