package info.makeyourpicks.dao.hibernate;

import info.makeyourpicks.model.Player;
import info.makeyourpicks.test.AbstractTestCase;

public class PlayerDaoTest extends AbstractTestCase {

	protected static Player player = new Player();
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		player = (Player)player.createTestObject();
		dao.save(player);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		dao.deleteObject(player);		
	}
	
	public void test_getPlayer()
	{
		assertEquals(player, playerDao.findPlayerByName(player.getUsername()));
	}
}
