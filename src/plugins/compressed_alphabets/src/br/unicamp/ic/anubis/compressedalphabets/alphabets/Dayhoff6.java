package br.unicamp.ic.anubis.compressedalphabets.alphabets;

import java.util.UUID;

public class Dayhoff6 extends CompressedAlphabet {
	
	private UUID ALPHABET_ID = UUID.fromString("7b700736-6283-4067-a9a7-ac934e4fa401");

	// AGPST, C, DENQ, FWY, HKR, ILMV
	
	{
	// positional matrix
	//                            { 'A', 'R', 'N', 'D', 'C', 'Q', 'E',
	translationArray = new char[] { 'A', 'H', 'D', 'D', 'C', 'D', 'D',
	//      'G', 'H', 'I', 'L', 'K', 'M', 'F', 'P', 'S', 'T', 'W', 'Y', 'V',
			'A', 'H', 'I', 'I', 'H', 'I', 'F', 'A', 'A', 'A', 'F', 'F', 'I',
	//      'B', 'Z', 'X'};
			'B', 'Z', 'X'};
	}

	@Override
	public UUID getID() {
		return ALPHABET_ID;
	}

	@Override
	public String getName() {
		return "Dayhoff(6)";
	}

}
