package br.unicamp.ic.anubis.successmetrics.sequence.datarepository;

import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.COLUMN_MATCH_REPOSITORY_ID;
import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.CONSIDER_GAPS_KEY;
import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.RESIDUE_SUCCESS_CALCULATOR_ID;
import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.SEQUENCE_SUCCESS_REPOSITORY_ID;

import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.data.IAlignmentDataRepository;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.mechanism.settings.ISettingsManager;
import br.unicamp.ic.anubis.successmetrics.residue.residuesuccess.ResidueSuccess;

public class SequenceSuccessDataRepository implements IAlignmentDataRepository,
		IEventHandler {

	private Integer[] targetSequenceSuccess;
	private Integer[][] targetSuccess;
	public boolean loaded = false;

	@Override
	public void eventRaised(IEvent message) {
		if (message != null && message instanceof AlignmentDataLoadedEvent) {

			AlignmentDataLoadedEvent event = (AlignmentDataLoadedEvent) message;
			UUID sourceId = event.getSource();

			if (RESIDUE_SUCCESS_CALCULATOR_ID.equals(sourceId)) {
				AnubisManager manager = AnubisManager.getInstance();

				processSequenceSuccess();
				// TODO: handle errors
				notifyDataChanged(manager);
			}
		}
	}

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

	private void processSequenceSuccess() {
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		ISettingsManager settingsManager = manager.getSettingsManager();

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

					boolean considerGaps = false;
					if (settingsManager != null) {
						Boolean gaps = (Boolean) settingsManager
								.getProperty(CONSIDER_GAPS_KEY);

						if (gaps != null) {
							considerGaps = gaps;
						}
					}

					calculateSequenceSuccess(alignmentRepository,
							residueSuccess, considerGaps);
					loaded = true;
				}
			}
		}
	}

	private void calculateSequenceSuccess(
			IAlignmentDataRepository alignmentRepository,
			ResidueSuccess residueSuccess, boolean considerGaps) {
		Integer[][] maximumMatches = residueSuccess.getMaximumMatches();
		Integer[][] targetMatches = residueSuccess.getTargetMatches();
		Integer[] benchmarkGapMatches = residueSuccess.getBenchmarkGapMatches();
		Integer[] targetGapMatches = residueSuccess.getTargetGapMatches();

		if (maximumMatches != null && targetMatches != null
				&& maximumMatches.length > 0 && targetMatches.length > 0) {
			int numberOfSequences = maximumMatches.length;
			// Calculate total matches
			int benchmarkScore = 0;
			int targetScore = 0;
			int benchmarkLength = maximumMatches[0].length;
			for (int i = 0; i < numberOfSequences; i++) {
				for (int j = 0; j < benchmarkLength; j++) {
					benchmarkScore += maximumMatches[i][j];
				}
			}
			int targetLength = targetMatches[0].length;
			for (int i = 0; i < numberOfSequences; i++) {
				for (int j = 0; j < targetLength; j++) {
					targetScore += targetMatches[i][j];
				}
			}

			// Calculate base percentage
			int baseSuccess = (int) (((double) targetScore / benchmarkScore) * 100.0);

			int maximumDelta = -100;
			int minimumDelta = 100;

			// Calculate success for each sequence
			targetSequenceSuccess = new Integer[numberOfSequences];

			for (int i = 0; i < numberOfSequences; i++) {
				int benchmarkSequenceMatches = 0;
				int targetSequenceMatches = 0;
				// Get matches for this sequence
				for (int j = 0; j < benchmarkLength; j++) {
					benchmarkSequenceMatches += maximumMatches[i][j];
				}
				for (int j = 0; j < targetLength; j++) {
					targetSequenceMatches += targetMatches[i][j];
				}

				if (considerGaps) {
					// Remove gaps (if applicable)
					if (benchmarkGapMatches != null) {
						benchmarkSequenceMatches -= benchmarkGapMatches[i];
					}

					if (targetGapMatches != null) {
						targetSequenceMatches -= targetGapMatches[i];
					}
				}

				targetSequenceSuccess[i] = (int) (((double) targetSequenceMatches / benchmarkSequenceMatches) * 100.0);

				int delta = targetSequenceSuccess[i] - baseSuccess;

				if (delta > maximumDelta) {
					maximumDelta = delta;
				}

				if (delta < minimumDelta) {
					minimumDelta = delta;
				}

				targetSequenceSuccess[i] = delta;

			}

			int total = maximumDelta - minimumDelta;

			for (int i = 0; i < numberOfSequences; i++) {
				if (total != 0) {
					targetSequenceSuccess[i] = (int) (((double) (targetSequenceSuccess[i] - minimumDelta) / total) * 100.0);
					targetSequenceSuccess[i] = 100 - targetSequenceSuccess[i];
				} else {
					targetSequenceSuccess[i] = 50;
				}
			}

			targetSuccess = new Integer[numberOfSequences][targetLength];

			for (int i = 0; i < numberOfSequences; i++) {
				for (int j = 0; j < targetLength; j++) {
					targetSuccess[i][j] = targetSequenceSuccess[i];
				}
			}

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
					SEQUENCE_SUCCESS_REPOSITORY_ID,
					AlignmentUtil.BOTH_ALIGNMENTS);
			eventManager.raise(event);
		}
	}
}
