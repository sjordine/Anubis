package br.unicamp.ic.anubis.ui.dialog;

import java.io.File;
import java.util.HashMap;

import br.unicamp.ic.anubis.plugin.IParameterizedContext;

public abstract class FileAction implements IParameterizedContext{
	
	private HashMap<String, Object> parameters;
	private IParameterizedContext parentContext = null;

	public FileAction() {
		parameters = new HashMap<String, Object>();
	}

	public abstract void execute(File targetFile);
	
	public void setParameter(String parameterName, Object value) {
		parameters.put(parameterName, value);
	}

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
	
	public void setParentContext(IParameterizedContext parentContext){
		this.parentContext = parentContext;
	}

}
