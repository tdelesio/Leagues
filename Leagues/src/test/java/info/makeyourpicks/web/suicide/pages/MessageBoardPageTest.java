package info.makeyourpicks.web.suicide.pages;

import info.makeyourpicks.AbstractSuicidePageTestCase;
import info.makeyourpicks.web.LeagueSession;
import info.makeyourpicks.web.league.pages.MessageBoardPage;

import org.junit.Test;

public class MessageBoardPageTest extends AbstractSuicidePageTestCase{

	@Test
	public void testMessageBoardPage() {
		login();
		
		LeagueSession.getWebSession().setActiveLeague(suicide);
		
		tester.startPage(MessageBoardPage.class);
		tester.assertRenderedPage(MessageBoardPage.class);
	}

}
