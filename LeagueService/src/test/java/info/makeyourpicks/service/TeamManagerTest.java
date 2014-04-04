package info.makeyourpicks.service;

import info.makeyourpicks.model.Team;
import info.makeyourpicks.test.AbstractTestCase;

import org.junit.Assert;
import org.junit.Test;

public class TeamManagerTest extends AbstractTestCase{
	
	


	@Test
	public void testLoadTeam() {
		
		
		Team testTeam = teamManager.loadTeam(giants.getId());
		Assert.assertEquals(giants, testTeam);
		
		Team testTeam2 = teamManager.loadTeam(cowboys.getId());
		Assert.assertEquals(cowboys, testTeam2);
		
	}
	
	@Test
	public void testLoadAllTeams() {
		Assert.assertEquals(true, teamManager.loadAllTeams().size()>=2);
	}
//	
//	@Test
//	public void testGetTeamsByLeagueType()
//	{
//		Assert.assertEquals(8, teamManager.getTeamsByLeagueType(footballLeagueType).size());
//	}

}
