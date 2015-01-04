package br.unicamp.ic.anubis.mechanism.alignment;

public class AlignmentUtil {

	public static final int BENCHMARK_ALIGNMENT = 0;
	public static final int TARGET_ALIGNMENT = 1;
	public static final int BOTH_ALIGNMENTS = -1;
	public static final int NO_ALIGNMENT = 2;

	public static int otherAlignment(int alignmentId) {
		int returnValue = 0;

		switch (alignmentId) {
		case BENCHMARK_ALIGNMENT:
			returnValue = TARGET_ALIGNMENT;
			break;
		case TARGET_ALIGNMENT:
			returnValue = BENCHMARK_ALIGNMENT;
			break;
		case BOTH_ALIGNMENTS:
			returnValue = NO_ALIGNMENT;
			break;
		case NO_ALIGNMENT:
			returnValue = BOTH_ALIGNMENTS;
			break;
		default:
			break;
		}

		return returnValue;
	}
	
	public static boolean isGap(char residue){
		return (residue=='-')||(residue=='_')||(residue==' ')||(residue=='.');
	}

}
