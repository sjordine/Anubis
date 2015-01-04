package br.unicamp.ic.anubis.compressedalphabets.ui.settings.menu;

import java.util.UUID;

import br.unicamp.ic.anubis.compressedalphabets.alphabets.CompressedAlphabet;

public class CompressedAlphabetItem {

	    private UUID id;
	    private String name;

	    public CompressedAlphabetItem(CompressedAlphabet alphabet) {
	        this.id = alphabet.getID();
	        this.name = alphabet.getName();
	    }

	    public UUID getId() {
	        return id;
	    }
	   
	    public String toString(){
	        return this.name;
	    }
}
