package info.makeyourpicks;

import info.makeyourpicks.model.Player;
import info.makeyourpicks.web.LeagueSession;

public abstract class AbstractAdminPageTest extends AbstractSuicidePageTestCase {

	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		LeagueSession.getWebSession().setActiveLeague(suicide);
		
		player.setMemberLevel(Player.ADMIN);
		playerManager.createUpdatePlayer(player);
				
		
	}
	@Override
	protected void tearDown() throws Exception {
		
		
		
		
		super.tearDown();
	}
	
	

	
}
