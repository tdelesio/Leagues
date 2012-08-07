package info.makeyourpicks.dao;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Payment;
import info.makeyourpicks.model.Player;

import java.util.List;

public interface PlayerDao {

	public Player findPlayerByName(String username);
	public List<Payment> findPaymentsByLeague(League league);
	public List<Player> findPlayersInLeague(League league);
	public Player findPlayerByEmail(String email);
	public List<Player> findPlayersByFacebookId(long facebookId);
}
