package info.makeyourpicks.web;

import info.makeyourpicks.service.GameManager;
import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.service.MessageBoardManager;
import info.makeyourpicks.service.PicksManager;
import info.makeyourpicks.service.PlayerManager;
import info.makeyourpicks.service.TeamManager;

import org.apache.wicket.RequestCycle;

@Deprecated
public class ServiceLocator {
	
	public static TeamManager getTeamManagerService()
	{
		LeagueWebApplication application = (LeagueWebApplication)RequestCycle.get().getApplication();
		return application.getTeamManager();
	}
	
	public static PlayerManager getPlayerManagerService()
	{
		LeagueWebApplication application = (LeagueWebApplication)RequestCycle.get().getApplication();
		return application.getPlayerManager();
	}
	
	public static LeagueManager getLeagueManagerService()
	{
		LeagueWebApplication application = (LeagueWebApplication)RequestCycle.get().getApplication();
		return application.getLeagueManager();
	}
	
	public static GameManager getGameManagerService()
	{
		LeagueWebApplication application = (LeagueWebApplication)RequestCycle.get().getApplication();
		return application.getGameManager();
	}
	
	public static PicksManager getPicksManagerService()
	{
		LeagueWebApplication application = (LeagueWebApplication)RequestCycle.get().getApplication();
		return application.getPicksManager();
	}
	
	public static MessageBoardManager getMessageBoardService()
	{
		LeagueWebApplication application = (LeagueWebApplication)RequestCycle.get().getApplication();
		return application.getMessageBoardManager();
	}
}
