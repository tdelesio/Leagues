package info.makeyourpicks.model;

import com.delesio.model.AbstractUser;
import com.delesio.model.IPersistable;

public class Player extends AbstractUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6150998743191945581L;
	
	public static final int ADMIN = 1;
	public static final int LEAGUE_ADMIN = 2;
	public static final int USER = 3;
	
	private String username;
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
	public Player()
	{
	
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
	@Override
	public IPersistable createTestObject() {
		Player playerInfo = new Player("jtest");
//		playerInfo.setEmail("test@delesio.com");
		playerInfo.setAddress1("address1");
		playerInfo.setCity("denville");
		playerInfo.setState("NJ");
		playerInfo.setZip("12345");
//		playerInfo.setPlayingSuicide(false);
//		playerInfo.setPassword("password");
//		playerInfo.setFirstName("jtest");
//		playerInfo.setLastName("jtest");
		return playerInfo;
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

	@Override
	public String getFullName()
	{
		if (firstName==null || lastName == null)
		{
			return username;
		}
		else
		{
			return super.getFullName();
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
}
