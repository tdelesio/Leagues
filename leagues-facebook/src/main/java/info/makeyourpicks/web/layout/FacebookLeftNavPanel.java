package info.makeyourpicks.web.layout;

import info.makeyourpicks.model.League;
import info.makeyourpicks.web.LeagueFacebookApplication;
import info.makeyourpicks.web.league.pages.ActivateLeaguePage;
import info.makeyourpicks.web.league.pages.CreateLeaguePage;
import info.makeyourpicks.web.league.pages.JoinLeaguePage;
import info.makeyourpicks.web.league.panels.ActivePlayerLeaguesPanel;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

public class FacebookLeftNavPanel extends AbstractBasePanel
{

	public FacebookLeftNavPanel(String id)
	{
		super(id);
		
		/**
         * MEMBERS
         */
        //title
		add(new Label("memeberTitle", "MEMBERS").setVisible(true));
		
		//activate league link
		Link activateLeague = new Link("activateLeague")
		{
			@Override
			public void onClick()
			{
				setResponsePage(ActivateLeaguePage.class);
			}
		};
		add(activateLeague);
		
		//create league
        Link createLeaguePage = new BookmarkablePageLink(CreateLeaguePage.WICKET_ID, CreateLeaguePage.class);
        add(createLeaguePage);

      //join league link
		Link joinLink = new Link(JoinLeaguePage.WICKET_ID)
		{
			@Override
			public void onClick()
			{
				setResponsePage(JoinLeaguePage.class);
			}
		};
		add(joinLink);
		
		ActivePlayerLeaguesPanel activePlayerLeaguesPanel = new ActivePlayerLeaguesPanel("activePlayerLeaguesPanel")
        {

			@Override
			public Class<? extends AbstractLeagueSecurePage> getLeagueHomePage(
					IModel<League> model)
			{
				return LeagueFacebookApplication.homePageMap.get(model.getObject().getSeason().getLeagueType().getTypeOfLeague());
			}
        	
        };
        if (getPlayer()==null)
        {
        	activePlayerLeaguesPanel.setVisible(false);
        }
        add(activePlayerLeaguesPanel);
	}
}
