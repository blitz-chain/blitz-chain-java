package com.daisychain.dc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class DCNSNamespace {
	private String id;
	public String external_id; 
	public String name;
	public String region_id;
	
}
