package info.makeyourpicks.facebook.football.pages;

import info.makeyourpicks.web.football.panels.ViewPicksPanel;

/**
 * @author PRC9041
 */
public class ViewPicksPage extends FootballBasePage {

	public static final String WICKET_ID = "viewPicks";
	public ViewPicksPage()
	{
		this(0);
	}
	
	public ViewPicksPage(int selectedWeekNumber)
	{
		super(selectedWeekNumber, ViewPicksPage.class);
	
		ViewPicksPanel viewPicksPanel = new ViewPicksPanel("viewPicksPanel", getWeekModel());
		add(viewPicksPanel);
	}

	@Override
	public String getWicketID() {
		return WICKET_ID;
	}
	
	
}

