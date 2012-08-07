package com.delesio.protocol.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import com.delesio.model.AbstractCredentials;

/**
 * AbstractAuthenticatedWebSession.java
 * 
 * @version Mar 14, 2008
 * @author Anthony DePalma
 */

public abstract class AbstractAuthenticatedWebSession extends WebSession
{

	private AbstractCredentials credentials;

	/**
	 * Returns the current web session
	 * 
	 * @return AbstractAuthenticatedWebSession
	 */

	public static AbstractAuthenticatedWebSession get()
	{
		return (AbstractAuthenticatedWebSession) Session.get();
	}

	// constructor
	public AbstractAuthenticatedWebSession(Request request)
	{
		super(request);
		// bind to force this session into the session store
		bind();
	}

	/**
	 * 
	 * Initialize the session by attempting to auto authenticate the user
	 * through cookies
	 * @param request
	 * 
	 * @param response
	 * 
	 */
	public void init(Request request, Response response)
	{
		/*
		 * 
		 * Attempt to log in the user automatically through rememberMeServices
		 * 
		 */
		Authentication authentication = ((AbstractAuthenticatedWebApplication) getApplication()).getRememberMeServices().autoLogin(
				((ServletWebRequest) request).getHttpServletRequest(),
				((WebResponse) response).getHttpServletResponse());
		/*
		 * 
		 * If the authentication was made, store the principal in session
		 * 
		 */
		if (authentication != null)
		{
			credentials = (AbstractCredentials) authentication.getPrincipal();
			onSuccessfulAuthentication();
		}
	}

	/**
	 * 
	 * Determines whether a given user is signed into this session
	 * 
	 * @return true if the user is signed; false otherwise
	 * 
	 */
	public synchronized boolean isSignedIn()
	{
		return credentials != null;
	}

	/**
	 * 
	 * Attempts to authenticate a user that has provided the given username and
	 * password
	 * 
	 * @param username
	 *            current username
	 * 
	 * @param password
	 *            current password
	 * 
	 * @return <code>true</code> if authentication succeeds,
	 *         <code>false</code> otherwise
	 * 
	 */

	public synchronized boolean authenticate(final String username,
			final String password)
	{
		// create an Acegi authentication request
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

		// attempt authentication
		try
		{
			AuthenticationManager authenticationManager = ((AbstractAuthenticatedWebApplication) getApplication()).getAuthenticationManager();
			Authentication authResult = authenticationManager.authenticate(authRequest);
			credentials = (AbstractCredentials) authResult.getPrincipal();

			// hook into a successful authentication
			onSuccessfulAuthentication();
			return true;

		}

		catch (BadCredentialsException e)
		{
			credentials = null;
			return false;
		}
		catch (AuthenticationException e)
		{
			credentials = null;
			throw e;
		}
		catch (RuntimeException e)
		{
			credentials = null;
			throw e;
		}
	}

	/**
	 * 
	 * Hook into a successful authentication
	 */
	public synchronized void onSuccessfulAuthentication()
	{

	}

	/**
	 * 
	 * Returns the current user roles
	 * @return Roles
	 * 
	 */
	public synchronized Roles getRoles()
	{
		if (isSignedIn())
		{
			Roles roles = new Roles();
			// retrieve the granted authorities from the current authentication
			// these correspond one on one with user roles.
			GrantedAuthority[] authorities = credentials.getAuthorities();

			for (int i = 0; i < authorities.length; i++)
			{
				GrantedAuthority authority = authorities[i];
				roles.add(authority.getAuthority());
			}
			return roles;
		}
		return null;
	}

	/**
	 * 
	 * Returns the user's account verification id to verify new accounts
	 * @return String
	 * 
	 */
	// public synchronized String getAccountVerificationID() {
	//
	// return credentials.getUsername();
	//
	// }

	/**
	 * 
	 * Returns the user's credentials
	 * @return String
	 * 
	 */

	public synchronized AbstractCredentials getCredentials()
	{
		return credentials;
	}

	/**
	 * 
	 * Return the user's primary role
	 * @return String
	 * 
	 */

	public synchronized String getPrimaryRole()
	{
		return getRoles().iterator().next();
	}

	/**
	 * 
	 * Returns the currently logged in user's username
	 * @return String
	 * 
	 */
	public synchronized String getUsername()
	{
		// return credentials.getAlias().getUsername();
		return credentials.getUsername();
	}

	/**
	 * 
	 * Signout and invalidate the session
	 */
	public synchronized void signout()
	{
		credentials = null;
		invalidate();
	}

	/**
	 * 
	 * Signout and invalidate the session, as well as removes the remember me
	 * cookie
	 */
	public synchronized void signoutAndRemoveRememberMeCookie(HttpServletRequest request, HttpServletResponse response)
	{
		removeRememberMeCookie(request, response);
		signout();
	}

	/**
	 * 
	 * Removes the remember me cookie, and then signs out and invalidates the
	 * session
	 */
	public synchronized void removeRememberMeCookie(HttpServletRequest request,HttpServletResponse response)
	{
		((AbstractAuthenticatedWebApplication) getApplication()).getRememberMeServices().loginFail(request, response);
	}

	/**
	 * 
	 * Sets a remember me cookie
	 * @param request
	 * @param response
	 */
	public synchronized void storeRememberMeCookie(HttpServletRequest request,
			HttpServletResponse response)
	{
		((AbstractAuthenticatedWebApplication) getApplication()).getRememberMeServices().loginSuccess(request, response,credentials);
	}

	// getters and setters
	public void setCredentials(AbstractCredentials credentials)
	{
		this.credentials = credentials;
	}
}
