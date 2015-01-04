package br.unicamp.ic.anubis.compressedalphabets.alphabets;

import java.util.UUID;

import br.unicamp.ic.anubis.util.SequenceUtil;

public abstract class CompressedAlphabet {
	
	protected char[] translationArray;
	
	public char replace(char originalResidue, boolean keepCase){
		char residue = originalResidue;
		int index = Residue.getIndex(originalResidue);
		if (index >=0 && index < translationArray.length){
			residue = translationArray[index];
		}
		
		residue = (Character.isLowerCase(originalResidue) && keepCase)?Character.toLowerCase(residue):residue;
		
		
		return residue;
	}
	
	public Character[] replace(Character[] originalSequence, boolean keepCase){
		Character[] result = null;
		
		if (originalSequence!=null){
			result = new Character[originalSequence.length];
			
			for (int i=0; i<originalSequence.length; i++){
				char currentPos = originalSequence[i];
				if (SequenceUtil.isGap(currentPos)){
					result[i] = currentPos;
				} else {
					result[i] =  replace(currentPos,keepCase);
				}
				
			}
		}

		return result;
	}

	public abstract UUID getID();
	public abstract String getName();
	

}
