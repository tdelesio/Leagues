package info.makeyourpicks.web.weight.panels;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.SeasonStats;
import info.makeyourpicks.web.dataprovider.PlayerPicksSortableDataProvider;
import info.makeyourpicks.web.layout.AbstractBasePanel;

public class WinOverviewPanel extends AbstractBasePanel
{

	public WinOverviewPanel(String id, final IModel<Season> seasonModel)
	{
		super(id);
		
		List<IColumn> columns = new ArrayList<IColumn>(2);
		columns.add(new PropertyColumn(new Model("Player"), "username"));
		columns.add(new AbstractColumn(new Model("Total Wins"))
		{
			public void populateItem(Item item, String id, IModel model) {
				Player player = (Player)model.getObject();
				SeasonStats seasonStats = picksManager.getPlayersSeasonStats(player, getActiveLeague(), seasonModel.getObject());
				item.add(new Label(id, String.valueOf(seasonStats.getWins())));
			}
		});
		
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
				return playerManager.getPlayersInLeague(getActiveLeague());
			}
			
		};
		DefaultDataTable defaultDataTable = new DefaultDataTable("dataview", columns, dataProvider, 100);
		add(defaultDataTable);
	}
}
