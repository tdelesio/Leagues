package info.makeyourpicks.service;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.WinSummary;
import info.makeyourpicks.test.AbstractTestCase;

import java.util.List;

import org.junit.Test;

public class PicksManagerTest extends AbstractTestCase {

//	@Test
//	public void testUpdatePlayerPick() {
//		
//		pick1.setTeam(team2);
//		picksManager.updatePlayerPick(pick1, false);
//		
//		Picks picksFromDB = picksManager.loadPicks(pick1.getId());
//		assertEquals(team2, picksFromDB.getTeam());
//		
//		
//	}
//
//	@Test
//	public void testSpreadsWinner()
//	{
//		Game game = gameManager.loadGame(game1.getId());
//		assertEquals(game1, game);
//		game.setDogScore(40);
//		game.setFavScore(50);
//		gameManager.updateGame(game);
//		
//		Picks picksFromDB = picksManager.loadPicks(pick1.getId());
//		assertEquals(Picks.LOSER, picksFromDB.getWinnerStatus());
//	}
//	
//	@Test
//	public void testNoSpreadsWinner()
//	{
//		league1.setSpreads(false);
//		leagueManager.createOrUpdateLeague(league1);
//		
//		Game game = gameManager.loadGame(game1.getId());
//		assertEquals(game1, game);
//		game.setDogScore(40);
//		game.setFavScore(50);
//		gameManager.updateGame(game);
//		
//		Picks picksFromDB = picksManager.loadPicks(pick1.getId());
//		assertEquals(Picks.WINNER, picksFromDB.getWinnerStatus());
//	}
//	
//	@Test
//	public void testGetPicksByLeague() {
//		List<Picks> picks = picksManager.getPicksByLeague(league1);
//		System.out.println(picks.get(0).getId());
//		System.out.println(picks.contains(pick1));
//		System.out.println(pick1.getId());
//		assertTrue(picks.contains(pick1));
//	}
//
////	@Test
////	public void testGetPicksByWeek() {
////		assertTrue(picksManager.getPicksByWeek(week).contains(pick1));
////	}
//
//	@Test
//	public void testGetPicksByLeagueAndWeek() {
//		assertTrue(picksManager.getPicksByLeagueAndWeek(league1, week).contains(pick1));
//	}
//
////	@Test
////	public void testGetPicksByPlayer() {
////		assertTrue(picksManager.getPicksByPlayer(player).contains(pick1));
////	}
////
////	@Test
////	public void testGetPicksByPlayerAndWeek() {
////		assertTrue(picksManager.getPicksByPlayerAndWeek(player, week).contains(pick1));
////	}
//
//	@Test
//	public void testGetPicksByPlayerLeagueAndWeek() {
//		assertTrue(picksManager.getPicksByPlayerLeagueAndWeek(player, league1, week).contains(pick1));
//	}

	@Test
	public void testCalculateMoney()
	{
//		League league = leagueManager.getLeague("testtest");
		List<WinSummary> winsummaries = picksManager.getWinSummaryTX(football09League);
		for (WinSummary winSummary : winsummaries)
		{
			System.out.println(winSummary.getPlayer().getUsername()+" "+winSummary.getMoneyWon());
		}
	}
}
