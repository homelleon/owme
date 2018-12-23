package outworldmind.owme.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigValidator {
	
	private Config config;
	private List<ValidCheck> checks;
	private List<String> succeed;
	private List<String> failed;
	
	public ConfigValidator(Config config) {
		this.config = config;
		checks = new ArrayList<ValidCheck>();
		succeed = new ArrayList<String>();
		failed = new ArrayList<String>();
	}
	
	public ConfigValidator addCheck(String name, String className, boolean canBeNull) {
		checks.add(new ValidCheck(name, className, canBeNull));
		
		return this;
	}
	
	public ConfigValidator addCheck(String name, String className, boolean canBeNull, boolean isOptional) {
		checks.add(new ValidCheck(name, className, canBeNull).makeOptional(isOptional));
		
		return this;
	}
	
	public boolean validate() {
		succeed.clear();
		failed.clear();
		var params = config.getAllParams();
		
		checks.forEach(check -> {
			var list = checkIfValid(check, params) ? succeed : (check.getIsOptional() ? succeed : failed);			
			list.add(check.getName());
		});
		
		return failed.isEmpty();
	}
	
	private boolean checkIfValid(ValidCheck check, Map<String, Object> params) {
		var name = check.getName();
		
		if (!params.containsKey(name)) return false;
		
		var param = params.get(name);
		if (param == null)
			return check.getCanBeNull();
		return param.getClass().getName().equals(check.getClassName());
	}
	
	public List<String> getSucceedParams() {
		return succeed;
	}
	
	public List<String> getFailedParams() {
		return failed;
	}
	
	public void clear() {
		checks.clear();
		succeed.clear();
		failed.clear();
	}
	
}

class ValidCheck {
	
	private String name;
	private String className;
	private boolean canBeNull = false;
	private boolean isOptional = false;
	
	protected ValidCheck(String name, String className, boolean canBeNull) {
		this.name = name;
		this.className = className;
		this.canBeNull = canBeNull;
	}
	
	protected ValidCheck(String name, String className) {
		this.name = name;
		this.className = className;
	}
	
	protected ValidCheck makeOptional(boolean value) {
		isOptional = value;
		
		return this;
	}
	
	protected String getName() {
		return name;
	}
	
	protected String getClassName() {
		return className;
	}
	
	protected boolean getCanBeNull() {
		return canBeNull;
	}
	
	protected boolean getIsOptional() {
		return isOptional;
	}
}

class ParamValidationException extends Exception {
	
	private static final long serialVersionUID = -8873767342373014419L;
	
	public ParamValidationException(String message) {
		super(message);
	}
	
	public ParamValidationException(String description, ConfigValidator validator) {	
		super(description + " - config validation failed, " + 
					"succeed params: " + validator.getSucceedParams() + ", " + 
					"failed params: " + validator.getFailedParams());
	}
	
}
