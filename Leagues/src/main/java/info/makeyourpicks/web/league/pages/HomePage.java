package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.model.League;
import info.makeyourpicks.web.LeagueWebApplication;
import info.makeyourpicks.web.authentication.LoginPage;
import info.makeyourpicks.web.layout.pages.LeagueMemberWebPage;
import info.makeyourpicks.web.layout.pages.LeagueNonMemberWebPage;
import info.makeyourpicks.web.league.panels.ActivePlayerLeaguesPanel;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.model.IModel;

/**
 * Homepage
 */
public class HomePage extends LeagueNonMemberWebPage {

	private static final long serialVersionUID = 1L;
	public static final String WICKET_ID = "homePage";

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
    public HomePage() 
    {
    	super();
    	
    	boolean showMemberNav = false;
    	if (getPlayer()!=null)
    	{
    		showMemberNav=true;
    	}
    	
//    	ActivePlayerLeaguesPanel activePlayerLeaguesPanel = new ActivePlayerLeaguesPanel("activePlayerLeaguesPanel")
//    	{
//
//			@Override
//			public Class<? extends LeagueMemberWebPage> getLeagueHomePage(
//					IModel<League> model)
//			{
////				return LeagueWebConstants.homePageMap.get(model.getObject().getSeason().getLeagueType().getTypeOfLeague());
//				return ((LeagueWebApplication)getApplication()).getLeagueHomePage(model.getObject().getSeason().getLeagueType().getTypeOfLeague());
//			}
//    		
//    	};
//        activePlayerLeaguesPanel.setVisible(showMemberNav);
//        add(activePlayerLeaguesPanel);
        
    	add(new PageLink(LoginPage.WICKET_ID, LoginPage.class));
	}

	@Override
	protected Label getPageTitle() {
		return new Label("pageTitle", "Make Your Picks - Your Home for Office Pools and Fantasy Sports");
	}

	
}
