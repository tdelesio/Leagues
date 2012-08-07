package info.makeyourpicks.dao;

import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Picks;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.Team;
import info.makeyourpicks.model.Week;

import java.util.List;

public interface PickDao {

	public List<Picks> findPicksByLeague(League league);
	public List<Picks> findPicksByWeek(Week week);
	public List<Picks> findPicksByLeagueAndWeek(League league, Week week);
	public List<Picks> findPicksByPlayer(Player player);
	public List<Picks> findPicksByPlayerAndWeek(Player player, Week week);
	public List<Picks> findPicksByPlayerLeagueAndWeek(Player player, League league, Week week);
	public List<Picks> findPickByPlayerLeagueWeekAndGame(Player player, League league, Week week, Game game);
	public List<Picks> findDoublePickByPlayerLeagueAndWeek(Player player, League league, Week week);
	public List<Picks> findPickByPlayerLeagueWeekAndTeam(Player player, League league, Week week, Team team);
	public Picks findPicksByPlayerLeagueWeekAndWeight(Player player, League league, Week week, int weight);
}
