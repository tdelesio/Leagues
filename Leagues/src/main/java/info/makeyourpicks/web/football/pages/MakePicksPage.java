package info.makeyourpicks.web.football.pages;

import info.makeyourpicks.web.football.panels.MakePicksPanel;
import info.makeyourpicks.web.football.panels.MakePicksRedesignPanel;
import info.makeyourpicks.web.football.panels.WeekStatusPanel;
import info.makeyourpicks.web.layout.IHeaderWidget;
import info.makeyourpicks.web.layout.pages.LeagueMemberWebPage;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.basic.Label;

/**
 * @author PRC9041
 */
public class MakePicksPage extends LeagueMemberWebPage implements IHeaderWidget {

	public static final String WICKET_ID = "makePicks";

	public MakePicksPage()
	{
		this(0);
	}
	public MakePicksPage(int selectedWeekNumber)
	{
		super(selectedWeekNumber);
		
		add(CSSPackageResource.getHeaderContribution("styles/makePicks.css"));
//		add(CSSPackageResource.getHeaderContribution("styles/master.css"));
		
//		MakePicksPanel makePicksPanel = new MakePicksPanel("makePicksPanel", getWeekModel());
		MakePicksRedesignPanel makePicksPanel = new MakePicksRedesignPanel("makePicksPanel", getWeekModel());
		add(makePicksPanel);
		
	}
	@Override
	protected Class<? extends Page> getWeekSelectionRedirectPage() {
		return MakePicksPage.class;
	}
	public Component getHeaderWidget() {
//		return new Label(IHeaderWidget.WICKET_ID, "WEEEEE");
		WeekStatusPanel weekStatusPanel = new WeekStatusPanel(IHeaderWidget.WICKET_ID, getWeekModel());
		return weekStatusPanel;
	}
	

	
			
	
}

