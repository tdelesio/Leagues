package info.makeyourpicks.facebook.football.pages;

import info.makeyourpicks.facebook.football.panels.FootballTopNavPanel;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.service.PicksManager;
import info.makeyourpicks.service.PlayerManager;
import info.makeyourpicks.service.TeamManager;
import info.makeyourpicks.web.layout.AbstractLeagueSecureFacebookPage;
import info.makeyourpicks.web.layout.LeagueNameHeaderPanel;
import info.makeyourpicks.web.layout.WeekNavPanel;

import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Tim Delesio
 * League is the parent league.  
 * leagueType = football
 */
public abstract class FootballBasePage extends AbstractLeagueSecureFacebookPage {

	@SpringBean(name="teamManager")
	protected TeamManager teamManager;
		
	@SpringBean(name="picksManager")
	protected PicksManager picksManager;
	
	@SpringBean(name="playerManager")
	protected PlayerManager playerManager;
	
	public FootballBasePage()
	{
		this(0, ViewPicksPage.class, false);
	}
	
	public FootballBasePage(int selectedWeekNumber, Class weekSelectionRedirectPage)
	{
		this(selectedWeekNumber, weekSelectionRedirectPage, true);
	}
	public FootballBasePage(int selectedWeekNumber, Class weekSelectionRedirectPage, boolean showWeekSelectionForm)
	{
		super(selectedWeekNumber);
//		add(HeaderContributor.forCss(
//			       "styles/football.css"
//			   ));
		add(CSSPackageResource.getHeaderContribution("styles/football.css"));
		

		add(new WeekNavPanel("weekNavPanel", getWeekModel(), weekSelectionRedirectPage).setVisible(showWeekSelectionForm));
		add(new LeagueNameHeaderPanel("footballHeaderPanel"));
		add(new FootballTopNavPanel("footballTopNavPanel"));
		
	}

	@Override
	protected IModel getWeekModel()
	{
		return new LoadableDetachableModel()
		{
			@Override
			protected Object load()
			{
				return gameManager.getWeek(weekNumber, (Season)getSeasonModel().getObject()); 
			}
		};
	}

	@Override
	protected IModel getSeasonModel()
	{
		return new LoadableDetachableModel()
		{
			@Override
			protected Object load()
			{
				return getActiveLeague().getSeason(); 
			}
		};
	}
	
	
	
}

