package info.makeyourpicks.model;

import com.delesio.model.AbstractPersistantObject;
import com.delesio.model.IPersistable;


public class Picks extends AbstractPersistantObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1009050700283777633L;
	
	private int weight=1;
	private Team team;
	private League league;
	private Player name;
	private Week week;
	private Game game;
	private boolean noPick=false;
	
	public static final int WINNER=1;
	public static final int LOSER=2;
	public static final int UNPLAYED=0;
	
	private String defaultPicksDisplay="-";
	private Game doublePick;
	
	public Picks()
	{
		
	}
	
	public Picks(Player _player, League _league, Week _week, Game game, boolean noPick)
	{
		this.name = _player;
		this.league = _league;
		this.week = _week;
		this.noPick = noPick;
		this.game = game;
	}
	
	public Team getTeam() {
		return team;
	}



	public void setTeam(Team team) {
		this.team = team;
	}

	public Player getName() {
		return name;
	}



	public void setName(Player name) {
		this.name = name;
	}
	
	public int getWinValue()
	{
		if (getWinnerStatus()==WINNER)
		{
			return weight;
		}
		else
		{
			return 0;
		}
		
	}
	
	public int getWinnerStatus() {
		
		if ((noPick&&week.hasCurrentWeekStarted()))
		{
			return LOSER;
		}
		if (game==null)
		{
			return UNPLAYED;
		}
		Team winner = game.getWinner(league.isSpreads()); 
		if (winner==null)
		{
			return UNPLAYED;
		}
		else if (winner.equals(team))
		{
			return WINNER;
		}
		else
		{
			return LOSER;
		}
	}
	
	public String getPickDisplay()
	{
		return getPickDisplay(false);
	}
	
	public String getPickDisplay(boolean useShort)
	{
		if (team!=null && game!=null && game.hasGameStarted())
		{
			if (useShort)
			{
				return team.getShortName();
			}
			else
			{
				return team.getFullTeamName();
			}
		}
		else
		{
			return defaultPicksDisplay;
		}
	}
	public String getWinnerStatusLabel()
	{
		int status = getWinnerStatus();
		if (status==WINNER)
		{
			return "Win";
		}
		else if (status == LOSER)
		{
			return "Lose";
		}
		else
		{
			return "";
		}
	}


	@Override
	public IPersistable createTestObject() {
		Picks picks = new Picks();
		return picks;
	}

	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Week getWeek() {
		return week;
	}

	public void setWeek(Week week) {
		this.week = week;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public boolean isNoPick() {
		return noPick;
	}

	public void setNoPick(boolean noPick) {
		this.noPick = noPick;
	}	
	
	public String getSpreadsLabel()
	{
		if (game==null||!league.isSpreads())
		{
			return "-";
		}
		else
		{
//			String prefix;
//			if (game.getDog().equals(team))
//			{
//				prefix="+";
//			}
//			else
//			{
//				prefix="-";
//			}
//			return prefix+String.valueOf(game.getSpread());
			return String.valueOf(game.getSpread());
		}
	}

	public void setDefaultPicksDisplay(String defaultPicksDisplay) {
		this.defaultPicksDisplay = defaultPicksDisplay;
	}

	public Game getDoublePick() {
		return doublePick;
	}

	public void setDoublePick(Game doublePick) {
		this.doublePick = doublePick;
	}

	public boolean hasGameStarted()
	{
		if (game==null)
		{
			return false;
		}
		else
		{
			return game.hasGameStarted();
		}
	}
	
	public void addWeight()
	{
		weight++;
	}
	
	public void subtractWeight()
	{
		weight--;
	}
	
	public boolean getFav()
	{
		if (team!=null&&team.equals(game.getFav()))
			return true;
		else
			return false;
	}
	
	public boolean getDog()
	{
		if (team!=null&&team.equals(game.getDog()))
			return true;
		else
			return false;
	}
	
	public void setDog(boolean value)
	{
		if (value)
			team = game.getDog();
	}
	
	public void setFav(boolean value)
	{
		if (value)
			team = game.getFav();
	}
}
