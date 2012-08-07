package info.makeyourpicks.web;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.web.authentication.LoginPage;
import info.makeyourpicks.web.layout.pages.LeagueMemberWebPage;
import info.makeyourpicks.web.league.pages.CreateLeaguePage;
import info.makeyourpicks.web.league.pages.HomePage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.delesio.protocol.http.AbstractAuthenticatedWebSession;
import com.delesio.web.model.SiteMapUrl;

public class LeagueWebApplication extends AbstractLeagueApplication {
	
	//private IResourceStreamLocator streamLocator = new SubPackageResourceStreamLocator("/html/");
	// private String htmlResourcePath;
	public LeagueWebApplication() {
	}

//	private Map<String, Class<? extends LeagueMemberWebPage>> homePageMap;
//	private List<SiteMapUrl<? extends Page>> mountableUrls;
	private Map<String, Map<String, SiteMapUrl<? extends Page>>> mountableUrls;

	
//	private void initilizeHomePageMap()
//	{
//		homePageMap = new HashMap<String, Class<? extends LeagueMemberWebPage>>(11);
//		homePageMap.put("ncaa", info.makeyourpicks.web.ncaa.pages.WinSummaryPage.class);
//		homePageMap.put("suicide", info.makeyourpicks.web.suicide.pages.MakePicksPage.class);
//		homePageMap.put("football", info.makeyourpicks.web.football.pages.MakePicksPage.class);
//		homePageMap.put("ncaa", info.makeyourpicks.web.ncaa.pages.MakePicksPage.class);
//		homePageMap.put("ncaa-weight", info.makeyourpicks.web.weight.pages.MakePicksPage.class);
//		homePageMap.put("nfl-weight", info.makeyourpicks.web.weight.pages.MakePicksPage.class);
//	}
	
	@Override
	protected void init() {
		super.init();
		
//		initilizeHomePageMap();
        
		// add REST url support
//		mount(FootballConstants.WEB_CONTEXT, PackageName
//				.forClass(HomePage.class));
//		mountBookmarkablePage("suicide/makePicks.html", info.makeyourpicks.web.suicide.pages.MakePicksPage.class);
//		mountBookmarkablePage("suicide/viewPicks.html", info.makeyourpicks.web.suicide.pages.ViewPicksPage.class);
//		mountBookmarkablePage("football/makePicks.html", info.makeyourpicks.web.football.pages.MakePicksPage.class);
//		mountBookmarkablePage("football/viewPicks.html", info.makeyourpicks.web.football.pages.ViewPicksPage.class);
//		mountBookmarkablePage("contactUs.html", ContactUsPage.class);
//		mountBookmarkablePage("createLeague.html", CreateLeaguePage.class);
//		mount(new QueryStringUrlCodingStrategy("joinLeague.html", JoinLeaguePage.class));
//		mount(new QueryStringUrlCodingStrategy("activateLeague.html", ActivateLeaguePage.class));
//		mount(new QueryStringUrlCodingStrategy("reviewPayment.html", ReviewPaymentPage.class));
		
		if(mountableUrls != null)
			for (Map<String, SiteMapUrl<? extends Page>> subMap:mountableUrls.values())
				for(SiteMapUrl<? extends Page> url : subMap.values())
                    if(!"/".equals(url.getLoc())) 
                    {
                    	try
                    	{
                    		mountBookmarkablePage(url.getLoc(), url.getUrlClass());
                    	}
                    	catch (WicketRuntimeException exception)
                    	{
                    		
                    	}
                    }
                            
		
		getApplicationSettings().setPageExpiredErrorPage(getHomePage());

	}

	public static LeagueWebApplication get()
	{
		return (LeagueWebApplication) Application.get();
	}
	
	public Class getLoggedInHomePage()
	{
		//return HomePage.class;
		return CreateLeaguePage.class;
	}
	@Override
	public Class getHomePage() {
		return HomePage.class;
//		return CreateLeaguePage.class;
	}

	@Override
	protected Class getSignInPageClass() {
		return LoginPage.class;
	}

	@Override
	protected Class getWebSessionClass() {
		return LeagueSession.class;
	}

	// // getters and setters
	// public String getHtmlResourcePath() {
	// return htmlResourcePath;
	// }
	// public void setHtmlResourcePath(String htmlResourcePath) {
	// this.htmlResourcePath = htmlResourcePath;
	// }

	@Override
	public Class<? extends WebPage> getAccessDeniedPage()
	{
		return LoginPage.class;
	}
 
	@Override
	public Class<? extends WebPage> getSuccessfulAuthenticationPage()
	{
		return getLoggedInHomePage();
	}

	@Override
	public String getCssFileName()
	{
		return "styles/master.css";
	}

	public void setMountableUrls(
			Map<String, Map<String, SiteMapUrl<? extends Page>>> mountableUrls) {
		this.mountableUrls = mountableUrls;
	}

	public Map<String, Map<String, SiteMapUrl<? extends Page>>> getMountableUrls() {
		return mountableUrls;
	}
	
	public Class<? extends Page> getPageForLeague(String leagueType, String pageName)
	{
		return mountableUrls.get(leagueType).get(pageName).getUrlClass();
	}
	
	public Class<? extends Page> getPageForLeague(League league, String pageName)
	{
		return getPageForLeague(league.getSeason(), pageName);
	}
	
	public Class<? extends Page> getPageForLeague(Season season, String pageName)
	{
		return getPageForLeague(season.getLeagueType(), pageName);
	}
	
	public Class<? extends Page> getPageForLeague(LeagueType leagueType, String pageName)
	{
		return getPageForLeague(leagueType.getTypeOfLeague(), pageName);
	}
	

//	@Override
//	public Panel getFooter()
//	{
//		return new Footer("footer");
//	}
//
//	@Override
//	public Panel getHeader()
//	{
//		return new Header("header");
//	}
//
//	@Override
//	public Panel getLeftNav()
//	{
//		return new LeftNavPanel("leaguePanel");
//	}
	
//	public Class<? extends LeagueMemberWebPage> getLeagueHomePage(String leagueType)
//	{
//		return homePageMap.get(leagueType);
//	}
	
	
}
