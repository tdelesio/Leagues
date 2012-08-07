package info.makeyourpicks.web.weight.pages;

import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.service.PicksManager;
import info.makeyourpicks.service.PlayerManager;
import info.makeyourpicks.web.layout.AbstractLeagueSecureFacebookPage;
import info.makeyourpicks.web.layout.LeagueNameHeaderPanel;
import info.makeyourpicks.web.layout.WeekNavPanel;
import info.makeyourpicks.web.weight.panels.WeightTopNavPanel;

import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Tim Delesio
 * leagueType:			ncaa-weight
 * parentLeagueType:	ncaa
 */
public abstract class WeightBasePage extends AbstractLeagueSecureFacebookPage {

	@SpringBean(name="picksManager")
	protected PicksManager picksManager;
	
	@SpringBean(name="playerManager")
	protected PlayerManager playerManager;
	
	protected boolean showNav=true;
	
	public WeightBasePage()
	{
		this(0, ViewPicksPage.class, false);
		
	}
	
	public WeightBasePage(int selectedWeekNumber, Class weekSelectionRedirectPage)
	{
		this(selectedWeekNumber, weekSelectionRedirectPage, true);
	}
	public WeightBasePage(int selectedWeekNumber, Class weekSelectionRedirectPage, boolean showWeekSelectionForm)
	{
		super(selectedWeekNumber);
//		add(HeaderContributor.forCss(
//			       "styles/ncaa.css"
//			   )); 
		add(CSSPackageResource.getHeaderContribution("styles/ncaa.css"));
		
		add(new WeekNavPanel("weekNavPanel", getWeekModel(), weekSelectionRedirectPage).setVisible(showWeekSelectionForm));
		add(new WeightTopNavPanel("ncaaWeightTopPanel"));
		add(new LeagueNameHeaderPanel("leagueNameHeaderPanel"));
	}

	@Override
	protected IModel<Week> getWeekModel()
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
	protected IModel<Season> getSeasonModel()
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

