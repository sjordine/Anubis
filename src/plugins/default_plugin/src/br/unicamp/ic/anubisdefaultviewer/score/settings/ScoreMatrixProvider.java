package br.unicamp.ic.anubisdefaultviewer.score.settings;

import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.SCORE_LIST_ID;

import java.util.ArrayList;
import java.util.List;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.ui.settingsdialog.fields.selectionfield.ISelectionFieldDataProvider;
import br.unicamp.ic.anubis.ui.settingsdialog.fields.selectionfield.Item;
import br.unicamp.ic.jbio.matrix.DistanceMatrix;

public class ScoreMatrixProvider implements ISelectionFieldDataProvider {

	@Override
	public List<Item> getItems() {
		List<Item> returnValue = new ArrayList<Item>();
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		
		if (resourceManager!=null){
			List<DistanceMatrix> matrices = (List<DistanceMatrix>)resourceManager.getResource(SCORE_LIST_ID);
			if (matrices!=null){
				for (int i=0; i< matrices.size();i++){
					DistanceMatrix matrix = matrices.get(i);
					Item item = new Item(matrix.getName(), i);
					returnValue.add(item);
				}
			}
		}	
		
		return returnValue;
	}

}
