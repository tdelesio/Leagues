package info.makeyourpicks.web.admin.pages;

import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.Week;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

public class LeagueTypeSelectionPage extends AbstractAdminBasePage
{

	public static final int SETUP_WEEK = 1;
	public static final int ENTER_TEAMS = 2;
	private Season season=new Season();
	public LeagueTypeSelectionPage(final int redirectPage) 
	{
		super();
		
		final IModel model = new LoadableDetachableModel()
		{

			@Override
			protected Object load()
			{
				return new Week();
			}
			
		};
//		
		String title="";
		if (redirectPage==SETUP_WEEK)
		{
			title = "Setup Week";
		}
		else if (redirectPage==ENTER_TEAMS)
		{
			title = "Enter Teams";
		}
		
		add(new Label("leagueTypeTitle", title));
		
		add(new LeagueSelectForm("form", new CompoundPropertyModel(model), redirectPage));
	}
	
	private class LeagueSelectForm extends Form
	{
		public LeagueSelectForm(String id, IModel model, final int redirectPage)
		{
			super(id, model);
			
			DropDownChoice dropDownChoice = new DropDownChoice("season", leagueManager.getParentSeasons(), new ChoiceRenderer("displaySeason", "id"));
			dropDownChoice.setOutputMarkupId(true);
			dropDownChoice.add(new AjaxFormComponentUpdatingBehavior("onChange")
			{
				@Override
				protected void onUpdate(AjaxRequestTarget target) 
				{
					final Week week = (Week)getModelObject();
					LoadableDetachableModel model = new LoadableDetachableModel()
					{
						@Override
						protected Object load()
						{
							
							return week.getSeason();
						}
					};
					if (redirectPage==SETUP_WEEK)
					{	
						setResponsePage(new SetupWeekPage(model));
					}
					else if (redirectPage==ENTER_TEAMS)
					{
						setResponsePage(new EnterTeamsPage(model));
					}
				}
			});
			add(dropDownChoice);
		}
	}
	

}
