package info.makeyourpicks.model;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.delesio.model.AbstractSequenceModel;


public class Player extends AbstractPersistantObject implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6150998743191945581L;
	
	public static final int ADMIN = 1;
	public static final int LEAGUE_ADMIN = 2;
	public static final int USER = 3;
	
	private String username;
	private String firstName;
	private String lastName;
	private String address1;
	private String city;
	private String state;
	private String zip;
	private boolean verified;	
	private int retryAttempts;
	private boolean unreadMail=false;
	private boolean rememberMe;
	private long facebookId;

	
	private String emailAddresses;
	private String emailText;
	
	private int wins;
	
	private String email;
	
	protected String password;
	protected String repassword;
	protected boolean enabled=true;
	protected boolean locked=false;
	protected boolean expired=false;
	private int memberLevel = USER;
	private boolean authenticated;
	
	
	public Player()
	{
	
	}

	public Player(long id)
	{
		super.id = id;
	}
	
	public Player(String name)
	{
		setUsername(name);
	}
	
	
	
	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public Object getPrincipal() {
		return getUsername();	
	}
	
	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}
	public int getRetryAttempts() {
		return retryAttempts;
	}

	public void setRetryAttempts(int retryAttempts) {
		this.retryAttempts = retryAttempts;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	

	public boolean isUnreadMail() {
		return unreadMail;
	}

	public void setUnreadMail(boolean unreadMail) {
		this.unreadMail = unreadMail;
	}

	public boolean isRememberMe()
	{
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe)
	{
		this.rememberMe = rememberMe;
	}

	public String getFullName()
	{
		if (firstName==null || lastName == null)
		{
			return username;
		}
		else
		{
			return firstName+" "+lastName;
		}
	}
	
	public void setLocked(boolean locked)
	{
		this.locked = locked;
	}
	
	public boolean isLocked()
	{
		return locked;
	}

	public long getFacebookId()
	{
		return facebookId;
	}

	public void setFacebookId(long facebookId)
	{
		this.facebookId = facebookId;
	}

	public String getEmailAddresses() {
		return emailAddresses;
	}

	public void setEmailAddresses(String emailAddresses) {
		this.emailAddresses = emailAddresses;
	}

	public String getEmailText() {
		return emailText;
	}

	public void setEmailText(String emailText) {
		this.emailText = emailText;
	}
	
	public boolean isAdmin()
	{
		if (getMemberLevel() == ADMIN)
			return true;
		else
			return false;
	}
	
	public boolean isLeagueAdmin(League league)
	{
		if (getMemberLevel() <= LEAGUE_ADMIN && league.getAdmin()==this)
			return true;
		else
			return false;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(int memberLevel) {
		this.memberLevel = memberLevel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
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
		

	public Collection<GrantedAuthority> getAuthorities() {
//		GrantedAuthority[] authority = new GrantedAuthority[1];
		GrantedAuthority authority;
		final String roleName = getRoleName();

		
//		authority[0] = new GrantedAuthority() 
		authority = new GrantedAuthority() 
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
			
//		return authority;
		
		HashSet<GrantedAuthority> authorities =  new HashSet<GrantedAuthority>();
		authorities.add(authority);
		return authorities;
		
	}

	public Object getCredentials() {
		return getPassword();			
	}
	
	public Object getDetails() {
		return null;	
	}
	


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




	public boolean isAuthenticated() {
		return authenticated;
	}
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
	
	
	
}
