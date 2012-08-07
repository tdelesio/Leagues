package info.makeyourpicks.web.weight.pages;

import info.makeyourpicks.model.Picks;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.SeasonStats;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.web.dataprovider.PlayerPicksSortableDataProvider;
import info.makeyourpicks.web.weight.panels.WinOverviewPanel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

public class WinOverviewPage extends WeightBasePage
{

	public static final String WICKET_ID = "overview";
	@Override
	public String getWicketID()
	{
		return WICKET_ID;
	}
	
	public WinOverviewPage()
	{
		
		WinOverviewPanel winOverviewPanel = new WinOverviewPanel("winOverviewPanel", getSeasonModel());
		add(winOverviewPanel);
		
	}

}
