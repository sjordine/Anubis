package br.unicamp.ic.anubis.successmetrics.residue.residuesuccess;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.data.IAlignmentDataRepository;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.mechanism.settings.ISettingsManager;

import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.COLUMN_MATCH_REPOSITORY_ID;
import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.CONSIDER_GAPS_KEY;

public class ResidueSuccess {

	private Integer[][] maximumMatches;
	private Integer[][] targetMatches;
	private Integer[] benchmarkGapMatches;
	private Integer[] targetGapMatches;
	private boolean loaded = false;

	public void processResidueSuccess() {
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		ISettingsManager settingsManager = manager.getSettingsManager();

		IAlignmentDataRepository alignmentRepository = (IAlignmentDataRepository) resourceManager
				.getResource(COLUMN_MATCH_REPOSITORY_ID);

		if (alignmentRepository != null) {

			boolean considerGaps = false;

			if (settingsManager != null) {
				Boolean gaps = (Boolean) settingsManager
						.getProperty(CONSIDER_GAPS_KEY);

				if (gaps != null) {
					considerGaps = gaps;
				}
			}

			// If both sequences are loaded, calculate target alignment success
			// per residue
			if (alignmentRepository.isLoaded(AlignmentUtil.BENCHMARK_ALIGNMENT)
					&& alignmentRepository
							.isLoaded(AlignmentUtil.TARGET_ALIGNMENT)) {
				// Calculate maximum matches per position on benchamark
				// alignment
				calculateMaximumMatches(alignmentRepository, considerGaps);
				// Calculate matches on target alignment
				calculateMatches(alignmentRepository, considerGaps);
				loaded = true;
			} else {
				loaded = false;
			}
		}

	}

	public void calculateMaximumMatches(
			IAlignmentDataRepository alignmentRepository, boolean considerGaps) {
		int benchmarkNumberOfSequences = alignmentRepository
				.numberOfSequences(AlignmentUtil.BENCHMARK_ALIGNMENT);
		int benchmarkLength = alignmentRepository
				.length(AlignmentUtil.BENCHMARK_ALIGNMENT);

		Integer[][] benchmarkMatches = (Integer[][]) alignmentRepository
				.getData(AlignmentUtil.BENCHMARK_ALIGNMENT, 0, 0,
						benchmarkLength - 1, benchmarkNumberOfSequences - 1);

		maximumMatches = new Integer[benchmarkNumberOfSequences][benchmarkLength];

		for (int i = 0; i < benchmarkNumberOfSequences; i++) {
			for (int j = 0; j < benchmarkLength; j++) {
				maximumMatches[i][j] = 0;
			}
		}

		benchmarkGapMatches = new Integer[benchmarkNumberOfSequences];

		for (int i = 0; i < benchmarkNumberOfSequences; i++) {
			benchmarkGapMatches[i] = 0;
		}

		for (int i = 0; i < benchmarkLength; i++) {
			calculateMatch(maximumMatches, benchmarkMatches,
					benchmarkNumberOfSequences, i, considerGaps);

			if (considerGaps) {
				// Considering gaps
				for (int j = 0; j < benchmarkNumberOfSequences; j++) {
					int column1 = benchmarkMatches[j][i];
					if (column1 >= 0) {
						for (int k = 0; k < benchmarkNumberOfSequences; k++) {
							int column2 = benchmarkMatches[k][i];
							if (column2 == -1) {
								benchmarkGapMatches[k] += 1;
							}
						}
					}
				}
			}
		}

	}

	private void calculateMatches(IAlignmentDataRepository alignmentRepository,
			boolean considerGaps) {

		int targetNumberOfSequences = alignmentRepository
				.numberOfSequences(AlignmentUtil.TARGET_ALIGNMENT);
		int targetLength = alignmentRepository
				.length(AlignmentUtil.TARGET_ALIGNMENT);
		int benchmarkNumberOfSequences = alignmentRepository
				.numberOfSequences(AlignmentUtil.BENCHMARK_ALIGNMENT);
		int benchmarkLength = alignmentRepository
				.length(AlignmentUtil.BENCHMARK_ALIGNMENT);

		Integer[][] benchmarkMatches = (Integer[][]) alignmentRepository
				.getData(AlignmentUtil.BENCHMARK_ALIGNMENT, 0, 0,
						benchmarkLength - 1, benchmarkNumberOfSequences - 1);
		Integer[][] targetColumnMatches = (Integer[][]) alignmentRepository
				.getData(AlignmentUtil.TARGET_ALIGNMENT, 0, 0,
						targetLength - 1, targetNumberOfSequences - 1);

		targetMatches = new Integer[targetNumberOfSequences][targetLength];
		targetGapMatches = new Integer[targetNumberOfSequences];

		// initialization
		for (int i = 0; i < targetNumberOfSequences; i++) {
			for (int j = 0; j < targetLength; j++) {
				targetMatches[i][j] = 0;
			}
			targetGapMatches[i] = 0;
		}

		for (int i = 0; i < targetLength; i++) {

			calculateMatch(targetMatches, targetColumnMatches,
					targetNumberOfSequences, i, false);

			if (considerGaps) {
				// Considering gaps
				for (int j = 0; j < targetNumberOfSequences; j++) {
					int column1 = targetColumnMatches[j][i];
					if (column1 >= 0) {
						// check gaps
						for (int k = 0; k < targetNumberOfSequences; k++) {
							int onTarget = targetColumnMatches[k][i];
							int onBenchmark = benchmarkMatches[k][column1];
							if (onTarget == -1 && onBenchmark == -1) {
								targetMatches[j][i] += 1;
								// Add a gap match to the sequence
								targetGapMatches[k] += 1;
							}
						}
					}
				}
			}

		}
	}

	public void calculateMatch(Integer[][] targetMatrix,
			Integer[][] columnMatches, int numberOfSequences, int column,
			boolean considerGaps) {
		for (int j = 0; j < numberOfSequences; j++) {
			for (int k = j; k < numberOfSequences; k++) {
				int column1 = columnMatches[j][column];
				int column2 = columnMatches[k][column];
				if (column1 >= 0 && column2 >= 0 && column1 == column2) {
					targetMatrix[j][column] += 1;
					if (k != j) {
						targetMatrix[k][column] += 1;
					}
				}
				if (considerGaps) {
					if (column1 == -1 && column2 >= 0) {
						targetMatrix[k][column] += 1;
					}
					if (column2 == -1 && column1 >= 0) {
						targetMatrix[j][column] += 1;
					}
				}
			}
		}
	}

	public Integer[][] getTargetMatches() {

		return targetMatches;
	}

	public Integer[][] getMaximumMatches() {

		return maximumMatches;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public Integer[] getBenchmarkGapMatches() {
		return benchmarkGapMatches;
	}

	public Integer[] getTargetGapMatches() {
		return targetGapMatches;
	}

}
