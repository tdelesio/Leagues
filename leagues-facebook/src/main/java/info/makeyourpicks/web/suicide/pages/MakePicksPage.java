package info.makeyourpicks.web.suicide.pages;

import info.makeyourpicks.web.suicide.panels.SuicideMakePicksPanel;

import org.apache.wicket.model.CompoundPropertyModel;

/**
 * @author PRC9041
 */
public class MakePicksPage extends SuicideBasePage {
	
	public static final String WICKET_ID = "makePicks";
	public MakePicksPage()
	{
		this(0);
	}
	
	public MakePicksPage(int selectedWeekNumber)
	{
		super(selectedWeekNumber, MakePicksPage.class);
		
		add(new SuicideMakePicksPanel("makePicksPanel", new CompoundPropertyModel(getWeekModel())));
		
	}

	@Override
	public String getWicketID() {
		return WICKET_ID;
	}
	
	
}

