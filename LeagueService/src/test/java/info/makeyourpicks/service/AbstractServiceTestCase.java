package info.makeyourpicks.service;

import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.League;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Picks;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Team;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.test.AbstractTestCase;

import org.junit.After;
import org.junit.Before;

public abstract class AbstractServiceTestCase extends AbstractTestCase {

	protected static Player player;
	protected static LeagueType leagueType;
	protected static League league1;
	protected static League league2;
	protected static Team team1;
	protected static Team team2;
	protected static Week week;
	protected static Game game1;
	protected static Picks pick1;
	
	@Before
	public void setUp() throws Exception 
	{
		
//		createLeagueTypes();
//		createTeams();
//		createPlayers();
//		createSeasons();
//		createLeagues();
//		createWeeks();
//		createGames();
	}

	@After
	public void tearDown() throws Exception 
	{
//		deleteGames();
//		deleteWeeks();
//		deleteLeagues();
//		deleteSeasons();
//		deletePlayers();
//		deleteTeams();
//		deleteLeagueTypes();
		
	}
	
	

}
