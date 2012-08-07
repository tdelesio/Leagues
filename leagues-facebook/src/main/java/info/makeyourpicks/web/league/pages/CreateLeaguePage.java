package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.model.League;
import info.makeyourpicks.web.layout.AbstractLeagueSecureFacebookPage;
import info.makeyourpicks.web.league.panels.CreateLeagueStep1Panel;

import org.apache.wicket.Page;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

/**
 * @author PRC9041
 */
public class CreateLeaguePage extends AbstractLeagueSecureFacebookPage {

	public static final String WICKET_ID="createLeague";
	
	public String getWicketID()
	{
		return WICKET_ID;
	}
	
	public CreateLeaguePage() 
	{
		IModel leagueModel = new CompoundPropertyModel(new League());
		
		init(leagueModel);
	}
	
	public CreateLeaguePage(IModel<League> leagueModel)
	{
		init(leagueModel);
	}
	
	private void init(IModel<League> leagueModel)
	{
		CreateLeagueStep1Panel createLeagueStep1Panel = new CreateLeagueStep1Panel("step1Panel", leagueModel)
		{
			@Override
			public Page getStep2Page(IModel<League> leagueModel)
			{
				return new CreateLeagueStep2Page(leagueModel);
			}
		};
        add(createLeagueStep1Panel);

        
	}
	
	
}

