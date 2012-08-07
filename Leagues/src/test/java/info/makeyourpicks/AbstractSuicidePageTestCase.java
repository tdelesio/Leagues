package info.makeyourpicks;

import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.League;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Team;
import info.makeyourpicks.model.Week;

public abstract class AbstractSuicidePageTestCase extends AbstractFootballPageTest {

	protected static League suicide;
	protected static Week week;
	protected static Team team1;
	protected static Team team2;
	protected static Game game1;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		
		suicide = new League();
		suicide = (League)suicide.createTestObject();
		suicide.setLeagueName("jtestsuicide");
		suicide.setAdmin(player);
//		suicide.setLeagueType(leagueManager.getLeagueType("suicide"));
		leagueManager.createOrUpdateLeague(suicide);
		
		leagueManager.addPlayerToLeague(suicide, player);
		
		LeagueType leagueType = leagueManager.getLeagueType("football");
		week = new Week();
		week = (Week)week.createTestObject();
//		week.setLeagueType(leagueType);
		
		gameManager.insertWeek(week);
		
		team1 = new Team();
		team1 = (Team)team1.createTestObject();
		team1.setLeagueType(leagueType);
		
		team2 = new Team();
		team2 = (Team)team2.createTestObject();
		team2.setLeagueType(leagueType);
		
		teamManager.insertTeam(team1);
		teamManager.insertTeam(team2);
		
		game1 = new Game();
		game1 = (Game)game1.createTestObject();
		game1.setDog(team2);
		game1.setFav(team1);
		game1.setSpread(12.5);
		game1.setFavHome(true);
		game1.setWeek(week);
		
		gameManager.insertGame(game1);
		
	}

	@Override
	protected void tearDown() throws Exception {
		gameManager.deleteGame(game1);
		
		gameManager.deleteWeek(week);
		
		teamManager.deleteTeam(team1);
		teamManager.deleteTeam(team2);
		
		leagueManager.removePlayerFromLeague(suicide, player);
		leagueManager.deleteLeague(suicide);
		
		super.tearDown();
		
		
	}

	
}
