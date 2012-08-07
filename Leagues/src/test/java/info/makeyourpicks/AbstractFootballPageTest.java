package info.makeyourpicks;

import info.makeyourpicks.model.League;

public abstract class AbstractFootballPageTest extends AbstractPageTestCase {

	protected static League football;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		football = new League();
		football = (League)football.createTestObject();
		football.setLeagueName("jtestfootball");
		football.setAdmin(player);
//		football.setLeagueType(leagueManager.getLeagueType("football"));
		leagueManager.createOrUpdateLeague(football);
		
		leagueManager.addPlayerToLeague(football, player);
	}

	@Override
	protected void tearDown() throws Exception {
		leagueManager.removePlayerFromLeague(football, player);
		leagueManager.deleteLeague(football);
		
		super.tearDown();
		
		
	}
	
}
