package br.unicamp.ic.anubis.compressedalphabets.alphabets;

import java.util.UUID;

public class SE_V10 extends CompressedAlphabet {

	private UUID ALPHABET_ID = UUID.fromString("b2913068-518c-48d1-85b2-1af9e0a88ab9");
	
	//AST, C, DEN, FY, G, H, ILMV, KQR, P, W
	{
		// positional matrix
		//                            { 'A', 'R', 'N', 'D', 'C', 'Q', 'E',
		translationArray = new char[] { 'A', 'K', 'D', 'D', 'C', 'K', 'D',
		//      'G', 'H', 'I', 'L', 'K', 'M', 'F', 'P', 'S', 'T', 'W', 'Y', 'V',
				'G', 'H', 'I', 'I', 'K', 'I', 'F', 'P', 'A', 'A', 'W', 'F', 'I',
		//      'B', 'Z', 'X'};
				'B', 'Z', 'X'};
		}
	
	@Override
	public UUID getID() {
		return ALPHABET_ID;
	}
	
	@Override
	public String getName() {
		return "SE-V(10)";
	}

}
