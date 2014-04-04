package info.makeyourpicks.service;

import java.util.List;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Team;
import info.makeyourpicks.model.Week;

public interface TeamManager {

	/**
	 * This will add new teams to the league type
	 * @param teamInfo
	 * @param leagueType
	 * @throws SystemException
	 */
	public void insertTeam(Team teamInfo);
		
	/**
	 * Delete team.
	 * @param teamId
	 * @param leagueType
	 * @throws SystemException
	 */
	public void deleteTeam(Team team);
	
	public Team loadTeam(long teamId);
	
	public List<Team> loadAllTeams();
	public List<Team> getTeamsByLeagueTypeTX(LeagueType leagueType);
	public List<Team> getUnenteredTeamsForWeek(Week week);
	public List<Team> getUnpickedTeams(Player player, Week week, League league);
	public List<Team> getPickedTeamsForPlayerAndLeague(Player player, League league);
	public List<Team> getPickedTeamsForPlayerWeekAndLeague(Player player, Week week, League league);
	public List<Team> getPickedDoubleTeamsForPlayerWeekAndLeague(Player player, Week week, League league);
}
