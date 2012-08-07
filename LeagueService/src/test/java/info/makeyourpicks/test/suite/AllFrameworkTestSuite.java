package info.makeyourpicks.test.suite;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllFrameworkTestSuite {

	/**
	 * Returns a suite of tests
	 * 
	 * @return Test
	 */
	public static Test suite() throws Exception {

		TestSuite suite = new TestSuite("ServiceTest");

		// Dao Tests
		DaoTestSuite.addTests(suite);

		// Service Tests
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
