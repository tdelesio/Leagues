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

import org.junit.Test;

public class GameDaoTest extends AbstractTestCase {
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		createLeagueTypes();
		createTeams();
		createPlayers();
		createSeasons();
		createLeagues();
		createWeeks();
		createGames();
	}

	@Override
	protected void tearDown() throws Exception {
		deleteGames();
		deleteWeeks();
		deleteLeagues();
		deleteSeasons();
		deletePlayers();
		deleteTeams();
		deleteLeagueTypes();
		
		super.tearDown();
	}

	@Test
	public void testfindWeek()
	{
		Week testWeek = gameDao.findWeek(week1.getWeekNumber(), seasonFootball09);
		assertEquals(week1, testWeek);
		
		testWeek = gameDao.findWeek(week2.getWeekNumber(), seasonFootball09);
		assertEquals(week2, testWeek);
		
		assertNull(gameDao.findWeek(week2.getWeekNumber(), seasonFootball08));
		assertNull(gameDao.findWeek(week1.getWeekNumber(), seasonFootball08));
		assertNull(gameDao.findWeek(week2.getWeekNumber(), seasonSuicide08));
		assertNull(gameDao.findWeek(week1.getWeekNumber(), seasonSuicide08));
	}
	
	@Test
	public void testgetCurrentWeek()
	{
		int testGameNumber = gameDao.getCurrentWeek(seasonFootball09);
		assertEquals(1, testGameNumber);
	}
	
	@Test
	public void testfindWeekBySeason()
	{
		List<Week> testWeeks = gameDao.findWeekBySeason(seasonFootball09);
		assertTrue(testWeeks.contains(week1));
		assertTrue(testWeeks.contains(week2));
		assertFalse(testWeeks.contains(week1Suicide));
		assertFalse(testWeeks.contains(week2Suicide));
	}
	
	@Test
	public void testfindGamesByWeek()
	{
		List<Game> testGames = gameDao.findGamesByWeek(week2);
		assertTrue(testGames.isEmpty());
		assertTrue(gameDao.findGamesByWeek(week1Suicide).isEmpty());
		assertTrue(gameDao.findGamesByWeek(week2Suicide).isEmpty());
		testGames = gameDao.findGamesByWeek(week1);
		assertTrue(testGames.contains(game1Week1));
		assertTrue(testGames.contains(game2Week1));
		assertTrue(testGames.contains(game3Week1));
		assertTrue(testGames.contains(game4Week1));
	}
	
	@Test
	public void testfindGameByTeamWeekLeague()
	{
		Game testGame = gameDao.findGameByTeamWeekLeague(giants, week1);
		assertEquals(game1Week1, testGame);
		
		testGame = gameDao.findGameByTeamWeekLeague(cowboys, week1);
		assertEquals(game1Week1, testGame);
		
		testGame = gameDao.findGameByTeamWeekLeague(eagles, week1);
		assertEquals(game2Week1, testGame);
		
		testGame = gameDao.findGameByTeamWeekLeague(redskins, week1);
		assertEquals(game2Week1, testGame);
		
		testGame = gameDao.findGameByTeamWeekLeague(jets, week1);
		assertEquals(game3Week1, testGame);
		
		testGame = gameDao.findGameByTeamWeekLeague(bills, week1);
		assertEquals(game3Week1, testGame);
		
		testGame = gameDao.findGameByTeamWeekLeague(dolphins, week1);
		assertEquals(game4Week1, testGame);
		
		testGame = gameDao.findGameByTeamWeekLeague(patriots, week1);
		assertEquals(game4Week1, testGame);
	}
}
