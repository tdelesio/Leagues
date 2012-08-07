package info.makeyourpicks.web.suicide.panels;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Picks;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.web.components.CssClassModifier;
import info.makeyourpicks.web.layout.AbstractBasePanel;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.AbstractReadOnlyModel;

/**
 * @author PRC9041
 */
public class SuicideViewPicksPanel extends AbstractBasePanel {
	
	public SuicideViewPicksPanel(String id, Week week)
	{ 
		super(id);
		
		add(new FeedbackPanel("feedbackpanel"));
		
		League activeLeague = getActiveLeague();

		RepeatingView repeatingView = new PlayerPicksDataView("repeating", activeLeague, week);
		add(repeatingView);
	}
	
	private class PlayerPicksDataView extends RepeatingView
	{
		public PlayerPicksDataView(String id, League league, Week week)
		{
			super(id);
			
			int index = 0;
			Iterator<Player> players = playerManager.getPlayersInLeague(league).iterator();
			Player player;
			while (players.hasNext())
			{
				String cssClass = "suicidewinner";
				player = players.next();
				List<Picks> playerPicks = picksManager.getPicksByPlayerLeagueAndWeek(player, league, week);
				String teamDisplay="-";
				String spreadDisplay="";
				if (!playerPicks.isEmpty())
				{
					Picks pick = playerPicks.iterator().next(); 
					if (!pick.hasGameStarted())
					{
						continue;
					}
					teamDisplay = pick.getPickDisplay();
					spreadDisplay = String.valueOf(pick.getGame().getSpread());
					
					if (pick.getWinnerStatus()==Picks.LOSER)
					{
						cssClass="suicideloser";
					}
					
					
				}
				WebMarkupContainer item = new WebMarkupContainer(newChildId());
				add(item);
				item.add(new Label("playerName", player.getUsername()));
				Label team = new Label("team", teamDisplay);
				final String css = cssClass;
				team.add(new AttributeModifier("class", true, new AbstractReadOnlyModel()
		            {
		                public Object getObject()
		                {
		                    return css;
		                }
		            }));
				item.add(team);
				if (league.isSpreads())
				{
					item.add(new Label("spread", spreadDisplay));
				}
				else
				{
					item.add(new Label("spread", "-"));
				}
				
				
		        item.add(new CssClassModifier(CssClassModifier.EvenOdd, index % 2 != 0));
		        index++;		        
			}
		}
	}

}

