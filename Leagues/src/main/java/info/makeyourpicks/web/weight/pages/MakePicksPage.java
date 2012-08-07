package info.makeyourpicks.web.weight.pages;

import info.makeyourpicks.web.layout.pages.LeagueMemberWebPage;
import info.makeyourpicks.web.weight.panels.MakePicksPanel;

public class MakePicksPage extends LeagueMemberWebPage
{

	public static final String WICKET_ID = "makePicks"; 

	
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
