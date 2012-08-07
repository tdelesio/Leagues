package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.model.League;
import info.makeyourpicks.web.layout.AbstractLeagueSecureFacebookPage;
import info.makeyourpicks.web.league.panels.CreateLeagueStep2Panel;

import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;

/**
 * @author PRC9041
 */
public class CreateLeagueStep2Page extends AbstractLeagueSecureFacebookPage {

	public static final String WICKET_ID="createLeagueStep2Page";
	
	public String getWicketID()
	{
		return WICKET_ID;
	}
	
	public CreateLeagueStep2Page(IModel<League> model) 
	{
		
        CreateLeagueStep2Panel createLeagueStep2Panel = new CreateLeagueStep2Panel("step2Panel", model)
        {
        	@Override
			public Page getStep3Page(IModel<League> leagueModel)
			{
				return new CreateLeagueStep3Page(leagueModel);
			}
        };
        add(createLeagueStep2Panel);
	}
	
	
}

