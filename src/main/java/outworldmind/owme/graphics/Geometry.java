package outworldmind.owme.graphics;

import outworldmind.owme.core.Disposable;

public interface Geometry extends Disposable {
	
	public void bind();
	public void unbind();
	public int size();
	
}
