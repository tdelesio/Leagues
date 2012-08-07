package info.makeyourpicks.service;

import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.League;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Picks;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.SeasonStats;
import info.makeyourpicks.model.Team;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.model.WinSummary;

import java.util.List;

public interface PicksManager extends ICacheConstants {
	
	public void insertPlayerPick(Picks picks);
	public void updatePlayerPick(Picks picks,boolean forceUpdate);
	public void deletePicks(Picks picks);
	public List<Picks> getPicksByLeague(League league);
//	public List<Picks> getPicksByWeek(Week week);
	public List<Picks> getPicksByLeagueAndWeek(League league, Week week);
//	public List<Picks> getPicksByPlayer(Player player);
//	public List<Picks> getPicksByPlayerAndWeek(Player player, Week week);
	public List<Picks> getPicksByPlayerLeagueAndWeek(Player player, League league, Week week);
	public <T>Picks getPickByPlayerLeagueAndWeek(Player player, League league, Week week);
	public Picks getPickByPlayerLeagueWeekAndGame(Player player, League league, Week week, Game game);
	public Picks loadPicks(long pickId);
	public SeasonStats getPlayersSeasonStats(Player player, League league, Season season);
	public Picks getDoublePickForPlayerLeagueAndWeek(Player player, League league, Week week);
	public void setDoublePick(Player player, League league, Week week, Team team);
	public SeasonStats getPlayersStatsForWeek(Player player, League league, Week week);
	public int getWinningScoreForLeagueAndWeek(League league, Week week);
	public void clearPickCache();
	public Picks getPicksByPlayerLeagueWeekAndWeight(Player player, League league, Week week, int weight);
	public List<WinSummary> getWinSummary(League league);
	public List<Player> getPlayersForLeagueSortedByWeekWins(League league, Week week);
	public double percentChangeToWinForWeek(Player player, League league, Week week);
	public WinSummary getWinSummaryForPlayer(League league, Player player);
}
