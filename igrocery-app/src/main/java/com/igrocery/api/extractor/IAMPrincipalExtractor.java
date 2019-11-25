package com.igrocery.api.extractor;

import java.util.Map;
import java.util.Set;

import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;

public class IAMPrincipalExtractor implements PrincipalExtractor {
	@Override
    public Object extractPrincipal(Map<String, Object> map) {
//		Set<String> keys =  map.keySet();
//		System.out.println("***********************************");
//		for(String key : keys) {
//			System.out.println(map.get(key));
//		}
//		System.out.println("***********************************");
        return map.get("email");
    }
}
