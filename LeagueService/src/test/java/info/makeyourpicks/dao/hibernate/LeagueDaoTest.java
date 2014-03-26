package info.makeyourpicks.dao.hibernate;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.test.AbstractTestCase;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class LeagueDaoTest extends AbstractTestCase {

	protected static League league = new League();
	protected static Player player = new Player();
	protected static LeagueType leagueType = new LeagueType();
	

	@Test
	public void testfindFreeLeagues()
	{
		List<League> leagues = leagueDao.findFreeLeagues();
		Assert.assertTrue(leagues.contains(football09FreeLeague));
		Assert.assertTrue(leagues.contains(suicide08FreeLeague));
		Assert.assertFalse(leagues.contains(football08League));
		Assert.assertFalse(leagues.contains(football09League));
		Assert.assertFalse(leagues.contains(suicide08League));
		Assert.assertFalse(leagues.contains(suicide09League));
	}
	
	@Test 
	public void testfindLeague()
	{
		League testLeague = leagueDao.findLeague(football09FreeLeague.getLeagueName());
		Assert.assertEquals(football09FreeLeague, testLeague);
		
		testLeague = leagueDao.findLeague(suicide08FreeLeague.getLeagueName());
		Assert.assertEquals(suicide08FreeLeague, testLeague);
		
		testLeague = leagueDao.findLeague(football08League.getLeagueName());
		Assert.assertEquals(football08League, testLeague);
		
		testLeague = leagueDao.findLeague(football09League.getLeagueName());
		Assert.assertEquals(football09League, testLeague);
		
		testLeague = leagueDao.findLeague(suicide08FreeLeague.getLeagueName());
		Assert.assertEquals(suicide08FreeLeague, testLeague);
		
		testLeague = leagueDao.findLeague(suicide08League.getLeagueName());
		Assert.assertEquals(suicide08League, testLeague);
		
		testLeague = leagueDao.findLeague(suicide09League.getLeagueName());
		Assert.assertEquals(suicide09League, testLeague);
	}

	@Test
	public void testfindLeagueType()
	{
		LeagueType testLeagueType = leagueDao.findLeagueType(footballLeagueType.getTypeOfLeague());
		Assert.assertEquals(footballLeagueType, testLeagueType);
		
		testLeagueType = leagueDao.findLeagueType(suicideLeagueType.getTypeOfLeague());		
		Assert.assertEquals(suicideLeagueType, testLeagueType);		
	}
	
	@Test
	public void testfindMasterLeagueTypes()
	{
		List<LeagueType> testLeagueTypes = leagueDao.findMasterLeagueTypes();
		Assert.assertTrue(testLeagueTypes.contains(footballLeagueType));
		Assert.assertFalse(testLeagueTypes.contains(suicideLeagueType));
			
	}

	@Test
	public void testfindLeaguesBySeason()
	{
		List<League> testLeagues = leagueDao.findLeaguesBySeason(seasonFootball08);
		Assert.assertTrue(testLeagues.contains(football08League));
		Assert.assertFalse(testLeagues.contains(football09League));
		Assert.assertFalse(testLeagues.contains(football09FreeLeague));
		Assert.assertFalse(testLeagues.contains(suicide08League));
		Assert.assertFalse(testLeagues.contains(suicide09League));
		Assert.assertFalse(testLeagues.contains(suicide08FreeLeague));
		
		testLeagues = leagueDao.findLeaguesBySeason(seasonFootball09);
		Assert.assertTrue(testLeagues.contains(football09League));
		Assert.assertTrue(testLeagues.contains(football09FreeLeague));
		Assert.assertFalse(testLeagues.contains(football08League));
		Assert.assertFalse(testLeagues.contains(suicide08League));
		Assert.assertFalse(testLeagues.contains(suicide09League));
		Assert.assertFalse(testLeagues.contains(suicide08FreeLeague));
		
		testLeagues = leagueDao.findLeaguesBySeason(seasonSuicide08);
		Assert.assertTrue(testLeagues.contains(suicide08League));
		Assert.assertTrue(testLeagues.contains(suicide08FreeLeague));
		Assert.assertFalse(testLeagues.contains(football08League));
		Assert.assertFalse(testLeagues.contains(football09League));
		Assert.assertFalse(testLeagues.contains(football09FreeLeague));
		Assert.assertFalse(testLeagues.contains(suicide09League));
		
		testLeagues = leagueDao.findLeaguesBySeason(seasonSuicide09);
		Assert.assertTrue(testLeagues.contains(suicide09League));
		Assert.assertFalse(testLeagues.contains(football08League));
		Assert.assertFalse(testLeagues.contains(football09League));
		Assert.assertFalse(testLeagues.contains(football09FreeLeague));
		Assert.assertFalse(testLeagues.contains(suicide08League));
		Assert.assertFalse(testLeagues.contains(suicide08FreeLeague));
	}

	@Test
	public void testgetLeagueTypeForSeason()
	{
		LeagueType testLeagueType = leagueDao.getLeagueTypeForSeason(seasonFootball08);
		Assert.assertEquals(footballLeagueType, testLeagueType);
		
		testLeagueType = leagueDao.getLeagueTypeForSeason(seasonFootball09);
		Assert.assertEquals(footballLeagueType, testLeagueType);
		
		testLeagueType = leagueDao.getLeagueTypeForSeason(seasonSuicide08);
		Assert.assertEquals(suicideLeagueType, testLeagueType);
		
		testLeagueType = leagueDao.getLeagueTypeForSeason(seasonSuicide09);
		Assert.assertEquals(suicideLeagueType, testLeagueType);
	}
	
	@Test
	public void testgetSeason()
	{
		Season testSeason = leagueDao.getSeason(footballLeagueType, seasonFootball08.getStartYear(), seasonFootball08.getEndYear());
		Assert.assertEquals(seasonFootball08, testSeason);
		
		testSeason = leagueDao.getSeason(footballLeagueType, seasonFootball09.getStartYear(), seasonFootball09.getEndYear());
		Assert.assertEquals(seasonFootball09, testSeason);
		
		testSeason = leagueDao.getSeason(suicideLeagueType, seasonSuicide08.getStartYear(), seasonSuicide08.getEndYear());
		Assert.assertEquals(seasonSuicide08, testSeason);
		
		testSeason = leagueDao.getSeason(suicideLeagueType, seasonSuicide09.getStartYear(), seasonSuicide09.getEndYear());
		Assert.assertEquals(seasonSuicide09, testSeason);
	}
	
	@Test
	public void testgetSeasonStrng()
	{
		Season testSeason = leagueDao.getSeason(footballLeagueType.getTypeOfLeague(), seasonFootball08.getStartYear(), seasonFootball08.getEndYear());
		Assert.assertEquals(seasonFootball08, testSeason);
		
		testSeason = leagueDao.getSeason(footballLeagueType.getTypeOfLeague(), seasonFootball09.getStartYear(), seasonFootball09.getEndYear());
		Assert.assertEquals(seasonFootball09, testSeason);
		
		testSeason = leagueDao.getSeason(suicideLeagueType.getTypeOfLeague(), seasonSuicide08.getStartYear(), seasonSuicide08.getEndYear());
		Assert.assertEquals(seasonSuicide08, testSeason);
		
		testSeason = leagueDao.getSeason(suicideLeagueType.getTypeOfLeague(), seasonSuicide09.getStartYear(), seasonSuicide09.getEndYear());
		Assert.assertEquals(seasonSuicide09, testSeason);
	}
	
//	@Test
//	public void testfindLeaguesByPlayer()
//	{
//		leagueDao.findLeaguesByPlayer(player)
//	}
//	
//	
//	@Test
//	public void testfindNumberOfPlayersInLeague(League league)
//	{
//		
//	}
}
