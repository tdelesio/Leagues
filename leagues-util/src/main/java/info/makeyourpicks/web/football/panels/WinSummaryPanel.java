package info.makeyourpicks.web.football.panels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import info.makeyourpicks.model.Week;
import info.makeyourpicks.model.WinSummary;
import info.makeyourpicks.model.YesNoEnum;
import info.makeyourpicks.web.layout.AbstractBasePanel;

public class WinSummaryPanel extends AbstractBasePanel
{

	private YesNoEnum bankerEnum;
//	private int totalWins = 0;
	
	public WinSummaryPanel(String id)
	{
		this(id, YesNoEnum.No);
	}
	
	public WinSummaryPanel(String id, YesNoEnum yesNoEnum)
	{
		super(id);
		setOutputMarkupId(true);
		bankerEnum = yesNoEnum;		
		
		ISortableDataProvider<WinSummary> dataProvider = new SortableDataProvider<WinSummary>()
		{

			public Iterator<? extends WinSummary> iterator(int start, int count)
			{
				return picksManager.getWinSummary(getActiveLeague()).subList(start, start+count).iterator();
			}

			public IModel<WinSummary> model(WinSummary winSummary)
			{
				return new CompoundPropertyModel<WinSummary>(winSummary);
			}

			public int size()
			{
				return picksManager.getWinSummary(getActiveLeague()).size();
			}

			public void detach()
			{
				
			}
			
		};
		
		List<IColumn<WinSummary>> columns = new ArrayList<IColumn<WinSummary>>(23);
//		Iterator<Week> weeks = gameManager.getWeeksBySeason(getActiveLeague().getSeason()).iterator();
		columns.add(new PropertyColumn<WinSummary>(new Model("Place"), "place"));
		columns.add(new PropertyColumn<WinSummary>(new Model("Player"), "player.username"));	
		PropertyColumn<WinSummary> totalWinsColumn = new PropertyColumn<WinSummary>(new Model("Total Wins"), "numberOfWins");
		columns.add(totalWinsColumn);
		
		Iterator<Week> weeks = gameManager.getWeeksBySeason(getActiveLeague().getSeason()).iterator();
//		totalWins = 0;
		while (weeks.hasNext())
		{
			final Week week = weeks.next();
			final String columnHeader = week.getWeekLabel();
			columns.add(new AbstractColumn<WinSummary>(new Model(columnHeader))
			{

				public void populateItem(Item<ICellPopulator<WinSummary>> item,
						String id, IModel<WinSummary> model)
				{
					boolean isWinner = model.getObject().getWeekTotal().get(week).isWinner();
					int weekWins = model.getObject().getWeekTotal().get(week).getWins();
//					totalWins += weekWins;
					item.add(new Label(id, String.valueOf(weekWins)));
					if (isWinner)
					{
						item.add(new SimpleAttributeModifier("class", "winner"));
					}
				}
				
			});
		}
		
		
		PropertyColumn<WinSummary> entryPrizeWonColumn = new PropertyColumn<WinSummary>(new Model("Placement earnings"), "entryPrizeWonDisplay");
		PropertyColumn<WinSummary>  weeklyMoneyWonColumn = new PropertyColumn<WinSummary>(new Model("Week earnings"), "weeklyMoneyWonDisplay");
		PropertyColumn<WinSummary> totalColumn = new PropertyColumn<WinSummary>(new Model("Total"), "totalMoneyWon");
		
		if (getActiveLeague().isBanker()&&bankerEnum.equals(YesNoEnum.YES))
		{
			columns.add(entryPrizeWonColumn);
			columns.add(weeklyMoneyWonColumn);
			columns.add(totalColumn);
		}
		
		
		
		final DefaultDataTable<WinSummary> defaultDataTable = new DefaultDataTable<WinSummary>("table", columns, dataProvider, 100);
		add(defaultDataTable);
		
		WebMarkupContainer bankerContainer = new WebMarkupContainer("bankerContainer");
		add(bankerContainer);
		
		//dropdown for banker
		DropDownChoice<YesNoEnum> banker = new DropDownChoice<YesNoEnum>("bankerEnum", new PropertyModel(this, "bankerEnum") ,Arrays.asList(YesNoEnum.values()));
		
		final WinSummaryPanel panel = this;
		banker.add(new AjaxFormComponentUpdatingBehavior("onChange")
		{

			@Override
			protected void onUpdate(AjaxRequestTarget target) 
			{
				WinSummaryPanel newPanel = new WinSummaryPanel("winSummaryPanel", getBankerEnum());
				panel.replaceWith(newPanel);
				target.addComponent(newPanel);
			}
			
		});
		bankerContainer.add(banker);
		
		if (!getActiveLeague().isBanker())
			bankerContainer.setVisible(false);
	}

	public YesNoEnum getBankerEnum() {
		return bankerEnum;
	}

	public void setBankerEnum(YesNoEnum bankerEnum) {
		this.bankerEnum = bankerEnum;
	}
	
	
}
