package info.makeyourpicks.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.delesio.model.AbstractPersistantObject;
import com.delesio.model.IPersistable;

public class LeagueType extends AbstractPersistantObject {
	private String typeOfLeague="";
	private String leagueTypeDisplay;
	private String parentTypeOfLeague;
	private Date start;
	private Date end;
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MM/dd/yyyy @ HH:mm z");
//	private Set teams=new HashSet();
//	private Set leagues=new HashSet();
//	private Map weeks = new HashMap();
	/**
	 * Copy Constructor
	 *
	 * @param leagueType a <code>LeagueType</code> object
	 */
	public LeagueType(LeagueType leagueType) 
	{
	    this.typeOfLeague = leagueType.typeOfLeague;
	    this.leagueTypeDisplay = leagueType.leagueTypeDisplay;
	    this.start = new Date(leagueType.start.getTime());
	    this.end = new Date(leagueType.end.getTime());
	    this.simpleDateFormat = leagueType.simpleDateFormat;
//	    this.teams = new HashSet(leagueType.teams);
//	    this.leagues = new HashSet(leagueType.leagues);
//	    this.weeks = new HashMap(leagueType.weeks);
	}



	public LeagueType()
	{
		
	}
	
	
	
	public LeagueType(String leagueType)
	{
		setTypeOfLeague(leagueType);
	}
	
	
	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}
	
	
	
	public String getLeagueTypeDisplay() {
		return leagueTypeDisplay;
	}

	public String getStartDisplay()
	{
		return simpleDateFormat.format(start);
	}
	
	public String getEndDisplay()
	{
		return simpleDateFormat.format(end);
	}
	
	public void setTypeOfLeague(String leagueType) {
		this.typeOfLeague = leagueType;
	}
	public void setLeagueTypeDisplay(String leagueTypeDisplay) {
		this.leagueTypeDisplay = leagueTypeDisplay;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public void setEnd(Date end) {
		this.end = end;
	}



	@Override
	public IPersistable createTestObject() {
		LeagueType leagueType = new LeagueType("leaguetypetest");
		leagueType.setEnd(new Date(System.currentTimeMillis()));
		leagueType.setStart(new Date(System.currentTimeMillis()));
		leagueType.setLeagueTypeDisplay("junit Test league");
		return leagueType;
	}

	public String getTypeOfLeague() {
		return typeOfLeague;
	}

	public String getDescriptionStatus()
	{
		if (typeOfLeague==null||typeOfLeague.equalsIgnoreCase(""))
		{
			return "default";
		}
		else
		{
			return typeOfLeague;
		}
	}

	public String getParentTypeOfLeague() {
		return parentTypeOfLeague;
	}



	public void setParentTypeOfLeague(String parentTypeOfLeague) {
		this.parentTypeOfLeague = parentTypeOfLeague;
	}
	
	
	public boolean hasParent()
	{
		if (parentTypeOfLeague==null||parentTypeOfLeague.equals(""))
		{
			return false;
		}
		else
		{
			return true;
		}
	}

}
