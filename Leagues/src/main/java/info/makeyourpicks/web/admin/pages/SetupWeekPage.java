package info.makeyourpicks.web.admin.pages;

import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.web.admin.panels.SetupWeekPanel;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;


/**
 * @author PRC9041
 */
public class SetupWeekPage extends AbstractAdminBasePage {

	public SetupWeekPage()
	{
		throw new RestartResponseException(new LeagueTypeSelectionPage(LeagueTypeSelectionPage.SETUP_WEEK));
	}
	
	public SetupWeekPage(IModel seasonModel)
	{
		final SetupWeekPanel displayWeekPanel = new SetupWeekPanel("displayWeekPanel", seasonModel);
		add(displayWeekPanel);
	}
	
}

 	