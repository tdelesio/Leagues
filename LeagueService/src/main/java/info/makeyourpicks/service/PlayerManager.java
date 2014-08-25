package info.makeyourpicks.service;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.SeasonStats;
import info.makeyourpicks.model.Ticket;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.delesio.exception.ValidationException;

public interface PlayerManager extends UserDetailsService {


	/**
	 * This gets all the players that are playing in the league
	 * @param leagueName
	 * @return List<PlayerInfo>
	 */
	@SuppressWarnings("unchecked")
	public List<Player> getAllPlayers();
	
	/**
	 * This is used to register a player
	 * @param playerInfo
	 * @throws SystemException
	 */
//	public void insertPlayerProfile(Player playerInfo);
	
	/**
	 * This is used when a player wants to update their profile
	 * @param playerInfo
	 * @throws SystemException
	 */
//	public void updatePlayerProfile(Player playerInfo);
	
	/**
	 * This gets a player from a league based on thier email
	 * @param email
	 * @param leagueName
	 * @return
	 */
	public Player getPlayer(String name);
	
	@SuppressWarnings("unchecked")
	public UserDetails loadUserByUsername(String name);
	
	/**
	 * Removes the user from the system
	 * @param name
	 */
	public void deleteUser(String name);
	public List<Player> getPlayersInLeague(League league);
	public Player loadPlayer(long playerId);
	public Player getPlayerByEmail(String email);
	@Deprecated
	public boolean requestPassword(Player player);
	public boolean retrievePassword(Player player);
	public void createUpdatePlayer(Player player);
	public List<Player> getPlayersByFacebookId(long facebookID);
	public List<Player> getPlayersInLeagueTX(long seasonId) throws ValidationException;
	public List<Player> getPlayersInLeague(long seasonId) throws ValidationException;
	public List<SeasonStats> getPlayersPlusWinsInLeagueTX(long leagueId, long weekid) throws ValidationException;
	public Authentication getAuthenicationTX(String tgt);
	public Ticket loginTX(String userName, String password) throws ValidationException;
	public boolean retrievePasswordTX(Player emailOrUsername);
//	public String encypt(String unencryptedValue);
//	public String decypt(String encryptedValue);
}
