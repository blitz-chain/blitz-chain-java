package com.daisychain.dc;

import java.util.LinkedList;
import java.util.List;

import com.daisychain.exception.DCException;
import com.daisychain.http.Client;

public class DCSession {
	
	public final String id;
	public final String key;
	public final String endpoint;
	public final Client client;

	public DCSession(String id, String key, String endpoint) {
		this.id = id;
		this.key = key;
		this.endpoint = endpoint;
		this.client = new Client(this.id, this.key, this.endpoint);
		this.authenticate();
		
		
	}
	private void authenticate() {
		this.client.authenticateDC();
	}
	
	public List<DCEnvironment> listEnvironments() {
		return this.client.environmenGetAll();
	}
	public DCEnvironment getEnvironmentById(String id) {
		return this.client.environmentGetById(id);
	}
	public DCEnvironment getEnvironmentByExternalId(String externalId) {
		return this.client.environmentGetByExternalId(externalId);
	}
	public DCEnvironment createEnvironment(DCEnvironment daisychainEnvironment) {
		if(daisychainEnvironment.getUser_id() == null) {
			daisychainEnvironment.setUser_id(this.id);
		}
		if(daisychainEnvironment.getUser_id() != this.id) {
			throw new DCException("Environmtn root id is not from this account/user id");
		}
		return this.client.environmentCreate(daisychainEnvironment);
	}
	public DCEnvironment disableEnvironment(String id) {
		return this.client.environmentDisable(id);
	}
	public DCEnvironment enableEnvironment(String id) {
		return this.client.environmentEnable(id);
	}
	
	
	public List<DCRegion> listRegions(DCEnvironment dcEnvironment) {
		return this.client.regionGetAll(dcEnvironment.getId());
	}
	public DCRegion getRegionById(String id) {
		return this.client.regionGetById(id);
	}
	public DCRegion getRegionByExternalId(DCEnvironment dcEnvironment, String externalId) {
		return this.client.regionGetByExternalId(dcEnvironment.getId(), externalId);
	}
	public DCRegion createRegion(DCEnvironment dcEnvironment, DCRegion daisychainRegion) {
		if(daisychainRegion.getEnvironment_id() == null) {
			daisychainRegion.setEnvironment_id(dcEnvironment.getId());
		}
		if(daisychainRegion.getEnvironment_id() != dcEnvironment.getId()) {
			throw new DCException("Region environment id is not from this region");
		}
		return this.client.regionCreate(daisychainRegion);
	}
	public DCRegion disableRegion(String id) {
		return this.client.regionDisable(id);
	}
	public DCRegion enableRegion(String id) {
		return this.client.regionEnable(id);
	}
	
	
	public List<DCNSNamespace> listNamespaces(DCRegion dcRegion) {
		return this.client.namespaceGetAll(dcRegion.getId());
	}
	public DCNSNamespace getNamespaceById(String namespaceId) {
		return this.client.namespaceGetById(namespaceId);
	}
	public DCNSNamespace getNamespaceByExternalId(DCRegion dcRegion, String externalId) {
		return this.client.namespaceGetByExternalId(dcRegion.getId(), externalId);
	}
	public DCNSNamespace createNamespace(DCRegion dcRegion, DCNSNamespace dcNamespace) {
		if(dcNamespace.getRegion_id() == null) {
			dcNamespace.setRegion_id(dcRegion.getId());
		}
		if(dcNamespace.getRegion_id() != dcRegion.getId()) {
			throw new DCException("Namespace region id is not from this region");
		}
		return this.client.namespaceCreate(dcNamespace);
	}
	public DCNSNamespace disableNamespace(String namespaceId) {
		return this.client.namespaceDisable(namespaceId);
	}
	public DCNSNamespace enableNamespace(String namespaceId) {
		return this.client.namespaceEnable(namespaceId);
	}
	

	
	public LinkedList<DCNSNamespaceManager> listNamespaceManagers(DCNSNamespace dcNamespace) {
		return this.client.namespaceManagerGetAll(dcNamespace.getId());
	}
	public DCNSNamespaceManager getNamespaceManagerById(String id) {
		return this.client.namespaceManagerGetById(id);
	}
	public DCNSNamespaceManager getNamespaceManagerByExternalId(DCNSNamespace dcNamespace, String externalId) {
		return this.client.namespaceManagerGetByExternalId(dcNamespace.getId(), externalId);
	}
	public DCNSNamespaceManager createNamespaceManager(DCNSNamespace dcNamespace, DCNSNamespaceManager dcnsManager) {
		if(dcnsManager.getNamespace_id() == null) {
			dcnsManager.setNamespace_id(dcNamespace.getId());
		}
		if(dcnsManager.getNamespace_id() != dcNamespace.getId()) {
			throw new DCException("NamespaceManager namespace is not from this namespace");
		}
		return this.client.namespaceManagerCreate(dcnsManager);
	}
	public DCNSNamespaceManager disableNamespaceManager(String id) {
		return this.client.namespaceManagerDisable(id);
	}
	public DCNSNamespaceManager enableNamespaceManager(String id) {
		return this.client.namespaceManagerEnable(id);
	}	
	
}
