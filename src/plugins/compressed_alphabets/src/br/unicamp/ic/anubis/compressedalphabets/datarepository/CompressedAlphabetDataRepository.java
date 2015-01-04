package br.unicamp.ic.anubis.compressedalphabets.datarepository;

import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.compressedalphabets.alphabets.CompressedAlphabet;
import br.unicamp.ic.anubis.compressedalphabets.ui.viewer.Settings;
import br.unicamp.ic.anubis.data.IAlignmentDataRepository;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.event.PropertyChangedEvent;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;

import static br.unicamp.ic.anubis.compressedalphabets.util.CommonConstants.ALIGNMENT_DATAREPOSITORY_INTERFACE_ID;
import static br.unicamp.ic.anubis.compressedalphabets.util.CommonConstants.COMPRESSED_ALPHABET_INTERFACE_ID;
import static br.unicamp.ic.anubis.compressedalphabets.util.CommonConstants.COMPRESSED_ALPHABET_PROPERTY_ID;

public class CompressedAlphabetDataRepository implements
		IAlignmentDataRepository, IEventHandler {

	// Alignment Data Structures
	private Character[][][] alignments = new Character[2][][];
	public boolean[] loaded = new boolean[2];

	private Settings settings;

	public CompressedAlphabetDataRepository() {
		loaded = new boolean[2];
		alignments = new Character[2][][];
		settings = new Settings();
	}

	@Override
	public void setData(int alignmentId, Object data) {
		// This is a calculated information, therefore this
		// method will not be implemented
	}

	@Override
	public Object getData(int alignmentId, int startColumn, int startRow,
			int endColumn, int endRow) {
		Character[][] returnValue = null;

		if (validateParameters(alignmentId, startRow, startColumn, endRow,
				endColumn)) {
			int numberOfRows = endRow - startRow;
			int numberOfColumns = endColumn - startColumn;

			returnValue = new Character[numberOfRows + 1][numberOfColumns + 1];

			for (int i = 0; i <= numberOfRows; i++) {
				Character[] sequence = alignments[alignmentId][i + startRow];
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

	@Override
	public void eventRaised(IEvent message) {
		if (message != null) {
			if (message instanceof AlignmentDataLoadedEvent) {

				AlignmentDataLoadedEvent event = (AlignmentDataLoadedEvent) message;
				UUID sourceId = event.getSource();

				if (ALIGNMENT_DATAREPOSITORY_INTERFACE_ID.equals(sourceId)) {
					int affectedAlignments = event.getAlignmentIndex();
					if (affectedAlignments == AlignmentUtil.BENCHMARK_ALIGNMENT
							|| affectedAlignments == AlignmentUtil.TARGET_ALIGNMENT) {
						processAlignment(affectedAlignments);
						notifyDataChanged(affectedAlignments);
					} else {
						if (affectedAlignments == AlignmentUtil.BOTH_ALIGNMENTS) {
							processAlignment(AlignmentUtil.BENCHMARK_ALIGNMENT);
							processAlignment(AlignmentUtil.TARGET_ALIGNMENT);
							notifyDataChanged(affectedAlignments);
						}
					}

					// TODO: handle errors

				}
			}
			
			if (message instanceof PropertyChangedEvent) {
				PropertyChangedEvent event = (PropertyChangedEvent) message;
				String property = event.getPropertyName();
				if (COMPRESSED_ALPHABET_PROPERTY_ID.equals(property)){
					processAlignment(AlignmentUtil.BENCHMARK_ALIGNMENT);
					processAlignment(AlignmentUtil.TARGET_ALIGNMENT);
					notifyDataChanged(AlignmentUtil.BOTH_ALIGNMENTS);
				}
			}
		}
	}

	private void notifyDataChanged(int affectedAlignments) {
		AnubisManager manager = AnubisManager.getInstance();
		// Notify loaded alignment, if successful
		IEventManager eventManager = manager.getEventManager();
		if (eventManager != null) {
			IEvent event = new AlignmentDataLoadedEvent(
					COMPRESSED_ALPHABET_INTERFACE_ID, affectedAlignments);
			eventManager.raise(event);
		}
	}

	private void processAlignment(int alignmentId) {
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		IAlignmentDataRepository alignmentRepository = (IAlignmentDataRepository) resourceManager
				.getResource(ALIGNMENT_DATAREPOSITORY_INTERFACE_ID);

		if (alignmentRepository != null) {
			// Get base alignment information
			if (alignmentRepository.isLoaded(alignmentId)) {
				int numberOfSequences = alignmentRepository
						.numberOfSequences(alignmentId);
				int length = alignmentRepository.length(alignmentId);
				Character[][] baseAlignment = (Character[][]) alignmentRepository
						.getData(alignmentId, 0, 0, length - 1,
								numberOfSequences - 1);
				alignments[alignmentId] = new Character[numberOfSequences][length];

				// Get compressed alphabet
				CompressedAlphabet alphabetHandler = getSettings()
						.getSelectedAlphabet();

				// Translate to compressed alphabet
				if (alphabetHandler != null) {
					for (int i = 0; i < numberOfSequences; i++) {
						Character[] translatedSequence = alphabetHandler
								.replace(baseAlignment[i], false);
						System.arraycopy(translatedSequence, 0,
								alignments[alignmentId][i], 0, length);
					}
					loaded[alignmentId] = true;
				}

			} else {
				alignments[alignmentId] = null;
				loaded[alignmentId] = false;
			}
		}
	}

	private boolean validateParameters(int alignmentId, int startRow,
			int startColumn, int endRow, int endColumn) {

		boolean result = startColumn >= 0 && startColumn <= endColumn
				&& startRow >= 0 && startRow <= endRow;
		result = result
				&& (alignmentId == AlignmentUtil.BENCHMARK_ALIGNMENT || alignmentId == AlignmentUtil.TARGET_ALIGNMENT);
		result = result && alignments[alignmentId] != null;
		if (result) {

			Character[][] alignment = alignments[alignmentId];
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

	public Settings getSettings() {
		return settings;
	}

}
