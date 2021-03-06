package info.makeyourpicks.web.football.pages;

import info.makeyourpicks.AbstractSuicidePageTestCase;
import info.makeyourpicks.web.LeagueSession;

import org.junit.Test;

public class ViewPicksPageTest extends AbstractSuicidePageTestCase{

	@Test
	public void testViewPicksPage() {
		login();
		
		LeagueSession.getWebSession().setActiveLeague(football);
		
		tester.startPage(ViewPicksPage.class);
		tester.assertRenderedPage(ViewPicksPage.class);
	}

	@Test
	public void testViewPicksPageInt() {
		login();
		
		LeagueSession.getWebSession().setActiveLeague(football);
		
		tester.setupRequestAndResponse();
		tester.startPage(new ViewPicksPage(week.getWeekNumber()));
		tester.assertRenderedPage(ViewPicksPage.class);
	}

}
