package info.makeyourpicks.service.impl;

import info.makeyourpicks.dao.TeamDao;
import info.makeyourpicks.model.League;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Team;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.service.TeamManager;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public class TeamManagerHibernate extends AbstractLeagueService implements
		TeamManager {

	protected TeamDao teamDao;

	public void deleteTeam(Team team){
		dao.deleteObject(team);
	}

	public void insertTeam(Team team) {
		dao.save(team);
	}

	public TeamDao getTeamDao() {
		return teamDao;
	}

	public void setTeamDao(TeamDao teamDao) {
		this.teamDao = teamDao;
	}

	public Team loadTeam(long teamId)
	{
		return (Team)dao.loadObject(Team.class, teamId);
	}

	public List<Team> loadAllTeams() {
		return teamDao.loadAllTeamsOrderByCity();
	}
	
	@Transactional
	public List<Team> getTeamsByLeagueTypeTX(LeagueType leagueType)
	{
		return getTeamsByLeagueType(leagueType);
	}
	
	public List<Team> getTeamsByLeagueType(LeagueType leagueType)
	{
		return teamDao.getTeamsByLeagueType(leagueType);
	}
	
	public List<Team> getUnenteredTeamsForWeek(Week week)
	{
		List<Team> teams = getTeamsByLeagueType(week.getSeason().getLeagueType());
		teams.removeAll(teamDao.findTeamsPlayingInWeek(week));
		return teams;
	}
	
	public List<Team> getUnpickedTeams(Player player, Week week, League league)
	{
		List<Team> teams = teamDao.findTeamsPlayingInWeek(week);
		teams.removeAll(getPickedTeamsForPlayerAndLeague(player, league));
		return teams;
	}
	
	public List<Team> getPickedTeamsForPlayerAndLeague(Player player, League league)
	{
		return teamDao.findPickedTeamsForPlayerAndLeague(player, league);
	}
	
	public List<Team> getPickedTeamsForPlayerWeekAndLeague(Player player, Week week, League league)
	{
		return teamDao.findPickedTeamsForPlayerWeekAndLeague(player,week, league);
	}
	

	public List<Team> getPickedDoubleTeamsForPlayerWeekAndLeague(Player player, Week week, League league)
	{
		return teamDao.findPickedDoubleTeamsForPlayerWeekAndLeague(player, week, league);
	}
	
}
