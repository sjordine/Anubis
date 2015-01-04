package br.unicamp.ic.anubisdefaultviewer.score.matrixloader;

import java.util.ArrayList;
import java.util.List;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.event.PropertyChangedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.mechanism.settings.ISettingsManager;
import br.unicamp.ic.jbio.matrix.DistanceMatrix;

import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.MATRIX_PATH_PROPERTY;
import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.SELECTED_MATRIX_INDEX_PROPERTY;
import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.SCORE_LIST_ID;
import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.SCORE_MATRIX_ID;

public class MatrixLoaderWatcher implements IEventHandler {

	@Override
	public void eventRaised(IEvent message) {
		if (message instanceof PropertyChangedEvent) {
			PropertyChangedEvent event = (PropertyChangedEvent)message;
			if (MATRIX_PATH_PROPERTY.equals(event.getPropertyName())){
				MatrixBatchLoader.loadMatrices();
			}
			if (SELECTED_MATRIX_INDEX_PROPERTY.equals(event.getPropertyName())){
				setSelectedMatrix();
			}
		}
	}

	private void setSelectedMatrix() {
		AnubisManager manager = AnubisManager.getInstance();
		ISettingsManager settingsManager = manager.getSettingsManager();
		IResourceManager resourceManager = manager.getResourceManager();
		if (settingsManager != null && resourceManager != null) {
			// Get path
			Integer index = (Integer) settingsManager.getProperty(SELECTED_MATRIX_INDEX_PROPERTY);
			List<DistanceMatrix> matrices = (List<DistanceMatrix>)resourceManager.getResource(SCORE_LIST_ID);
			if (index!=null && matrices!=null && index < matrices.size() ){
				DistanceMatrix selectedMatrix = matrices.get(index);
				resourceManager.setResource(SCORE_MATRIX_ID, selectedMatrix);
			}
		}
	}

}
