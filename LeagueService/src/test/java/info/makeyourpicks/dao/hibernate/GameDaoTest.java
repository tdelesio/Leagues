package info.makeyourpicks.dao.hibernate;

import java.util.List;

import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.League;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.Team;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.test.AbstractTestCase;

import org.junit.Assert;
import org.junit.Test;

public class GameDaoTest extends AbstractTestCase {
	
	


	@Test
	public void testfindWeek()
	{
		Week testWeek = gameDao.findWeek(week1.getWeekNumber(), seasonFootball09);
		Assert.assertEquals(week1, testWeek);
		
		testWeek = gameDao.findWeek(week2.getWeekNumber(), seasonFootball09);
		Assert.assertEquals(week2, testWeek);
		
		Assert.assertNull(gameDao.findWeek(week2.getWeekNumber(), seasonFootball08));
		Assert.assertNull(gameDao.findWeek(week1.getWeekNumber(), seasonFootball08));
		Assert.assertNull(gameDao.findWeek(week2.getWeekNumber(), seasonSuicide08));
		Assert.assertNull(gameDao.findWeek(week1.getWeekNumber(), seasonSuicide08));
	}
	
	@Test
	public void testgetCurrentWeek()
	{
		int testGameNumber = gameDao.getCurrentWeek(seasonFootball09);
		Assert.assertEquals(1, testGameNumber);
	}
	
	@Test
	public void testfindWeekBySeason()
	{
		List<Week> testWeeks = gameDao.findWeekBySeason(seasonFootball09);
		Assert.assertTrue(testWeeks.contains(week1));
		Assert.assertTrue(testWeeks.contains(week2));
		Assert.assertFalse(testWeeks.contains(week1Suicide));
		Assert.assertFalse(testWeeks.contains(week2Suicide));
	}
	
	@Test
	public void testfindGamesByWeek()
	{
		List<Game> testGames = gameDao.findGamesByWeek(week2);
		Assert.assertTrue(testGames.isEmpty());
		Assert.assertTrue(gameDao.findGamesByWeek(week1Suicide).isEmpty());
		Assert.assertTrue(gameDao.findGamesByWeek(week2Suicide).isEmpty());
		testGames = gameDao.findGamesByWeek(week1);
		Assert.assertTrue(testGames.contains(game1Week1));
		Assert.assertTrue(testGames.contains(game2Week1));
		Assert.assertTrue(testGames.contains(game3Week1));
		Assert.assertTrue(testGames.contains(game4Week1));
	}
	
	@Test
	public void testfindGameByTeamWeekLeague()
	{
		Game testGame = gameDao.findGameByTeamWeekLeague(giants, week1);
		Assert.assertEquals(game1Week1, testGame);
		
		testGame = gameDao.findGameByTeamWeekLeague(cowboys, week1);
		Assert.assertEquals(game1Week1, testGame);
		
		testGame = gameDao.findGameByTeamWeekLeague(eagles, week1);
		Assert.assertEquals(game2Week1, testGame);
		
		testGame = gameDao.findGameByTeamWeekLeague(redskins, week1);
		Assert.assertEquals(game2Week1, testGame);
		
		testGame = gameDao.findGameByTeamWeekLeague(jets, week1);
		Assert.assertEquals(game3Week1, testGame);
		
		testGame = gameDao.findGameByTeamWeekLeague(bills, week1);
		Assert.assertEquals(game3Week1, testGame);
		
		testGame = gameDao.findGameByTeamWeekLeague(dolphins, week1);
		Assert.assertEquals(game4Week1, testGame);
		
		testGame = gameDao.findGameByTeamWeekLeague(patriots, week1);
		Assert.assertEquals(game4Week1, testGame);
	}
}
