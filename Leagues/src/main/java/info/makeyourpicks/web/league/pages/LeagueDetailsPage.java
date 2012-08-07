package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.model.League;
import info.makeyourpicks.web.layout.pages.LeagueMemberWebPage;
import info.makeyourpicks.web.league.panels.LeagueDetailsPanel;

import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 * @author PRC9041
 */
public class LeagueDetailsPage extends LeagueMemberWebPage {

	public static final String WICKET_ID = "leagueDetailsPage";
	
	
	public LeagueDetailsPage()
	{
		add(CSSPackageResource.getHeaderContribution("styles/leagueDetails.css"));
		
		LoadableDetachableModel<League> model = new LoadableDetachableModel<League>()
		{

			@Override
			protected League load()
			{
				return getActiveLeague();
			}
			
		};
		
		init(model);
	}
	public LeagueDetailsPage(IModel<League> model)
	{
		init(model);
	}
	
	private void init(IModel<League> model)
	{
		
		add(new LeagueDetailsPanel("leagueDetailsPanel", model));
	}
	
	
	
}

