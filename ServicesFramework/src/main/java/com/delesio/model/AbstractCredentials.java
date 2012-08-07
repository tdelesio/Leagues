package com.delesio.model;

import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

public abstract class AbstractCredentials extends AbstractPersistantObject implements UserDetails, Authentication
{

	protected String password;
	protected String repassword;
	protected boolean enabled=true;
	protected boolean locked=false;
	protected boolean expired=false;
	private int memberLevel = USER;
	private boolean authenticated;
	
	public static final int ADMIN = 1;
	public static final int USER = 3;
	
	protected String getRoleName() 
	{
		if (memberLevel == ADMIN)
		{
			return "admin";
		}
		else
		{
			return "user";
		}
	}
		
	final public GrantedAuthority[] getAuthorities() {
		GrantedAuthority[] authority = new GrantedAuthority[1];
		final String roleName = getRoleName();
		
//		if (getMemberLevel().equals(ADMIN))
//		{
//			roleName = "admin";
//		}
//		else
//		{
//			roleName = "user";
//		}
		
		authority[0] = new GrantedAuthority() 
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 4567840750031844122L;

			public String getAuthority() 
			{
				return roleName;
			}

			public int compareTo(Object o)
			{
				// TODO Auto-generated method stub
				return 0;
			}
			
			
		};
			
		return authority;
	}

	public Object getCredentials() {
		return getPassword();			
	}
	
	public Object getDetails() {
		return null;	
	}
	
	public String getPassword()
	{
		return password;
	}

	abstract public String getUsername();

	public boolean isAccountNonExpired()
	{
		return !expired;
	}

	public boolean isAccountNonLocked()
	{
		return !locked;
	}

	public String getName() {
		return getClass().getSimpleName();	
	}
	
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public String getRepassword()
	{
		return repassword;
	}

	public void setRepassword(String repassword)
	{
		this.repassword = repassword;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public int getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(int memberLevel) {
		this.memberLevel = memberLevel;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
}
