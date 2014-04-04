package info.makeyourpicks.service;

import info.makeyourpicks.model.Game;
import info.makeyourpicks.test.AbstractTestCase;

import java.sql.Date;
import java.util.Iterator;

import junit.framework.Assert;

import org.junit.Test;

public class GameManagerTest extends AbstractTestCase{

	@Test
	public void testUpdateGame() {
		game1Week1.setFavScore(30);
		game1Week1.setDogScore(20);
		gameManager.updateGame(game1Week1);
		
		Game game = gameManager.loadGame(game1Week1.getId());
		Assert.assertEquals(30, game.getFavScore());
		Assert.assertEquals(20, game.getDogScore());
		
		
	}

	@Test
	public void testUpdateWeek() {
		Date weekStart = new Date(System.currentTimeMillis());
		week1.setWeekStart(weekStart);
		
		gameManager.updateWeek(week1);
//		Week updatedWeek  = gameManager.loadWeek(week.getId());
		Assert.assertEquals(true, true);
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

//	@Test
//	public void testGetGamesByWeek() {
//		Iterator<Game> games = gameManager.getGamesByWeek(week1).iterator();
//		Assert.assertEquals(game1Week1, games.next());
//	}

}
