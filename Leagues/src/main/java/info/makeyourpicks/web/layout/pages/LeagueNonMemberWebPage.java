package info.makeyourpicks.web.layout.pages;

import info.makeyourpicks.web.AbstractLeagueApplication;
import info.makeyourpicks.web.layout.AbstractHeader;
import info.makeyourpicks.web.layout.AdvertisePanel;
import info.makeyourpicks.web.layout.Footer;
import info.makeyourpicks.web.layout.Header;
import info.makeyourpicks.web.layout.HeaderSecure;
import info.makeyourpicks.web.layout.LeftNavPanel;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.WebMarkupContainer;


public abstract class LeagueNonMemberWebPage extends LeagueNonMemberPage {


	public LeagueNonMemberWebPage()
	{
//		add(CSSPackageResource.getHeaderContribution("styles/screen2.css"));
		add(CSSPackageResource.getHeaderContribution("styles/screen3.css"));
		add(CSSPackageResource.getHeaderContribution(new ResourceReference(AbstractLeagueApplication.class, "jquery-ui-1.8.4.custom.css")));
		
		
		LeftNavPanel leftNavPanel = new LeftNavPanel("leaguePanel");
		add(leftNavPanel);
		
		Footer footer = new Footer("footer");
		add(footer);
		
		AbstractHeader header = new Header(HeaderSecure.ID);
		add(header);
		
//		WebMarkupContainer rightPanel = new WebMarkupContainer("adPanel");
		AdvertisePanel rightPanel = new AdvertisePanel("adPanel");
//		rightPanel.setVisible(showAds);
		add(rightPanel);
		
		add(getPageTitle());
	}
}
