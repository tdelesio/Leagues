package info.makeyourpicks.web.suicide.panels;

import java.util.Iterator;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Picks;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.SeasonStats;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.web.layout.AbstractBasePanel;

public class WinSummaryPanel extends AbstractBasePanel
{

	public WinSummaryPanel(String id, IModel<Week> model)
	{
		super(id);
		
		League league = getActiveLeague();
		Season season = model.getObject().getSeason();
		String losesDisplay;
		String cssClass="";
		
		RepeatingView repeatingView = new RepeatingView("repeating");
		add(repeatingView);
		
		Iterator<Player> players = playerManager.getPlayersInLeague(league).iterator();
		
		Player player;
		SeasonStats seasonStats;
		while (players.hasNext())
		{
			WebMarkupContainer item = new WebMarkupContainer(repeatingView.newChildId());
			repeatingView.add(item);
			
			player = players.next();
			seasonStats = picksManager.getPlayersSeasonStats(player, league, season);
			final int loses = seasonStats.getLoses();
			item.add(new Label("playername", player.getUsername()));
			
			if (loses>=2)
			{
				cssClass = "doublesuicidelose";
				losesDisplay = "2";
			}
			else if (loses==1)
			{
				cssClass = "singlesuicidelose";
				losesDisplay = "1";
			}
			else
			{
				losesDisplay = "0";
				cssClass = "nosuicideloses";
			}
			
			item.add(new Label("loses", losesDisplay));
			Label spreadPoints = new Label("spreadPoints", String.valueOf(seasonStats.getSpreadPoints()));
			item.add(spreadPoints);
			
			if (!gameManager.hasAllGamesForWeekStarted(model.getObject()))
//			Picks picks = picksManager.getPickByPlayerLeagueAndWeek(player, league, (Week)getWeekModel().getObject());
//			if (picks==null||picks.getGame()==null||!picks.getGame().hasGameStarted())
			{
				spreadPoints.setVisible(false);
			}
			item.add(new SimpleAttributeModifier("class", cssClass));
				
	
	          
		}
	}
}
