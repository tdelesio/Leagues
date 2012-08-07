package info.makeyourpicks.dao;

import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.Team;
import info.makeyourpicks.model.Week;

import java.util.Date;
import java.util.List;

public interface GameDao {

	public Week findWeek(int weekNumber, Season season);
	public int getCurrentWeek(Season season);
	public List<Week> findWeekBySeason(Season season);
	public List<Game> findGamesByWeek(Week week);
	public Game findGameByTeamWeekLeague(Team team, Week week);
	public Game findGamesByCityNames(String favCityName, String dogCityName, Date gameStart);
	public Game findGamesByTeams(Team fav, Team dog, Date gameStart);
}
