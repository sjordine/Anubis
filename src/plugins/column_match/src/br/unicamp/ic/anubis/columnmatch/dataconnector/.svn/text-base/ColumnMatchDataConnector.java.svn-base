package br.unicamp.ic.anubis.columnmatch.dataconnector;

import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.data.IAlignmentDataRepository;
import br.unicamp.ic.anubis.dataconnector.AlignmentDataBuilder;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;

public class ColumnMatchDataConnector {
	private static final UUID MATCH_DATA_REPOSITORY_ID = UUID
			.fromString("9df9b11f-c0b1-48e2-88ee-37fe3911e9e7");
	
	public Object getData(int alignmentIndex, int startColumn, int startRow, int columns, int rows){
		Object returnValue = null;
		
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		
		if (resourceManager != null) {
			// Get repository
			IAlignmentDataRepository dataRepository = (IAlignmentDataRepository) resourceManager
					.getResource(MATCH_DATA_REPOSITORY_ID);
			if (dataRepository!=null && dataRepository.isLoaded(alignmentIndex)){
				
				returnValue = AlignmentDataBuilder.<Integer>getData(dataRepository, alignmentIndex, startColumn, startRow, columns, rows, Integer.class);
				
				/*
				int numberOfRows = dataRepository.numberOfSequences(alignmentIndex);
				int numberOfColumns = dataRepository.length(alignmentIndex);
				
				
				if (startColumn >=0 && startRow >=0 && startColumn<numberOfColumns && startRow < numberOfRows){
					
					
					int lastColumn = Math.min(startColumn+columns,numberOfColumns-1);
					int lastRow = Math.min(startRow+rows,numberOfRows-1);
					

					returnValue = dataRepository.getData(alignmentIndex,startColumn,startRow, lastColumn, lastRow);
				}
				*/
			}
		}
		
		return returnValue;
	}
	
	public Object getSequences(int alignmentIndex, int startRow, int rows){
		Object returnValue = null;
		
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		
		if (resourceManager != null) {
			// Get repository
			IAlignmentDataRepository dataRepository = (IAlignmentDataRepository) resourceManager
					.getResource(MATCH_DATA_REPOSITORY_ID);
			if (dataRepository!=null && dataRepository.isLoaded(alignmentIndex)){
				int numberOfRows = dataRepository.numberOfSequences(alignmentIndex);				
				
				if (startRow >=0  && startRow < numberOfRows){					
					int lastRow = Math.min(startRow+rows,numberOfRows-1);			

					returnValue = dataRepository.getSequences(alignmentIndex, startRow, lastRow);
				}				
			}
		}
		
		return returnValue;
	}
}
