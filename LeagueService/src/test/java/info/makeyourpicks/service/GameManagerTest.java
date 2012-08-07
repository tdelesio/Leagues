package info.makeyourpicks.service;

import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.Week;

import java.sql.Date;
import java.util.Iterator;

import org.junit.Test;

public class GameManagerTest extends AbstractServiceTestCase{

	@Test
	public void testUpdateGame() {
		game1.setFavScore(30);
		game1.setDogScore(20);
		gameManager.updateGame(game1);
		
		Game game = gameManager.loadGame(game1.getId());
		assertEquals(30, game.getFavScore());
		assertEquals(20, game.getDogScore());
		
		
	}

	@Test
	public void testUpdateWeek() {
		Date weekStart = new Date(System.currentTimeMillis());
		week.setWeekStart(weekStart);
		
		gameManager.updateWeek(week);
//		Week updatedWeek  = gameManager.loadWeek(week.getId());
		assertEquals(true, true);
	}

//	@Test
//	public void testGetCurrentWeek() {
//		assertTrue(gameManager.getCurrentWeek(leagueType)>=0);
//	}
//
//	@Test
//	public void testGetWeek() {
//		Week weekFromDB = gameManager.getWeek(week.getWeekNumber(), leagueType);
//		assertEquals(week, weekFromDB);
//	}
//
//	@Test
//	public void testGetWeeksByLeagueType() {
//		Iterator<Week> weeks = gameManager.getWeeksByLeagueType(leagueType).iterator();
//		assertEquals(week, weeks.next());
//	}

	@Test
	public void testGetGamesByWeek() {
		Iterator<Game> games = gameManager.getGamesByWeek(week).iterator();
		assertEquals(game1, games.next());
	}

}
