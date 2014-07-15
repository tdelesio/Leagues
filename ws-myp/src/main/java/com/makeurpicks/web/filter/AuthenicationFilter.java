package com.makeurpicks.web.filter;

import info.makeyourpicks.service.PlayerManager;

import java.io.IOException;
import java.util.HashSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.util.CommonUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenicationFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	private PlayerManager playerManager;

	
	public void setPlayerManager(PlayerManager playerManager) {
		this.playerManager = playerManager;
	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;

		final String tgt = CommonUtils.safeGetParameter(request,"tgt");
		 if (CommonUtils.isNotBlank(tgt)) 
	     {
			Authentication authentication = playerManager.getAuthenicationTX(tgt);
			authentication.setAuthenticated(true);
			SecurityContextHolder.getContext().setAuthentication(authentication);
	     }
		 else
		 {
			 SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthentication());
		 }
		

		chain.doFilter(request, response);
	}
}
