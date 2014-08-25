package info.makeyourpicks.dao.hibernate;

import info.makeyourpicks.dao.PlayerDao;
import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Payment;
import info.makeyourpicks.model.Player;

import java.util.List;

import org.hibernate.Query;
import org.springframework.security.core.Authentication;

import com.delesio.dao.hibernate.HibernateDao;

public class PlayerDaoImpl extends HibernateDao implements PlayerDao{

	public Authentication getAuthenication(String tgt)
	{
		Query query = getQuery("from Ticket t where t.tgt = ?");
		query.setParameter(0, tgt);
		
		return (Authentication)query.uniqueResult();
	}
	
	public Player findPlayerByName(String username) {
		
//		List players = hibernateTemplate.find("from Player where username=?", username);
//		if (players.isEmpty())
//		{
//			return null;
//		}
//		else
//		{
//			return (Player)players.get(0);
//		}
		
		Query query = getQuery("from Player where username=?");
		query.setParameter(0, username);

		
		return (Player)query.uniqueResult();
	}

	public List<Payment> findPaymentsByLeague(League league) {
//		return hibernateTemplate.find("from Payment where league_id=?", league.getId());
		
		Query query = getQuery("from Payment where league_id=?");
		query.setParameter(0, league.getId());
		
		return query.list();
	}
	
	
	public List<Player> findPlayersInLeague(League league)
	{
//		return hibernateTemplate.find("select playerleague.player from PlayerLeague playerleague where playerleague.league.id=?", league.getId());
		
		Query query = getQuery("select playerleague.player from PlayerLeague playerleague where playerleague.league.id=?");
		query.setParameter(0, league.getId());
		
		return query.list();
	}
	
	public Player findPlayerByEmail(String email)
	{
//		List players = hibernateTemplate.find("from Player where email=?", email);
		
		
		Query query = getQuery("from Player where email=?");
		query.setParameter(0, email);
		
		return (Player)query.uniqueResult();
		
	}
	
	public List<Player> findPlayersByFacebookId(long facebookId)
	{
//		return (List<Player>)hibernateTemplate.find("from Player where facebookId=?", facebookId);
		
		Query query = getQuery("from Player where facebookId=?");
		query.setParameter(0, facebookId);

		
		return query.list();
	}
	

}
