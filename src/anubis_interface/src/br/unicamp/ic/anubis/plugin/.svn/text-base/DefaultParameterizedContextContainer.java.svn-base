package br.unicamp.ic.anubis.plugin;

import java.util.HashMap;

public class DefaultParameterizedContextContainer implements
		IParameterizedContext {
	
	private HashMap<String, Object> parameters;
	private IParameterizedContext parentContext = null;
	
	
	public DefaultParameterizedContextContainer(){
		parameters = new HashMap<String, Object>();
	}
	
	public DefaultParameterizedContextContainer(IParameterizedContext parentContext){
		parameters = new HashMap<String, Object>();
		this.parentContext = parentContext;
	}

	@Override
	public void setParameter(String parameterName, Object value) {
		parameters.put(parameterName, value);
	}

	@Override
	public Object getParameter(String parameterName) {
		Object returnValue = null;

		if (parameters.containsKey(parameterName)) {
			returnValue = parameters.get(parameterName);
		} else {
			if (parentContext!=null){
				returnValue = parentContext.getParameter(parameterName);
			}
		}

		return returnValue;
	}

	@Override
	public void setParentContext(IParameterizedContext parentContext) {
		this.parentContext = parentContext;
	}

}
