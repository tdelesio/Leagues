package info.makeyourpicks.dao.hibernate;

import info.makeyourpicks.dao.LeagueDao;
import info.makeyourpicks.model.League;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.PlayerLeague;
import info.makeyourpicks.model.Season;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.delesio.dao.hibernate.HibernateDao;
import com.delesio.util.TimeHelper;

public class LeagueDaoImpl extends HibernateDao implements LeagueDao{

	private String getLeagueQuery(String prefix, String suffix)
	{
		StringBuffer buffer = new StringBuffer(prefix);
		buffer.append("from League league ");
		buffer.append("inner join fetch league.season season ");
		buffer.append("inner join fetch league.season.leagueType leagueType ");
		buffer.append("inner join fetch league.admin admin ");
		buffer.append(suffix);
		
		return buffer.toString();
	}
	
	private String getLeagueTypeQuery(String prefix, String suffix)
	{
		StringBuffer buffer = new StringBuffer(prefix);
		buffer.append("from LeagueType leagueType ");
		buffer.append(suffix);
		
		return buffer.toString();
	}
	
	private String getPlayerLeagueQuery(String prefix, String suffix)
	{
		StringBuffer buffer = new StringBuffer(prefix);
		buffer.append("from PlayerLeague playerleague ");
		buffer.append("inner join fetch playerleague.league.admin admin ");
		buffer.append("inner join fetch playerleague.league.season season ");
		buffer.append("inner join fetch playerleague.league.season.leagueType leagueType " );
		buffer.append(suffix);
		
		return buffer.toString();
	}
//	public League getLeagueBySeason(long seasonID)
//	{
//		Query query = getQuery(getLeagueQuery("", "where league.season.id=?"));
//		query.setParameter(0, seasonID);
//		
//		return (League)query.uniqueResult();
//	}
	
	public PlayerLeague findPlayerLeagueByLeagueAndPlayer(long leagueId, long playerId)
	{
		Query query = getQuery(getPlayerLeagueQuery("", "where playerleague.player.id=? and playerleague.league.id=? order by playerleague.sortOrder, playerleague.league.id desc"));
		query.setParameter(0, playerId);
		query.setParameter(1, leagueId);

		return (PlayerLeague)query.uniqueResult();
	}
	
	private String getSeasonQuery(String prefix, String suffix)
	{
		StringBuffer buffer = new StringBuffer(prefix);
		buffer.append("from Season season ");
		buffer.append("inner join fetch season.leagueType leagueType ");
		buffer.append(suffix);
		
		return buffer.toString();
	}
	
	public List<Season> getCurrentSeasons()
	{
		int year = TimeHelper.getCurrentYear();
//		return getHibernateTemplate().find(getSeasonQuery("", "where startYear >= ?"), year);
		
		Query query = getQuery(getSeasonQuery("", "where startYear >= ?"));
		query.setParameter(0, year);
		
		return query.list();
		
	}
	public List findFreeLeagues() {
//		return hibernateTemplate.find(getLeagueQuery("", "where league.free=true"));
		
		Query query = getQuery(getLeagueQuery("", "where league.free=true"));
		
		return query.list();
	}
	
	public List findMasterLeagueTypes()
	{
//		return hibernateTemplate.find(getLeagueTypeQuery("", "where leagueType.parentTypeOfLeague is null"));
		
		Query query = getQuery(getLeagueTypeQuery("", "where leagueType.parentTypeOfLeague is null"));
		
		
		return query.list();
	}
	
	public League findLeague(String leagueName)
	{
//		return (League)extractSingleElement(hibernateTemplate.find(getLeagueQuery("", "where league.leagueName=?"),
//				new Object[] {leagueName}));
		
		Query query = getQuery(getLeagueQuery("", "where league.leagueName=?"));
		query.setParameter(0, leagueName);
		
		return (League)query.uniqueResult();
	}

	public LeagueType findLeagueType(String leagueType)
	{
//		return (LeagueType)extractSingleElement(hibernateTemplate.find(getLeagueTypeQuery("", "where leagueType.typeOfLeague=?"), leagueType));
		
		Query query = getQuery(getLeagueTypeQuery("", "where leagueType.typeOfLeague=?"));
		query.setParameter(0, leagueType);
		
		return (LeagueType)query.uniqueResult();
	}

	 
	
	public List<League> findLeaguesBySeason(Season season) {
//		return hibernateTemplate.find(getLeagueQuery("", "where season.id=?"), season.getId());
		
		Query query = getQuery(getLeagueQuery("", "where season.id=?"));
		query.setParameter(0, season.getId());

		return query.list();
	}

	public List<League> findLeaguesByPlayer(Player player) {

//		return hibernateTemplate.find(getPlayerLeagueQuery("select playerleague.league ", "where playerleague.player.id=? order by playerleague.sortOrder, playerleague.league.id desc"), 
//				player.getId());
		
		Query query = getQuery("select pl.league from PlayerLeague pl where pl.player.id=? order by pl.sortOrder, pl.league.id desc");
		query.setParameter(0, player.getId());
	
		return query.list();
	}
	
	public List<PlayerLeague> findPlayerLeaguesByPlayer(Player player) {
//		

//		return hibernateTemplate.find(getPlayerLeagueQuery("", "where playerleague.player.id=? order by playerleague.sortOrder, playerleague.league.id desc"),
//				player.getId());
		
		Query query = getQuery(getPlayerLeagueQuery("", "where playerleague.player.id=? order by playerleague.sortOrder, playerleague.league.id desc"));
		query.setParameter(0, player.getId());

		return query.list();
	}
	
	
	public List<League> findLeaguesByPlayer(Player player, boolean isDisplay) {
//		return hibernateTemplate.find(getPlayerLeagueQuery("select playerleague.league ", "where playerleague.player.id=? and playerleague.displayInNav=? order by playerleague.sortOrder, playerleague.league.id desc"), 
//			new Object[]{player.getId(), isDisplay});
		
		Query query = getQuery(getPlayerLeagueQuery("select playerleague.league ", "where playerleague.player.id=? and playerleague.displayInNav=? order by playerleague.sortOrder, playerleague.league.id desc"));
		query.setParameter(0, player.getId());
		query.setParameter(1, isDisplay);

		
		return query.list();
	}
	
	public void removePlayerFromLeague(League league, Player player)
	{
//		List<PlayerLeague> playerLeagues = hibernateTemplate.find(getPlayerLeagueQuery("", "where playerleague.player.id=? and playerleague.league.id=?"), 
//				new Object[] {player.getId(), league.getId()});
//		if (!playerLeagues.isEmpty())
//		{
//		 hibernateTemplate.delete(playerLeagues.iterator().next());
//		}
//		if (!playerLeagues.isEmpty())
//		{
//			dao.delete(playerLeagues.iterator().next());
//		}
		
		Query query = getQuery(getPlayerLeagueQuery("", "where playerleague.player.id=? and playerleague.league.id=?"));
		query.setParameter(0, player.getId());
		query.setParameter(1, league.getId());
		
		List<PlayerLeague> playerLeagues = query.list();
		
		for (PlayerLeague playerLeague : playerLeagues)
		{
			Query query2 = getQuery("delete from playerLeague where id = ?");
			query2.setParameter(0, playerLeague.getId());
			
			query2.executeUpdate();
		}
		

	}

	public List<League> getLeagues()
	{
		Query query = getQuery(getLeagueQuery("", "order by league.id desc"));
		return query.list();
	}
	
	public int findNumberOfPlayersInLeague(final League league) {
		
		
		
//		  return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
//			   public Object doInHibernate(Session session) throws HibernateException, SQLException {
//				   Query query = session.createQuery("select count(playerleague.player) from PlayerLeague playerleague where playerleague.league.id=?").setLong(0, league.getId());
////					"select count(playerleague.player) from PlayerLeague playerleague where playerleague.league.id=?").setLong(0, league.getId());
//					return Integer.parseInt(""+query.iterate().next());
//				   }
//				  });
		  
		  Query query = getQuery("select count(playerleague.player) from PlayerLeague playerleague where playerleague.league.id=?");
		query.setParameter(0, league.getId());
			
		int num = Integer.parseInt(""+query.iterate().next());
			return num;
	}

	public LeagueType getLeagueTypeForSeason(Season season)
	{
//		return (LeagueType)extractSingleElement(hibernateTemplate.find("select season.leagueType from Season season where season.id=?", 
//				season.getId()));
		
		Query query = getQuery("select season.leagueType from Season season where season.id=?");
		query.setParameter(0, season.getId());

		
		return (LeagueType)query.uniqueResult();
	}

	public Season getSeason(LeagueType leagueType, int startYear, int endYear)
	{
//		return (Season)extractSingleElement(hibernateTemplate.find(getSeasonQuery("", "where season.leagueType.id=? and season.startYear=? and season.endYear=?"), 
//				new Object[]{leagueType.getId(), startYear, endYear}));
		
		Query query = getQuery(getSeasonQuery("", "where season.leagueType.id=? and season.startYear=? and season.endYear=?"));
		query.setParameter(0, leagueType.getId());
		query.setParameter(1, startYear);
		query.setParameter(2, endYear);
		
		return (Season)query.uniqueResult();
	}

	public Season getSeason(String typeOfLeague, int startYear, int endYear)
	{
//		return (Season)extractSingleElement(hibernateTemplate.find(getSeasonQuery("", "where season.leagueType.typeOfLeague=? and season.startYear=? and season.endYear=?"),
//				new Object[]{typeOfLeague, startYear, endYear}));
		
		Query query = getQuery(getSeasonQuery("", "where season.leagueType.typeOfLeague=? and season.startYear=? and season.endYear=?"));
		query.setParameter(0, typeOfLeague);
		query.setParameter(1, startYear);
		query.setParameter(2, endYear);
		
		return (Season)query.uniqueResult();
	}
	
	public List<Season> getParentSeasons()
	{
//		return getHibernateTemplate().find(getSeasonQuery("", "where leagueType.parentTypeOfLeague is null"));
		
		Query query = getQuery(getSeasonQuery("", "where leagueType.parentTypeOfLeague is null"));
		
		return query.list();
	}
	
	public List<LeagueType> getLeagueTypesByParent(String parentLeagueType)
	{
//		return hibernateTemplate.find(getLeagueTypeQuery("", "where leagueType.parentTypeOfLeague=?"), parentLeagueType);
		
		Query query = getQuery(getLeagueTypeQuery("", "where leagueType.parentTypeOfLeague=?"));
		query.setParameter(0, parentLeagueType);
	
		
		return query.list();
	}

	
}
