package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.model.League;
import info.makeyourpicks.web.layout.AbstractLeagueSecureFacebookPage;
import info.makeyourpicks.web.league.panels.CreateLeagueStep4Panel;

import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;

/**
 * @author PRC9041
 */
public class CreateLeagueStep4Page extends AbstractLeagueSecureFacebookPage {

	public static final String WICKET_ID="createLeagueStep3Page";
	
	public String getWicketID()
	{
		return WICKET_ID;
	}
	
	public CreateLeagueStep4Page(IModel<League> model) 
	{
		
        CreateLeagueStep4Panel createLeagueStep4Panel = new CreateLeagueStep4Panel("step4Panel", model)
        {
        	@Override
			public Page getSummaryPage(IModel<League> leagueModel)
			{
				return new CreateLeagueSummaryPage(leagueModel);
			}
        };
        add(createLeagueStep4Panel);

	}
	
	
}

