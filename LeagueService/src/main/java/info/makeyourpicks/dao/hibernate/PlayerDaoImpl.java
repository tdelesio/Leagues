package info.makeyourpicks.dao.hibernate;

import info.makeyourpicks.dao.PlayerDao;
import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Payment;
import info.makeyourpicks.model.Player;

import java.util.List;

import com.delesio.dao.hibernate.AbstractDao;

public class PlayerDaoImpl extends AbstractDao implements PlayerDao{

	public Player findPlayerByName(String username) {
		
		List players = hibernateTemplate.find("from Player where username=?", username);
		if (players.isEmpty())
		{
			return null;
		}
		else
		{
			return (Player)players.get(0);
		}
	}

	public List<Payment> findPaymentsByLeague(League league) {
		return hibernateTemplate.find("from Payment where league_id=?", league.getId());
	}
	
	
	public List<Player> findPlayersInLeague(League league)
	{
		return hibernateTemplate.find("select playerleague.player from PlayerLeague playerleague where playerleague.league.id=?", league.getId());
	}
	
	public Player findPlayerByEmail(String email)
	{
		List players = hibernateTemplate.find("from Player where email=?", email);
		if (players.isEmpty())
		{
			return null;
		}
		else
		{
			return (Player)players.get(0);
		}
	}
	
	public List<Player> findPlayersByFacebookId(long facebookId)
	{
		return (List<Player>)hibernateTemplate.find("from Player where facebookId=?", facebookId);
	}
	

}
