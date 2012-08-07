package info.makeyourpicks.service;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Payment;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.PlayerLeague;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.Week;

import java.util.List;


public interface LeagueManager extends ICacheConstants{

	/**
	 * This method create a leauge.  In order to successfully join a league you
	 * need the league name and the league type.  This will also add the player to the league via
	 * the joinLeagueMethod
	 * @param leagueInfo
	 * @throws SystemException
	 */
//	public void createLeague(League leagueInfo);
	public void createUpdateSeason(Season season);
	/**
	 * This method gets all the league types that a user can join.
	 * @return List<LeagueInfo>
	 * @throws SystemException
	 */
	public List<LeagueType> getLeagueTypes();

	/**
	 * This method verifies that a selected league is valid.  It is used when a user tries to join a league.
	 * In order for them to join they need to have selected a valid one.  The league name and the league type must
	 * both be valid
	 * @param leagueInfo
	 * @return boolean
	 */
	public boolean validateLeague(League leagueInfo);
	
	/**
	 * This deletes the league
	 * @param leagueInfo
	 * @throws SystemException
	 */
	public void deleteLeague(League leagueInfo);
//	public void sendMimeEmail(ContactInfo contactInfo);
	public List<League> getFreeLeagues();
	public List<League> getLeagues();
//	public void updateLeague(League leagueInfo);
	public void createOrUpdateLeague(League league);
	public void createOrUpdateLeagueType(LeagueType leagueType);
	public void deleteLeagueType(LeagueType leagueType);
	public LeagueType getLeagueType(String _leagueType);
	public League getLeague(String leagueName);
	public void addPlayerToLeague(League league, Player player);
	public void addPlayerToLeague(PlayerLeague playerLeague);
	public void removePlayerFromLeague(League league, Player player);
	public void removePlayerFromLeague(PlayerLeague playerLeague);
	public void sendInviteEmail(List<String> emails, League league);
	public void createPayment(Payment payment);
	public List findMasterLeagueTypes();
	public List<League> getLeaguesForPlayer(Player player);
	public List<League> getLeaguesForNavigation(final Player player);
	public int getNumberOfPlayersInLeague(League league);

	public void makePayment(Payment payment);
	public List<Payment> getPaymentsByLeague(League league);
//	public void contactUs(ContactInfo contactInfo);
	public LeagueType getLeagueType(long id);
	public Season getSeason(long id);
	public LeagueType getLeagueTypeForSeason(Season season);
	public Season getSeason(LeagueType leagueType, int startYear ,int endYear);
	public Season getSeason(String typeOfLeague, int startYear ,int endYear);
	public List<Season> getSeasons();
	public List<Season> getParentSeasons();
	public League getLeague(long leagueId);
	public List<PlayerLeague> getPlayerLeaguesByPlayer(Player player);
	public void updatePlayerLeague(PlayerLeague playerLeague);
	public PlayerLeague loadPlayerLeague(long id);
	public List<Season> getCurrentSeasons();
	public void createLeague(final League league);
	public boolean sendWeekSetupEmailToAllPlayers(Week week);
	public List<LeagueType> getChildLeagueTypesFromParent(String parentLeagueType);
}
