package info.makeyourpicks.web.admin.panels;

import info.makeyourpicks.web.LeagueWebApplication;
import info.makeyourpicks.web.admin.pages.AdminLeaguesPage;
import info.makeyourpicks.web.admin.pages.AdminPlayersPage;
import info.makeyourpicks.web.admin.pages.EnterTeamsPage;
import info.makeyourpicks.web.admin.pages.SetupWeekPage;
import info.makeyourpicks.web.layout.AbstractBasePanel;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.PageLink;

/**
 * @author PRC9041
 */
public class AdminTopPanel extends AbstractBasePanel {

	public AdminTopPanel(String id)
	{
		super(id);
		
		add(new PageLink("back", ((LeagueWebApplication)getApplication()).getLoggedInHomePage()));
		
		add(new PageLink("enterTeams", EnterTeamsPage.class));
		add(new PageLink("setupWeek", SetupWeekPage.class));
		add(new PageLink("adminLeagues", AdminLeaguesPage.class));
		add(new PageLink("adminPlayers", AdminPlayersPage.class));
		
		add(new Link("clearPickCache")
		{

			@Override
			public void onClick()
			{
				picksManager.clearPickCache();
			}
			
		});
		
		add(new Link("updateScores")
		{

			@Override
			public void onClick()
			{
				gameManager.executeGameScoreParsing();
			}
			
		});
	}
}

