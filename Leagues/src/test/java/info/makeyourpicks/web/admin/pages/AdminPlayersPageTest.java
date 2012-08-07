package info.makeyourpicks.web.admin.pages;

import info.makeyourpicks.AbstractAdminPageTest;

import org.junit.Test;

public class AdminPlayersPageTest extends AbstractAdminPageTest{

	@Test
	public void testAdminPlayersPage() {
		tester.setupRequestAndResponse();
		login();
		
		tester.startPage(new AdminPlayersPage());
		tester.assertRenderedPage(AdminPlayersPage.class);
	}

}
