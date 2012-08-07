package info.makeyourpicks.web.weight.pages;

import info.makeyourpicks.web.layout.pages.LeagueMemberWebPage;
import info.makeyourpicks.web.weight.panels.WinOverviewPanel;

public class WinOverviewPage extends LeagueMemberWebPage
{

	public static final String WICKET_ID = "overview";

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
