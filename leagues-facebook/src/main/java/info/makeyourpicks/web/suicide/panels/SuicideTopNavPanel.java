package info.makeyourpicks.web.suicide.panels;



import info.makeyourpicks.web.layout.AbstractBasePanel;
import info.makeyourpicks.web.layout.AbstractLeagueSecurePage;
import info.makeyourpicks.web.suicide.pages.MakePicksPage;
import info.makeyourpicks.web.suicide.pages.SeasonDetailsPage;
import info.makeyourpicks.web.suicide.pages.ViewPicksPage;
import info.makeyourpicks.web.suicide.pages.WinSummaryPage;

import org.apache.wicket.markup.html.link.PageLink;

/**
 * @author PRC9041
 */
public class SuicideTopNavPanel extends AbstractBasePanel {

	public SuicideTopNavPanel(String id)
	{
		super(id);
		
		//add(new PageLink("messageBoard", MessageBoardPage.class));
		add(new PageLink(MakePicksPage.WICKET_ID, MakePicksPage.class));
		add(new PageLink(ViewPicksPage.WICKET_ID, ViewPicksPage.class));
		add(new PageLink(WinSummaryPage.WICKET_ID, WinSummaryPage.class));
	}
	
	@Override
	protected void onBeforeRender()
	{
		super.onBeforeRender();
		
		String wicketId = ((AbstractLeagueSecurePage)getPage()).getWicketID();
		
		if (wicketId.equals(MakePicksPage.WICKET_ID))
		{
			this.get(MakePicksPage.WICKET_ID).add(cssOn);
		}
		else if (wicketId.equals(ViewPicksPage.WICKET_ID))
		{
			this.get(ViewPicksPage.WICKET_ID).add(cssOn);
		}	
		else if (wicketId.equals(SeasonDetailsPage.WICKET_ID))
		{
			this.get(SeasonDetailsPage.WICKET_ID).add(cssOn);
		}
		else if (wicketId.equals(WinSummaryPage.WICKET_ID))
		{
			this.get(WinSummaryPage.WICKET_ID).add(cssOn);
		}
	}
}

