package info.makeyourpicks.web.football.pages;

import info.makeyourpicks.AbstractSuicidePageTestCase;
import info.makeyourpicks.web.LeagueSession;
import info.makeyourpicks.web.league.pages.MessageBoardPage;

import org.junit.Test;

public class MessageBoardPageTest extends AbstractSuicidePageTestCase{

	@Test
	public void testMessageBoardPage() {
		login();
		
		LeagueSession.getWebSession().setActiveLeague(football);
		
		tester.startPage(MessageBoardPage.class);
		tester.assertRenderedPage(MessageBoardPage.class);
	}

}
