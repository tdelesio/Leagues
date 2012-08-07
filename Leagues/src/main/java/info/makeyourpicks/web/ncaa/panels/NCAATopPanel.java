package info.makeyourpicks.web.ncaa.panels;

import info.makeyourpicks.web.league.pages.MessageBoardPage;
import info.makeyourpicks.web.ncaa.pages.MakePicksPage;
import info.makeyourpicks.web.ncaa.pages.ViewPicksPage;
import info.makeyourpicks.web.ncaa.pages.WinSummaryPage;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @author PRC9041
 */
public class NCAATopPanel extends Panel {

	public NCAATopPanel(String id)
	{
		super(id);
		
		add(new BookmarkablePageLink<Void>("messageBoard", MessageBoardPage.class));
		add(new BookmarkablePageLink<Void>("makePicks", MakePicksPage.class));
		add(new BookmarkablePageLink<Void>("viewPicks", ViewPicksPage.class));
		add(new BookmarkablePageLink<Void>("winSummary", WinSummaryPage.class));
	}
}

