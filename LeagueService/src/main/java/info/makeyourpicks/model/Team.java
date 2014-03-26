package info.makeyourpicks.model;

import java.io.Serializable;

import com.delesio.model.AbstractSequenceModel;

public class Team extends AbstractPersistantObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6156350856289377375L;
	private String teamName;
	private String city;
	private String shortName;
	private String theme;
	private String feedName;
	private LeagueType leagueType=new LeagueType();
	
	public Team()
	{
		
	}
	
	public Team(long teamId)
	{
		super.id = teamId;
	}
	
	public Team(Team teamInfo)
	{
		this.teamName = teamInfo.getTeamName();
		this.city = teamInfo.getCity();
		this.shortName = teamInfo.getShortName();
		this.theme = teamInfo.getTheme();
	}
	
	

	public String getFeedName() {
		return feedName;
	}

	public void setFeedName(String feedName) {
		this.feedName = feedName;
	}

	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	public String getFullTeamName()
	{
		return city +" "+teamName;
	}
	
	
	
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}

	
	public LeagueType getLeagueType() {
		return leagueType;
	}

	public void setLeagueType(LeagueType leagueType) {
		this.leagueType = leagueType;
	}

}
