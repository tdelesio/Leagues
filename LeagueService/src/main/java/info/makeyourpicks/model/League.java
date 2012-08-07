	package info.makeyourpicks.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import com.delesio.model.AbstractPersistantObject;
import com.delesio.model.IPersistable;



public class League extends AbstractPersistantObject implements Comparable<League>
{
	private static final long serialVersionUID = 8705798059125283743L;
	private String leagueName;
	private int maxSize=50;
	private int paidFor=0;
	private boolean money=false;
	private boolean free;
	private boolean active;
	private String password;
	private String repassword;
	private String playersToAddToLeague;
	private boolean spreads=true;
	
	//redesign parms
	private boolean doubleEnabled=true;
	private double entryFee;
	private double weeklyFee;
	private int firstPlacePercent;
	private int secondPlacePercent;
	private int thirdPlacePercent;
	private int fourthPlacePercent;
	private int fifthPlacePercent;
	private int doubleType;
	private boolean banker;
	
	private YesNoEnum bankerEnum;
	private YesNoEnum doubleEnabledEnum;
	private YesNoEnum spreadsEnum;
	private PickemTieBreakerEnum doubleTypeEnum;

	private Season season;
//	private LeagueType leagueType = new LeagueType();
//	private Set players=new HashSet();
//	private Set picks=new HashSet();
//	private Set payments = new HashSet();
	private Player admin = new Player();
	
	/**
	 * Copy Constructor
	 *
	 * @param league a <code>League</code> object
	 */
	public League(League league) 
	{
	    this.leagueName = league.leagueName;
	    this.admin = league.admin;
	    this.maxSize = league.maxSize;
	    this.paidFor = league.paidFor;
	    this.money = league.money;
	    this.free = league.free;
	    this.active = league.active;
	    this.password = league.password;
	    this.repassword = league.repassword;
//	    this.players = new HashSet();
	    this.playersToAddToLeague = league.playersToAddToLeague;
	}

	
	public PickemTieBreakerEnum getDoubleTypeEnum() {
		return doubleTypeEnum;
	}


	public void setDoubleTypeEnum(PickemTieBreakerEnum doubleTypeEnum) {
		this.doubleTypeEnum = doubleTypeEnum;
	}


	public YesNoEnum getDoubleEnabledEnum() {
		return doubleEnabledEnum;
	}



	public void setDoubleEnabledEnum(YesNoEnum doubleEnabledEnum) {
		this.doubleEnabledEnum = doubleEnabledEnum;
	}



	public YesNoEnum getSpreadsEnum() {
		return spreadsEnum;
	}



	public void setSpreadsEnum(YesNoEnum spreadsEnum) {
		this.spreadsEnum = spreadsEnum;
	}



	public YesNoEnum getBankerEnum() {
		return bankerEnum;
	}



	public void setBankerEnum(YesNoEnum bankerEnum) {
		this.bankerEnum = bankerEnum;
	}



	public boolean isBanker() {
		return banker;
	}



	public void setBanker(boolean banker) {
		this.banker = banker;
	}

//	public LeagueType getLeagueType() {
//		return leagueType;
//	}
//
//
//
//	public void setLeagueType(LeagueType leagueType) {
//		this.leagueType = leagueType;
//	}



	public boolean isDoubleEnabled() {
		return doubleEnabled;
	}



	public void setDoubleEnabled(boolean doubleEnabled) {
		this.doubleEnabled = doubleEnabled;
	}



	public double getEntryFee() {
		return entryFee;
	}



	public void setEntryFee(double entryFee) {
		this.entryFee = entryFee;
	}



	public double getWeeklyFee() {
		return weeklyFee;
	}



	public void setWeeklyFee(double weeklyFee) {
		this.weeklyFee = weeklyFee;
	}



	public int getFirstPlacePercent() {
		return firstPlacePercent;
	}



	public void setFirstPlacePercent(int firstPlacePercent) {
		this.firstPlacePercent = firstPlacePercent;
	}



	public int getSecondPlacePercent() {
		return secondPlacePercent;
	}



	public void setSecondPlacePercent(int secondPlacePercent) {
		this.secondPlacePercent = secondPlacePercent;
	}



	public int getThirdPlacePercent() {
		return thirdPlacePercent;
	}



	public void setThirdPlacePercent(int thirdPlacePercent) {
		this.thirdPlacePercent = thirdPlacePercent;
	}



	public int getFourthPlacePercent() {
		return fourthPlacePercent;
	}



	public void setFourthPlacePercent(int fourthPlacePercent) {
		this.fourthPlacePercent = fourthPlacePercent;
	}



	public int getFifthPlacePercent() {
		return fifthPlacePercent;
	}



	public void setFifthPlacePercent(int fifthPlacePercent) {
		this.fifthPlacePercent = fifthPlacePercent;
	}



	public int getDoubleType() {
		return doubleType;
	}



	public void setDoubleType(int doubleType) {
		this.doubleType = doubleType;
	}



	public League()
	{
		
	}

	

	public int compareTo(League arg0) {
		return arg0.getLeagueName().compareTo(leagueName);
	}

	public String getLeagueName() {
		return leagueName;
	}


	
	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}


	public boolean isMoney() {
		return money;
	}

	public void setMoney(boolean money) {
		this.money = money;
	}
	
//	public String getLeaugeBaseURL(String webContext)
//	{
//		return "/"+webContext+"/secure/"+leagueType+"/home.html?league="+leagueName+"&leagueType="+childLeagueType;
//	}

//	@Transient
//	public Class getBasePage()
//	{
//		return (Class)homePageMap.get(leagueType);
//	}
//
//	public String getName() {
//		return playerName;
//	}
//
//
//	public void setName(String name) {
//		this.playerName = name;
//	}


	public boolean isFree() {
		return free;
	}


	public void setFree(boolean free) {
		this.free = free;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}
	

	public Player getAdmin() {
		return admin;
	}

	public void setAdmin(Player admin) {
		this.admin = admin;
	}

	
	@Override
	public IPersistable createTestObject() {
		League league = new League();
		league.setLeagueName("jleague");
		//league.setLeagueType((LeagueType)(new LeagueType()).createTestObject());
		league.setActive(true);
		league.setFree(true);
		
		return league;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getRepassword() {
		return repassword;
	}


	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}


	public String getPlayersToAddToLeague() {
		return playersToAddToLeague;
	}


	public void setPlayersToAddToLeague(String playersToAddToLeague) {
		this.playersToAddToLeague = playersToAddToLeague;
	}


	public List<String> getInvitedPlayersEmailAddress()
	{
		List<String> emails = new ArrayList<String>();
		
		if (playersToAddToLeague==null)
		{
			return null;
		}
		StringTokenizer tokenizer = new StringTokenizer(playersToAddToLeague, ",");
		String emailAddress;
		
		
		while (tokenizer.hasMoreElements())
		{
			emailAddress =(String)tokenizer.nextElement();
			emailAddress = emailAddress.replaceAll("^\\s+", "");
			emails.add(emailAddress.trim());
		}
		
		return emails;
	}


	public int getMaxSize() {
		return maxSize;
	}


	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}


	public int getPaidFor() {
		return paidFor;
	}


	public void setPaidFor(int paidFor) {
		this.paidFor = paidFor;
	}
	
	public boolean isLeagueFull(int playersInLeauge)
	{
		if (playersInLeauge!=0 && (playersInLeauge == maxSize|| playersInLeauge == paidFor))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void generateFreeLeagueName()
	{
		Random random = new Random(System.currentTimeMillis());
		
//		leagueName= leagueType.getLeagueTypeDisplay()+random.nextInt(99999);
		leagueName= season.getLeagueType().getLeagueTypeDisplay()+random.nextInt(99999);
	}

	public boolean createNewFreeLeague(int playersInLeague)
	{
		if (playersInLeague == maxSize)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
//	public synchronized void paid()
//	{
//		paidFor = players.size();
//	}



	public boolean isSpreads() {
		return spreads;
	}



	public void setSpreads(boolean spreads) {
		this.spreads = spreads;
	}



	public Season getSeason()
	{
		return season;
	}



	public void setSeason(Season season)
	{
		this.season = season;
	}

	public String getDescriptionStatus()
	{
		if (season==null||season.getLeagueType()==null)
		{
			return "default";
		}
		else
		{
			return season.getLeagueType().getDescriptionStatus();
		}
	}
	
	public boolean doesPercentEqual100()
	{
		if (firstPlacePercent+secondPlacePercent+thirdPlacePercent+fourthPlacePercent+fifthPlacePercent == 100)
			return true;
		else
			return false;
	}

	public void convertEnums()
	{
		banker = bankerEnum.getValue();
		doubleEnabled = doubleEnabledEnum.getValue();
		spreads = spreadsEnum.getValue();
		doubleType = doubleTypeEnum.getType();
	}
	
	public int getHighestPayingSpot()
	{
		if (banker)
		{
			if (fifthPlacePercent>0)
				return 5;
			else if (fourthPlacePercent>0)
				return 4;
			else if (thirdPlacePercent>0)
				return 3;
			else if (secondPlacePercent>0)
				return 2;
			else
				return 1;
		}
		else
		{
			return 0;
		}
	}
//	public Set getPlayers() {
//		return players;
//	}
//
//
//
//	public void setPlayers(Set players) {
//		this.players = players;
//	}
	
}
