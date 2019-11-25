package com.igrocery.api.rest.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igrocery.api.domain.Product;
import com.igrocery.api.exception.CustomException;
import com.igrocery.api.security.GeneralTokenProvider;
import com.igrocery.api.service.ProductCatalogProxyService;
import com.igrocery.api.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api")
public class IGroceryController {

	private Logger logger = LoggerFactory.getLogger(IGroceryController.class);
	
	@Autowired
	private ProductCatalogProxyService productCatalogProxyService;
	
	@Autowired
	UserService userService;
	
	GeneralTokenProvider generalTokenProvider;
	
	List<Product> products;
	String serviceToken = null;
    String transactionToken = null;
	
//	@Context()
//	SecurityContext securityContext;
//	
//	@Value("${keycloak.url}")
//	String serverUrl;
	
//	@Autowired
//	private LoginService loginService;

//	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
//        String token = iLoginService.login(loginRequest.getUsername(),loginRequest.getPassword());
//        HttpHeaders headers = new HttpHeaders();
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
//        return new ResponseEntity<AuthResponse>(new AuthResponse(token), headers, HttpStatus.CREATED);
//    }
	
	@GetMapping(path = "/login")
	public ResponseEntity<?> getToken(Principal principal) throws CustomException{
		logger.info("Got Login....");
//		Keycloak kc = Keycloak.getInstance(serverUrl, (String) credentials.get("company"),
//				(String) credentials.get("username"), (String) credentials.get("password"),
//				(String) credentials.get("clientId"), (String) credentials.get("clientScreteId"));
//		String token = kc.tokenManager().getAccessToken().getToken();
//		KeycloakAuthenticationToken token24 = (KeycloakAuthenticationToken) principal;        
//        KeycloakPrincipal principal2=(KeycloakPrincipal)token24.getPrincipal();
//        KeycloakSecurityContext session = principal2.getKeycloakSecurityContext();
//        AccessToken accessToken = session.getToken();
//        System.out.println(accessToken.getPreferredUsername());
//        System.out.println(accessToken.getEmail());
//        System.out.println(accessToken.getFamilyName());
//        System.out.println(accessToken.getGivenName());
//        System.out.println(accessToken.getIssuer());            
//        System.out.println(accessToken.getRealmAccess());
//		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		 System.out.println(authentication.getPrincipal());
//		    KeycloakPrincipal userDetails = (KeycloakPrincipal) authentication.getPrincipal();
//		    IDToken idToken = userDetails.getKeycloakSecurityContext().getIdToken();
//		    System.out.println(">>>>>>>>idToken.getPreferredUsername() >>>"+idToken.getPreferredUsername());
//		if (securityContext != null && securityContext.getUserPrincipal() instanceof KeycloakPrincipal) {
//		    KeycloakPrincipal principal2 = ((KeycloakPrincipal) securityContext.getUserPrincipal());
//		    AccessToken accessToken = principal2.getKeycloakSecurityContext().getToken();
//		    // IDToken token = principal.getKeycloakSecurityContext().getIdToken();
//
//		    System.out.println("User logged in: " + accessToken.getPreferredUsername());
//		} else {
//		    System.out.println("SecurityContext could not provide a Keycloak context.");
//		}
		
//	    if (authentication != null) {
//	        if (authentication.getPrincipal() instanceof KeycloakPrincipal) {
//	            KeycloakPrincipal<KeycloakSecurityContext> kp = (KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal();
//	            // retrieving username here
//	            String username = kp.getKeycloakSecurityContext().getToken().getPreferredUsername();
//	            System.out.println(">>>>>>>>>>>>>>> username >> "+username);
//	            }
//	      }
//	    
	    
	    
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
        //headers.set("Authorization", token);
        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
//        Object token = ((OAuth2AuthenticationDetails) authentication1.getDetails()).getDecodedDetails();
//        System.out.println(token);
        System.out.println(((OAuth2AuthenticationDetails) authentication1.getDetails()).getTokenValue());
        final OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
        final Authentication authentication = oAuth2Authentication.getUserAuthentication();
        System.out.println(">> Name >> "+authentication.getName());
        String token = userService.validateUser(authentication.getName());
        
        if(token!=null) {
        	generalTokenProvider = new GeneralTokenProvider();
        	serviceToken = generalTokenProvider.createServiceToken();
        	transactionToken = generalTokenProvider.createServiceToken();        	
        }
        System.out.println(authentication.getDetails());
        System.out.println(authentication.getCredentials());
        System.out.println(authentication.getAuthorities());
        System.out.println(authentication.getPrincipal());
		// Validate the email inside token with DB
		// Return token if exist else "Invalid User"
		// return new ResponseEntity<AuthResponse>(new AuthResponse(token), headers, HttpStatus.CREATED);
        
        HttpHeaders headers = new HttpHeaders();
        List<String> headerlist = new ArrayList<>();
        List<String> exposeList = new ArrayList<>();
        headerlist.add("Content-Type");
        headerlist.add(" Accept");
        headerlist.add("X-Requested-With");
        headerlist.add("Authorization");
        headerlist.add("service-token");
        headerlist.add("transaction-token");
        headers.setAccessControlAllowHeaders(headerlist);
        exposeList.add("Authorization");
        exposeList.add("service-token");
        exposeList.add("transaction-token");
        headers.setAccessControlExposeHeaders(exposeList);
        headers.set("Authorization", token);
        headers.set("service-token", serviceToken);
        headers.set("transaction-token", transactionToken);
        products = productCatalogProxyService.allProducts(token,transactionToken,serviceToken);
       
        return new ResponseEntity<List<Product>>(products, headers, HttpStatus.OK);
        
//        products = new ArrayList<Product>(); 
//        Product product = new Product();
//        product.setCode("10");
//        product.setName("Bath Soap");
//        products.add(product);
//        product = new Product();
//        product.setCode("20");
//        product.setName("Cosmetic");
//        products.add(product);
//        HttpHeaders reqHeaders = new HttpHeaders();
//        reqHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		reqHeaders.add("Auth_Header", "Basic from iGroceryApp" );
//
//		HttpEntity<String> request = new HttpEntity<String>(reqHeaders);
//       

	}
}
