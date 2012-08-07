package info.makeyourpicks.facebook.football.panels;


import info.makeyourpicks.facebook.football.pages.MakePicksPage;
import info.makeyourpicks.facebook.football.pages.ViewPicksPage;
import info.makeyourpicks.facebook.football.pages.WinSummaryPage;
import info.makeyourpicks.web.layout.AbstractBasePanel;
import info.makeyourpicks.web.layout.AbstractLeaguePage;

import org.apache.wicket.markup.html.link.PageLink;

/**
 * @author PRC9041
 */
public class FootballTopNavPanel extends AbstractBasePanel {

	public FootballTopNavPanel(String id)
	{
		super(id);
//		
//		//add(new PageLink("messageBoard", MessageBoardPage.class));
		add(new PageLink(MakePicksPage.WICKET_ID, MakePicksPage.class));
		add(new PageLink(ViewPicksPage.WICKET_ID, ViewPicksPage.class));
		add(new PageLink(WinSummaryPage.WICKET_ID, WinSummaryPage.class));
		
	}
	
	@Override
	protected void onBeforeRender()
	{
		super.onBeforeRender();
		
		String wicketId = ((AbstractLeaguePage)getPage()).getWicketID();
		if (wicketId.equals(MakePicksPage.WICKET_ID))
		{
			this.get(MakePicksPage.WICKET_ID).add(cssOn);
		}
		else if (wicketId.equals(ViewPicksPage.WICKET_ID))
		{
			this.get(ViewPicksPage.WICKET_ID).add(cssOn);
		}	
		else if (wicketId.equals(WinSummaryPage.WICKET_ID))
		{
			this.get(WinSummaryPage.WICKET_ID).add(cssOn);
		}
	}
}

