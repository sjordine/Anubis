package br.unicamp.ic.anubis.compressedalphabets.alphabets;

import java.util.UUID;

public class SE_B8 extends CompressedAlphabet {
	
	private UUID ALPHABET_ID = UUID.fromString("e20eaab4-16fb-4500-ae06-c4844b77704b");
	
	//AST, C, DHN, EKQR, FWY, G, ILMV, P
	{
		// positional matrix
		//                            { 'A', 'R', 'N', 'D', 'C', 'Q', 'E',
		translationArray = new char[] { 'A', 'E', 'D', 'D', 'C', 'E', 'E',
		//      'G', 'H', 'I', 'L', 'K', 'M', 'F', 'P', 'S', 'T', 'W', 'Y', 'V',
				'G', 'D', 'I', 'I', 'E', 'I', 'F', 'P', 'A', 'A', 'F', 'F', 'I',
		//      'B', 'Z', 'X'};
				'B', 'Z', 'X'};
		}
	
	@Override
	public UUID getID() {
		return ALPHABET_ID;
	}
	
	@Override
	public String getName() {
		return "SE-B(8)";
	}
}
