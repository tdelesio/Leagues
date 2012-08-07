package info.makeyourpicks.web.suicide.pages;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Picks;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.web.components.CssClassModifier;
import info.makeyourpicks.web.layout.pages.LeagueMemberWebPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByLink;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import com.delesio.extensions.markup.html.repeater.data.sort.OrderByBorderLink;

/**
 * @author PRC9041
 */
public class SeasonDetailsPage extends LeagueMemberWebPage {

	public static final String WICKET_ID = "seasonDetails";
	public SeasonDetailsPage()
	{
		super();
		League league = getActiveLeague();
//		Season season = getParentSeason();
//		Season season = league.getSeason();
//		LeagueType leagueType = season.getLeagueType();
//		
//		if (leagueType.hasParent())
//		{
////			leagueType = leagueManager.getLeagueType(leagueType.getParentTypeOfLeague());
//			season = leagueManager.getSeason(leagueType.getParentTypeOfLeague(), season.getStartYear(), season.getEndYear());
//			leagueType = season.getLeagueType();
//		}		
		
		SortablePicksSummaryDataProvider dp = new SortablePicksSummaryDataProvider(league);
		final DataView<Picks> dataView = new DataView<Picks>("sorting", dp)
		{

			@Override
			protected void populateItem(final Item item) {
				boolean visible = true;
				Picks picks = (Picks)item.getModelObject();
				String cssClass = "suicidewinner";
				if (picks.getWinnerStatus()==Picks.LOSER )
				{
					cssClass = "suicideloser";
				}
				String pickDisplay = "-";
				if (picks.hasGameStarted())
				{
					pickDisplay = picks.getPickDisplay();
					//visible=false;
				}
				item.add(new Label("name", picks.getName().getUsername()).setVisible(visible));
				Label picksLabel = new Label("pick", pickDisplay);
				picksLabel.setVisible(visible);
				picksLabel.add(new SimpleAttributeModifier("class", cssClass));
				item.add(picksLabel);
				item.add(new Label("week", picks.getWeek().getWeekLabel()).setVisible(visible));
				item.add(new Label("winner", picks.getWinnerStatusLabel()).setVisible(visible));
				item.add(new Label("spread", picks.getSpreadsLabel()).setVisible(visible));
				item.add(new CssClassModifier(CssClassModifier.EvenOdd, item.getIndex() % 2 != 0));
			}
			
		};
		
		add(dataView);
		
		add(new OrderByBorderLink("orderByName", "name", dp, OrderByLink.DefaultCssProvider.getInstance(), new OrderByLink.CssProvider("orderLinkHeader", "orderLinkHeader", "orderLinkHeader"))
        {
            protected void onSortChanged()
            {
                dataView.setCurrentPage(0);
            }
            
            
            
        });
		
		
		add(new OrderByBorderLink("orderByWeek", "week", dp, OrderByLink.DefaultCssProvider.getInstance(), new OrderByLink.CssProvider("orderLinkHeader", "orderLinkHeader", "orderLinkHeader"))
        {
            protected void onSortChanged()
            {
                dataView.setCurrentPage(0);
            }
        });
		
		add(new OrderByBorderLink("orderByPick", "pick", dp, OrderByLink.DefaultCssProvider.getInstance(), new OrderByLink.CssProvider("orderLinkHeader", "orderLinkHeader", "orderLinkHeader"))
        {
            protected void onSortChanged()
            {
                dataView.setCurrentPage(0);
            }
        });
		
		add(new OrderByBorderLink("orderByWin", "winner", dp, OrderByLink.DefaultCssProvider.getInstance(), new OrderByLink.CssProvider("orderLinkHeader", "orderLinkHeader", "orderLinkHeader"))
        {
            protected void onSortChanged()
            {
                dataView.setCurrentPage(0);
            }
        });
	}
	
	private class SortablePicksSummaryDataProvider extends SortableDataProvider
	{
		private League league;
		private int size=1000;
		public SortablePicksSummaryDataProvider(League activeLeague)
		{
			this.league = activeLeague;
			setSort("week", true);
		}

		public Iterator iterator(int first, int count) {
			SortParam sp = getSort();
			LeagueType leagueType;
			List<Picks> picks = new ArrayList<Picks>(500);
			Iterator<Player> players;
			Iterator<Week> weeks;
			Week week;
			Player player;
			Picks pick;

			Season season = league.getSeason();
			leagueType = season.getLeagueType();
			if (leagueType.hasParent())
			{
//				leagueType = leagueManager.getLeagueType(league.getLeagueType().getParentTypeOfLeague());
				season = leagueManager.getSeason(leagueType.getParentTypeOfLeague(), season.getStartYear(), season.getEndYear());
			}
			
			weeks = gameManager.getWeeksBySeason(season).iterator();
			while (weeks.hasNext())
			{
				week = weeks.next();
				players = playerManager.getPlayersInLeague(league).iterator();
				while (players.hasNext())
				{
					player = players.next();	
					pick = picksManager.getPickByPlayerLeagueAndWeek(player, league, week);
					if (pick == null)
					{
						pick = new Picks(player, league, week, null, true);
					}
					picks.add(pick);
				}
			}
	
//			picks = picks.subList(first, first+count);
			if (sp.getProperty().equals("name"))
			{
				Collections.sort(picks, new Comparator<Picks>()
				{
					public int compare(Picks arg0, Picks arg1)
		            {
		                return (arg0.getName().getUsername().compareTo(arg1.getName().getUsername()));
		            }
				});
			}
			else if (sp.getProperty().equals("week"))
			{
				Collections.sort(picks, new Comparator<Picks>()
				{
					public int compare(Picks arg0, Picks arg1)
		            {
		                return (arg0.getWeek().getWeekLabel().compareTo(arg1.getWeek().getWeekLabel()));
		            }
				});
			}
			else if (sp.getProperty().equals("pick"))
			{
				Collections.sort(picks, new Comparator<Picks>()
				{
					public int compare(Picks arg0, Picks arg1)
		            {
		                return (arg0.getTeam().getFullTeamName().compareTo(arg1.getTeam().getFullTeamName()));
		            }
				});
			}
			else if (sp.getProperty().equals("winner"))
			{
				Collections.sort(picks, new Comparator<Picks>()
				{
					public int compare(Picks arg0, Picks arg1)
		            {
		                return (arg0.getWinnerStatusLabel().compareTo(arg1.getWinnerStatusLabel()));
		            }
				});
			}

			size = picks.size();
			return picks.iterator();
	
		}

		public IModel model(final Object object) {
			return new LoadableDetachableModel(){

				@Override
				protected Object load()
				{
					return picksManager.loadPicks(((Picks)object).getId());
				}
				
			};
			
		}

		public int size() {
			return size;
		}
		
	}

	
	
	
}

