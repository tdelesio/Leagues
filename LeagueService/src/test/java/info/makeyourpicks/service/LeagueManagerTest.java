package info.makeyourpicks.service;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Payment;
import info.makeyourpicks.model.Season;

import java.util.List;

import org.junit.Test;

public class LeagueManagerTest extends AbstractServiceTestCase{

	@Test
	public void testGetLeagueTypes() {
		assertEquals(true, leagueManager.getLeagueTypes().size()>0);
	}

	@Test
	public void testValidateLeague() {
		assertEquals(true, leagueManager.validateLeague(league1));
	}

	@Test
	public void testGetNumberOfPlayersInLeague() {
		assertEquals(1, leagueManager.getNumberOfPlayersInLeague(league1));
	}

	@Test
	public void testGetFreeLeagues() {
		assertEquals(true, leagueManager.getFreeLeagues().contains(league2));
	}

	@Test
	public void testGetLeagues() {
		assertEquals(true, leagueManager.getLeagues().size()>0);
	}

	@Test
	public void testUpdateLeague() 
	{
		league1.setPaidFor(10);
		leagueManager.createOrUpdateLeague(league1);
		League leagueFromDB = leagueManager.getLeague(league1.getLeagueName());
		assertEquals(10, leagueFromDB.getPaidFor());
	}

//	@Test
//	public void testUpdateLeagueType() {
//		leagueType.setLeagueTypeDisplay("new display");
//		leagueManager.updateLeagueType(leagueType);
//		
//		LeagueType lt = leagueManager.getLeagueType(leagueType.getTypeOfLeague());
//		assertEquals("new display", lt.getLeagueTypeDisplay());
//	}

	@Test
	public void testCreatePayment() {
		Payment payment = new Payment();
		payment = (Payment)payment.createTestObject();
		payment.setLeague(league1);
		leagueManager.createPayment(payment);
		
		dao.deleteObject(payment);
	}

	@Test
	public void testFindMasterLeagueTypes() {
		assertEquals(true, leagueManager.findMasterLeagueTypes().size()>0);
	}
	
	@Test
	public void testGetLeaguesForPlayer()
	{
		List<League> leagues = leagueManager.getLeaguesForPlayer(player);
		assertEquals(true, leagues.contains(league1));
		assertEquals(true, leagues.contains(league2));
	}

	
	
}
