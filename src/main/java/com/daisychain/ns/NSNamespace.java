package com.daisychain.ns;

import java.util.LinkedList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class NSNamespace {
	private String id;
	private String publicKey;
	public String external_id; 
	public String name;
	public String description;
	
	public NSNamespace(String id, String publicKey) {
		this.id = id;
		this.publicKey = publicKey;		
	}	

	public LinkedList<NSManager> getNamespaceManagers() {
		return new LinkedList<NSManager> ();
	}
	public NSManager getNamespaceManagerById(String namespaceManagerId) {
		return new NSManager();
	}
	public NSManager getNamespaceManagerByExternalId(String externalId) {
		return new NSManager();
	}
	public NSManager createNamespaceManager(String external_id, String name, String description, boolean read, boolean write) {
		return new NSManager();
	}
	public NSManager disableNamespaceManager(String id) {
		return new NSManager();
	}
	public NSManager disableNamespaceManagerById(String id) {
		return new NSManager();
	}
	public NSManager enableNamespaceManagerById(String id) {
		return new NSManager();
	}	

	
	
}
