package com.daisychain.dc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class DCNSNamespaceManager {
	private String id;
	private String name;
	private String public_key;
	private String external_id;
	private String key;
	private boolean read; 
	private boolean write;
	private String namespace_id;
	


}
