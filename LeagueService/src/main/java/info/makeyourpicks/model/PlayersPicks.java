package info.makeyourpicks.model;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class PlayersPicks  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3581999480612066279L;
	private List<Picks> playersPicks = new ArrayList<Picks>(16);
	//private int playersDouble;
	private int playersDoubleIndex=-1;
	private boolean insert=true;
	//private DoublePick doubleInfo;
	private String name;
	private int wins=0;
	
	public PlayersPicks()
	{
		//doubleInfo = new DoublePick();
	}
	
	public boolean hasPicks()
	{
		if (playersPicks==null || playersPicks.size()==0 || playersPicks.isEmpty())
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getWins() {
		return wins;
	}


	public void setWins(int wins) {
		this.wins = wins;
	}


	public void addPick(Picks picksInfo)
	{
		playersPicks.add(picksInfo);
	}
	
	
	public void addAllPicks(List<Picks> picks)
	{
		playersPicks.addAll(picks);
	}
	
	

	public List<Picks> getPlayersPicks() {
		return playersPicks;
	}

	public void setPlayersPicks(List<Picks> playersPicks) {
		this.playersPicks = playersPicks;
	}


	public int getPlayersDoubleIndex() {
		return playersDoubleIndex;
	}




	public void setPlayersDoubleIndex(int playersDoubleIndex) {
		this.playersDoubleIndex = playersDoubleIndex;
		
//		PicksInfo picksInfo = playersPicks.get(playersDoubleIndex);
//		doubleInfo.setName(picksInfo.getName());
//		doubleInfo.setTeam(picksInfo.getTeam());
//		doubleInfo.setWeek(picksInfo.getWeek());
	}


	public boolean isInsert() {
		return insert;
	}


	public void setInsert(boolean insert) {
		this.insert = insert;
	}


//	public DoublePick getDoubleInfo() {
//		return doubleInfo;
//	}
//
//
//	public void setDoubleInfo(DoublePick doubleInfo) {
//		this.doubleInfo = doubleInfo;
//	}
	
	public void loadNoPick(int week)
	{
		List<Picks> picks = new ArrayList<Picks>();
		Team teamInfo;
		for (int i=0;i<16;i++)
		{
			teamInfo = new Team();
			teamInfo.setShortName("---");
			teamInfo.setCity("NO PICK");
			//picksInfo = new Picks(name, week, i+1, -1, teamInfo);
			
		//	picksInfo.setTeamInfo(teamInfo);
			//picks.add(picksInfo);
		}
		
		setPlayersPicks(picks);
	}

	
	public String getInsert()
	{
		return String.valueOf(insert);
	}

}
