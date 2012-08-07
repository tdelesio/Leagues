package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.AbstractPageTestCase;

import org.junit.Test;


public class ContactUsPageTest extends AbstractPageTestCase{

	@Test 
	public void testContactUsPage()
	{
		login();
		
		tester.startPage(ContactUsPage.class);
		tester.assertRenderedPage(ContactUsPage.class);
	}
}
