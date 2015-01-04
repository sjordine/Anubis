package br.unicamp.ic.anubis.compressedalphabets.alphabets;

import java.util.UUID;

public class SOLIS_D10 extends CompressedAlphabet {

	private UUID ALPHABET_ID = UUID.fromString("0cc3da40-929f-4784-a4ae-c216f80a9e6c");
	
	//AM, C, DNS, EKQR, F, GP, HT, IV, LY, W
	{
		// positional matrix
		//                            { 'A', 'R', 'N', 'D', 'C', 'Q', 'E',
		translationArray = new char[] { 'A', 'E', 'D', 'D', 'C', 'E', 'E',
		//      'G', 'H', 'I', 'L', 'K', 'M', 'F', 'P', 'S', 'T', 'W', 'Y', 'V',
				'G', 'H', 'I', 'L', 'E', 'A', 'F', 'G', 'D', 'H', 'W', 'L', 'I',
		//      'B', 'Z', 'X'};
				'B', 'Z', 'X'};
		}
	
	@Override
	public UUID getID() {
		return ALPHABET_ID;
	}
	
	@Override
	public String getName() {
		return "SOLIS-D(10)";
	}

}
