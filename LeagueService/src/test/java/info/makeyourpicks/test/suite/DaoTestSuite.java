package info.makeyourpicks.test.suite;

import info.makeyourpicks.dao.hibernate.LeagueDaoTest;
import info.makeyourpicks.dao.hibernate.MessageBoardDaoTest;
import info.makeyourpicks.dao.hibernate.PlayerDaoTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class DaoTestSuite {

	/**
	 * Add all the TestSuites relevant to this package to an external suite
	 * 
	 * @param testSuite
	 */
	public static void addTests(TestSuite testSuite) {
		testSuite.addTestSuite(LeagueDaoTest.class);
		testSuite.addTestSuite(MessageBoardDaoTest.class);
		testSuite.addTestSuite(PlayerDaoTest.class);
	}

	/**
	 * Returns a suite of tests
	 * 
	 * @return Test
	 */
	public static Test suite() throws Exception {
		TestSuite suite = new TestSuite();
		DaoTestSuite.addTests(suite);		
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
