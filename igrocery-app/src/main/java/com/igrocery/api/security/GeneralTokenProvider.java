package com.igrocery.api.security;

import java.util.UUID;

public class GeneralTokenProvider {

	public String createServiceToken() {
		return UUID.randomUUID().toString();
	}
	
	
	
}
