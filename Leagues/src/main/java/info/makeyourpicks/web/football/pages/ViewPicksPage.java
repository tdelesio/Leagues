package info.makeyourpicks.web.football.pages;

import info.makeyourpicks.web.football.panels.ViewPicksPanel;
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
public class ViewPicksPage extends LeagueMemberWebPage 
//implements IHeaderWidget 
{

	public static final String WICKET_ID = "viewPicks";
	public ViewPicksPage()
	{
		this(0);
	}
	
	public ViewPicksPage(int selectedWeekNumber)
	{
		super(selectedWeekNumber, false);
	
		add(CSSPackageResource.getHeaderContribution("styles/viewPicks.css"));
		
		ViewPicksPanel viewPicksPanel = new ViewPicksPanel("viewPicksPanel", getWeekModel());
		add(viewPicksPanel);
	}

	@Override
	protected Class<? extends Page> getWeekSelectionRedirectPage() {
		return ViewPicksPage.class;
	}
	
//	public Component getHeaderWidget() {
//		return new Label(IHeaderWidget.WICKET_ID, "You have a "+picksManager.percentChangeToWinForWeek(getPlayer(), getActiveLeague(), getWeekModel().getObject())+"% chance to win week"+getWeekModel().getObject().getWeekNumber());
//	}
}

