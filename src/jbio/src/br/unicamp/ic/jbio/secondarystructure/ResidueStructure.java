package br.unicamp.ic.jbio.secondarystructure;

public class ResidueStructure {
	 private char residue;
	    private char structure;
	    private int confidence;

	    public static final char HELIX = 'H';
	    public static final char STRAND ='E';
	    public static final char COIL='C';

	    /**
	     * @return the structure
	     */
	    public char getStructure() {
	        return structure;
	    }

	    /**
	     * @param structure the structure to set
	     */
	    public void setStructure(char structure) {
	        this.structure = structure;
	    }

	    /**
	     * @return the confidence
	     */
	    public int getConfidence() {
	        return confidence;
	    }

	    /**
	     * @param confidence the confidence to set
	     */
	    public void setConfidence(int confidence) {
	        this.confidence = confidence;
	    }

	    /**
	     * @return the residue
	     */
	    public char getResidue() {
	        return residue;
	    }

	    /**
	     * @param residue the residue to set
	     */
	    public void setResidue(char residue) {
	        this.residue = residue;
	    }
}
