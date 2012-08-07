package info.makeyourpicks.web.football.pages;

import info.makeyourpicks.AbstractSuicidePageTestCase;
import info.makeyourpicks.web.LeagueSession;

import org.junit.Test;

public class WinSummaryPageTest extends AbstractSuicidePageTestCase{

	@Test
	public void testWinSummaryPage() {
		login();
		
		LeagueSession.getWebSession().setActiveLeague(football);
		
		tester.startPage(WinSummaryPage.class);
		tester.assertRenderedPage(WinSummaryPage.class);
	}

}
