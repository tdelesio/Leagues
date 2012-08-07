package com.delesio.web.model.session;

import org.apache.wicket.Request;
import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.AuthenticationServiceException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

@Deprecated
public abstract class AbstractAuthenticatedWebSession extends AuthenticatedWebSession {

	
	// constructor
	public AbstractAuthenticatedWebSession(final AuthenticatedWebApplication application, Request request) {
//        super(application, request);
		super(request);
    }
	
	public AbstractAuthenticatedWebSession(Request request)
	{
		super(request);
	}

	/**
	 * Returns the application AuthenticationManager
	 * 
	 * @return AuthenticationManager
	 */
	public abstract AuthenticationManager getAuthenticationManager();
	
	/**
	 * Hook into a successful authentication
	 * 
	 */
	public void onSuccessfulAuthentication() { }
	
    /**
     * Attempts to authenticate a user that has provided the given username and password
     * 
     * @param username current username
     * @param password current password
     * @return <code>true</code> if authentication succeeds, <code>false</code> otherwise
     */
    public boolean authenticate(String username, String password) {
        String u = username == null ? "" : username;
        String p = password == null ? "" : password;

        // create an Acegi authentication request
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(u, p);

        // attempt authentication
        try
        {
            AuthenticationManager authenticationManager = getAuthenticationManager();
            Authentication authResult = authenticationManager.authenticate(authRequest);
           setAuthentication(authResult);

            // hook into a successful authentication
            onSuccessfulAuthentication();
            
            return true;

        }
        catch (AuthenticationServiceException authenticationServiceException)
        {
        	setAuthentication(null);
        	return false;
        }
        catch (BadCredentialsException e)
        {
        	setAuthentication(null);
            return false;

        }
        catch (AuthenticationException e)
        {
            setAuthentication(null);
            throw e;
        }
        catch (RuntimeException e)
        {
            setAuthentication(null);
            throw e;
        }
    }

    /**
     * Returns the currently logged in principal, or null when no user is logged in
     * 
     * @return Object
     */
    public Object getPrincipal() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null ? null : authentication.getPrincipal();
    }

    /**
     * Returns the current user roles
     * 
     * @return Roles
     */
    public Roles getRoles() {
        if (isSignedIn())
        {
            Roles roles = new Roles();
            
            // retrieve the granted authorities from the current authentication
            // these correspond one on one with user roles.
            GrantedAuthority[] authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            
            for (int i = 0; i < authorities.length; i++) {
                GrantedAuthority authority = authorities[i];
                roles.add(authority.getAuthority());
            }
            
            return roles;
        }
        return null;
    }   

    /**
     * Signout and invalidates the session, afterwards redirect to the home page
     * 
     */
    public void signout() {
        setAuthentication(null);
        invalidate();
    }

    /**
     * Sets the acegi authentication
     * 
     * @param Authentication or null to clear
     */
    private void setAuthentication(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
