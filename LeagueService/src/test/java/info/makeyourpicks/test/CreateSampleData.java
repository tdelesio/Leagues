package info.makeyourpicks.test;

import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Team;

import java.util.Date;

import org.junit.Test;

public class CreateSampleData extends AbstractTestCase
{
	
	@Test
	public void testTestData()
	{	
		createLeagueTypes();
		createTeams();
		createPlayers();
		createSeasons();
		createLeagues();
	}


	

	
}
