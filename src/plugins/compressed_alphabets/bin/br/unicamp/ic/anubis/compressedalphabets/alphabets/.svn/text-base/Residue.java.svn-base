package br.unicamp.ic.anubis.compressedalphabets.alphabets;

import java.util.HashMap;
import java.util.Map;

public enum Residue {
	A('A', 0), R('R', 1), N('N', 2), D('D', 3), C('C', 4), Q('Q', 5), E('E', 6), G(
			'G', 7), H('H', 8), I('I', 9), L('L', 10), K('K', 11), M('M', 12), F(
			'F', 13), P('P', 14), S('S', 15), T('T', 16), W('W', 17), Y('Y', 18), V(
			'V', 19), B('B', 20), Z('Z', 21), X('X', 22);

	private final char residue;
	private final int index;

	private static final Map<Character, Residue> charToEnum = new HashMap<Character, Residue>();

	static { 
		// Initialize residue map
		for (Residue residue : values()){
			charToEnum.put(residue.getResidue(), residue);
		}
	}

	Residue(char residue, int index) {
		this.residue = residue;
		this.index = index;
	}

	public char getResidue() {
		return residue;
	}

	public int getIndex() {
		return index;
	}
	
	
	public static int getIndex(char character){
		int index = -1;
		if (charToEnum.containsKey(character)){
			index = charToEnum.get(character).index;
		} 
		return index;
	}
	
}
