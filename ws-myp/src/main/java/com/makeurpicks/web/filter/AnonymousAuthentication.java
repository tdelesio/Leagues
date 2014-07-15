package com.makeurpicks.web.filter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class AnonymousAuthentication extends AnonymousAuthenticationToken {

	private static Set<GrantedAuthority>authorities = new HashSet<GrantedAuthority>();
	static {
		GrantedAuthority userAuthority = new GrantedAuthority(){

			@Override
			public String getAuthority() {
				return "anonymous";
			}
			
		};
		authorities.add(userAuthority);
	
	};
	public AnonymousAuthentication() {
		
		super("anonymous", "anonymous", authorities);
	
		
	}

}
