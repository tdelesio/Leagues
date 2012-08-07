package info.makeyourpicks.web.admin.pages;

import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Team;
import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.web.admin.panels.EnterTeamsPanel;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author PRC9041
 */
public class EnterTeamsPage extends AbstractAdminBasePage {
	
	public EnterTeamsPage()
	{
		throw new RestartResponseException(new LeagueTypeSelectionPage(LeagueTypeSelectionPage.ENTER_TEAMS));
	}
	
	public EnterTeamsPage(final IModel seasonModel)
	{
		super();
		setOutputMarkupId(true);
		
		add(new FeedbackPanel("feedbackpanel"));
				
		final EnterTeamsPanel enterTeamsPanel = new EnterTeamsPanel("enterTeamsPanel", seasonModel);
//		enterTeamsPanel.setVisible(false);
		enterTeamsPanel.setOutputMarkupId(true);
		enterTeamsPanel.setOutputMarkupPlaceholderTag(true);
		add(enterTeamsPanel);
	}
}

