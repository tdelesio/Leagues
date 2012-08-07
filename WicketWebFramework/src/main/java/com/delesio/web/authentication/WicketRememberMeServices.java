package com.delesio.web.authentication;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.ui.rememberme.TokenBasedRememberMeServices;

public class WicketRememberMeServices extends TokenBasedRememberMeServices
{
	@Override
	protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
		return true;
	}
}
