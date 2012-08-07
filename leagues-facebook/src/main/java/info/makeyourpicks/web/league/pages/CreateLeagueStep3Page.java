package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.model.League;
import info.makeyourpicks.web.layout.AbstractLeagueSecureFacebookPage;
import info.makeyourpicks.web.league.panels.CreateLeagueStep3Panel;

import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;

/**
 * @author PRC9041
 */
public class CreateLeagueStep3Page extends AbstractLeagueSecureFacebookPage {

	public static final String WICKET_ID="createLeagueStep3Page";
	
	public String getWicketID()
	{
		return WICKET_ID;
	}
	
	public CreateLeagueStep3Page(IModel<League> model) 
	{
		
        CreateLeagueStep3Panel createLeagueStep3Panel = new CreateLeagueStep3Panel("step3Panel", model)
        {
        	@Override
			public Page getStep4Page(IModel<League> leagueModel)
			{
				return new CreateLeagueStep4Page(leagueModel);
			}
        };
        add(createLeagueStep3Panel);

	}
	
	
}

