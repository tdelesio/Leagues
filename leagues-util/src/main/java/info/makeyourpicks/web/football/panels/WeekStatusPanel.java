package info.makeyourpicks.web.football.panels;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

import info.makeyourpicks.model.SeasonStats;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.web.layout.AbstractBasePanel;

public class WeekStatusPanel extends AbstractBasePanel {

	public WeekStatusPanel(String id, IModel<Week> model)
	{
		super(id);
		
		final int winningNumber = picksManager.getWinningScoreForLeagueAndWeek(getActiveLeague(), model.getObject());
		
		SeasonStats seasonStats = picksManager.getPlayersStatsForWeek(getPlayer(), getActiveLeague(), model.getObject());
		int wins = seasonStats.getWins();
		
		String winner = "Week "+model.getObject().getWeekNumber()+" Leader: "+winningNumber;
		String you = "You have "+wins+" so far";
		
		Label weekNumber = new Label("weekNumber", String.valueOf(model.getObject().getWeekNumber()));
		add(weekNumber);
		
		Label winnerLabel = new Label("winnerPoints", String.valueOf(winningNumber));
		add(winnerLabel);
		
		Label youLabel = new Label("yourPoints", String.valueOf(wins));
		add(youLabel);
	} 
}
