package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.AbstractPageTestCase;

import org.junit.Test;


public class JoinLeaguePageTest extends AbstractPageTestCase{

	@Test 
	public void testJoinLeaguePage()
	{
		login();
		
		tester.startPage(JoinLeaguePage.class);
		tester.assertRenderedPage(JoinLeaguePage.class);
	}
}
