package info.makeyourpicks.test.suite;

import info.makeyourpicks.service.GameManagerTest;
import info.makeyourpicks.service.LeagueManagerTest;
import info.makeyourpicks.service.MessageBoardManagerTest;
import info.makeyourpicks.service.PicksManagerTest;
import info.makeyourpicks.service.PlayerManagerTest;
import info.makeyourpicks.service.TeamManagerTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class ServiceTestSuite {

	/**
	 * Add all the TestSuites relevant to this package to an external suite
	 * 
	 * @param testSuite
	 */
	public static void addTests(TestSuite testSuite) {
		testSuite.addTestSuite(PicksManagerTest.class);
		testSuite.addTestSuite(TeamManagerTest.class);
		testSuite.addTestSuite(GameManagerTest.class);
		testSuite.addTestSuite(LeagueManagerTest.class);
		testSuite.addTestSuite(PlayerManagerTest.class);
		testSuite.addTestSuite(MessageBoardManagerTest.class);
	}

	/**
	 * Returns a suite of tests
	 * 
	 * @return Test
	 */
	public static Test suite() throws Exception {
		TestSuite suite = new TestSuite();
		ServiceTestSuite.addTests(suite);		
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
