package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.model.League;
import info.makeyourpicks.web.LeagueWebApplication;
import info.makeyourpicks.web.PageEnum;
import info.makeyourpicks.web.layout.pages.LeagueNonMemberWebPage;
import info.makeyourpicks.web.layout.pages.LeaguePage;
import info.makeyourpicks.web.league.panels.CreateLeagueStep2Panel;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;

/**
 * @author PRC9041
 */
public class CreateLeagueStep2Page extends LeagueNonMemberWebPage {

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
			public Class<? extends Page> getStep3Page(IModel<League> leagueModel)
			{
//				return new CreateLeagueStep3Page(leagueModel);
//        		return getHomePageForLeague(leagueModel.getObject().getSeason().getLeagueType().getTypeOfLeague());
//        		return new CreateLeagueSummaryPage(leagueModel);
        		String leagueType = leagueModel.getObject().getSeason().getLeagueType().getTypeOfLeague();
        		return LeagueWebApplication.get().getMountableUrls().get(leagueType).get(PageEnum.HOME.getValue()).getUrlClass();
			}
        };
        add(createLeagueStep2Panel);
	}
	
	
}

