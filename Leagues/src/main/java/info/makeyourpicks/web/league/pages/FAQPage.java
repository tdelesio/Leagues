package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.web.layout.pages.LeagueNonMemberWebPage;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.PageLink;

public class FAQPage extends LeagueNonMemberWebPage {

	public static final String WICKET_ID = "faqPage";
	
	public FAQPage()
	{
		super();
		
		add(new PageLink(ContactUsPage.WICKET_ID, ContactUsPage.class));
	}
	
	@Override
	protected Label getPageTitle() {
		return new Label("pageTitle", "MakeYourPicks - Frequently Asked Questions");
	}

}
