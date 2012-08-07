package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.AbstractPageTestCase;

import org.junit.Test;

public class RegisterPageTest extends AbstractPageTestCase{

	@Test
	public void testRegisterPage() {
		tester.startPage(RegisterPage.class);
		tester.assertRenderedPage(RegisterPage.class);
	}

}
