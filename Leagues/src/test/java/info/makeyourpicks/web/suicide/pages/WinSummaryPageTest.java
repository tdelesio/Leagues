package info.makeyourpicks.web.suicide.pages;

import info.makeyourpicks.AbstractSuicidePageTestCase;
import info.makeyourpicks.web.LeagueSession;

import org.junit.Test;

public class WinSummaryPageTest extends AbstractSuicidePageTestCase{

	@Test
	public void testWinSummaryPage() {
		login();
		
		LeagueSession.getWebSession().setActiveLeague(suicide);
		
		tester.startPage(WinSummaryPage.class);
		tester.assertRenderedPage(WinSummaryPage.class);
	}

}
