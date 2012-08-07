package info.makeyourpicks.dao.hibernate;

import info.makeyourpicks.dao.LeagueDao;
import info.makeyourpicks.model.League;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.PlayerLeague;
import info.makeyourpicks.model.Season;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.delesio.dao.hibernate.AbstractDao;
import com.delesio.util.TimeHelper;

public class LeagueDaoImpl extends AbstractDao implements LeagueDao{

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
		return getHibernateTemplate().find(getSeasonQuery("", "where startYear >= ?"), year);
		
	}
	public List findFreeLeagues() {
		return hibernateTemplate.find(getLeagueQuery("", "where league.free=true"));
	}
	
	public List findMasterLeagueTypes()
	{
		return hibernateTemplate.find(getLeagueTypeQuery("", "where leagueType.parentTypeOfLeague is null"));
	}
	
	public League findLeague(String leagueName)
	{
		return (League)extractSingleElement(hibernateTemplate.find(getLeagueQuery("", "where league.leagueName=?"),new Object[] {leagueName}));
	}

	public LeagueType findLeagueType(String leagueType)
	{
		return (LeagueType)extractSingleElement(hibernateTemplate.find(getLeagueTypeQuery("", "where leagueType.typeOfLeague=?"), leagueType));
	}

	  
//	@Deprecated public List<League> findLeaguesByLeagueType(LeagueType leagueType) {
//		return hibernateTemplate.find("from League l inner join fetch l.leagueType where l.leagueType.id=?", leagueType.getId());
//	}
	
	public List<League> findLeaguesBySeason(Season season) {
//		return hibernateTemplate.find("from League l inner join fetch l.leagueType where l.leagueType.id=?", leagueType.getId());
		return hibernateTemplate.find(getLeagueQuery("", "where season.id=?"), season.getId());
	}

	public List<League> findLeaguesByPlayer(Player player) {
//		return hibernateTemplate.find("select playerleague.league from PlayerLeague playerleague inner join fetch playerleague.league.admin inner join fetch playerleague.league.leagueType where playerleague.player.id=?", player.getId());
		return hibernateTemplate.find(getPlayerLeagueQuery("select playerleague.league ", "where playerleague.player.id=? order by playerleague.sortOrder, playerleague.league.id desc"), player.getId());
	}
	
	public List<PlayerLeague> findPlayerLeaguesByPlayer(Player player) {
//		return hibernateTemplate.find("select playerleague.league from PlayerLeague playerleague inner join fetch playerleague.league.admin inner join fetch playerleague.league.leagueType where playerleague.player.id=?", player.getId());
		return hibernateTemplate.find(getPlayerLeagueQuery("", "where playerleague.player.id=? order by playerleague.sortOrder, playerleague.league.id desc"), player.getId());
	}
	
	public List<League> findLeaguesByPlayer(Player player, boolean isDisplay) {
//		return hibernateTemplate.find("select playerleague.league from PlayerLeague playerleague inner join fetch playerleague.league.admin inner join fetch playerleague.league.leagueType where playerleague.player.id=?", player.getId());
		return hibernateTemplate.find(getPlayerLeagueQuery("select playerleague.league ", "where playerleague.player.id=? and playerleague.displayInNav=? order by playerleague.sortOrder, playerleague.league.id desc"), new Object[]{player.getId(), isDisplay});
	}
	
	public void removePlayerFromLeague(League league, Player player)
	{
//		 List<PlayerLeague> playerLeagues = hibernateTemplate.find("from PlayerLeague playerleague inner join fetch playerleague.league inner join fetch playerleague.player where playerleague.player.id=? and playerleague.league.id=?", new Object[] {player.getId(), league.getId()});
		List<PlayerLeague> playerLeagues = hibernateTemplate.find(getPlayerLeagueQuery("", "where playerleague.player.id=? and playerleague.league.id=?"), new Object[] {player.getId(), league.getId()});
		if (!playerLeagues.isEmpty())
		{
		 hibernateTemplate.delete(playerLeagues.iterator().next());
		}
	}

	public int findNumberOfPlayersInLeague(final League league) {
		
		
		
		  return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			   public Object doInHibernate(Session session) throws HibernateException, SQLException {
				   Query query = session.createQuery("select count(playerleague.player) from PlayerLeague playerleague where playerleague.league.id=?").setLong(0, league.getId());
//					"select count(playerleague.player) from PlayerLeague playerleague where playerleague.league.id=?").setLong(0, league.getId());
					return Integer.parseInt(""+query.iterate().next());
				   }
				  });
	}

	public LeagueType getLeagueTypeForSeason(Season season)
	{
		return (LeagueType)extractSingleElement(hibernateTemplate.find("select season.leagueType from Season season where season.id=?", season.getId()));
	}

	public Season getSeason(LeagueType leagueType, int startYear, int endYear)
	{
		return (Season)extractSingleElement(hibernateTemplate.find(getSeasonQuery("", "where season.leagueType.id=? and season.startYear=? and season.endYear=?"), new Object[]{leagueType.getId(), startYear, endYear}));
	}

	public Season getSeason(String typeOfLeague, int startYear, int endYear)
	{
		return (Season)extractSingleElement(hibernateTemplate.find(getSeasonQuery("", "where season.leagueType.typeOfLeague=? and season.startYear=? and season.endYear=?"), new Object[]{typeOfLeague, startYear, endYear}));	
	}
	
	public List<Season> getParentSeasons()
	{
		return getHibernateTemplate().find(getSeasonQuery("", "where leagueType.parentTypeOfLeague is null"));
	}
	
	public List<LeagueType> getLeagueTypesByParent(String parentLeagueType)
	{
		return hibernateTemplate.find(getLeagueTypeQuery("", "where leagueType.parentTypeOfLeague=?"), parentLeagueType);
	}

	
}
