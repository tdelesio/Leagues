package info.makeyourpicks.service;

import org.junit.Test;

public class PlayerManagerTest extends AbstractServiceTestCase{

	@Test
	public void testGetAllPlayers() {
		assertEquals(true, playerManager.getAllPlayers().size()>0);
	}

	@Test
	public void testUpdatePlayerProfile() {
		player.setCity("New York");
		playerManager.createUpdatePlayer(player);
		
		assertEquals("New York", playerManager.getPlayer(player.getUsername()).getCity());
	}

	@Test
	public void testGetPlayer() {
		assertEquals(player, playerManager.getPlayer(player.getUsername()));
	}

	@Test
	public void testLoadUserByUsernameString() {
		assertEquals(player, playerManager.loadUserByUsername(player.getUsername()));
	}

	@Test
	public void testGetLeagueByPlayer()
	{
		assertEquals(player, playerManager.getPlayersInLeague(league1).iterator().next());
	}

}
