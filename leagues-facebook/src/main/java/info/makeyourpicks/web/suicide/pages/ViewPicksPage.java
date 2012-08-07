package info.makeyourpicks.web.suicide.pages;

import info.makeyourpicks.model.Week;
import info.makeyourpicks.web.suicide.panels.SuicideViewPicksPanel;

/**
 * @author PRC9041
 */
public class ViewPicksPage extends SuicideBasePage {

	public static final String WICKET_ID = "viewPicks";
	public ViewPicksPage()
	{
		this(0);
	}
	
	public ViewPicksPage(int selectedWeekNumber)
	{
		super(selectedWeekNumber, ViewPicksPage.class);
		
		add(new SuicideViewPicksPanel("viewPicksPanel", (Week)getWeekModel().getObject()));
	}

	@Override
	public String getWicketID() {
		return WICKET_ID;
	}
	
	
}

