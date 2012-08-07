package info.makeyourpicks.dao.hibernate;

import java.util.List;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.test.AbstractTestCase;

import org.junit.Test;

public class LeagueDaoTest extends AbstractTestCase {

	protected static League league = new League();
	protected static Player player = new Player();
	protected static LeagueType leagueType = new LeagueType();
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		createLeagueTypes();
		createTeams();
		createPlayers();
		createSeasons();
		createLeagues();
	}

	@Override
	protected void tearDown() throws Exception {
		deleteLeagues();
		deleteSeasons();
		deletePlayers();
		deleteTeams();
		deleteLeagueTypes();
		
		super.tearDown();
	}

	@Test
	public void testfindFreeLeagues()
	{
		List<League> leagues = leagueDao.findFreeLeagues();
		assertTrue(leagues.contains(football09FreeLeague));
		assertTrue(leagues.contains(suicide08FreeLeague));
		assertFalse(leagues.contains(football08League));
		assertFalse(leagues.contains(football09League));
		assertFalse(leagues.contains(suicide08League));
		assertFalse(leagues.contains(suicide09League));
	}
	
	@Test 
	public void testfindLeague()
	{
		League testLeague = leagueDao.findLeague(football09FreeLeague.getLeagueName());
		assertEquals(football09FreeLeague, testLeague);
		
		testLeague = leagueDao.findLeague(suicide08FreeLeague.getLeagueName());
		assertEquals(suicide08FreeLeague, testLeague);
		
		testLeague = leagueDao.findLeague(football08League.getLeagueName());
		assertEquals(football08League, testLeague);
		
		testLeague = leagueDao.findLeague(football09League.getLeagueName());
		assertEquals(football09League, testLeague);
		
		testLeague = leagueDao.findLeague(suicide08FreeLeague.getLeagueName());
		assertEquals(suicide08FreeLeague, testLeague);
		
		testLeague = leagueDao.findLeague(suicide08League.getLeagueName());
		assertEquals(suicide08League, testLeague);
		
		testLeague = leagueDao.findLeague(suicide09League.getLeagueName());
		assertEquals(suicide09League, testLeague);
	}

	@Test
	public void testfindLeagueType()
	{
		LeagueType testLeagueType = leagueDao.findLeagueType(footballLeagueType.getTypeOfLeague());
		assertEquals(footballLeagueType, testLeagueType);
		
		testLeagueType = leagueDao.findLeagueType(suicideLeagueType.getTypeOfLeague());		
		assertEquals(suicideLeagueType, testLeagueType);		
	}
	
	@Test
	public void testfindMasterLeagueTypes()
	{
		List<LeagueType> testLeagueTypes = leagueDao.findMasterLeagueTypes();
		assertTrue(testLeagueTypes.contains(footballLeagueType));
		assertFalse(testLeagueTypes.contains(suicideLeagueType));
			
	}

	@Test
	public void testfindLeaguesBySeason()
	{
		List<League> testLeagues = leagueDao.findLeaguesBySeason(seasonFootball08);
		assertTrue(testLeagues.contains(football08League));
		assertFalse(testLeagues.contains(football09League));
		assertFalse(testLeagues.contains(football09FreeLeague));
		assertFalse(testLeagues.contains(suicide08League));
		assertFalse(testLeagues.contains(suicide09League));
		assertFalse(testLeagues.contains(suicide08FreeLeague));
		
		testLeagues = leagueDao.findLeaguesBySeason(seasonFootball09);
		assertTrue(testLeagues.contains(football09League));
		assertTrue(testLeagues.contains(football09FreeLeague));
		assertFalse(testLeagues.contains(football08League));
		assertFalse(testLeagues.contains(suicide08League));
		assertFalse(testLeagues.contains(suicide09League));
		assertFalse(testLeagues.contains(suicide08FreeLeague));
		
		testLeagues = leagueDao.findLeaguesBySeason(seasonSuicide08);
		assertTrue(testLeagues.contains(suicide08League));
		assertTrue(testLeagues.contains(suicide08FreeLeague));
		assertFalse(testLeagues.contains(football08League));
		assertFalse(testLeagues.contains(football09League));
		assertFalse(testLeagues.contains(football09FreeLeague));
		assertFalse(testLeagues.contains(suicide09League));
		
		testLeagues = leagueDao.findLeaguesBySeason(seasonSuicide09);
		assertTrue(testLeagues.contains(suicide09League));
		assertFalse(testLeagues.contains(football08League));
		assertFalse(testLeagues.contains(football09League));
		assertFalse(testLeagues.contains(football09FreeLeague));
		assertFalse(testLeagues.contains(suicide08League));
		assertFalse(testLeagues.contains(suicide08FreeLeague));
	}

	@Test
	public void testgetLeagueTypeForSeason()
	{
		LeagueType testLeagueType = leagueDao.getLeagueTypeForSeason(seasonFootball08);
		assertEquals(footballLeagueType, testLeagueType);
		
		testLeagueType = leagueDao.getLeagueTypeForSeason(seasonFootball09);
		assertEquals(footballLeagueType, testLeagueType);
		
		testLeagueType = leagueDao.getLeagueTypeForSeason(seasonSuicide08);
		assertEquals(suicideLeagueType, testLeagueType);
		
		testLeagueType = leagueDao.getLeagueTypeForSeason(seasonSuicide09);
		assertEquals(suicideLeagueType, testLeagueType);
	}
	
	@Test
	public void testgetSeason()
	{
		Season testSeason = leagueDao.getSeason(footballLeagueType, seasonFootball08.getStartYear(), seasonFootball08.getEndYear());
		assertEquals(seasonFootball08, testSeason);
		
		testSeason = leagueDao.getSeason(footballLeagueType, seasonFootball09.getStartYear(), seasonFootball09.getEndYear());
		assertEquals(seasonFootball09, testSeason);
		
		testSeason = leagueDao.getSeason(suicideLeagueType, seasonSuicide08.getStartYear(), seasonSuicide08.getEndYear());
		assertEquals(seasonSuicide08, testSeason);
		
		testSeason = leagueDao.getSeason(suicideLeagueType, seasonSuicide09.getStartYear(), seasonSuicide09.getEndYear());
		assertEquals(seasonSuicide09, testSeason);
	}
	
	@Test
	public void testgetSeasonStrng()
	{
		Season testSeason = leagueDao.getSeason(footballLeagueType.getTypeOfLeague(), seasonFootball08.getStartYear(), seasonFootball08.getEndYear());
		assertEquals(seasonFootball08, testSeason);
		
		testSeason = leagueDao.getSeason(footballLeagueType.getTypeOfLeague(), seasonFootball09.getStartYear(), seasonFootball09.getEndYear());
		assertEquals(seasonFootball09, testSeason);
		
		testSeason = leagueDao.getSeason(suicideLeagueType.getTypeOfLeague(), seasonSuicide08.getStartYear(), seasonSuicide08.getEndYear());
		assertEquals(seasonSuicide08, testSeason);
		
		testSeason = leagueDao.getSeason(suicideLeagueType.getTypeOfLeague(), seasonSuicide09.getStartYear(), seasonSuicide09.getEndYear());
		assertEquals(seasonSuicide09, testSeason);
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
