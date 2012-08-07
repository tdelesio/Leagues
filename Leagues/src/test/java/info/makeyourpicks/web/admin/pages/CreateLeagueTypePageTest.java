package info.makeyourpicks.web.admin.pages;

import info.makeyourpicks.AbstractAdminPageTest;

import org.junit.Test;

public class CreateLeagueTypePageTest extends AbstractAdminPageTest{

	@Test
	public void testCreateLeagueTypePage() {
		login();
		tester.setupRequestAndResponse();
		tester.startPage(new AdminLeagueTypePage());
		tester.assertRenderedPage(AdminLeagueTypePage.class);
	}

}
