package info.makeyourpicks.dao;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Team;
import info.makeyourpicks.model.Week;

import java.util.List;


public interface TeamDao {

	public List<Team> getTeamsByLeagueType(LeagueType leagueType);
	public List<Team> loadAllTeamsOrderByCity();
	public List<Team> findTeamsPlayingInWeek(Week week);
	public List<Team> findPickedTeamsForPlayerAndLeague(Player player,League league);
	public List<Team> findPickedTeamsForPlayerWeekAndLeague(Player player,Week week, League league);
	public List<Team> findPickedDoubleTeamsForPlayerWeekAndLeague(Player player,Week week, League league);
	public Team findTeamByCity(String city);
	public Team findTeamByFeedName(String feedName);
}
