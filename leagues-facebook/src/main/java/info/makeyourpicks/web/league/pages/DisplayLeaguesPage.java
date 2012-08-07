package info.makeyourpicks.web.league.pages;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.PlayerLeague;
import info.makeyourpicks.web.layout.AbstractLeagueSecureFacebookPage;
import info.makeyourpicks.web.layout.AbstractLeagueSecurePage;
import info.makeyourpicks.web.league.panels.PlayersLeaguesPanel;

public class DisplayLeaguesPage extends AbstractLeagueSecureFacebookPage
{

	public static final String WICKET_ID = "displayLeaguesPage";
	
	public DisplayLeaguesPage()
	{
		LoadableDetachableModel<Player> model = new LoadableDetachableModel<Player>()
		{

			@Override
			protected Player load()
			{
				return getPlayer();
			}
			
		};
		PlayersLeaguesPanel playersLeaguesPanel = new PlayersLeaguesPanel("playersLeaguesPanel", model)
		{

			@Override
			public Class<? extends AbstractLeagueSecurePage> getOnClickClass(
					IModel<PlayerLeague> model)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
		};
		
		add(playersLeaguesPanel);
	}
	
	@Override
	public String getWicketID()
	{
		return WICKET_ID;
	}

}
