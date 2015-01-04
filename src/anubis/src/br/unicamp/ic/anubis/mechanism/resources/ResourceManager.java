package br.unicamp.ic.anubis.mechanism.resources;

import java.util.HashMap;
import java.util.UUID;

public class ResourceManager implements IResourceManager {

	private static ResourceManager instance = new ResourceManager();

	private HashMap<UUID, Object> resourceDataBase;

	private ResourceManager() {
		resourceDataBase = new HashMap<UUID, Object>();
	}

	@Override
	public void setResource(UUID guid, Object resource) {
		if (!resourceDataBase.containsKey(guid)) {
			resourceDataBase.put(guid, resource);
		} else {
			// TODO handle existent resource
		}

	}

	@Override
	public Object getResource(UUID guid) {
		Object resource = null;

		if (resourceDataBase.containsKey(guid)) {
			resource = resourceDataBase.get(guid);
		}

		return resource;

	}

	public static ResourceManager getInstance() {
		return instance;
	}

}
