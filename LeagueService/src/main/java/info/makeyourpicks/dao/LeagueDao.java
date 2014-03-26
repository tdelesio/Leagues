package info.makeyourpicks.dao;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.PlayerLeague;
import info.makeyourpicks.model.Season;

import java.util.List;

public interface LeagueDao {
	public List findFreeLeagues();
	public League findLeague(String leagueName);
	public LeagueType findLeagueType(String leagueType);
	public List findMasterLeagueTypes();
//	public List<League> findLeaguesByLeagueType(LeagueType leagueType);
	public List<League> findLeaguesBySeason(Season season);
	public List<League> findLeaguesByPlayer(Player player);
	public void removePlayerFromLeague(League league, Player player);
	public int findNumberOfPlayersInLeague(League league);
	public LeagueType getLeagueTypeForSeason(Season season);
	public Season getSeason(LeagueType leagueType, int startYear ,int endYear);
	public Season getSeason(String typeOfLeague, int startYear ,int endYear);
	public List<Season> getParentSeasons();
	public List<League> findLeaguesByPlayer(Player player, boolean filterByDisplay);
	public List<PlayerLeague> findPlayerLeaguesByPlayer(Player player);
	public List<Season> getCurrentSeasons();
	public List<LeagueType> getLeagueTypesByParent(String parentLeagueType);
	
	public PlayerLeague findPlayerLeagueByLeagueAndPlayer(long leagueId, long playerId);
}
