package info.makeyourpicks.web.admin.pages;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.service.PlayerManager;

import java.util.List;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author PRC9041
 */
public class AdminLeaguePage extends AbstractAdminBasePage {

	@SpringBean(name="leagueManager")
	private LeagueManager leagueManager;
	
	@SpringBean(name="playerManager")
	protected PlayerManager playerManager;
	
	public AdminLeaguePage()
	{
		super();
		
		
		IModel leagueModel = new LoadableDetachableModel()
		{

			@Override
			protected Object load()
			{
				return new League();
			}
			
		};
		
		init(leagueModel);
	}
	
	public AdminLeaguePage(IModel<League> leagueModel)
	{
		init(leagueModel);
	}
	
	private void init(IModel<League> leagueModel)
	{
		add(new FeedbackPanel("feedbackpanel"));
		
		add(new UpdateLeagueForm("updatLeagueForm", new CompoundPropertyModel(leagueModel)));
	}
	
	private class UpdateLeagueForm extends Form {
		
		public UpdateLeagueForm(String id, IModel model)
		{
			super(id, model); 
			League leagueInfo = (League)model.getObject();
			
			add(new CheckBox("active"));
			add(new CheckBox("free"));
			add(new CheckBox("spreads"));
			
			List<Player> players = playerManager.getAllPlayers();
			
			DropDownChoice playersDD = new DropDownChoice("admin", players, new ChoiceRenderer("username", "username"));
			add(playersDD);
			
//			List<LeagueType> leagues = leagueManager.getLeagueTypes();
			List<Season> seasons = leagueManager.getSeasons();
			DropDownChoice leagueTypesDD = new DropDownChoice("season", seasons, new ChoiceRenderer("displaySeason", "id"));
			leagueTypesDD.setRequired(true);
			add(leagueTypesDD);
			
			RequiredTextField leagueName = new RequiredTextField("leagueName");
			leagueName.setLabel(new Model("League Name"));
			add(leagueName);
			
			add(new RequiredTextField("maxSize", Integer.class));
			
			add(new TextField("password"));
			
			add(new Button("submit"));
			
			add(new Button("delete")
			{

				@Override
				public void onSubmit() {
					League leagueInfo = (League)getParent().getDefaultModelObject();
					try
					{
						leagueManager.deleteLeague(leagueInfo);
					}
					catch (Exception exception)
					{
						error(exception.getMessage());
					}
					setResponsePage(AdminLeaguesPage.class);
				}
				
			}.setDefaultFormProcessing(false));
			
		}

		@Override
		public void onSubmit() {
			League league = (League)getModelObject();
			if (league.getAdmin()==null)
			{
				league.setAdmin(getPlayer());
			}
			
			leagueManager.createOrUpdateLeague(league);
			
			setResponsePage(AdminLeaguesPage.class);
			
		}
		

	}
}

