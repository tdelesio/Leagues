package info.makeyourpicks.web.suicide.pages;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.CSSPackageResource;

import info.makeyourpicks.model.Week;
import info.makeyourpicks.web.layout.pages.LeagueMemberWebPage;
import info.makeyourpicks.web.suicide.panels.SuicideViewPicksPanel;

/**
 * @author PRC9041
 */
public class ViewPicksPage extends LeagueMemberWebPage {

	public static final String WICKET_ID = "viewPicks";
	public ViewPicksPage()
	{
		this(0);
	}
	
	public ViewPicksPage(int selectedWeekNumber)
	{
		super(selectedWeekNumber);

		add(CSSPackageResource.getHeaderContribution("styles/suicide.css"));
		
		add(new SuicideViewPicksPanel("viewPicksPanel", (Week)getWeekModel().getObject()));
	}

	@Override
	protected Class<? extends Page> getWeekSelectionRedirectPage() {
		return ViewPicksPage.class;
	}

	
	
	
}

