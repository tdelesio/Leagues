package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.AbstractSuicidePageTestCase;
import info.makeyourpicks.web.LeagueSession;

import org.junit.Test;

public class ActivateLeaguePageTest extends AbstractSuicidePageTestCase{

	@Test
	public void testActivateLeaguePage() {
		login();
		
		LeagueSession.getWebSession().setActiveLeague(suicide);
		
		tester.startPage(ActivateLeaguePage.class);
		tester.assertRenderedPage(ActivateLeaguePage.class);
	}

	

}
