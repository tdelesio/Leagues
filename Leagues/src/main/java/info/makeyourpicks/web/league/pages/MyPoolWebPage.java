package info.makeyourpicks.web.league.pages;

import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;

import info.makeyourpicks.model.League;
import info.makeyourpicks.web.LeagueWebApplication;
import info.makeyourpicks.web.layout.pages.LeagueMemberPage;
import info.makeyourpicks.web.layout.pages.LeagueMemberWebPage;
import info.makeyourpicks.web.league.panels.ActivePlayerLeaguesPanel;

public class MyPoolWebPage extends LeagueMemberWebPage {

	public MyPoolWebPage()
	{
		ActivePlayerLeaguesPanel activePlayerLeaguesPanel = new ActivePlayerLeaguesPanel("myPools") {
			
			@Override
			public Class<? extends Page> getLeagueHomePage(IModel<League> model) {
//				return ((LeagueWebApplication)getApplication()).getLeagueHomePage(model.getObject().getSeason().getLeagueType().getTypeOfLeague());
				return getHomePageForLeague(model.getObject().getSeason().getLeagueType().getTypeOfLeague());
			}
		}; 
		
		add(activePlayerLeaguesPanel);
	}
}
