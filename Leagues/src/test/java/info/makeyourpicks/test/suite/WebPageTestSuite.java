package info.makeyourpicks.test.suite;

import junit.framework.Test;
import junit.framework.TestSuite;

public class WebPageTestSuite {

	public static Test suite() throws Exception {

		TestSuite suite = new TestSuite("WebTest");

		AdminPageTestSuite.addTests(suite);
		
		// base leagues
		LeaugePageTestSuite.addTests(suite);

		// suicide Tests
		SuicidePageTestSuite.addTests(suite);

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
