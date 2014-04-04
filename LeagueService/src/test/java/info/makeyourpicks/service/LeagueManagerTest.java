package info.makeyourpicks.service;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Payment;
import info.makeyourpicks.test.AbstractTestCase;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class LeagueManagerTest extends AbstractTestCase{

	@Test
	public void testGetLeagueTypes() {
		Assert.assertEquals(true, leagueManager.getLeagueTypes().size()>0);
	}

//	@Test
//	public void testValidateLeague() {
//		Assert.assertEquals(true, leagueManager.validateLeague(football09League));
//	}

	@Test
	public void testGetNumberOfPlayersInLeague() {
		Assert.assertEquals(1, leagueManager.getNumberOfPlayersInLeague(football09League));
	}

	@Test
	public void testGetFreeLeagues() {
		Assert.assertFalse(leagueManager.getFreeLeagues().contains(football09League));
	}

//	@Test
//	public void testGetLeagues() {
//		Assert.assertEquals(true, leagueManager.getLeagues().size()>0);
//	}

	@Test
	public void testUpdateLeague() 
	{
		football09League.setPaidFor(10);
		leagueManager.createOrUpdateLeague(football09League);
		League leagueFromDB = leagueManager.getLeague(football09League.getLeagueName());
		Assert.assertEquals(10, leagueFromDB.getPaidFor());
	}

//	@Test
//	public void testUpdateLeagueType() {
//		leagueType.setLeagueTypeDisplay("new display");
//		leagueManager.updateLeagueType(leagueType);
//		
//		LeagueType lt = leagueManager.getLeagueType(leagueType.getTypeOfLeague());
//		Assert.assertEquals("new display", lt.getLeagueTypeDisplay());
//	}

	@Test
	public void testCreatePayment() {
		Payment payment = new Payment();
		payment = (Payment)payment();
		payment.setLeague(football09League);
		leagueManager.createPayment(payment);
		
		dao.deleteObject(payment);
	}

	@Test
	public void testFindMasterLeagueTypes() {
		Assert.assertEquals(true, leagueManager.findMasterLeagueTypes().size()>0);
	}
	
	@Test
	public void testGetLeaguesForPlayer()
	{
		List<League> leagues = leagueManager.getLeaguesForPlayerTX(tim);
		Assert.assertEquals(true, leagues.contains(football09League));
		Assert.assertEquals(true, leagues.contains(football08League));
	}

	
	
}
