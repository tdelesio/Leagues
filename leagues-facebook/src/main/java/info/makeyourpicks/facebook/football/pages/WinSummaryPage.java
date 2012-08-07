package info.makeyourpicks.facebook.football.pages;

import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.SeasonStats;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.model.WeekWinner;
import info.makeyourpicks.model.WinSummary;
import info.makeyourpicks.web.dataprovider.PlayerPicksSortableDataProvider;
import info.makeyourpicks.web.football.panels.WinSummaryPanel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * @author PRC9041
 */
public class WinSummaryPage extends FootballBasePage {
	
	public static final String WICKET_ID = "winSummary";
	public WinSummaryPage()
	{
		super();
		
		WinSummaryPanel winSummaryPanel = new WinSummaryPanel("winSummaryPanel");
		add(winSummaryPanel);
		
	}
	@Override
	public String getWicketID() {
		return WICKET_ID;
	}

	
}

