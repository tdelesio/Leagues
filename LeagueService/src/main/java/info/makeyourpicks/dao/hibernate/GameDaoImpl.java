package info.makeyourpicks.dao.hibernate;

import info.makeyourpicks.dao.GameDao;
import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.Team;
import info.makeyourpicks.model.Week;

import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.delesio.dao.hibernate.AbstractDao;

public class GameDaoImpl extends AbstractDao implements GameDao {

	private String getGameQuery(String prefix, String suffix)
	{
		StringBuffer buffer = new StringBuffer(prefix);
		buffer.append("from Game game ");
		buffer.append("inner join fetch game.week week ");
		buffer.append("inner join fetch game.week.season season ");
		buffer.append("inner join fetch game.week.season.leagueType leagueType ");
		buffer.append("inner join fetch game.fav fav ");
		buffer.append("inner join fetch game.dog dog ");
		buffer.append(suffix);
		return buffer.toString();
	}
	
	private String getWeekQuery(String prefix, String suffix)
	{
		StringBuffer buffer = new StringBuffer(prefix);
		buffer.append("from Week week ");
		buffer.append("inner join fetch week.season season ");
		buffer.append("inner join fetch week.season.leagueType leagueType ");
		buffer.append(suffix);
		return buffer.toString();
	}
	
	public int getCurrentWeek(final Season season) {
//select max(game.gameStart),  
		
		Query query = getSession().createQuery("select max(game.gameStart), game.week.weekNumber from Game game where game.week.season.id=? and curdate() < game.gameStart group by game.week.season.id");
		query.setLong(0, season.getId());
				
		List list = (List<Object>) getHibernateTemplate().execute(new HibernateCallback() {
			   public Object doInHibernate(Session session) throws HibernateException, SQLException {
				   Query query = session.createQuery("select max(game.gameStart), game.week.weekNumber from Game game where game.week.season.id=? and curdate() < game.gameStart group by game.week.season.id");
				   query.setLong(0, season.getId()); 
				    return query.list();
				   }
				  });
		
		int weeknumber=0;
		Iterator<Object[]> iter = list.iterator();
		while (iter.hasNext())
		{
			Object[] objects = iter.next();
			Date weekEnd = (Date)objects[0];
			weeknumber = (Integer)objects[1];
			return weeknumber;
		}
		
		return weeknumber;
	}
	
	public Week findWeek(int weekNumber, Season season)
	{
//		List list = hibernateTemplate.find("from Week w inner join fetch w.leagueType where w.weekNumber=? and w.leagueType.id=?", new Object[] {weekNumber, leagueType.getId()});
		return (Week)extractSingleElement(hibernateTemplate.find(getWeekQuery("", "where week.weekNumber=? and season.id=?"), new Object[] {weekNumber, season.getId()}));
	}
	
	public List<Week> findWeekBySeason(Season season)
	{
//		return getHibernateTemplate().find("from Week w inner join fetch w.leagueType where w.leagueType.id=? order by w.weekNumber", leagueType.getId());
		return hibernateTemplate.find(getWeekQuery("", "where season.id=? order by week.weekNumber"), season.getId());
	}

	public List<Game> findGamesByWeek(Week week) {
		return hibernateTemplate.find(getGameQuery("", "where week.id=?"), week.getId());
//		return hibernateTemplate.find("from Game g inner join fetch g.fav inner join fetch g.dog inner join fetch g.week inner join fetch g.week.leagueType where week_id=?", week.getId());
	}
	
	public Game findGameByTeamWeekLeague(Team team, Week week)
	{
//		Iterator<Game> games = hibernateTemplate.find("from Game g inner join fetch g.fav inner join fetch g.dog inner join fetch g.week where g.week.id=? and (g.fav.id=? or g.dog.id=?)", new Object[]{week.getId(), team.getId(), team.getId()}).iterator();
//		Game game=null;
//		if (games.hasNext())
//		{
//			game = games.next();
//		}
//		return game;
		return (Game)extractSingleElement(hibernateTemplate.find(getGameQuery("", "where game.week.id=? and (game.fav.id=? or game.dog.id=?)"), new Object[]{week.getId(), team.getId(), team.getId()}));
	}

	public Game findGamesByCityNames(String favCityName, String dogCityName, Date gameStart)
    {
        return (Game)extractSingleElement(hibernateTemplate.find(getGameQuery("", "where lower(game.fav.city)=? and lower(game.dog.city)=? and DATE(game.gameStart)=DATE(?)"), new Object[] {
            favCityName, dogCityName, gameStart
        }));
    }
	
	public Game findGamesByTeams(Team fav, Team dog, Date gameStart)
	{
		return (Game)extractSingleElement(hibernateTemplate.find(getGameQuery("", "where game.fav.id=? and game.dog.id=? and DATE(game.gameStart)=DATE(?)"), new Object[]{fav.getId(), dog.getId(), gameStart}));
	}
}
