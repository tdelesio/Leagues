package info.makeyourpicks.dao.hibernate;

import info.makeyourpicks.dao.TeamDao;
import info.makeyourpicks.model.League;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Team;
import info.makeyourpicks.model.Week;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.delesio.dao.hibernate.HibernateDao;

public class TeamDaoImpl extends HibernateDao implements TeamDao {


	public List<Team> getTeamsByLeagueType(LeagueType leagueType)
	{
//		return getHibernateTemplate().find("from Team t inner join fetch t.leagueType where t.leagueType.id = ? order by t.city", 
//				leagueType.getId());
		
		Query query = getQuery("from Team t inner join fetch t.leagueType where t.leagueType.id = ? order by t.city");
		query.setParameter(0, leagueType.getId());
		
		return query.list();
	}
	
	public List<Team> loadAllTeamsOrderByCity()
	{
//		return hibernateTemplate.find("from Team t inner join fetch t.leagueType order by t.city");
		
		Query query = getQuery("from Team t inner join fetch t.leagueType order by t.city");
		
		return query.list();
	}
	
	public List<Team> findTeamsPlayingInWeek(Week week)
	{
//		List<Team> teams = hibernateTemplate.find("select g.fav from Game g inner join fetch g.fav.leagueType where g.week.id=? and g.gameStart > ?", 
//				new Object[] {week.getId(), new Timestamp(System.currentTimeMillis())});
//		teams.addAll(hibernateTemplate.find("select g.dog from Game g inner join fetch g.dog.leagueType where g.week.id=? and g.gameStart > ?", 
//				new Object[] {week.getId(), new Timestamp(System.currentTimeMillis())}));
//		return teams;
		
		Query query = getQuery("select g.fav from Game g inner join fetch g.fav.leagueType where g.week.id=? and g.gameStart > ?");
		query.setParameter(0, week.getId());
		query.setParameter(1,  new Timestamp(System.currentTimeMillis()));
		
		List<Team> teams = query.list();
		
		Query query2 = getQuery("select g.dog from Game g inner join fetch g.dog.leagueType where g.week.id=? and g.gameStart > ?");
		query2.setParameter(0, week.getId());
		query2.setParameter(1,  new Timestamp(System.currentTimeMillis()));
		
		teams.addAll(query2.list());
		return teams;
	}
	
	public List<Team> findPickedTeamsForPlayerAndLeague(Player player,League league)
	{
//		return hibernateTemplate.find("select p.team from Picks p inner join fetch p.team.leagueType where p.name.id=? and p.league.id=?", 
//				new Object[]{player.getId(), league.getId()});
		
		Query query = getQuery("select p.team from Picks p inner join fetch p.team.leagueType where p.name.id=? and p.league.id=?");
		query.setParameter(0, player.getId());
		query.setParameter(1, league.getId());
		
		return query.list();
	}
	
	public List<Team> findPickedTeamsForPlayerWeekAndLeague(Player player,Week week, League league)
	{
//		return hibernateTemplate.find("select p.team from Picks p inner join fetch p.team.leagueType where p.name.id=? and p.week.id=? and p.league.id=?", 
//				new Object[]{player.getId(), week.getId(), league.getId()});
		
		Query query = getQuery("select p.team from Picks p inner join fetch p.team.leagueType where p.name.id=? and p.week.id=? and p.league.id=?");
		query.setParameter(0, player.getId());
		query.setParameter(1, week.getId());
		query.setParameter(2, league.getId());
		
		return query.list();
	}
	
	public List<Team> findPickedDoubleTeamsForPlayerWeekAndLeague(final Player player,final Week week, final League league)
	{
		
		
//		List list = (List)getHibernateTemplate().execute(new HibernateCallback() {
//			   public Object doInHibernate(Session session) throws HibernateException, SQLException {
//				   Query query = session.createQuery("select p.team, p.game from Picks p inner join fetch p.team.leagueType where p.name.id=? and p.week.id=? and p.league.id=? and p.game.gameStart<?");
//					query.setLong(0, player.getId());
//					query.setLong(1, week.getId());
//					query.setLong(2, league.getId());
//					query.setDate(3, new Date(System.currentTimeMillis()));
//					
//					return query.list();
//				   }
//				  });
		
		Query query = getQuery("select p.team, p.game from Picks p inner join fetch p.team.leagueType where p.name.id=? and p.week.id=? and p.league.id=? and p.game.gameStart<?");
		query.setLong(0, player.getId());
		query.setLong(1, week.getId());
		query.setLong(2, league.getId());
		query.setDate(3, new Date(System.currentTimeMillis()));
		
		List list = query.list();
		
		Iterator<Object[]> iter = list.iterator();
		List<Team> teams = new ArrayList<Team>();
		while (iter.hasNext())
		{
			Object[] objects = iter.next();
			Team team = (Team)objects[0];
			teams.add(team);
		}
		
		return teams;
		
		
	}

	public Team findTeamByCity(String city)
	{
//		return (Team)extractSingleElement(hibernateTemplate.find("from Team t inner join fetch t.leagueType where lower(t.city)=?", city));
		
		Query query = getQuery("from Team t inner join fetch t.leagueType where lower(t.city)=?");
		query.setParameter(0, city);
		
		return (Team)query.uniqueResult();
	}
	
	public Team findTeamByFeedName(String feedName)
	{
//		return (Team)extractSingleElement(hibernateTemplate.find("from Team t inner join fetch t.leagueType where t.feedName=?", feedName));
		
		Query query = getQuery("from Team t inner join fetch t.leagueType where t.feedName=?");
		query.setParameter(0, feedName);
		
		return (Team)query.uniqueResult();
	}
}
