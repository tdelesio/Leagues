package info.makeyourpicks.test.suite;

import info.makeyourpicks.web.football.pages.MakePicksPageTest;
import info.makeyourpicks.web.football.pages.MessageBoardPageTest;
import info.makeyourpicks.web.football.pages.ViewPicksPageTest;
import info.makeyourpicks.web.football.pages.WinSummaryPageTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class FootballPageTestSuite {

	/**
	 * Add all the TestSuites relevant to this package to an external suite
	 * 
	 * @param testSuite
	 */
	public static void addTests(TestSuite testSuite) {
		testSuite.addTestSuite(MakePicksPageTest.class);
		testSuite.addTestSuite(ViewPicksPageTest.class);
		testSuite.addTestSuite(WinSummaryPageTest.class);
//		testSuite.addTestSuite(LeagueDetailsPageTest.class);
		testSuite.addTestSuite(MessageBoardPageTest.class);
		
	}

	/**
	 * Returns a suite of tests
	 * 
	 * @return Test
	 */
	public static Test suite() throws Exception {
		TestSuite suite = new TestSuite();
		FootballPageTestSuite.addTests(suite);		
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
