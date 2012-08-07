package info.makeyourpicks.web.layout;

import info.makeyourpicks.web.league.pages.CreateLeaguePage;
import info.makeyourpicks.web.league.pages.JoinLeaguePage;

import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @author PRC9041
 */
public class TopPanel extends Panel {

	public TopPanel(String id)
	{
		super(id);
		
		add(new PageLink("joinLeague", JoinLeaguePage.class));
		add(new PageLink("createLeague", CreateLeaguePage.class));
	}
}

