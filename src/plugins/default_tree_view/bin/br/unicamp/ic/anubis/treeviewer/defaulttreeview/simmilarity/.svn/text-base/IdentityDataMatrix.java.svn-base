package br.unicamp.ic.anubis.treeviewer.defaulttreeview.simmilarity;

import java.util.UUID;

import jebl.evolution.distances.DistanceMatrix;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.data.IAlignmentDataRepository;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.treeviewer.mechanism.AnubisDistanceMatrix;
import br.unicamp.ic.anubis.treeviewer.mechanism.IDistanceMethodAlgorithm;

import static br.unicamp.ic.anubis.treeviewer.defaulttreeview.common.CommonConstants.ALIGNMENT_DATAREPOSITORY_INTERFACE_ID;

public class IdentityDataMatrix implements IEventHandler,
		IDistanceMethodAlgorithm {

	private boolean[] loaded;
	private Double[][][] distanceMatrix;

	public IdentityDataMatrix() {
		loaded = new boolean[2];
		distanceMatrix = new Double[2][][];
	}

	@Override
	public String getName() {
		return "Sequence Identity";
	}

	@Override
	public boolean isLoaded(int alignmentId) {
		boolean returnValue = false;
		if (alignmentId >= AlignmentUtil.BENCHMARK_ALIGNMENT
				&& alignmentId <= AlignmentUtil.TARGET_ALIGNMENT) {
			returnValue = loaded[alignmentId];
		}
		return returnValue;
	}

	@Override
	public String[] getSequences(int alignmentId) {
		String[]  sequences = null;		

		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();

		if (resourceManager != null) {
			// Get alignment repository
			IAlignmentDataRepository alignmentRepository = (IAlignmentDataRepository) resourceManager
					.getResource(ALIGNMENT_DATAREPOSITORY_INTERFACE_ID);
			if (alignmentRepository != null) {
				int numberOfSequences = alignmentRepository
						.numberOfSequences(alignmentId);
				sequences = (String[])alignmentRepository.getSequences(alignmentId, 0, numberOfSequences-1);
			}		
		}
		
		return sequences;
	}

	@Override
	public DistanceMatrix getDistances(int alignmentId) {
		AnubisDistanceMatrix returnValue = null;

		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();

		if (resourceManager != null) {
			// Get alignment repository
			IAlignmentDataRepository alignmentRepository = (IAlignmentDataRepository) resourceManager
					.getResource(ALIGNMENT_DATAREPOSITORY_INTERFACE_ID);
			if (alignmentRepository != null) {
				if (alignmentId >= AlignmentUtil.BENCHMARK_ALIGNMENT
						&& alignmentId <= AlignmentUtil.TARGET_ALIGNMENT
						&& loaded[alignmentId]) {

					returnValue = new AnubisDistanceMatrix(getSequences(alignmentId),
							distanceMatrix[alignmentId]);

				}
				
			}
		}
		
		return returnValue;
	}

	@Override
	public void eventRaised(IEvent message) {
		if (message != null && message instanceof AlignmentDataLoadedEvent) {

			AlignmentDataLoadedEvent event = (AlignmentDataLoadedEvent) message;
			UUID sourceId = event.getSource();

			if (ALIGNMENT_DATAREPOSITORY_INTERFACE_ID.equals(sourceId)) {
				loadDistanceMatrix(event.getAlignmentIndex());
			}
		}
	}

	private void loadDistanceMatrix(int changedAlignment) {
		switch (changedAlignment) {
		case AlignmentUtil.BENCHMARK_ALIGNMENT:
		case AlignmentUtil.TARGET_ALIGNMENT:
			processAlignment(changedAlignment);
			break;
		case AlignmentUtil.BOTH_ALIGNMENTS:
			loadDistanceMatrix(AlignmentUtil.BENCHMARK_ALIGNMENT);
			loadDistanceMatrix(AlignmentUtil.TARGET_ALIGNMENT);
			break;
		default:
			break;
		}
	}

	private void processAlignment(int alignmentId) {
		// Get Resource Manager
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		if (resourceManager != null) {
			// Get alignment repository
			IAlignmentDataRepository alignmentRepository = (IAlignmentDataRepository) resourceManager
					.getResource(ALIGNMENT_DATAREPOSITORY_INTERFACE_ID);
			if (alignmentRepository != null
					&& alignmentRepository.isLoaded(alignmentId)) {
				int numberOfSequences = alignmentRepository
						.numberOfSequences(alignmentId);
				distanceMatrix[alignmentId] = new Double[numberOfSequences][numberOfSequences];
				// Calculate distance matrix
				for (int i = 0; i < numberOfSequences; i++) {
					distanceMatrix[alignmentId][i][i] = 0.0;
				}
				for (int i = 0; i < numberOfSequences - 1; i++) {
					for (int j = i + 1; j < numberOfSequences; j++) {
						double distance = calculateDistance(
								alignmentRepository, alignmentId, i, j);
						distanceMatrix[alignmentId][j][i] = distance;
						distanceMatrix[alignmentId][i][j] = distance;
					}
				}
				// Mount sequence array

				loaded[alignmentId] = true;
			}
		}
	}

	private double calculateDistance(
			IAlignmentDataRepository alignmentRepository, int alignmentId,
			int seq1Index, int seq2Index) {
		double returnValue = 0;
		double nonGapSize = 0;
		// Get alignment size
		int length = alignmentRepository.length(alignmentId);

		Character[][] sequence1 = (Character[][]) alignmentRepository.getData(
				alignmentId, 0, seq1Index, length - 1, seq1Index);
		Character[][] sequence2 = (Character[][]) alignmentRepository.getData(
				alignmentId, 0, seq2Index, length - 1, seq2Index);

		if (sequence1 != null && sequence2 != null) {
			for (int i = 0; i < length; i++) {
				char residue1 = sequence1[0][i];
				char residue2 = sequence2[0][i];

				if (!AlignmentUtil.isGap(residue1)
						|| !AlignmentUtil.isGap(residue2)) {
					nonGapSize += 1;
				}
				if (!AlignmentUtil.isGap(residue1)) {
					if (residue1 == residue2) {
						returnValue += 1;
					}
				}
			}
		} else {
			// TODO Handle error
		}

		returnValue = returnValue / nonGapSize;

		return returnValue;
	}

}
