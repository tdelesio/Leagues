package info.makeyourpicks.web.suicide.pages;

import org.apache.wicket.markup.html.CSSPackageResource;

import info.makeyourpicks.web.layout.pages.LeagueMemberWebPage;
import info.makeyourpicks.web.suicide.panels.WinSummaryPanel;

/**
 * @author PRC9041
 */
public class WinSummaryPage extends LeagueMemberWebPage {

	public static final String WICKET_ID = "winSummary";
	public WinSummaryPage()
	{
		super();
		
		add(CSSPackageResource.getHeaderContribution("styles/suicide.css"));
		
		WinSummaryPanel summaryPanel = new WinSummaryPanel("winSummaryPanel", getWeekModel());
		add(summaryPanel);
	}
	
	
}

