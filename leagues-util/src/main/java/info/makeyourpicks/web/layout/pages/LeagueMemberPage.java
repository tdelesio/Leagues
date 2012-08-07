package info.makeyourpicks.web.layout.pages;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.service.GameManager;
import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.service.PicksManager;
import info.makeyourpicks.service.PlayerManager;
import info.makeyourpicks.web.AbstractLeagueApplication;

import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

@AuthorizeInstantiation({"user","admin"})
public abstract class LeagueMemberPage extends LeaguePage {

	@SpringBean(name="leagueManager")
	protected LeagueManager leagueManager;
	
	@SpringBean(name="gameManager")
	protected GameManager gameManager;
	
	@SpringBean(name="playerManager")
	protected PlayerManager playerManager;
	
	@SpringBean(name="picksManager")
	protected PicksManager picksManager;
	
	protected int weekNumber;
	
	public LeagueMemberPage()
	{
		League league = getActiveLeague();
		if (league==null)
		{
			List<League> playerLeagues = leagueManager.getLeaguesForPlayer(getPlayer());
			if (playerLeagues.isEmpty())
			{
				throw new RestartResponseException(getApplication().getHomePage());
			}
			else
			{
				League activeLeague = (League)playerLeagues.iterator().next();
				setActiveLeague(activeLeague);
			}
		}
	}

	public LeagueMemberPage(int selectedWeekNumber)
	{
		this();
		
		if (getActiveLeague()!=null&&!getActiveLeague().isActive())
		{
			Week week = gameManager.getWeek(2, getActiveLeague().getSeason());
			if (week!=null)
			{
				if (getActiveLeague().getAdmin().equals(getPlayer()))
				{
//					throw new RestartResponseException(ActivateLeaguePage.class);
					throw new RestartResponseException(getActivateLeaguePage());
				}
				else
				{
					throw new RestartResponseException(getLeagueOnHoldPage());
//					throw new RestartResponseException(LeagueOnHoldPage.class);
				}
			}
		}
		
		if (selectedWeekNumber==0)
		{
			int currentWeek = gameManager.getCurrentWeek((Season)getSeasonModel().getObject());
			if (currentWeek==0)
			{
				weekNumber=1;
			}
			else
			{
				weekNumber = currentWeek;
			}
		
		}
		else
		{
			weekNumber = selectedWeekNumber;
		}
				

		if (getWeekModel().getObject()==null)
		{
			throw new RestartResponseException(((AbstractLeagueApplication)getApplication()).getLoggedInHomePage());
		}
	}

	protected IModel<Week> getWeekModel()
	{
		return new LoadableDetachableModel<Week>()
		{
			private static final long serialVersionUID = 3277193492248380997L;

			@Override
			protected Week load()
			{
				return gameManager.getWeek(weekNumber, (Season)getSeasonModel().getObject()); 
			}
		};
	}


	
	
	protected IModel<Season> getSeasonModel()
	{
		return new LoadableDetachableModel<Season>()
		{
			private static final long serialVersionUID = -3217975284704112490L;

			@Override
			protected Season load()
			{
				Season season = getActiveLeague().getSeason();
				LeagueType leagueType = season.getLeagueType();
				if (leagueType.hasParent())
				{
//					leagueType = leagueManager.getLeagueType(leagueType.getParentTypeOfLeague());
					season = leagueManager.getSeason(leagueType.getParentTypeOfLeague(), season.getStartYear(), season.getEndYear());
				}
//				
				return season;
			}
		};
	}
	
	protected IModel<Season> getSubSeasonModel()
	{
		return new LoadableDetachableModel()
		{

			@Override
			protected Object load()
			{
				Season parentSeason = getActiveLeague().getSeason();
				LeagueType parentLeagueType = parentSeason.getLeagueType();
				parentSeason = leagueManager.getSeason(parentLeagueType.getParentTypeOfLeague(), parentSeason.getStartYear(), parentSeason.getEndYear());
				return parentSeason;

			}
			
		};
	}
	
	
	protected Class<? extends Page> getWeekSelectionRedirectPage()
	{
		return null;
	}
	
	protected abstract Class<? extends Page> getActivateLeaguePage();
	protected abstract Class<? extends Page> getLeagueOnHoldPage();
	

}
