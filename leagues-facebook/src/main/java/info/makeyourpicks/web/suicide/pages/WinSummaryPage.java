package info.makeyourpicks.web.suicide.pages;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Picks;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.SeasonStats;
import info.makeyourpicks.model.Week;

import java.util.Iterator;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;

/**
 * @author PRC9041
 */
public class WinSummaryPage extends SuicideBasePage {

	public static final String WICKET_ID = "winSummary";
	public WinSummaryPage()
	{
		super();
		League league = getActiveLeague();
//		Season season = league.getSeason();
//		Season season = getParentSeason();
		Season season = ((Week)getWeekModel().getObject()).getSeason();
//		LeagueType leagueType = season.getLeagueType();
		String losesDisplay;
		String cssClass="";
		
//		if (leagueType.hasParent())
//		{
////			leagueType = leagueManager.getLeagueType(leagueType.getParentTypeOfLeague());
//			season = leagueManager.getSeason(leagueType.getParentTypeOfLeague(), season.getStartYear(), season.getEndYear());
//			leagueType = season.getLeagueType();
//		}		
		
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
			
			Picks picks = picksManager.getPickByPlayerLeagueAndWeek(player, league, (Week)getWeekModel().getObject());
			if (picks==null||picks.getGame()==null||!picks.getGame().hasGameStarted())
			{
				spreadPoints.setVisible(false);
			}
			item.add(new SimpleAttributeModifier("class", cssClass));
				
	
	          
		}
		
		
		
	}
	@Override
	public String getWicketID() {
		return WICKET_ID;
	}	
	
	
}

