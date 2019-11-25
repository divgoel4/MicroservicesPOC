package com.igrocery.api.service;

import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.igrocery.api.domain.User;
import com.igrocery.api.exception.CustomException;
import com.igrocery.api.respository.UserRepository;
import com.igrocery.api.security.JwtTokenProvider;

@Service
@Transactional
public class UserService {

	private final UserRepository userRepository;
	
	@Autowired
    private JwtTokenProvider jwtTokenProvider;
	
	 @Autowired
	    public UserService(UserRepository userRepository) {
	        this.userRepository = userRepository;
	    }
	
	 
	public String validateUser(String email) {
//		HttpHeaders headerList = new HttpHeaders();
//		HttpHeaders headers = new HttpHeaders();
//        List<String> headerlist = new ArrayList<>();
//        List<String> exposeList = new ArrayList<>();
//        headerlist.add("Content-Type");
//        headerlist.add(" Accept");
//        headerlist.add("X-Requested-With");
//        headerlist.add("Authorization");
//        headers.setAccessControlAllowHeaders(headerlist);
//        exposeList.add("Authorization");
//        headers.setAccessControlExposeHeaders(exposeList);
//        headers.set("Authorization", token);
		User user = userRepository.findByEmail(email);
		 if (user == null) {
             throw new CustomException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
         }
		 String token =  jwtTokenProvider.createToken(user.getEmail());
		 System.out.println("Token >"+token);
         return token;
	}

}
