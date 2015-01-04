package br.unicamp.ic.anubis.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.ui.score.IScoreMethod;

public class AnubisScoreMethodManager {
	
	private HashMap<UUID,IScoreMethod> scoreMethods;
	
	
	public AnubisScoreMethodManager(){
		scoreMethods = new HashMap<UUID, IScoreMethod>();
	}
	

	public List<IScoreMethod> getScoreMethods() {
		List<IScoreMethod> returnValue = null;
		
		returnValue = new ArrayList<IScoreMethod>(scoreMethods.values());
		
		return returnValue;
	}

	public IScoreMethod getScoreMethod(UUID id) {
		IScoreMethod returnValue = null;
		
		if (scoreMethods.containsKey(id)){
			returnValue = scoreMethods.get(id);
		}
		
		return returnValue;
	}

	public void addScoreMethod(IScoreMethod scoreMethod) {
		if (scoreMethod!=null && scoreMethod.getId()!=null){
			scoreMethods.put(scoreMethod.getId(), scoreMethod);
		}
	}

}
