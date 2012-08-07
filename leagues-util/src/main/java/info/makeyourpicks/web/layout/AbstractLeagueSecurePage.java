package info.makeyourpicks.web.layout;


import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.service.GameManager;
import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.web.AbstractLeagueApplication;

import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

@AuthorizeInstantiation({"user","admin"})
public abstract class AbstractLeagueSecurePage extends AbstractLeaguePage {
	
	@SpringBean(name="leagueManager")
	protected LeagueManager leagueManager;
	
	@SpringBean(name="gameManager")
	protected GameManager gameManager;
	
	protected int weekNumber;
	
	protected abstract IModel getWeekModel();
	protected abstract IModel getSeasonModel();
	protected abstract Class<? extends Page> getActivateLeaguePage();
	protected abstract Class<? extends Page> getLeagueOnHoldPage();
	
	public AbstractLeagueSecurePage() {

		if (getActiveLeague()==null)
		{
			List<League> playerLeagues = leagueManager.getLeaguesForPlayer(getPlayer());
			if (playerLeagues.isEmpty())
			{
//				throw new RestartResponseException(getApplication().getHomePage());
			}
			else
			{
				League activeLeague = (League)playerLeagues.iterator().next();
				setActiveLeague(activeLeague);
			}
		}
	}
	
	public AbstractLeagueSecurePage(int selectedWeekNumber) {
	
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
	
	

}