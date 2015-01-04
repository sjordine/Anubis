package br.unicamp.ic.anubis.successmetrics.residue.datarepository;

import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.data.IAlignmentDataRepository;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.successmetrics.residue.residuesuccess.ResidueSuccess;

import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.COLUMN_MATCH_REPOSITORY_ID;
import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.RESIDUE_SUCCESS_REPOSITORY_ID;
import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.RESIDUE_SUCCESS_CALCULATOR_ID;

public class ResidueSuccessDataRepository implements IAlignmentDataRepository,
		IEventHandler {

	private Integer[][] targetSuccess;
	public boolean loaded = false;

	@Override
	public void setData(int alignmentId, Object data) {
		// This is a calculated information, therefore this
		// method will not be implemented
	}

	@Override
	public Object getData(int alignmentId, int startColumn, int startRow,
			int endColumn, int endRow) {
		Integer[][] returnValue = null;

		if (validateParameters(alignmentId, startRow, startColumn, endRow,
				endColumn)) {

			int numberOfRows = endRow - startRow;
			int numberOfColumns = endColumn - startColumn;

			returnValue = new Integer[numberOfRows + 1][numberOfColumns + 1];

			for (int i = 0; i <= numberOfRows; i++) {
				Integer[] sequence = targetSuccess[i + startRow];
				System.arraycopy(sequence, startColumn, returnValue[i], 0,
						numberOfColumns + 1);
			}
		}

		return returnValue;
	}

	@Override
	public Object getSequences(int alignmentId, int startRow, int endRow) {
		Object sequences = null;

		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();

		if (resourceManager != null) {

			IAlignmentDataRepository alignmentRepository = (IAlignmentDataRepository) resourceManager
					.getResource(COLUMN_MATCH_REPOSITORY_ID);
			if (alignmentRepository != null) {
				// Delegate to alignment data repository
				sequences = alignmentRepository.getSequences(alignmentId,
						startRow, endRow);
			}
		}

		return sequences;
	}

	@Override
	public boolean isLoaded(int aligmentId) {
		return loaded;
	}

	@Override
	public int numberOfSequences(int aligmentId) {
		int size = 0;

		if (isLoaded(aligmentId)) {
			size = targetSuccess.length;
		}

		return size;
	}

	@Override
	public int length(int aligmentId) {
		int length = 0;

		if (isLoaded(aligmentId)) {
			length = targetSuccess[0].length;
		}

		return length;
	}

	@Override
	public void eventRaised(IEvent message) {
		if (message != null && message instanceof AlignmentDataLoadedEvent) {

			AlignmentDataLoadedEvent event = (AlignmentDataLoadedEvent) message;
			UUID sourceId = event.getSource();

			if (RESIDUE_SUCCESS_CALCULATOR_ID.equals(sourceId)) {
				AnubisManager manager = AnubisManager.getInstance();

				processResidueSuccess();
				// TODO: handle errors
				notifyDataChanged(manager);
			}
		}
	}

	private void processResidueSuccess() {
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();

		if (resourceManager != null) {
			ResidueSuccess residueSuccess = (ResidueSuccess) resourceManager
					.getResource(RESIDUE_SUCCESS_CALCULATOR_ID);
			IAlignmentDataRepository alignmentRepository = (IAlignmentDataRepository) resourceManager
					.getResource(COLUMN_MATCH_REPOSITORY_ID);
			if (residueSuccess != null && alignmentRepository != null) {
				if (residueSuccess.isLoaded()
						&& (alignmentRepository
								.isLoaded(AlignmentUtil.BENCHMARK_ALIGNMENT) && alignmentRepository
								.isLoaded(AlignmentUtil.TARGET_ALIGNMENT))) {
					calculateMatches(alignmentRepository, residueSuccess);
				}
			}
		}

	}

	private void calculateMatches(IAlignmentDataRepository alignmentRepository,
			ResidueSuccess residueSuccess) {

		Integer[][] maximumMatches = residueSuccess.getMaximumMatches();
		Integer[][] targetMatches = residueSuccess.getTargetMatches();
	
		if (maximumMatches != null && targetMatches != null
				&& maximumMatches.length > 0 && targetMatches.length > 0) {
			
			int targetNumberOfSequences = targetMatches.length;
			int targetLength = targetMatches[0].length;
			
			Integer[][] targetColumnMatches =
					  (Integer[][]) alignmentRepository.getData( AlignmentUtil.TARGET_ALIGNMENT, 0,
					  0, targetLength - 1, targetNumberOfSequences - 1);


			targetSuccess = new Integer[targetNumberOfSequences][targetLength];

			for (int i = 0; i < targetNumberOfSequences; i++) {
				for (int j = 0; j < targetLength; j++) {
					targetSuccess[i][j] = 0;
				}
			}

			for (int i = 0; i < targetLength; i++) {
				// Success is defined based on the number of matches on target
				// compared to the benchmark matches for the same residue
				for (int j = 0; j < targetNumberOfSequences; j++) {
					int column1 = targetColumnMatches[j][i];
					int benchmarkColumn = column1;
					if (benchmarkColumn != -1) {
						int benchmarkValue = maximumMatches[j][benchmarkColumn];
						if (benchmarkValue != 0) {
							targetSuccess[j][i] = (int) (((double) targetMatches[j][i] / benchmarkValue) * 100.0);
						} else {
							targetSuccess[j][i] = 0;
						}
					} else {
						targetSuccess[j][i] = -1;
					}
				}
			}
		
			loaded = true;
		}
	}

	
	private boolean validateParameters(int alignmentId, int startRow,
			int startColumn, int endRow, int endColumn) {

		boolean result = startColumn >= 0 && startColumn <= endColumn
				&& startRow >= 0 && startRow <= endRow;
		result = result && (alignmentId == AlignmentUtil.TARGET_ALIGNMENT);
		result = result && targetSuccess != null;
		if (result) {

			Integer[][] alignment = targetSuccess;
			int numberOfSequences = alignment.length;
			result = result && startRow < numberOfSequences
					&& endRow < numberOfSequences;
			if (result) {
				int alignmentLength = alignment[startRow].length;
				result = result && startColumn < alignmentLength
						&& endColumn < alignmentLength;
			}
		}

		return result;
	}

	private void notifyDataChanged(AnubisManager manager) {
		// Notify calculated data, if successful
		IEventManager eventManager = manager.getEventManager();
		if (eventManager != null) {
			IEvent event = new AlignmentDataLoadedEvent(
					RESIDUE_SUCCESS_REPOSITORY_ID,
					AlignmentUtil.BOTH_ALIGNMENTS);
			eventManager.raise(event);
		}
	}

	private void resetData() {
		// maximumMatches = null;
		targetSuccess = null;
		loaded = false;
	}

}
