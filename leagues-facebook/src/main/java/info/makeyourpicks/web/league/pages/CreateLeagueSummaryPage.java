package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.model.League;
import info.makeyourpicks.web.LeagueFacebookApplication;
import info.makeyourpicks.web.layout.AbstractLeagueSecureFacebookPage;
import info.makeyourpicks.web.league.panels.CreateLeagueSummaryPanel;

import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;

/**
 * @author PRC9041
 */
public class CreateLeagueSummaryPage extends AbstractLeagueSecureFacebookPage {

	public static final String WICKET_ID="createLeagueStep3Page";
	
	public String getWicketID()
	{
		return WICKET_ID;
	}
	
	public CreateLeagueSummaryPage(IModel<League> model) 
	{
		
        CreateLeagueSummaryPanel createLeagueSummaryPanel = new CreateLeagueSummaryPanel("summaryPanel", model)
        {

			@Override
			public Class<? extends Page> getLeagueHomePage(
					IModel<League> leagueModel)
			{
				return LeagueFacebookApplication.homePageMap.get(leagueModel.getObject().getSeason().getLeagueType().getTypeOfLeague());
			}

			@Override
			public Page getStep1Page(IModel<League> leagueModel)
			{
				return new CreateLeaguePage(leagueModel);
			}

			@Override
			public Page getStep2Page(IModel<League> leagueModel)
			{
				return new CreateLeagueStep2Page(leagueModel);
			}

			@Override
			public Page getStep3Page(IModel<League> leagueModel)
			{
				return new CreateLeagueStep3Page(leagueModel);
			}

			@Override
			public Page getStep4Page(IModel<League> leagueModel)
			{
				return new CreateLeagueStep4Page(leagueModel);
			}

			@Override
			public Page getSummaryPage(IModel<League> leagueModel)
			{
				return new CreateLeagueSummaryPage(leagueModel);
			}
        	
        };
        
        add(createLeagueSummaryPanel);

	}
	
	
}

