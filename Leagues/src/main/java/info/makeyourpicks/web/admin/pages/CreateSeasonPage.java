package info.makeyourpicks.web.admin.pages;

import info.makeyourpicks.model.Season;
import info.makeyourpicks.service.LeagueManager;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.NumberValidator;

public class CreateSeasonPage extends AbstractAdminBasePage
{

	@SpringBean(name="leagueManager")
	private LeagueManager leagueManager;
	
	public CreateSeasonPage()
	{
		LoadableDetachableModel seasonModel = new LoadableDetachableModel()
		{

			@Override
			protected Object load()
			{
				return new Season();
			}
			
		};
		
		init(seasonModel);
	}
	
	public CreateSeasonPage(IModel seasonModel)
	{
		init(seasonModel);
	}
	
	private void init(IModel seasonModel)
	{
		add(new FeedbackPanel("feedbackpanel"));
		
		Form form = new Form("form", new CompoundPropertyModel(seasonModel))
		{

			@Override
			protected void onSubmit()
			{
				Season season = (Season)getModelObject();
				leagueManager.createUpdateSeason(season);
				
				setResponsePage(AdminLeaguesPage.class);
			}
			
		};
		add(form);
		
		RequiredTextField startYear = new RequiredTextField("startYear");
		startYear.add(NumberValidator.range(2008, 2100));
		form.add(startYear);
		
		RequiredTextField endYear = new RequiredTextField("endYear");
		endYear.add(NumberValidator.range(2008, 2100));
		form.add(endYear);
		
		DropDownChoice leagueType = new DropDownChoice("leagueType", leagueManager.getLeagueTypes(), new ChoiceRenderer("typeOfLeague", "id"));
		form.add(leagueType);
		
		form.add(new Button("submit"));
	}
}
