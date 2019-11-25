package com.igrocery.api.security;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import com.igrocery.api.exception.CustomException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import io.jsonwebtoken.JwtException;

public class AuthHeaderFilter extends ZuulFilter {
	
	private JwtTokenProvider jwtTokenProvider;

    public AuthHeaderFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String filterType() {
        return PRE_TYPE;
    }
 
    @Override
    public int filterOrder() {
        return 0;
    }
 
    @Override
    public boolean shouldFilter() {
        return true;
    }
 
    @Override
    public Object run() throws  CustomException, ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        String token = jwtTokenProvider.resolveToken(request);
        
        if (token != null) {
//            if (/*!jwtTokenProvider.isTokenPresentInDB(token)*/false) {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid JWT token");
//                throw new CustomException("Invalid JWT token",HttpStatus.UNAUTHORIZED);
//            }
            try {
            	System.out.println("Before validating token");
                jwtTokenProvider.validateToken(token) ;
                System.out.println("after validating token");
            } catch (JwtException | IllegalArgumentException e) {
//                try {
//					response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid JWT token");
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
            	System.out.println("Invalid Token found");
                throw  new CustomException("Invalid from custom JWT token",HttpStatus.UNAUTHORIZED);
            }
          //***  Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
            //setting auth in the context.
           //*** SecurityContextHolder.getContext().setAuthentication(auth);
        }
        System.out.println("HEYYYYYYYYYYYYYYYYYYYY!!!!");
        if (request.getAttribute("AUTH_HEADER") == null) {
            //generate or get AUTH_TOKEN, ex from Spring Session repository
            
            ctx.addZuulRequestHeader("AUTH_HEADER", token);
        }
        return null;
    }
}
