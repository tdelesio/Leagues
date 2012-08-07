package info.makeyourpicks.web.admin.pages;

import info.makeyourpicks.AbstractAdminPageTest;

import org.junit.Test;

public class EnterTeamsPageTest extends AbstractAdminPageTest{

	@Test
	public void testEnterTeamsPage() {
		login();
		
		tester.startPage(new EnterTeamsPage());
		tester.assertRenderedPage(EnterTeamsPage.class);
	}

}
