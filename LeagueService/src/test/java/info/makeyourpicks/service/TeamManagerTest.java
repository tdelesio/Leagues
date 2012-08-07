package info.makeyourpicks.service;

import info.makeyourpicks.model.Team;

import org.junit.Test;

public class TeamManagerTest extends AbstractServiceTestCase{
	
	


	@Test
	public void testLoadTeam() {
		
		
		Team testTeam = teamManager.loadTeam(team1.getId());
		assertEquals(team1, testTeam);
		
		Team testTeam2 = teamManager.loadTeam(team2.getId());
		assertEquals(team2, testTeam2);
		
	}
	
	@Test
	public void testLoadAllTeams() {
		assertEquals(true, teamManager.loadAllTeams().size()>=2);
	}
	
	@Test
	public void testGetTeamsByLeagueType()
	{
		assertEquals(2, teamManager.getTeamsByLeagueType(leagueType).size());
	}

}
