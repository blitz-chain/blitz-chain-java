package com.daisychain.ns;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class NSBlockchain {
	private String id;
	private String public_key;
	private String external_id;
	private String name;
	private String user_data_base64;
	private String namespace_id;
	
	
}
