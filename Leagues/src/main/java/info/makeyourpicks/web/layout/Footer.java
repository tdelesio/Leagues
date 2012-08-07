package info.makeyourpicks.web.layout;

import info.makeyourpicks.web.league.pages.ContactUsPage;
import info.makeyourpicks.web.league.pages.FAQPage;
import info.makeyourpicks.web.league.pages.HomePage;
import info.makeyourpicks.web.league.pages.SiteMapPage;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.PageLink;

public class Footer extends BasePanel{

	public Footer(String id)
	{
		super(id);
		
		add(new PageLink("home", getApplication().getHomePage()));
		add(new PageLink("aboutUs", HomePage.class).setVisible(false));
		add(new PageLink("products", HomePage.class).setVisible(false));
		add(new Link<SiteMapPage>("siteMap")
				{

					@Override
					public void onClick() {
						setResponsePage(SiteMapPage.class);
					}
			
				});
//		add(new PageLink("contactUs", ContactUsPage.class));
		add(new Link(FAQPage.WICKET_ID)
		{
			@Override
			public void onClick() {
				setResponsePage(FAQPage.class);
			}
		});
	}
	
	
}
