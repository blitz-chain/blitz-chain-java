package com.daisychain.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import com.daisychain.dc.DCEnvironment;
import com.daisychain.dc.DCNSNamespaceManager;
import com.daisychain.dc.DCNSNamespace;
import com.daisychain.dc.DCRegion;
import com.daisychain.exception.DCException;
import com.daisychain.exception.NSException;
import com.daisychain.ns.NSBlock;
import com.daisychain.ns.NSBlockchain;
import com.daisychain.ns.NSIdentity;
import com.daisychain.ns.NSWallet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Client {
	private  final Type NAMESPACE_LIST_TYPE = new TypeToken<LinkedList<DCNSNamespace>>(){}.getType();
	private  final Type DC_NS_MANAGER_LIST_TYPE = new TypeToken <LinkedList<DCNSNamespaceManager>>(){}.getType();
	private  final Type ENVIRONMENT_LIST_TYPE = new TypeToken<LinkedList<DCEnvironment>>(){}.getType();
	private  final Type REGION_LIST_TYPE = new TypeToken<LinkedList<DCRegion>>(){}.getType();
	private  final Type BLOCKCHAIN_LIST_TYPE = new TypeToken<LinkedList<NSBlockchain>>(){}.getType();
	private  final Type BLOCK_LIST_TYPE = new TypeToken<LinkedList<NSBlock>>(){}.getType();
	private  final Type IDENTITY_LIST_TYPE = new TypeToken<LinkedList<NSIdentity>>(){}.getType();
	private  final Type WALLET_LIST_TYPE = new TypeToken<LinkedList<NSWallet>>(){}.getType();
	
	private  final Logger logger = Logger.getLogger(Client.class.getName());
	private  Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm a z").create();
	
	private AtomicInteger http401Count = new AtomicInteger(0);
	
	private  final String DC_AUTH_API_URL = "/api/v1/dc_auth";
	private  final String NS_AUTH_API_URL = "/api/v1/ns_auth";
	
	private  final String DC_ENVIRONEMENT_API_URL = "/api/v1/dc_environment";
	private  final String DC_REGION_API_URL = "/api/v1/dc_region";
	private  final String DC_NS_NAMESPACE_API_URL = "/api/v1/dc_ns_namespace";
	private  final String DC_NAMESPACE_MANAGER_API_URL = "/api/v1/dc_ns_namespace_manager";
	
	private  final String NS_WALLET_API_URL = "/api/v1/ns_wallet";
	private  final String NS_IDENTITY_API_URL = "/api/v1/ns_identity";
	private  final String NS_BLOCKCHAIN_API_URL = "/api/v1/ns_blockchain";
	private  final String NS_BLOCK_API_URL = "/api/v1/ns_block";

	
	private String id = null;
	private String key = null;
	private String endpoint = null;
	private String namespaceId = null;
	private String xAccessToken = null;
	
	public Client(String id, String key, String endpoint) {
		this.id = id;
		this.key = key;
		this.endpoint = endpoint;
	}
	
	public Client(String id, String key, String namespaceId, String endpoint) {
		this.id = id;
		this.key = key;
		this.namespaceId = namespaceId;
		this.endpoint = endpoint;
	}
	
	
	protected Map<String, String> getHeader(){
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type",  "application/json");
		if(this.xAccessToken != null) {
			headers.put("x-access-token", this.xAccessToken);
		}
		return headers;
	}
	
	public Response authenticateDC() {
		JSONObject body = new JSONObject();
		body.put("id", this.id);
		body.put("key", this.key);
//		logger.info(body.toString());
		Response response = post(this.endpoint+DC_AUTH_API_URL+"/login", body.toString());
		if(response.getResponseCode() != 200) {
			throw new DCException("authentication failled");
		}else {
			body = new JSONObject(response.getBody());
			this.xAccessToken = body.getString("token");
		}
		return response; 
	}
	
	public Response authenticateNS() {
		JSONObject body = new JSONObject();
		body.put("id", this.id);
		body.put("key", this.key);
		body.put("namespace_id", this.namespaceId);
		
//		logger.info(body.toString());
		Response response = post(this.endpoint+NS_AUTH_API_URL+"/login", body.toString());
		if(response.getResponseCode() != 200) {
			throw new NSException("authentication failled");
		}else {
			body = new JSONObject(response.getBody());
			this.xAccessToken = body.getString("token");
		}
		return response; 
	}
	
	public DCEnvironment environmentCreate(DCEnvironment environment) {
		return gson.fromJson(post(this.endpoint+DC_ENVIRONEMENT_API_URL+"/create", gson.toJson(environment)).getBody(), DCEnvironment.class);
	}
	public LinkedList<DCEnvironment> environmenGetAll() {
		return gson.fromJson(post(this.endpoint+DC_ENVIRONEMENT_API_URL+"/get_all", "{}").getBody(), ENVIRONMENT_LIST_TYPE);
	}
	public DCEnvironment environmentGetById(String id) {
		return gson.fromJson(post(this.endpoint+DC_ENVIRONEMENT_API_URL+"/get_by_id", "{\"id\":\""+id+"\"}").getBody(), DCEnvironment.class);
	}
	public DCEnvironment environmentGetByExternalId(String externalId) {
		return gson.fromJson(post(this.endpoint+DC_ENVIRONEMENT_API_URL+"/get_by_external_id", "{\"external_id\":\""+externalId+"\"}").getBody(), DCEnvironment.class);
	}
	public  DCEnvironment environmentDisable(String id) {
		return gson.fromJson(post(this.endpoint+DC_ENVIRONEMENT_API_URL+"/disable", "{\"id\":\""+id+"\"}").getBody(), DCEnvironment.class);
	}
	public  DCEnvironment environmentEnable(String id) {
		return gson.fromJson(post(this.endpoint+DC_ENVIRONEMENT_API_URL+"/enable", "{\"id\":\""+id+"\"}").getBody(), DCEnvironment.class);
	}
	
	
	public  DCRegion regionCreate(DCRegion region) {
		return gson.fromJson(post(this.endpoint+DC_REGION_API_URL+"/create", gson.toJson(region)).getBody(), DCRegion.class);
	}
	public  LinkedList<DCRegion> regionGetAll(String environmentId) {
		return gson.fromJson(post(this.endpoint+DC_REGION_API_URL+"/get_all", "{\"environment_id\":\""+environmentId+"\"}").getBody(), REGION_LIST_TYPE);
	}
	public  DCRegion regionGetById(String id) {
		return gson.fromJson(post(this.endpoint+DC_REGION_API_URL+"/get_by_id", "{\"id\":\""+id+"\"}" ).getBody(), DCRegion.class);
	}
	public  DCRegion regionGetByExternalId(String environmentId, String externalId) {
		return gson.fromJson(post(this.endpoint+DC_REGION_API_URL+"/get_by_external_id", "{\"external_id\":\""+externalId+"\"}").getBody(), DCRegion.class);
	}
	public  DCRegion regionDisable(String id) {
		return gson.fromJson(post(this.endpoint+DC_REGION_API_URL+"/disable", "{\"id\":\""+id+"\"}").getBody(), DCRegion.class);
	}
	public  DCRegion regionEnable(String id) {
		return gson.fromJson(post(this.endpoint+DC_REGION_API_URL+"/enable", "{\"id\":\""+id+"\"}").getBody(), DCRegion.class);
	}

	public  DCNSNamespace namespaceCreate(DCNSNamespace namespace) {
		return gson.fromJson(post(this.endpoint+DC_NS_NAMESPACE_API_URL+"/create", gson.toJson(namespace)).getBody(), DCNSNamespace.class);
	}
	public  LinkedList<DCNSNamespace> namespaceGetAll(String regionId) {
		return gson.fromJson(post(this.endpoint+DC_NS_NAMESPACE_API_URL+"/get_all",  "{\"region_id\":\""+regionId+"\"}").getBody(), NAMESPACE_LIST_TYPE);		
	}
	public  DCNSNamespace namespaceGetById(String id) {
		return gson.fromJson(post(this.endpoint+DC_NS_NAMESPACE_API_URL+"/get_by_id",  "{\"id\":\""+id+"\"}").getBody(), DCNSNamespace.class);
	}
	public  DCNSNamespace namespaceGetByExternalId(String regionId, String externalId) {
		return gson.fromJson(post(this.endpoint+DC_NS_NAMESPACE_API_URL+"/get_by_external_id", "{\"region_id\":\""+regionId+"\", \"external_id\": \""+externalId+"\"}").getBody(), DCNSNamespace.class);
	}
	public  DCNSNamespace namespaceDisable(String id) {
		return gson.fromJson(post(this.endpoint+DC_NS_NAMESPACE_API_URL+"/disable", "{\"id\":\""+id+"\"}").getBody(), DCNSNamespace.class);
	}
	public  DCNSNamespace namespaceEnable(String id) {
		return gson.fromJson(post(this.endpoint+DC_NS_NAMESPACE_API_URL+"/enable", "{\"id\":\""+id+"\"}").getBody(), DCNSNamespace.class);
	}

	public  DCNSNamespaceManager namespaceManagerCreate(DCNSNamespaceManager dcnsManager) {
		return gson.fromJson(post(this.endpoint+DC_NAMESPACE_MANAGER_API_URL+"/create", gson.toJson(dcnsManager)).getBody(), DCNSNamespaceManager.class);
	}
	public  LinkedList<DCNSNamespaceManager> namespaceManagerGetAll(String namespaceId) {
		return gson.fromJson(post(this.endpoint+DC_NAMESPACE_MANAGER_API_URL+"/get_all", "{\"namespace_id\":\""+namespaceId+"\"}").getBody(), DC_NS_MANAGER_LIST_TYPE);		
	}
	public  DCNSNamespaceManager namespaceManagerGetById(String id) {
		return gson.fromJson(post(this.endpoint+DC_NAMESPACE_MANAGER_API_URL+"/get_by_id", "{\"id\":\""+id+"\"}").getBody(), DCNSNamespaceManager.class);
	}
	public  DCNSNamespaceManager namespaceManagerGetByExternalId(String namespaceId, String externalId) {
		return gson.fromJson(post(this.endpoint+DC_NAMESPACE_MANAGER_API_URL+"/get_by_external_id", "{\"namespace_id\":\""+namespaceId+"\", \"external_id\": \""+externalId+"\"}").getBody(), DCNSNamespaceManager.class);
	}
	public  DCNSNamespaceManager namespaceManagerDisable(String id) {
		return gson.fromJson(post(this.endpoint+DC_NAMESPACE_MANAGER_API_URL+"/disable", "{\"id\":\""+id+"\"}").getBody(), DCNSNamespaceManager.class);
	}
	public  DCNSNamespaceManager namespaceManagerEnable(String id) {
		return gson.fromJson(post(this.endpoint+DC_NAMESPACE_MANAGER_API_URL+"/enable", "{\"id\":\""+id+"\"}").getBody(), DCNSNamespaceManager.class);
	}


	

	public  LinkedList<NSWallet> namespaceGetWallet() {
		return gson.fromJson(post(this.endpoint+NS_WALLET_API_URL+"/get_all", "{}").getBody(), WALLET_LIST_TYPE);
	}
	
	
	public  NSIdentity identityCreate(NSIdentity identity) {
		return gson.fromJson(post(this.endpoint+NS_IDENTITY_API_URL+"/create", gson.toJson(identity)).getBody(), NSIdentity.class);
	}
	public  LinkedList<NSIdentity> identityGetAll() {
		return gson.fromJson(post(this.endpoint+NS_IDENTITY_API_URL+"/get_all", "{}").getBody(), IDENTITY_LIST_TYPE);
	}

	public  NSIdentity identityGetById(String id) {
		return gson.fromJson(post(this.endpoint+NS_IDENTITY_API_URL+"/get_by_id","{\"id\":\""+id+"\"}").getBody(), NSIdentity.class);
	}
	public  NSIdentity identityGetByExternalId(String externalId) {
		return gson.fromJson(post(this.endpoint+NS_IDENTITY_API_URL+"/get_by_external_id", "{\"external_id\":\""+externalId+"\"}").getBody(), NSIdentity.class);
	}
	public  NSIdentity identityDisable(String id) {
		return gson.fromJson(post(this.endpoint+NS_IDENTITY_API_URL+"/disable","{\"id\":\""+id+"\"}").getBody(), NSIdentity.class);
	}
	public  NSIdentity identityEnable(String id) {
		return gson.fromJson(post(this.endpoint+NS_IDENTITY_API_URL+"/enable","{\"id\":\""+id+"\"}").getBody(), NSIdentity.class);
	}
	
	
	public  NSBlockchain blockchainCreate(NSBlockchain blockchain) {
		return gson.fromJson(post(this.endpoint+NS_BLOCKCHAIN_API_URL+"/create", gson.toJson(blockchain)).getBody(), NSBlockchain.class);
	}
	public  LinkedList<NSBlockchain> blockchainGetAll() {
		return gson.fromJson(post(this.endpoint+NS_BLOCKCHAIN_API_URL+"/get_all", "{}").getBody(), BLOCKCHAIN_LIST_TYPE);		
	}
	public  NSBlockchain blockchainGetById(String id) {
		return gson.fromJson(post(this.endpoint+NS_BLOCKCHAIN_API_URL+"/get_by_id", "{\"id\":\""+id+"\"}").getBody(), NSBlockchain.class);
	}
	public  NSBlockchain blockchainGetByExternalId(String externalId) {
		return gson.fromJson(post(this.endpoint+NS_BLOCKCHAIN_API_URL+"/get_by_external_id", "{\"external_id\":\""+externalId+"\"}").getBody(), NSBlockchain.class);
	}
	public  NSBlockchain blockchainDisable(String id) {
		return gson.fromJson(post(this.endpoint+NS_BLOCKCHAIN_API_URL+"/disable", "{\"id\":\""+id+"\"}").getBody(), NSBlockchain.class);
	}
	public  NSBlockchain blockchainEnable(String id) {
		return gson.fromJson(post(this.endpoint+NS_BLOCKCHAIN_API_URL+"/enable", "{\"id\":\""+id+"\"}").getBody(), NSBlockchain.class);
	}
	
	
	public  NSBlock blockCreate(NSBlock block) {
		return gson.fromJson(post(this.endpoint+NS_BLOCK_API_URL+"/create", gson.toJson(block)).getBody(), NSBlock.class);
	}
	public  LinkedList<NSBlock> blockGetAll(String blockchainId) {
		return gson.fromJson(post(this.endpoint+NS_BLOCK_API_URL+"/get_all", "{\"blockchainId\":\""+blockchainId+"\"}").getBody(), BLOCK_LIST_TYPE);		
	}
	public  NSBlock blockGetById(String id) {
		return gson.fromJson(post(this.endpoint+NS_BLOCK_API_URL+"/get_by_id", "{\"id\":\""+id+"\"}").getBody(), NSBlock.class);
	}
	public  NSBlock blockGetByHash(String hash) {
		return gson.fromJson(post(this.endpoint+NS_BLOCK_API_URL+"/hash", "{\"hash\":\""+hash+"\"}").getBody(), NSBlock.class);
	}
	public  NSBlock blockGetByExternalId(String externalId) {
		return gson.fromJson(post(this.endpoint+NS_BLOCK_API_URL+"/get_by_external_id", "{\"external_id\":\""+externalId+"\"}").getBody(), NSBlock.class);
	}

	
	
//	private  Response get(String endpoint)  {
//		try {
//			HttpClient client = HttpClientBuilder.create().build();
//			HttpGet get = new HttpGet(endpoint);
//			for (Map.Entry<String, String> entry : getHeader().entrySet()) {
//				get.addHeader(entry.getKey(), entry.getValue());
//			}
//			HttpResponse response = client.execute(get);
//			logger.info("GET - " + endpoint);
//			logger.info("Status Code - " + response.getStatusLine().getStatusCode());
//			if(response.getStatusLine().getStatusCode() != 200) {
//				throw new RuntimeException("Http response code >> " + response.getStatusLine().getStatusCode() + " for endpoint >> " + endpoint);
//			}else {
//				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//				StringBuffer body = new StringBuffer();
//				String line = "";
//				while ((line = rd.readLine()) != null) {
//					body.append(line);
//				}			
//				Response daisychainResponse = new Response();
//				daisychainResponse.setBody(body.toString());
//				for(Header header :response.getAllHeaders()) {
//					daisychainResponse.getHeaders().put(header.getName(), header.getValue());
//				}
//				daisychainResponse.setResponseCode(response.getStatusLine().getStatusCode());
//				return daisychainResponse;
//			}
//		}catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}


	private  Response post(String endpoint, String body){
		try {
//			logger.info("body>>" + body);
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(endpoint);
			for (Map.Entry<String, String> entry : getHeader().entrySet()) {
				post.addHeader(entry.getKey(), entry.getValue());
			}
//			logger.info("headers>>" + post.getAllHeaders());
			HttpEntity entity = new StringEntity(body);
			post.setEntity(entity);
			HttpResponse response = client.execute(post);
//			logger.info("POST - " + endpoint);
//			logger.info("Status Code - " + response.getStatusLine().getStatusCode() + " -- " + response.getStatusLine().getReasonPhrase());
			if(response.getStatusLine().getStatusCode() == 401) {
				http401Count.getAndAdd(1);
				if(http401Count.get()>=3) {
					http401Count.set(0);
					BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					StringBuffer result = new StringBuffer();
					String line = "";
					while ((line = rd.readLine()) != null) {
						result.append(line);
					}
					logger.info("Body - " + result.toString());
					throw new RuntimeException("Http response code >> " + response.getStatusLine().getStatusCode() + " for endpoint >> " + endpoint + ">>> " + result.toString());
				}
				if(endpoint.indexOf("/api/v1/dc")!= -1) {
					authenticateDC();
					return post(endpoint, body);
				}else {
					authenticateNS();
					return post(endpoint, body);
				}
			}else if(response.getStatusLine().getStatusCode() != 200) {
				http401Count.set(0);
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				StringBuffer result = new StringBuffer();
				String line = "";
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
				logger.info("Body - " + result.toString());
				throw new RuntimeException("Http response code >> " + response.getStatusLine().getStatusCode() + " for endpoint >> " + endpoint + ">>> " + result.toString());

			}else {
				http401Count.set(0);
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				StringBuffer result = new StringBuffer();
				String line = "";
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
//				logger.info("Body - " + result.toString());
				Response daisychainResponse = new Response();
				daisychainResponse.setBody(result.toString());
				for(Header header :response.getAllHeaders()) {
					daisychainResponse.getHeaders().put(header.getName(), header.getValue());
				}
				daisychainResponse.setResponseCode(response.getStatusLine().getStatusCode());
				return daisychainResponse;
			}
		}catch (Exception e) {
			throw new DCException(e);
		}
	}
	
//	private  Response put(String endpoint, String body) {
//		try {
//			HttpClient client = HttpClientBuilder.create().build();
//			HttpPut put = new HttpPut(endpoint);
//			HttpEntity entity = new StringEntity(body);
//			put.setEntity(entity);
//			for (Map.Entry<String, String> entry : getHeader().entrySet()) {
//				put.addHeader(entry.getKey(), entry.getValue());
//			}
//			HttpResponse response = client.execute(put);
//			logger.info("PUT - " + endpoint);
//			logger.info("Status Code - " + response.getStatusLine().getStatusCode());
//			if(response.getStatusLine().getStatusCode() != 200) {
//				throw new RuntimeException("Http response code >> " + response.getStatusLine().getStatusCode() + " for endpoint >> " + endpoint);
//			}else {
//				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//				StringBuffer result = new StringBuffer();
//				String line = "";
//				while ((line = rd.readLine()) != null) {
//					result.append(line);
//				}
//				Response daisychainResponse = new Response();
//				daisychainResponse.setBody(body.toString());
//				for(Header header :response.getAllHeaders()) {
//					daisychainResponse.getHeaders().put(header.getName(), header.getValue());
//				}
//				daisychainResponse.setResponseCode(response.getStatusLine().getStatusCode());
//				return daisychainResponse;
//			}
//		}catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

}


	
	

