package info.makeyourpicks.facebook.football.pages;

import info.makeyourpicks.web.football.panels.MakePicksPanel;

/**
 * @author PRC9041
 */
public class MakePicksPage extends FootballBasePage {

	public static final String WICKET_ID = "makePicks";
	

	public MakePicksPage()
	{
		this(0);
	}
	public MakePicksPage(int selectedWeekNumber)
	{
		super(selectedWeekNumber, MakePicksPage.class);
		
		
		MakePicksPanel makePicksPanel = new MakePicksPanel("makePicksPanel", getWeekModel());
		add(makePicksPanel);
		
	}

	@Override
	public String getWicketID() {
		return WICKET_ID;
	}
			
	
	
}

