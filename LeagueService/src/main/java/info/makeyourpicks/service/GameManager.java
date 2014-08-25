package info.makeyourpicks.service;

import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.Team;
import info.makeyourpicks.model.Week;

import java.util.List;

import com.delesio.exception.ValidationException;

public interface GameManager extends ICacheConstants {

	
	public void deleteGame(Game game);
	public void updateGame(Game game);
	public void insertWeekTX(Week week);
	public void deleteWeek(Week week);
	public void updateWeek(Week week);
	public Week loadWeek(long weekId);
	public Game loadGame(long gameId);
	public int getCurrentWeek(Season season);
//	public Week getWeek(int weekNumber, Season season);
	public List<Week> getWeeksBySeasonTX(Season season);
	public List<Week> getWeeksBySeason(Season season);
	public List<Game> getGamesByWeekTX(Week week);
	public Game getGameByTeamWeekLeague(Team team, Week week);
	public boolean hasAllGamesForWeekStarted(Week week);
	public void executeGameScoreParsing();
	public void createUpdateGame(Game game);
	public void reloadGameTriggers(Week week);
//	public void weekSmartSetup();
//	/public List<Team> findTeamsPlayingInWeek(Week week);
	
	public void insertGameTX(Game game) throws ValidationException;
	public void updateScore(Game game) throws ValidationException;
}
