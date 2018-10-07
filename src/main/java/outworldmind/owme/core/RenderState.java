package outworldmind.owme.core;

import outworldmind.owme.math.Color;

public class RenderState {
	
	private Color clearColor;

	public Color getClearColor() {
		return clearColor;
	}

	public RenderState setClearColor(Color clearColor) {
		this.clearColor = clearColor;
		
		return this;
	}

}
