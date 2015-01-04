package br.unicamp.ic.jbio.matrix;

public class MatrixUtil {

	private static final int GAP_SCORE = -6;
	private static final int GAP_GAP_SCORE = -4;
	private static final String GAP = "-";

	public static int getScore(DistanceMatrix matrix, char first, char second) {
		return getScore(matrix, Character.toString(first),
				Character.toString(second));
	}

	public static int getScore(DistanceMatrix matrix, int first, char second) {

		int index = 0;
		int value = 0;

		index = getIndex(Character.toString(second));

		if (index != -1) {
			value = matrix.getScores()[first][index];
		} else {
			// TODO Error
		}

		return value;

	}

	public static int getScore(DistanceMatrix matrix, String first,
			String second) {

		try {

			int index1 = 0, index2 = 0;
			int value = 0;

			index1 = getIndex(first);
			index2 = getIndex(second);

			if (index1 != -1 && index2 != -1) {
				value = matrix.getScores()[index1][index2];
			} else {
				// TODO Error
			}

			return value;

		} catch (RuntimeException ex) {
			throw ex;
		}

	}

	private static int getIndex(String aminoacid) {

		int index = -1;

		if (aminoacid.equals(" ") || aminoacid.equals("*")
				|| aminoacid.equals("-")) {
			index = Aminoacids.valueOf("GAP").getIndex();
		} else {
			index = Aminoacids.valueOf(aminoacid).getIndex();
		}

		return index;
	}
}
