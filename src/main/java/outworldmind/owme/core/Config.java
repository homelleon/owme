package outworldmind.owme.core;

import java.util.HashMap;
import java.util.Map;

public class Config {
	
	private Map<String, Object> params;
	
	public Config() {
		params = new HashMap<String, Object>();
	}
	
	public Object getParam(String name) {
		return params.get(name);
	}
	
	public Config setParam(String name, Object value) {
		params.put(name, value);
		
		return this;
	}
	
	protected Map<String, Object> getAllParams() {
		return params;
	}
	
	public void clear() {
		params.clear();
	}

}
