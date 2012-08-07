package info.makeyourpicks.test.suite;

import info.makeyourpicks.web.authentication.LoginPageTest;
import info.makeyourpicks.web.league.pages.ContactUsPageTest;
import info.makeyourpicks.web.league.pages.CreateLeaguePageTest;
import info.makeyourpicks.web.league.pages.HomePageTest;
import info.makeyourpicks.web.league.pages.JoinLeaguePageTest;
import info.makeyourpicks.web.league.pages.ProfilePageTest;
import info.makeyourpicks.web.league.pages.RegisterPageTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class LeaugePageTestSuite {

	/**
	 * Add all the TestSuites relevant to this package to an external suite
	 * 
	 * @param testSuite
	 */
	public static void addTests(TestSuite testSuite) {
		testSuite.addTestSuite(LoginPageTest.class);
		testSuite.addTestSuite(ContactUsPageTest.class);
		testSuite.addTestSuite(CreateLeaguePageTest.class);
		testSuite.addTestSuite(HomePageTest.class);
		testSuite.addTestSuite(JoinLeaguePageTest.class);
		testSuite.addTestSuite(ProfilePageTest.class);
		testSuite.addTestSuite(RegisterPageTest.class);
		
	}

	/**
	 * Returns a suite of tests
	 * 
	 * @return Test
	 */
	public static Test suite() throws Exception {
		TestSuite suite = new TestSuite();
		LeaugePageTestSuite.addTests(suite);		
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
