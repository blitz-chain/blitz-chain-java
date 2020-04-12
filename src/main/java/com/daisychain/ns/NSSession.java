package com.daisychain.ns;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.daisychain.exception.DCException;
import com.daisychain.http.Client;
import com.daisychain.http.Response;

@Repository
public class NSSession {
	

	@Value("daisychain.endpoint")
	private String endpoint;
	@Value("daisychain.namespace.id")
	private String id;
	@Value("daisychain.namespace.key")
	private String key;
	@Value("daisychain.namespace.namespace_id")
	private String namespaceId;
	
	private Client client = null; 
	
	public  NSSession() {
	
		this.authenticate();
	}
	
	public  NSSession(String id, String key, String namespaceId, String endpoint) {
		this.id = id;
		this.key = key;
		this.endpoint = endpoint;
		this.namespaceId = namespaceId;
		this.client = new Client(this.id,  this.key, this.namespaceId, this.endpoint);
		this.authenticate();
	}
	@PostConstruct
	private void authenticate() {
		if(this.client == null) {
			this.client = new Client(this.id,  this.key, this.endpoint);
		}
		Response response = this.client.authenticateNS();
		if(response.getResponseCode() != 200) {
			throw new DCException("failled to authenticate");
		}
	}

	public NSWallet getWallet() {
		return this.client.namespaceGetWallet().get(0);
	}

	
	public List<NSIdentity> listIdentities() {
		return this.client.identityGetAll();
	}
	public NSIdentity getIdentityById(NSIdentity nsIdentity) {
		return this.client.identityGetById(nsIdentity.getId());

	}
	public NSIdentity getIdentityByExternalId(String externalId) {
		return this.client.identityGetByExternalId(externalId);
	}
	public NSIdentity createIdentity(NSIdentity nsIdentity) {
		return this.client.identityCreate(nsIdentity);
	}
	public NSIdentity disableIdentity(NSIdentity nsIdentity) {
		return this.client.identityDisable(nsIdentity.getId());
	}
	public NSIdentity enableIdentity(NSIdentity nsIdentity) {
		return this.client.identityEnable(nsIdentity.getId());
	}
	
	
	
	
	
	
	public LinkedList<NSBlockchain> nsBlockainGetAll() {
		return this.client.blockchainGetAll();
	}
	public NSBlockchain nsBlockchainGetByid(String blockchainId) {
		return this.client.blockchainGetById(blockchainId);
	}
	public NSBlockchain nsBlockchainGetByExternalId(String externalId) {
		return this.client.blockchainGetByExternalId(externalId);
	}
	public NSBlockchain nsBlockchainCreate(NSBlockchain nsBlockchain) {
		return this.client.blockchainCreate(nsBlockchain);
	}
	public NSBlockchain nsBlockchainDisable(String blockchainId) {
		return this.client.blockchainDisable(blockchainId);
	}
	public NSBlockchain nBlockchainEnable(String blockchainId) {
		return this.client.blockchainDisable(blockchainId);
	}
	
	
	public LinkedList<NSBlock> getBlocks(NSBlockchain nsBlockchain) {
		return this.client.blockGetAll(nsBlockchain.getId());
	}
	public NSBlock getBlockByid(String blockId) {
		return this.client.blockGetById(blockId);
	}
	public NSBlock getBlockByHash(String hash) {
		return this.client.blockGetByHash(hash);
	}
	public NSBlock createBlock(NSBlockchain nsBlockchain, NSBlock nsBlock) {
		if(nsBlock.getBlockchain_id() == null) {
			nsBlock.setBlockchain_id(nsBlockchain.getId());
		}
		if(nsBlock.getBlockchain_id() != nsBlockchain.getId()) {
			throw new DCException("Block if not valid in this Blockchain - blockkchain_id fior block and blockchain do not match");
		}
		return this.client.blockCreate(nsBlock);
	}
	public NSBlock getBlockByExternalId(String externalId) {
		return this.client.blockGetByExternalId(externalId);
	}

	
//	public List<NSBlockchain> listBlockchains() {
//		return Client.blockchainGetAll();
//	}
//	public DCEnvironment getEnvironmentById(String id) {
//		return Client.environmentGetById(id);
//	}
//	public DCEnvironment getEnvironmentByExternalId(String externalId) {
//		return Client.environmentGetById(externalId);
//	}
//	public DCEnvironment createEnvironment(DCEnvironment daisychainEnvironment) {
//		if(daisychainEnvironment.getRoot_id() == null) {
//			daisychainEnvironment.setRoot_id(Config.getProperty(Config.DAISYCHAIN_ID));
//		}
//		if(daisychainEnvironment.getRoot_id() != Config.getProperty(Config.DAISYCHAIN_ID)) {
//			throw new DCException("Environmtn root id is not from this account/user id");
//		}
//		return Client.environmentCreate(daisychainEnvironment);
//	}
//	public DCEnvironment disableEnvironment(String id) {
//		return Client.environmentDisable(id);
//	}

	
}
