package br.unicamp.ic.anubis.compressedalphabets.ui.viewer;

import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.compressedalphabets.alphabets.CompressedAlphabet;
import br.unicamp.ic.anubis.compressedalphabets.alphabets.CompressedAlphabetFactory;
import br.unicamp.ic.anubis.compressedalphabets.alphabets.Dayhoff6;
import br.unicamp.ic.anubis.mechanism.settings.ISettingsManager;

import static br.unicamp.ic.anubis.compressedalphabets.util.CommonConstants.COMPRESSED_ALPHABET_PROPERTY_ID;


public class Settings {
	
	private CompressedAlphabet selectedAlphabet;
	private UUID currentAlphabetId;

	public CompressedAlphabet getSelectedAlphabet() {
		CompressedAlphabet returnValue = null;
		
		
		AnubisManager manager = AnubisManager.getInstance();
		ISettingsManager settingsManager = manager.getSettingsManager();
		if (settingsManager!=null){
			UUID selectedAlphabetID = (UUID)settingsManager.getProperty(COMPRESSED_ALPHABET_PROPERTY_ID);

			if (selectedAlphabetID!=null){
				if (selectedAlphabetID.equals(currentAlphabetId)){
					returnValue = selectedAlphabet;
				} else {
					//Get compressed alphabet based on UUID
					CompressedAlphabetFactory alphabetFactory = CompressedAlphabetFactory.getInstance();
					if (alphabetFactory!=null){
						selectedAlphabet = alphabetFactory.getAlphabet(selectedAlphabetID);
						currentAlphabetId = selectedAlphabetID;
						returnValue = selectedAlphabet;
					}
				}
			} else {
				selectedAlphabet = new Dayhoff6();
				settingsManager.setProperty(COMPRESSED_ALPHABET_PROPERTY_ID, selectedAlphabet.getID());
				returnValue = selectedAlphabet;
			}
		}
		
		return returnValue;
	}


	

}
