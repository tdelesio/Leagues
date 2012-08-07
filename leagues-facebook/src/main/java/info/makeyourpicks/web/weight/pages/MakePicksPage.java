package info.makeyourpicks.web.weight.pages;

import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.Picks;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.web.dataprovider.PicksDataProvider;
import info.makeyourpicks.web.dataview.MakePickDataView;
import info.makeyourpicks.web.weight.panels.MakePicksPanel;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

public class MakePicksPage extends WeightBasePage
{

	public static final String WICKET_ID = "makePicks"; 
	@Override
	public String getWicketID()
	{
		return WICKET_ID;
	}

	public MakePicksPage()
	{
		
		MakePicksPanel makePicksPanel = new MakePicksPanel("makePicksPanel", getWeekModel());
		add(makePicksPanel);
	}
	
}
