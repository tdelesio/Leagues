package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.AbstractPageTestCase;

import org.junit.Test;

public class CreateLeaguePageTest extends AbstractPageTestCase{

	@Test
	public void testCreateLeaguePage() {
		login();
		
		tester.startPage(CreateLeaguePage.class);
		tester.assertRenderedPage(CreateLeaguePage.class);
	}

}
