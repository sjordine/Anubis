package br.unicamp.ic.anubis.compressedalphabets.datarepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.compressedalphabets.alphabets.CompressedAlphabet;
import br.unicamp.ic.anubis.compressedalphabets.alphabets.CompressedAlphabetFactory;
import br.unicamp.ic.anubis.compressedalphabets.alphabets.Dayhoff6;
import br.unicamp.ic.anubis.compressedalphabets.alphabets.LI_A10;
import br.unicamp.ic.anubis.compressedalphabets.alphabets.LI_B10;
import br.unicamp.ic.anubis.compressedalphabets.alphabets.Murphy10;
import br.unicamp.ic.anubis.compressedalphabets.alphabets.SE_B10;
import br.unicamp.ic.anubis.compressedalphabets.alphabets.SE_B14;
import br.unicamp.ic.anubis.compressedalphabets.alphabets.SE_B6;
import br.unicamp.ic.anubis.compressedalphabets.alphabets.SE_B8;
import br.unicamp.ic.anubis.compressedalphabets.alphabets.SE_V10;
import br.unicamp.ic.anubis.compressedalphabets.alphabets.SOLIS_D10;
import br.unicamp.ic.anubis.compressedalphabets.alphabets.SOLIS_G10;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.event.PropertyChangedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;

import static br.unicamp.ic.anubis.compressedalphabets.util.CommonConstants.ALIGNMENT_DATAREPOSITORY_INTERFACE_ID;
import static br.unicamp.ic.anubis.compressedalphabets.util.CommonConstants.COMPRESSED_ALPHABET_INTERFACE_ID;


public class CompressedAlphabetDataRepositoryFeature implements IFeature {

	public static final UUID ID = UUID
			.fromString("d6409d98-59fe-4e34-b609-74c35ab5aeb4");


	@Override
	public UUID getId() {
		return ID;
	}

	@Override
	public UUID getInterfaceID() {
		return COMPRESSED_ALPHABET_INTERFACE_ID;
	}

	@Override
	public List<UUID> getRequiredInterfaces() {
		List<UUID> requiredInterfaces = new ArrayList<UUID>();
		requiredInterfaces.add(ALIGNMENT_DATAREPOSITORY_INTERFACE_ID);
		return requiredInterfaces;
	}

	@Override
	public void enable() {
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		IEventManager eventManager = manager.getEventManager();
		CompressedAlphabetFactory alphabetFactory = CompressedAlphabetFactory.getInstance();

		if (resourceManager != null && eventManager != null) {
			CompressedAlphabetDataRepository dataRepository = new CompressedAlphabetDataRepository();
			resourceManager.setResource(COMPRESSED_ALPHABET_INTERFACE_ID, dataRepository);
			eventManager.register(AlignmentDataLoadedEvent.class,
					dataRepository);
			eventManager.register(PropertyChangedEvent.class, dataRepository);
		}
		
		if (alphabetFactory!=null){
			CompressedAlphabet alphabet = new Dayhoff6();
			alphabetFactory.register(alphabet.getID(), alphabet);
			alphabet = new SE_B6();
			alphabetFactory.register(alphabet.getID(), alphabet);
			alphabet = new SE_B8();
			alphabetFactory.register(alphabet.getID(), alphabet);
			alphabet = new SE_B10();
			alphabetFactory.register(alphabet.getID(), alphabet);
			alphabet = new SE_B14();
			alphabetFactory.register(alphabet.getID(), alphabet);
			alphabet = new SE_V10();
			alphabetFactory.register(alphabet.getID(), alphabet);
			alphabet = new LI_A10();
			alphabetFactory.register(alphabet.getID(), alphabet);
			alphabet = new LI_B10();
			alphabetFactory.register(alphabet.getID(), alphabet);
			alphabet = new SOLIS_D10();
			alphabetFactory.register(alphabet.getID(), alphabet);
			alphabet = new SOLIS_G10();
			alphabetFactory.register(alphabet.getID(), alphabet);
			alphabet = new Murphy10();
			alphabetFactory.register(alphabet.getID(), alphabet);
		}
		
		
	}

}
