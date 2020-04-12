package com.daisychain.dc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class DCRegion {
	
	public static final String AWS_EU_WEST_1 = "AWS.EU_WEST_1";
	public static final String AZURE_EU_WEST_1 = "AZURE.EU_WEST";
	
	private String id;
	private String cloud_region_id;
	private String name;
	private String environment_id;
	private String external_id;
	

	
}
