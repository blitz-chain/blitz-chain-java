package com.daisychain.ns;


import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class NSIdentity {
	String id;
	String name;
	String public_key;
	String external_id;
	String description;
	String user_data_base64;
	String wallet_id;


}
