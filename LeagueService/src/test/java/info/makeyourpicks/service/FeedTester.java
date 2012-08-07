package info.makeyourpicks.service;

import info.makeyourpicks.service.impl.GameManagerHibernate;
import junit.framework.TestCase;

public class FeedTester extends TestCase {

	public void testFeed()
	{
		GameManager gameManager = new GameManagerHibernate();
//		gameManager.weekSmartSetup();
	}
}
