package br.unicamp.ic.anubis.data;

import java.util.HashMap;

public class Session {
	
	private HashMap<String,Object> sessionVariables;
	private static Session instance = new Session();
	
	private Session(){
		sessionVariables = new HashMap<String, Object>();
	}
	
	public Object get(String variable){
		Object returnValue = null;
		
		if (sessionVariables.containsKey(variable)){
			returnValue = sessionVariables.get(variable);
		}
		
		return returnValue;
	}
	
	public void set(String variable, Object value){
		sessionVariables.put(variable,value);
	}

	public static Session getInstance() {
		return instance;
	}


}
