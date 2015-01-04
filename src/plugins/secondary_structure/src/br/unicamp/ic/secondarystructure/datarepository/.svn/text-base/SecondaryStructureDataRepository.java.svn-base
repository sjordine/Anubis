package br.unicamp.ic.secondarystructure.datarepository;

import static br.unicamp.ic.secondarystructure.common.CommonConstants.ALIGNMENT_DATAREPOSITORY_INTERFACE_ID;
import static br.unicamp.ic.secondarystructure.common.CommonConstants.SECONDARY_STRUCTURE_PATH_PROPERTY;
import static br.unicamp.ic.secondarystructure.common.CommonConstants.SECONDARY_STRUCTURE_REPOSITORY_ID;

import java.util.ArrayList;
import java.util.List;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.data.IAlignmentDataRepository;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.event.PropertyChangedEvent;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.mechanism.settings.ISettingsManager;
import br.unicamp.ic.jbio.secondarystructure.HorizReader;
import br.unicamp.ic.jbio.secondarystructure.ResidueStructure;

public class SecondaryStructureDataRepository implements
		IAlignmentDataRepository, IEventHandler {

	private static final String FILE_EXTENSION = ".horiz";
	
	private static final Character GAP = '-';
	private static final Character UNKNOWN_RESIDUE='?';
	
	// Alignment Data Structures
	private Character[][][] alignments = new Character[2][][];
	List<List<ResidueStructure>> secondaryStructure = new ArrayList<List<ResidueStructure>>();
	public boolean[] loaded = new boolean[2];

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

	/**
	 * Validate whether the alignment and positions are valkid
	 * 
	 * @param alignmentId
	 *            Alignment Identifier (Benchmark or target)
	 * @param startRow
	 *            requested starting row
	 * @param startColumn
	 *            request starting column
	 * @param endRow
	 *            requested end row
	 * @param endColumn
	 *            requested end column
	 * @return true if request is valid, false otherwise
	 */
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

	private void loadSecondaryStructures() {
		// Get alignment repository
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		ISettingsManager settingsManager = manager.getSettingsManager();

		if (resourceManager != null && settingsManager != null) {
			IAlignmentDataRepository alignmentRepository = (IAlignmentDataRepository) resourceManager
					.getResource(ALIGNMENT_DATAREPOSITORY_INTERFACE_ID);
			// Get path
			String secondaryStructurePath = (String) settingsManager
					.getProperty(SECONDARY_STRUCTURE_PATH_PROPERTY);

			if (alignmentRepository != null && secondaryStructurePath != null) {

				if (alignmentRepository
						.isLoaded(AlignmentUtil.BENCHMARK_ALIGNMENT)) {
					processAlignment(manager,AlignmentUtil.BENCHMARK_ALIGNMENT,
							secondaryStructurePath, alignmentRepository);
				} else {
					if (alignmentRepository
							.isLoaded(AlignmentUtil.TARGET_ALIGNMENT)) {
						processAlignment(manager,AlignmentUtil.TARGET_ALIGNMENT,
								secondaryStructurePath, alignmentRepository);
					}
				}
			}

		}
	}

	private void processAlignment(AnubisManager manager, int alignmentId, String path,
			IAlignmentDataRepository repository) {
		// Load secondary structures
		loadSecondaryStructures(alignmentId, path,
				repository.numberOfSequences(alignmentId));
		// Merge structure and alignment data
		mergeAlignment(repository, alignmentId);
		mergeAlignment(repository, AlignmentUtil.otherAlignment(alignmentId));
		
		notifyDataChanged(manager, alignmentId);
	}

	private void mergeAlignment(IAlignmentDataRepository repository,
			int alignmentId) {
		if (repository.isLoaded(alignmentId)) {
			int numberOfSequences = repository.numberOfSequences(alignmentId);
			int alignmentSize = repository.length(alignmentId);

			alignments[alignmentId] = new Character[numberOfSequences][alignmentSize];

			for (int i = 0; i < numberOfSequences; i++) {
				List<ResidueStructure> structure = secondaryStructure.get(i);
				Character[][] sequence = (Character[][]) repository.getData(
						alignmentId, 0, i, alignmentSize - 1, i);
				if (sequence != null && structure != null
						&& sequence.length == 1
						&& sequence[0].length == alignmentSize) {
					int currentPosition = 0;
					for (int j = 0; j < alignmentSize; j++) {
						if (AlignmentUtil.isGap(sequence[0][j])) {
							alignments[alignmentId][i][j] = GAP;
						} else {
							ResidueStructure residueStructure = structure
									.get(currentPosition);
							alignments[alignmentId][i][j] = residueStructure
									.getStructure();
							currentPosition++;
						}
					}
				} else {
					for (int j = 0; j < alignmentSize; j++){
						alignments[alignmentId][i][j] = UNKNOWN_RESIDUE;
					}
				}
			}
			loaded[alignmentId]=true;
		}
	}

	private void loadSecondaryStructures(int alignmentId, String path,
			int length) {
		// Clear previous structures
		secondaryStructure.clear();
		// Get Sequences
		String[] sequences = (String[]) getSequences(alignmentId, 0, length - 1);

		if (sequences != null) {

			for (int i = 0; i < sequences.length; i++) {
				try {
					String filePath = path + "/" + sequences[i]
							+ FILE_EXTENSION;
					List<ResidueStructure> structure = HorizReader
							.readFile(filePath);
					secondaryStructure.add(structure);
				} catch (Exception ex) {
					// Add a null list as a place holder
					secondaryStructure.add(null);
				}
			}
		}
	}

	@Override
	public void eventRaised(IEvent message) {
		if (message != null) {
			if (message instanceof AlignmentDataLoadedEvent) {
				AlignmentDataLoadedEvent alignmentLoadedEvent = (AlignmentDataLoadedEvent) message;
				if (ALIGNMENT_DATAREPOSITORY_INTERFACE_ID
						.equals(alignmentLoadedEvent.getSource())) {
					loadSecondaryStructures();
				}
			}
			if (message instanceof PropertyChangedEvent) {
				PropertyChangedEvent propertyChangedEvent = (PropertyChangedEvent) message;
				if (SECONDARY_STRUCTURE_PATH_PROPERTY
						.equals(propertyChangedEvent.getPropertyName())) {
					loadSecondaryStructures();
				}
			}
		}
	}
	
	private void notifyDataChanged(AnubisManager manager, int alignmentId) {

		// Notify loaded alignment, if successful
		IEventManager eventManager = manager.getEventManager();
		if (eventManager != null) {
			IEvent event = new AlignmentDataLoadedEvent(SECONDARY_STRUCTURE_REPOSITORY_ID,
					alignmentId);
			eventManager.raise(event);
		}
	}

}
