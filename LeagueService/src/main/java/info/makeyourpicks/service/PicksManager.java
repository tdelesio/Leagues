package info.makeyourpicks.service;

import info.makeyourpicks.ValidationErrorEnum;
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

import org.springframework.transaction.annotation.Transactional;

import com.delesio.exception.ValidationException;

public interface PicksManager extends ICacheConstants {
	
	public void insertPlayerPickTX(Picks pick) throws ValidationException;
	public Picks updatePlayerPickTX(Picks pick, long loggedInPlayerId) throws ValidationException;
	
	
	public void updatePlayerPick(Picks picks,boolean forceUpdate);
	public void deletePicks(Picks picks);
	public List<Picks> getPicksByLeague(League league);
//	public List<Picks> getPicksByWeek(Week week);
	public List<Picks> getPicksByLeagueAndWeekTX(League league, Week week);
//	public List<Picks> getPicksByPlayer(Player player);
//	public List<Picks> getPicksByPlayerAndWeek(Player player, Week week);
	public List<Picks> getPicksByPlayerLeagueAndWeekTX(Player player, League league, Week week);
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
	public List<WinSummary> getWinSummaryTX(League league);
	public List<Player> getPlayersForLeagueSortedByWeekWins(League league, Week week);
	public double percentChangeToWinForWeek(Player player, League league, Week week);
	public WinSummary getWinSummaryForPlayer(League league, Player player);
	
	public Picks insertPlayerPickTX(Picks pick, long playerId) throws ValidationException;
}
