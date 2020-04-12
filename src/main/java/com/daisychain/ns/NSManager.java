package com.daisychain.ns;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @NoArgsConstructor @ToString
public class NSManager {
	String publicKey;
	String privateKey;
	String externalId;
	String description;
	
	
}
