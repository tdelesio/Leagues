package info.makeyourpicks.web.football.panels;

import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.Picks;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.SeasonStats;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.web.dataprovider.PlayerPicksSortableDataProvider;
import info.makeyourpicks.web.layout.AbstractBasePanel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class ViewPicksPanel extends AbstractBasePanel
{

	public ViewPicksPanel(String id, final IModel<Week> model)
	{
		super(id);
		
		add(new FeedbackPanel("feedbackpanel"));
		
		final int winningNumber = picksManager.getWinningScoreForLeagueAndWeek(getActiveLeague(), model.getObject());
		List<IColumn> columns = new ArrayList<IColumn>(17);
		Iterator<Game> games = gameManager.getGamesByWeek(model.getObject()).iterator();
		IColumn nameColumn = new PropertyColumn(new Model("Player"), "username");
		columns.add(nameColumn);
		
		
		
		columns.add(new AbstractColumn(new Model("Wins"))
		{
			public void populateItem(Item item, String id, IModel itemModel) {
				Player player = (Player)itemModel.getObject();
				SeasonStats seasonStats = picksManager.getPlayersStatsForWeek(player, getActiveLeague(), model.getObject());
				int wins = seasonStats.getWins();
				item.add(new Label(id, String.valueOf(wins)));
				if (wins>0 && wins==winningNumber)
				{
					item.add(new SimpleAttributeModifier("class", "winning"));
				}
			}
		});
		
		while (games.hasNext())
		{
			final Game game = games.next();
//			final String columnHeader = game.getFav().getShortName()+" vs "+game.getDog().getShortName()+" ("+game.getSpread()+")";
//			final String columnHeader = game.getFav().getShortName()+"<br/>vs<br/>"+game.getDog().getShortName()+"<br/>("+game.getSpread()+")";
			final String columnHeader = game.getFav().getShortName()+"\nvs\n"+game.getDog().getShortName()+"\n("+game.getSpread()+")";
			//columns.add(new PropertyColumn(new Model(columnHeader), "firstName", "firstName"));
			
			columns.add(new AbstractColumn(new Model())
			{
				
				public void populateItem(Item item, String id, IModel itemModel) {
					Player player = (Player)itemModel.getObject();
					Picks pick = picksManager.getPickByPlayerLeagueWeekAndGame(player, getActiveLeague(), model.getObject(), game);
					
					String cssClass = "";
					
					if (pick.getWinnerStatus()==Picks.WINNER)
					{
						cssClass="winner ";
						if (getPlayer().equals(player))
						{
							cssClass="playerwinner ";
						}
					}
					else if (getPlayer().equals(player))
					{
						cssClass = "player ";
					}
					
					if (pick.getWeight()==2)
					{
						cssClass = cssClass+"double ";
					}
					
					final String css = cssClass;
					item.add(new Label(id, pick.getPickDisplay(true)));
					item.add(new AttributeModifier("class", true, new AbstractReadOnlyModel()
		            {
		                public Object getObject()
		                {
		                    return css;
		                }
		            }));			
				}

				@Override
				public Component getHeader(String componentId) {
					return new MultiLineLabel(componentId, columnHeader);
				}

				@Override
				public String getCssClass() {
					return "viewpicks";
				}

				
				
				
			});
		}
		
		PlayerPicksSortableDataProvider dataProvider = new PlayerPicksSortableDataProvider()
		{

			@Override
			public Player getPlayer(Player player)
			{
				return playerManager.loadPlayer(player.getId());
			}

			@Override
			public List<Player> getPlayersInLeague()
			{
//				return playerManager.getPlayersInLeague(getActiveLeague());
				return picksManager.getPlayersForLeagueSortedByWeekWins(getActiveLeague(), model.getObject());
			}
			
		};
		DefaultDataTable defaultDataTable = new DefaultDataTable("table", columns, dataProvider, getActiveLeague().getMaxSize()+1)
		{

			@Override
			protected Item newRowItem(String id, int index, IModel model)
			{
				return super.newRowItem(id, index, model);
			}
			
		};
		defaultDataTable.add(new SimpleAttributeModifier("class", "viewpicks"));
		add(defaultDataTable);
		
		if (!(model.getObject()).hasCurrentWeekStarted())
		{
			defaultDataTable.setVisible(false);
			info("Week "+(model.getObject()).getWeekNumber()+" has not started yet.");
		}
		
	}
}
