package info.makeyourpicks.web.admin.panels;

import info.makeyourpicks.web.layout.AbstractBasePanel;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;

/**
 * @author prc9041
 */
public class EditLeagueTypePanel extends AbstractBasePanel {
	public EditLeagueTypePanel(String id, IModel model)
	{
		super(id, model);
		add(new FeedbackPanel("feedbackpanel"));
		add(new EditLeagueTypeForm("editLeagueTypeForm", model));
	}
	
	private class EditLeagueTypeForm extends Form
	{
		public EditLeagueTypeForm(String id, IModel model)
		{
			super(id, model);
			
			add(new Label("typeOfLeague"));
			add(new RequiredTextField("leagueTypeDisplay"));
		}
	}

}

