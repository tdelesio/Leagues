package info.makeyourpicks.web.weight.panels;

import info.makeyourpicks.web.layout.AbstractBasePanel;
import info.makeyourpicks.web.layout.AbstractLeagueSecurePage;
import info.makeyourpicks.web.weight.pages.MakePicksPage;
import info.makeyourpicks.web.weight.pages.ViewPicksPage;
import info.makeyourpicks.web.weight.pages.WinOverviewPage;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.PageLink;

public class WeightTopNavPanel extends AbstractBasePanel {

	public WeightTopNavPanel(String id)
	{
		super(id);
		
		//add(new PageLink("messageBoard", MessageBoardPage.class));
		add(new PageLink(MakePicksPage.WICKET_ID, MakePicksPage.class));
		add(new PageLink(ViewPicksPage.WICKET_ID, ViewPicksPage.class));
		add(new PageLink(WinOverviewPage.WICKET_ID, WinOverviewPage.class));
//		add(new PageLink(WinSummaryPage.WICKET_ID, WinSummaryPage.class));
//		add(new PageLink(SeasonDetailsPage.WICKET_ID, SeasonDetailsPage.class));
//		add(new PageLink(LeagueDetailsPage.WICKET_ID, LeagueDetailsPage.class));
		
//		Link addPlayers = new PageLink(InviteFriendsPage.WICKET_ID, InviteFriendsPage.class);
//		add(addPlayers);
	}
	
//	@Override
//	protected void onBeforeRender()
//	{
//		super.onBeforeRender();
//		
//		String wicketId = ((AbstractLeagueSecurePage)getPage()).getWicketID();
//		
//		if (wicketId.equals(LeagueDetailsPage.WICKET_ID))
//		{
//			this.get(LeagueDetailsPage.WICKET_ID).add(cssOn);
//		}
//		else 
//			if (wicketId.equals(MakePicksPage.WICKET_ID))
//		{
//			this.get(MakePicksPage.WICKET_ID).add(cssOn);
//		}
//		else if (wicketId.equals(ViewPicksPage.WICKET_ID))
//		{
//			this.get(ViewPicksPage.WICKET_ID).add(cssOn);
//		}	
//		else if (wicketId.equals(SeasonDetailsPage.WICKET_ID))
//		{
//			this.get(SeasonDetailsPage.WICKET_ID).add(cssOn);
//		}
//		else if (wicketId.equals(WinSummaryPage.WICKET_ID))
//		{
//			this.get(WinSummaryPage.WICKET_ID).add(cssOn);
//		}
//	}
}