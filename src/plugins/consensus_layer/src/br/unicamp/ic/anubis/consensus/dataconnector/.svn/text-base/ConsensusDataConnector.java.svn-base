package br.unicamp.ic.anubis.consensus.dataconnector;


import java.util.HashMap;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.data.IAlignmentDataRepository;
import br.unicamp.ic.anubis.data.IDataRepository;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;

import static br.unicamp.ic.anubis.consensus.common.CommonConstants.CONSENSUS_DATAREPOSITORY_INTERFACE_ID;
import static br.unicamp.ic.anubis.consensus.common.CommonConstants.ALIGNMENT_ID_PARAMETER;
import static br.unicamp.ic.anubis.consensus.common.CommonConstants.FIRST_COLUMN_PARAMETER;
import static br.unicamp.ic.anubis.consensus.common.CommonConstants.SIZE_PARAMETER;

public class ConsensusDataConnector {
	
	public Character[] getSequence(int alignmentId, int firstPosition,int columns){
		Character[] returnvalue = null;
		
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		
		if (resourceManager != null) {
			// Get repository
			IDataRepository dataRepository = (IDataRepository) resourceManager
					.getResource(CONSENSUS_DATAREPOSITORY_INTERFACE_ID);
					
			
			if (dataRepository!=null){
				//Set parameters
				HashMap<String, Object> parameters = setParameters(alignmentId, firstPosition, columns);
				returnvalue = (Character[])dataRepository.getData(parameters);
			}
			
		}
		
		return returnvalue;
	}

	public HashMap<String, Object>  setParameters(int alignmentId, int firstPosition, int columns) {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(ALIGNMENT_ID_PARAMETER, alignmentId);
		parameters.put(FIRST_COLUMN_PARAMETER, firstPosition);
		parameters.put(SIZE_PARAMETER, columns);
		
		return parameters;
	}

}
