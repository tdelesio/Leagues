package info.makeyourpicks.web.admin.pages;

import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.service.LeagueManager;

import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author PRC9041
 */
public class AdminLeagueTypePage extends AbstractAdminBasePage {
	
	@SpringBean(name="leagueManager")
	private LeagueManager leagueManager;
	
	public AdminLeagueTypePage()
	{
		add(new Label("title", "Create"));
		
		IModel<LeagueType> leagueModel = new LoadableDetachableModel()
		{

			@Override
			protected Object load()
			{
				return new LeagueType();
			}
			
		};
		
		init(leagueModel);
	}
		
	public AdminLeagueTypePage(IModel<LeagueType> model)
	{
		add(new Label("title", "Edit"));
		
		init(model);
	}
	private void init(IModel<LeagueType> model)
	{
		add(new FeedbackPanel("feedbackpanel"));
		
		Form<LeagueType> form = new Form<LeagueType>("leagueTypeForm", new CompoundPropertyModel(model))
		{
			@Override
			protected void onSubmit() {
				LeagueType leagueType = (LeagueType)getModelObject();
				leagueManager.createOrUpdateLeagueType(leagueType);

				setResponsePage(AdminLeaguesPage.class);
			}
		};
		form.add(new RequiredTextField("typeOfLeague"));
		form.add(new RequiredTextField("leagueTypeDisplay"));
		form.add(new TextField("parentTypeOfLeague"));
		form.add(new DateField("start"));
		form.add(new DateField("end"));
		form.add(new Button("add"));
		add(form);
		
	}
	

}

