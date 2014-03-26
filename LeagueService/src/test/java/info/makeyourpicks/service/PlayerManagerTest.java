package info.makeyourpicks.service;

import info.makeyourpicks.test.AbstractTestCase;

import org.junit.Assert;
import org.junit.Test;

public class PlayerManagerTest extends AbstractTestCase{

	@Test
	public void testGetAllPlayers() {
		Assert.assertEquals(true, playerManager.getAllPlayers().size()>0);
	}

	@Test
	public void testUpdatePlayerProfile() {
		tim.setCity("New York");
		playerManager.createUpdatePlayer(tim);
		
		Assert.assertEquals("New York", playerManager.getPlayer(tim.getUsername()).getCity());
	}

	@Test
	public void testGetPlayer() {
		Assert.assertEquals(tim, playerManager.getPlayer(tim.getUsername()));
	}

	@Test
	public void testLoadUserByUsernameString() {
		Assert.assertEquals(tim, playerManager.loadUserByUsername(tim.getUsername()));
	}

	@Test
	public void testGetLeagueByPlayer()
	{
		Assert.assertEquals(tim, playerManager.getPlayersInLeague(football09League).iterator().next());
	}

}
