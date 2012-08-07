package info.makeyourpicks.web.admin.pages;

import info.makeyourpicks.AbstractAdminPageTest;

import org.junit.Test;


public class AdminCreateLeaguePageTest extends AbstractAdminPageTest{
	
	@Test
	public void testAdminCreateLeaguePage()
	{
		login();
		
		//start and render the test page
		tester.startPage(AdminLeaguePage.class);
 
		//assert rendered page class
		tester.assertRenderedPage(AdminLeaguePage.class);

	}
	
	@Override
	protected void setUp() throws Exception 
	{
		super.setUp();
	}

}
