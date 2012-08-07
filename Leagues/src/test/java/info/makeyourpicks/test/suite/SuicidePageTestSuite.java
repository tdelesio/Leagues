package info.makeyourpicks.test.suite;

import info.makeyourpicks.web.league.pages.ActivateLeaguePageTest;
import info.makeyourpicks.web.league.pages.PaymentHistoryPageTest;
import info.makeyourpicks.web.suicide.pages.MakePicksPageTest;
import info.makeyourpicks.web.suicide.pages.MessageBoardPageTest;
import info.makeyourpicks.web.suicide.pages.ViewPicksPageTest;
import info.makeyourpicks.web.suicide.pages.WinSummaryPageTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class SuicidePageTestSuite {

	/**
	 * Add all the TestSuites relevant to this package to an external suite
	 * 
	 * @param testSuite
	 */
	public static void addTests(TestSuite testSuite) {
		testSuite.addTestSuite(ActivateLeaguePageTest.class);
//		testSuite.addTestSuite(LeagueDetailsPageTest.class);
		testSuite.addTestSuite(PaymentHistoryPageTest.class);
		testSuite.addTestSuite(MakePicksPageTest.class);
		testSuite.addTestSuite(ViewPicksPageTest.class);
		testSuite.addTestSuite(WinSummaryPageTest.class);
		testSuite.addTestSuite(MessageBoardPageTest.class);
		
	}

	/**
	 * Returns a suite of tests
	 * 
	 * @return Test
	 */
	public static Test suite() throws Exception {
		TestSuite suite = new TestSuite();
		SuicidePageTestSuite.addTests(suite);		
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
