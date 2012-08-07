package info.makeyourpicks.web.suicide.pages;

import info.makeyourpicks.AbstractSuicidePageTestCase;
import info.makeyourpicks.web.LeagueSession;

import org.junit.Test;

public class MakePicksPageTest extends AbstractSuicidePageTestCase{

	@Test
	public void testMakePicksPage() {
		login();
		
		LeagueSession.getWebSession().setActiveLeague(suicide);
		
		tester.startPage(MakePicksPage.class);
		tester.assertRenderedPage(MakePicksPage.class);
	}

	@Test
	public void testMakePicksPageInt() {
		login();
		
		LeagueSession.getWebSession().setActiveLeague(suicide);
		
		tester.setupRequestAndResponse();
		
		tester.startPage(new MakePicksPage(week.getWeekNumber()));
		tester.assertRenderedPage(MakePicksPage.class);
	}

}
