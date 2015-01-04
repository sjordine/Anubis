package br.unicamp.ic.anubis.columnmatch.datarepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.data.IAlignmentDataRepository;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.util.SequenceUtil;

import static br.unicamp.ic.anubis.util.SequenceUtil.GAP_VALUE;

public class ColumnMatchDataRepository implements IAlignmentDataRepository,
		IEventHandler {

	private static final UUID INTERFACE_ID = UUID
			.fromString("9df9b11f-c0b1-48e2-88ee-37fe3911e9e7");

	private static final UUID ALIGNMENT_DATAREPOSITORY_INTERFACE_ID = UUID
			.fromString("a663f847-7aa8-4714-9d91-810ac40a7fbd");


	// Alignment Data Structures
	private Integer[][][] alignments = new Integer[2][][];
	public boolean[] loaded = new boolean[2];

	public ColumnMatchDataRepository() {
		loaded = new boolean[2];
		alignments = new Integer[2][][];
	}

	@Override
	public void setData(int alignmentId, Object data) {
		// This is a calculated information, therefore this
		// method will not be implemented
	}

	@Override
	public Object getData(int alignmentId,int startColumn, int startRow, int endColumn, int endRow) {


		Integer[][] returnValue = null;

		if (validateParameters(alignmentId, startRow, startColumn, endRow,
				endColumn)) {
			int numberOfRows = endRow - startRow;
			int numberOfColumns = endColumn - startColumn;

			returnValue = new Integer[numberOfRows + 1][numberOfColumns + 1];

			for (int i = 0; i <= numberOfRows; i++) {
				Integer[] sequence = alignments[alignmentId][i + startRow];
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
					.getResource(ALIGNMENT_DATAREPOSITORY_INTERFACE_ID);
			if (alignmentRepository != null) {
				// Delegate to alignment data repository
				sequences = alignmentRepository.getSequences(alignmentId,
						startRow, endRow);
			}
		}

		return sequences;
	}

	private boolean validateParameters(int alignmentId, int startRow,
			int startColumn, int endRow, int endColumn) {

		boolean result = startColumn >= 0 && startColumn <= endColumn
				&& startRow >= 0 && startRow <= endRow;
		result = result
				&& (alignmentId == AlignmentUtil.BENCHMARK_ALIGNMENT || alignmentId == AlignmentUtil.TARGET_ALIGNMENT);
		result = result && alignments[alignmentId] != null;
		if (result) {
			
			Integer[][] alignment = alignments[alignmentId];
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

	@Override
	public void eventRaised(IEvent message) {
		if (message != null && message instanceof AlignmentDataLoadedEvent) {

			AlignmentDataLoadedEvent event = (AlignmentDataLoadedEvent) message;
			UUID sourceId = event.getSource();

			if (ALIGNMENT_DATAREPOSITORY_INTERFACE_ID.equals(sourceId)) {
				AnubisManager manager = AnubisManager.getInstance();
				processMatches();
				// TODO: handle errors
				notifyDataChanged(manager);
			}
		}
	}

	private void processMatches() {
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		IAlignmentDataRepository alignmentRepository = (IAlignmentDataRepository) resourceManager
				.getResource(ALIGNMENT_DATAREPOSITORY_INTERFACE_ID);

		if (alignmentRepository != null) {

			// If both sequences are loaded, calculate column match
			if (alignmentRepository.isLoaded(AlignmentUtil.BENCHMARK_ALIGNMENT)
					&& alignmentRepository
							.isLoaded(AlignmentUtil.TARGET_ALIGNMENT)) {

				Map<String, Integer> benchmarkMap = new HashMap<String, Integer>();
				Map<String, Integer> targetMap = new HashMap<String, Integer>();

				if (mapSequences(alignmentRepository, benchmarkMap, targetMap)) {
					alignments = matchColumns(alignmentRepository,
							benchmarkMap, targetMap);
					loaded[AlignmentUtil.BENCHMARK_ALIGNMENT] = true;
					loaded[AlignmentUtil.TARGET_ALIGNMENT] = true;
				}
			} else {
				alignments = new Integer[2][][];
				loaded[AlignmentUtil.BENCHMARK_ALIGNMENT] = false;
				loaded[AlignmentUtil.TARGET_ALIGNMENT] = false;
			}
		}
	}

	private Integer[][][] matchColumns(
			IAlignmentDataRepository alignmentRepository,
			Map<String, Integer> benchmarkMap, Map<String, Integer> targetMap) {

		Integer[][][] returnValue = null;

		int benchmarkNumberOfSequences = alignmentRepository
				.numberOfSequences(AlignmentUtil.BENCHMARK_ALIGNMENT);
		int benchmarkLength = alignmentRepository
				.length(AlignmentUtil.BENCHMARK_ALIGNMENT);
		Character[][] benchmarkAlignment = (Character[][]) alignmentRepository
				.getData(AlignmentUtil.BENCHMARK_ALIGNMENT, 0, 0,
						benchmarkLength - 1, benchmarkNumberOfSequences - 1);

		int targetnumberOfSequences = alignmentRepository
				.numberOfSequences(AlignmentUtil.TARGET_ALIGNMENT);
		int targetLength = alignmentRepository
				.length(AlignmentUtil.TARGET_ALIGNMENT);
		Character[][] targetAlignment = (Character[][]) alignmentRepository
				.getData(AlignmentUtil.TARGET_ALIGNMENT, 0, 0,
						targetLength - 1, targetnumberOfSequences - 1);

		if (targetAlignment != null && benchmarkAlignment != null) {

			returnValue = new Integer[2][][];
			returnValue[AlignmentUtil.BENCHMARK_ALIGNMENT] = new Integer[benchmarkNumberOfSequences][benchmarkLength];
			returnValue[AlignmentUtil.TARGET_ALIGNMENT] = new Integer[targetnumberOfSequences][targetLength];

			for (Map.Entry<String, Integer> item : benchmarkMap.entrySet()) {

				Integer[] benchmarkMatches = new Integer[benchmarkLength];
				Integer[] targetMatches = new Integer[targetLength];

				// Get indexes
				String sequence = item.getKey();
				int benchmarkIndex = item.getValue();
				int targetIndex = targetMap.get(sequence);

				Character[] benchmarkSequence = benchmarkAlignment[benchmarkIndex];
				Character[] targetSequence = targetAlignment[targetIndex];

				int benchmarkCursor = 0;
				int benchmarkSize = benchmarkSequence.length;
				int targetCursor = 0;
				int targetSize = targetSequence.length;

				while (benchmarkCursor < benchmarkSize
						&& targetCursor < targetSize) {
					Character benchmarkPos = benchmarkSequence[benchmarkCursor];
					Character targetPos = targetSequence[targetCursor];

					if (SequenceUtil.isGap(benchmarkPos)) {
						benchmarkMatches[benchmarkCursor] = GAP_VALUE;
						benchmarkCursor++;
					}

					if (SequenceUtil.isGap(targetPos)) {
						targetMatches[targetCursor] = GAP_VALUE;
						targetCursor++;
					}

					if (!SequenceUtil.isGap(benchmarkPos) && !SequenceUtil.isGap(targetPos)) {
						if (benchmarkPos == targetPos) {
							benchmarkMatches[benchmarkCursor] = benchmarkCursor;
							targetMatches[targetCursor] = benchmarkCursor;
							benchmarkCursor++;
							targetCursor++;
						} else {
							// TODO: error
						}
					}
				}

				// Complete sequence
				if (benchmarkCursor < benchmarkSize) {
					for (int i = benchmarkCursor; i < benchmarkSize; i++) {
						Character benchmarkPos = benchmarkSequence[i];
						if (SequenceUtil.isGap(benchmarkPos)) {
							benchmarkMatches[i] = GAP_VALUE;
						} else {
							// TODO: error
						}
					}
				}

				if (targetCursor < targetSize) {
					for (int i = targetCursor; i < targetSize; i++) {
						Character targetPos = targetSequence[i];
						if (SequenceUtil.isGap(targetPos)) {
							targetMatches[i] = GAP_VALUE;
						} else {
							// TODO: error
						}
					}
				}

				System.arraycopy(
						benchmarkMatches,
						0,
						returnValue[AlignmentUtil.BENCHMARK_ALIGNMENT][benchmarkIndex],
						0, benchmarkSize);
				System.arraycopy(
						targetMatches,
						0,
						returnValue[AlignmentUtil.TARGET_ALIGNMENT][targetIndex],
						0, targetSize);

			}
		}

		return returnValue;
	}



	private boolean mapSequences(IAlignmentDataRepository alignmentRepository,
			Map<String, Integer> benchmarkMap, Map<String, Integer> targetMap) {

		boolean result = false;

		int benchmarkNumberOfSequences = alignmentRepository
				.numberOfSequences(AlignmentUtil.BENCHMARK_ALIGNMENT);
		int targetNumberOfSequences = alignmentRepository
				.numberOfSequences(AlignmentUtil.TARGET_ALIGNMENT);

		if (benchmarkNumberOfSequences == targetNumberOfSequences) {

			String[] benchmarkSequences = (String[]) alignmentRepository
					.getSequences(AlignmentUtil.BENCHMARK_ALIGNMENT, 0,
							benchmarkNumberOfSequences - 1);
			String[] targetSequences = (String[]) alignmentRepository
					.getSequences(AlignmentUtil.TARGET_ALIGNMENT, 0,
							targetNumberOfSequences - 1);

			if (benchmarkSequences != null && targetSequences != null) {
				// Prepare mapping
				for (int i = 0; i < benchmarkSequences.length; i++) {
					benchmarkMap.put(benchmarkSequences[i], -1);
				}
				for (int i = 0; i < targetSequences.length; i++) {
					targetMap.put(targetSequences[i], -1);
				}
				// Map sequences
				for (int i = 0; i < benchmarkSequences.length; i++) {
					String currentSequence = benchmarkSequences[i];
					if (targetMap.containsKey(currentSequence)) {
						targetMap.put(currentSequence, i);
					}
				}
				for (int i = 0; i < targetSequences.length; i++) {
					String currentSequence = targetSequences[i];
					if (benchmarkMap.containsKey(currentSequence)) {
						benchmarkMap.put(currentSequence, i);
					}
				}
				// Check if any sequence was not mapped,
				boolean allMapped = sequencesWereMapped(benchmarkMap);
				allMapped = allMapped && sequencesWereMapped(targetMap);

				result = allMapped;
			}
		}

		return result;
	}

	private boolean sequencesWereMapped(Map<String, Integer> sequenceMap) {
		boolean allMapped = true;
		for (Map.Entry<String, Integer> item : sequenceMap.entrySet()) {
			allMapped = allMapped && (item.getValue() != -1);
		}
		return allMapped;
	}

	@Override
	public boolean isLoaded(int aligmentId) {
		return loaded[aligmentId];
	}

	@Override
	public int numberOfSequences(int aligmentId) {

		int size = 0;

		if (isLoaded(aligmentId)) {
			size = alignments[aligmentId].length;
		}

		return size;
	}

	@Override
	public int length(int aligmentId) {
		int length = 0;

		if (isLoaded(aligmentId)) {
			length = alignments[aligmentId][0].length;
		}

		return length;
	}

	private void notifyDataChanged(AnubisManager manager) {

		// Notify loaded alignment, if successful
		IEventManager eventManager = manager.getEventManager();
		if (eventManager != null) {
			IEvent event = new AlignmentDataLoadedEvent(INTERFACE_ID,
					AlignmentUtil.BOTH_ALIGNMENTS);
			eventManager.raise(event);
		}
	}

}
