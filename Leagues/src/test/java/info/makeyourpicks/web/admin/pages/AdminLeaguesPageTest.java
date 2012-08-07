package info.makeyourpicks.web.admin.pages;

import info.makeyourpicks.AbstractAdminPageTest;

import org.junit.Test;

public class AdminLeaguesPageTest extends AbstractAdminPageTest{

	@Test
	public void testAdminLeaguesPage() {
		login();
		
		tester.startPage(AdminLeaguesPage.class);
		tester.assertRenderedPage(AdminLeaguesPage.class);
	}

}
