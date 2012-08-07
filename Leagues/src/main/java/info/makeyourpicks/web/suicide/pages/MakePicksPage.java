package info.makeyourpicks.web.suicide.pages;

import info.makeyourpicks.web.layout.pages.LeagueMemberWebPage;
import info.makeyourpicks.web.suicide.panels.SuicideMakePicksPanel;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * @author PRC9041
 */
public class MakePicksPage extends LeagueMemberWebPage {
	
	public static final String WICKET_ID = "makePicks";
	public MakePicksPage()
	{
		this(0);
	}
	
	public MakePicksPage(int selectedWeekNumber)
	{
		super(selectedWeekNumber);
		
		add(CSSPackageResource.getHeaderContribution("styles/suicide.css"));
		
		add(new SuicideMakePicksPanel("makePicksPanel", new CompoundPropertyModel(getWeekModel())));
		
	}

	@Override
	protected Class<? extends Page> getWeekSelectionRedirectPage() {
		return MakePicksPage.class;
	}

	
	
	
}

