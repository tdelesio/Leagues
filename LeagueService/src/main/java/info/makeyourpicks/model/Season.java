package info.makeyourpicks.model;

import com.delesio.model.AbstractPersistantObject;
import com.delesio.model.IPersistable;

public class Season extends AbstractPersistantObject
{

	private int startYear;
	private int endYear;
	private LeagueType leagueType;
	
	@Override
	public IPersistable createTestObject()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public int getStartYear()
	{
		return startYear;
	}

	public void setStartYear(int startYear)
	{
		this.startYear = startYear;
	}

	public int getEndYear()
	{
		return endYear;
	}

	public void setEndYear(int endYear)
	{
		this.endYear = endYear;
	}

	public LeagueType getLeagueType()
	{
		return leagueType;
	}

	public void setLeagueType(LeagueType leagueType)
	{
		this.leagueType = leagueType;
	}

	public String displaySeason()
	{
		return leagueType.getLeagueTypeDisplay()+" ("+startYear+"-"+endYear+")";
	}
}
