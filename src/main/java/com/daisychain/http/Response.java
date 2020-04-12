package com.daisychain.http;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Response {

	private Map<String, String> headers = new HashMap<>();
	private String body;
	private int responseCode;
	
	
	
}
