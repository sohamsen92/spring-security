package com.eazybytes.filter;

import java.io.IOException;
import java.util.logging.*;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;





public class AuthoritiesLoggingAfterFilter implements Filter {
	
	private final Logger log = Logger.getLogger(AuthoritiesLoggingAfterFilter.class.getName());
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null) {
			log.info("Use name : "+ authentication.getName()+" is successfully authenticated and has authorities "+ authentication.getAuthorities().toString());
		}
		chain.doFilter(request, response);
	}

}
