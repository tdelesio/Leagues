package info.makeyourpicks.test.suite;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllPagesTestSuite {

	public static Test suite() throws Exception {

		TestSuite suite = new TestSuite();
		suite.addTest(WebPageTestSuite.suite());
		
		suite.addTest(AllFrameworkTestSuite.suite());		
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
