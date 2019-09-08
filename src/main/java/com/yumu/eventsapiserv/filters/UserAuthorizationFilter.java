/*
 * Copyright (c) 2015, 2016, Smirva Systems Private Limited. All rights reserved.
 */
package com.yumu.eventsapiserv.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class UserAuthorizationFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
	
	@Autowired
	YumuUserDetailsService userService;

	
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		
		HttpServletRequest request = toHttpRequest(arg0);
        String userId = request.getHeader("X-YUMU-SECURITY-CONTEXT");
        
        UserDetails userDetails = userService.loadUserByUsername(userId);
        
        if(userDetails != null) {
        	UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        																	userDetails, 
        																	null, 
        																	userDetails.getAuthorities());
        	SecurityContextHolder.getContext().setAuthentication(authentication);
        }       
 
        arg2.doFilter(arg0, arg1);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	

	private HttpServletRequest toHttpRequest(ServletRequest request) {
        if (!(request instanceof HttpServletRequest)) {
            throw new RuntimeException("Invalid HTTP request");
        }
        return (HttpServletRequest) request;
    }
 

}
