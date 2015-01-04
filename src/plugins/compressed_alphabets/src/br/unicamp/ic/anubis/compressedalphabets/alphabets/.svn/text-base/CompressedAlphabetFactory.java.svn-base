package br.unicamp.ic.anubis.compressedalphabets.alphabets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

public class CompressedAlphabetFactory {
	
	private static CompressedAlphabetFactory instance = new CompressedAlphabetFactory();
	
	private Map<UUID,CompressedAlphabet> registeredAlphabets;
	
	private CompressedAlphabetFactory(){
		registeredAlphabets = new HashMap<UUID, CompressedAlphabet>();
	}
	
	public synchronized void register(UUID id, CompressedAlphabet alphabet){
		registeredAlphabets.put(id, alphabet);
	}
	
	public synchronized CompressedAlphabet getAlphabet(UUID id){
		CompressedAlphabet returnValue = null;
		
		if (registeredAlphabets.containsKey(id)){
			returnValue = registeredAlphabets.get(id);
		}
		
		return returnValue;
	}

	public static CompressedAlphabetFactory getInstance() {
		return instance;
	}
	
	public synchronized Set<Entry<UUID,CompressedAlphabet>> getRegisteredAlphabets(){
		return registeredAlphabets.entrySet();
	}


}
