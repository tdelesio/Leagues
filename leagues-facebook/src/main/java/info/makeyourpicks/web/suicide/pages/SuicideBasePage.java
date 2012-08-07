package info.makeyourpicks.web.suicide.pages;

import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.service.PicksManager;
import info.makeyourpicks.service.PlayerManager;
import info.makeyourpicks.web.layout.AbstractLeagueSecureFacebookPage;
import info.makeyourpicks.web.layout.LeagueNameHeaderPanel;
import info.makeyourpicks.web.layout.WeekNavPanel;
import info.makeyourpicks.web.suicide.panels.SuicideTopNavPanel;

import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Tim Delesio
 * parentLeagueType:  football
 * leagyeType: suicide
 */
public abstract class SuicideBasePage extends AbstractLeagueSecureFacebookPage {

	@SpringBean(name="playerManager")
	protected PlayerManager playerManager;
	
	@SpringBean(name="picksManager")
	protected PicksManager picksManager;
	
	public SuicideBasePage()
	{
		this(0, ViewPicksPage.class, false);
		
	}
	
	public SuicideBasePage(int selectedWeekNumber, Class weekSelectionRedirectPage)
	{
		this(selectedWeekNumber, weekSelectionRedirectPage, true);
	}
	public SuicideBasePage(int selectedWeekNumber, Class weekSelectionRedirectPage, boolean showWeekSelectionForm)
	{
		super(selectedWeekNumber);

		add(CSSPackageResource.getHeaderContribution("styles/suicide.css"));
				
		
		add(new WeekNavPanel("weekNavPanel", getWeekModel(), weekSelectionRedirectPage).setVisible(showWeekSelectionForm));
		add(new LeagueNameHeaderPanel("suicideHeaderPanel"));
		add(new SuicideTopNavPanel("suicideTopNavPanel"));
	}

	@Override
	public String getWicketID()
	{
		// TODO Auto-generated method stub
		return null;
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
				Season parentSeason = getActiveLeague().getSeason();
				LeagueType parentLeagueType = parentSeason.getLeagueType();
				parentSeason = leagueManager.getSeason(parentLeagueType.getParentTypeOfLeague(), parentSeason.getStartYear(), parentSeason.getEndYear());
				return parentSeason;

			}
			
		};
	}
	
	
	
	
}

