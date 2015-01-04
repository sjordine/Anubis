package br.unicamp.ic.anubis.model;

import java.util.ArrayList;
import java.util.List;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.settings.ISettingsManager;

public class AlignmentScrollBarSynchronizer {
	
	private List<AlignmentScrollBarModel> modelList;
	
	public AlignmentScrollBarSynchronizer(){
		modelList = new ArrayList<AlignmentScrollBarModel>();
	}
	
	
	public void notifyChange(AlignmentScrollBarModel source, int deltaValue){
		if (source!=null && deltaValue!=0 && modelList !=null){
			//Check if models are locked
			if (areLocked()){
				for (AlignmentScrollBarModel currentModel: modelList){
					if (currentModel!=source){
						currentModel.adjustValue(deltaValue);
					}
				}
			}
		}
	}
	
	public void addModel(AlignmentScrollBarModel model){
		if (!modelList.contains(model)){
			modelList.add(model);
		}
	}
	
	public void removeModel(AlignmentScrollBarModel model){
		if (modelList.contains(model)){
			modelList.remove(model);
		}
	}
	
	public void clearModels(){
		modelList.clear();
	}

	private boolean areLocked() {
		boolean lockAlignments = false;
		AnubisManager manager = AnubisManager.getInstance();
		ISettingsManager settingsManager = manager.getSettingsManager();

		if (settingsManager != null) {
			Boolean areLocked = (Boolean) settingsManager
					.getProperty("LOCK_ALIGNMENTS");
			lockAlignments = areLocked != null && areLocked;

		}

		return lockAlignments;
	}

}
