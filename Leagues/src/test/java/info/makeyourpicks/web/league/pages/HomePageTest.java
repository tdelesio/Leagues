package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.AbstractPageTestCase;

import org.junit.Test;

public class HomePageTest extends AbstractPageTestCase {

	@Test
	public void testHomePage()
	{
		login();
		
		tester.startPage(HomePage.class);
		tester.assertRenderedPage(JoinLeaguePage.class);
	}
}
