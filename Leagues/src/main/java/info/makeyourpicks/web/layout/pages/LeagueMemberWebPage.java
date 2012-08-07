package info.makeyourpicks.web.layout.pages;

import java.util.Map;

import info.makeyourpicks.service.GameManager;
import info.makeyourpicks.web.LeagueWebApplication;
import info.makeyourpicks.web.PageEnum;
import info.makeyourpicks.web.layout.AdvertisePanel;
import info.makeyourpicks.web.layout.Footer;
import info.makeyourpicks.web.layout.HeaderSecure;
import info.makeyourpicks.web.layout.IHeaderWidget;
import info.makeyourpicks.web.layout.LeftNavPanel;
import info.makeyourpicks.web.layout.LeftNavSecurePanel;
import info.makeyourpicks.web.league.pages.ActivateLeaguePage;
import info.makeyourpicks.web.league.pages.LeagueOnHoldPage;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.delesio.web.model.SiteMapUrl;

public class LeagueMemberWebPage extends LeagueMemberPage {

	@SpringBean(name="leagueTypes")
	protected Map<String, Map<String, SiteMapUrl<? extends Page>>> mountableUrls;
	
	public LeagueMemberWebPage()
	{
		this(0, true);
	}
	
	public LeagueMemberWebPage(boolean showAds)
	{
		this(0, showAds);
	}
	
	public LeagueMemberWebPage(int selectedWeek)
	{
		this(selectedWeek, true);
	}
	
	public LeagueMemberWebPage(int selectedWeek, boolean showAds)
	
	{
		super(selectedWeek);
	
		HeaderSecure headerSecure; 
		if (this instanceof IHeaderWidget)
		{
			headerSecure = new HeaderSecure(HeaderSecure.ID, getWeekModel(), getWeekSelectionRedirectPage(), ((IHeaderWidget)this).getHeaderWidget());
		}
		else
		{
			headerSecure = new HeaderSecure(HeaderSecure.ID, getWeekModel(), getWeekSelectionRedirectPage());
		}
//		add(CSSPackageResource.getHeaderContribution("styles/screen2.css"));
		add(CSSPackageResource.getHeaderContribution("styles/screen3.css"));
		
		
//		if (getWeekSelectionRedirectPage() == null)
//		{
//			headerSecure.setVisible(false);
//		}
		add(headerSecure);
		
//		LeftNavPanel leftNavPanel = new LeftNavPanel("leaguePanel");
//		add(leftNavPanel);
		
		LeftNavSecurePanel leftNavSecurePanel = new LeftNavSecurePanel("leaguePanel");
		add(leftNavSecurePanel);
		
//		WebMarkupContainer rightPanel = new WebMarkupContainer("adPanel");
		AdvertisePanel rightPanel = new AdvertisePanel("adPanel");
		rightPanel.setVisible(showAds);
		add(rightPanel);
		
		
		Footer footer = new Footer("footer");
		add(footer);
		
		add(getPageTitle());
	}
	
	@Override
	protected Class<? extends Page> getActivateLeaguePage() {
		return ActivateLeaguePage.class;
	}

	@Override
	protected Class<? extends Page> getLeagueOnHoldPage() {
		return LeagueOnHoldPage.class;
	}
	
	protected Class<? extends Page> getHomePageForLeague(String leagueType)
	{
//		return ((LeagueWebApplication)getApplication()).getLeagueHomePage(leagueType);
		return mountableUrls.get(leagueType).get(PageEnum.HOME.getValue()).getUrlClass();
	}
	

}
