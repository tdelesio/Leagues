package info.makeyourpicks.dao.hibernate;

import info.makeyourpicks.dao.PickDao;
import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Picks;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.Team;
import info.makeyourpicks.model.Week;

import java.util.List;

import com.delesio.dao.hibernate.AbstractDao;

public class PickDaoImpl extends AbstractDao implements PickDao {

	public List<Picks> findPicksByLeague(League league) {
//		return getHibernateTemplate().find("from Picks p inner join fetch p.week inner join fetch p.league inner join fetch p.name inner join fetch p.team inner join fetch p.game inner join fetch p.league.season.leagueType where p.league.id=?", league.getId());
		return getHibernateTemplate().find("from Picks p inner join fetch p.week inner join fetch p.league inner join fetch p.name inner join fetch p.game inner join fetch p.league.season.leagueType where p.league.id=?", league.getId());
	}

	public List<Picks> findPicksByLeagueAndWeek(League league, Week week) {
		return hibernateTemplate.find("from Picks p inner join fetch p.week inner join fetch p.league inner join fetch p.name inner join fetch p.team inner join fetch p.game inner join fetch p.league.season.leagueType where p.league.id=? and p.week.id=?", new Object[] {league.getId(), week.getId()});
	}

	public List<Picks> findPicksByWeek(Week week) {
		return hibernateTemplate.find("from Picks p inner join fetch p.week inner join fetch p.league inner join fetch p.name inner join fetch p.team inner join fetch p.game inner join fetch p.league.season.leagueType where p.week.id=?", week.getId());
	}

	public List<Picks> findPicksByPlayer(Player player) {
		return hibernateTemplate.find("from Picks p inner join fetch p.week inner join fetch p.league inner join fetch p.name inner join fetch p.team inner join fetch p.game inner join fetch p.league.season.leagueType where p.name.id=?", player.getId());
	}

	public List<Picks> findPicksByPlayerAndWeek(Player player, Week week) {
		return hibernateTemplate.find("from Picks p inner join fetch p.week inner join fetch p.league inner join fetch p.name inner join fetch p.team inner join fetch p.game inner join fetch p.league.season.leagueType where p.name.id=? and p.week.id=?", new Object[] {player.getId(), week.getId()});
	}

	public List<Picks> findPicksByPlayerLeagueAndWeek(Player player,
			League league, Week week) {
		return hibernateTemplate.find("from Picks p inner join fetch p.week inner join fetch p.league inner join fetch p.name inner join fetch p.team inner join fetch p.game inner join fetch p.league.season.leagueType where p.name.id=? and p.league.id=? and p.week.id=?", new Object[] {player.getId(), league.getId(), week.getId()});
	}
	
	public List<Picks> findPickByPlayerLeagueWeekAndGame(Player player,
			League league, Week week, Game game) {
		return hibernateTemplate.find("from Picks p inner join fetch p.week inner join fetch p.league inner join fetch p.name inner join fetch p.team inner join fetch p.game inner join fetch p.league.season.leagueType where p.name.id=? and p.league.id=? and p.week.id=? and p.game.id=?", new Object[] {player.getId(), league.getId(), week.getId(), game.getId()});
	}

	public List<Picks> findDoublePickByPlayerLeagueAndWeek(Player player, League league, Week week) {
		return hibernateTemplate.find("from Picks p inner join fetch p.week inner join fetch p.league inner join fetch p.name inner join fetch p.team inner join fetch p.game inner join fetch p.league.season.leagueType where p.weight = 2 and p.name.id=? and p.league.id=? and p.week.id=?", new Object[]{player.getId(), league.getId(), week.getId()});
	}

	public List<Picks> findPickByPlayerLeagueWeekAndTeam(Player player,
			League league, Week week, Team team) {
		return hibernateTemplate.find("from Picks p inner join fetch p.week inner join fetch p.league inner join fetch p.name inner join fetch p.team inner join fetch p.game inner join fetch p.league.season.leagueType where p.name.id=? and p.league.id=? and p.week.id = ? and p.team.id=?", new Object[]{player.getId(), league.getId(), week.getId(), team.getId()});
	}

	public Picks findPicksByPlayerLeagueWeekAndWeight(Player player,
			League league, Week week, int weight) {
		return (Picks)extractSingleElement(hibernateTemplate.find("from Picks p inner join fetch p.week inner join fetch p.league inner join fetch p.name inner join fetch p.team inner join fetch p.game inner join fetch p.league.season.leagueType where p.name.id=? and p.league.id=? and p.week.id=? and p.weight=?", new Object[] {player.getId(), league.getId(), week.getId(),weight}));
	}
	
	
	
	

}
