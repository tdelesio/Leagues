package info.makeyourpicks.test.suite;

import info.makeyourpicks.web.admin.pages.AdminCreateLeaguePageTest;
import info.makeyourpicks.web.admin.pages.AdminLeaguesPageTest;
import info.makeyourpicks.web.admin.pages.AdminPlayersPageTest;
import info.makeyourpicks.web.admin.pages.CreateLeagueTypePageTest;
import info.makeyourpicks.web.admin.pages.CreateWeekPageTest;
import info.makeyourpicks.web.admin.pages.DisplayPlayerProfilePageTest;
import info.makeyourpicks.web.admin.pages.EnterScoresPageTest;
import info.makeyourpicks.web.admin.pages.EnterTeamsPageTest;
import info.makeyourpicks.web.admin.pages.SetupWeekPageTest;
import info.makeyourpicks.web.admin.pages.UpdateWeekPageTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AdminPageTestSuite {

	/**
	 * Add all the TestSuites relevant to this package to an external suite
	 * 
	 * @param testSuite
	 */
	public static void addTests(TestSuite testSuite) {
		testSuite.addTestSuite(AdminCreateLeaguePageTest.class);
		testSuite.addTestSuite(AdminLeaguesPageTest.class);
		testSuite.addTestSuite(AdminPlayersPageTest.class);
		testSuite.addTestSuite(CreateLeagueTypePageTest.class);
		testSuite.addTestSuite(CreateWeekPageTest.class);
		testSuite.addTestSuite(DisplayPlayerProfilePageTest.class);
		testSuite.addTestSuite(EnterScoresPageTest.class);
		testSuite.addTestSuite(EnterTeamsPageTest.class);
		testSuite.addTestSuite(SetupWeekPageTest.class);
		testSuite.addTestSuite(UpdateWeekPageTest.class);
		
	}

	/**
	 * Returns a suite of tests
	 * 
	 * @return Test
	 */
	public static Test suite() throws Exception {
		TestSuite suite = new TestSuite();
		AdminPageTestSuite.addTests(suite);		
		return suite;
	}
	
	/**
	 * Runs the test suite using the textual runner.
	 * 
	 */
	public static void main(String[] args) throws Exception {
		junit.textui.TestRunner.run(suite());
	}
}
