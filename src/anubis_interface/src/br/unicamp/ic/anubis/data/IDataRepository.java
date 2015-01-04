package br.unicamp.ic.anubis.data;

import java.util.HashMap;

public interface IDataRepository {
	
	public void setData(HashMap<String,Object> parameters, Object data);
	public Object getData(HashMap<String,Object> parameters);

}
