package info.makeyourpicks.web.admin.pages;

import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.Team;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.service.TeamManager;
import info.makeyourpicks.web.components.CssClassModifier;
import info.makeyourpicks.web.dataprovider.GamesDataProvider;
import info.makeyourpicks.web.forms.WeekForm;

import java.util.List;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.yui.calendar.DateTimeField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author PRC9041
 */
public class UpdateWeekPage extends AbstractAdminBasePage {
	
	@SpringBean(name="teamManager")
	protected TeamManager teamManager;
	
	public UpdateWeekPage(final IModel<Week> weekModel)
	{
		super();
//		final Week week = (Week)weekModel.getObject();
		
		add(new WeekForm("weekForm", weekModel)
		{
			@Override
			public void onSubmit() {
		
				Week week = (Week) getModelObject();
				gameManager.updateWeek(week);
		
				setResponsePage(SetupWeekPage.class);
			}
		});
		
		add(new Link("reloadJobs")
		{

			@Override
			public void onClick()
			{
				gameManager.reloadGameTriggers(weekModel.getObject());
			}
			
		});
		
		Form<Week> sendEmailForm = new Form<Week>("sendEmailForm", weekModel)
		{
			@Override
			public void onSubmit() {
				leagueManager.sendWeekSetupEmailToAllPlayers(weekModel.getObject());
				info("Email sent");
			}
		};
		add(sendEmailForm);
		
		Button button = new Button("sendEmail");
		sendEmailForm.add(button);
		
		Form updateScoreForm = new Form("updateScore");
		IModel gameModel = new CompoundPropertyModel(new LoadableDetachableModel()
		{

			@Override
			protected Object load()
			{
				Game game = new Game();
				game.setWeek((Week)weekModel.getObject());
				//game.setGameStart(((Week)weekModel.getObject()).getWeekStart());
				
				game.setGameStart(((Week)weekModel.getObject()).getWeekStart());
				return game;
			}
			
		});
		
		GamesDataProvider gamesDataProvider = new GamesDataProvider()
		{
			@Override
			public List<Game> getGames()
			{
				return gameManager.getGamesByWeek((Week)weekModel.getObject());
			}

			@Override
			public Game loadGame(Game obj)
			{
				return gameManager.loadGame(obj.getId());
			}
			
			
		};
		
		
//		updateScoreForm.add(new GamesDataView("repeating", gamesDataProvider, true, true).setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance()));
		DataView dataView = new DataView("repeating", gamesDataProvider)
		{
			@Override
			protected void populateItem(final Item item) {

				Game game = (Game)item.getModelObject();
				Link deleteGame = new Link("deleteGame")
				{

					@Override
					public void onClick() {
						gameManager.deleteGame((Game)item.getModelObject());
					}
					
				};
//				deleteGame.setVisible(edit);
				deleteGame.add(new SimpleAttributeModifier("onclick", "return confirm('Delete Game?');"));
				item.add(deleteGame);
				
				Link submitLink =  new Link("updateGame", item.getModel())
				{
					
					@Override
					public void onClick() {				
						setResponsePage(new EnterScoresPage(item.getModel()));
					}
					
				};
//				submitLink.setVisible(edit);
				item.add(submitLink);
				
//				if (showSpreads)
					item.add(new Label("spread"));
//				else
//					item.add(new Label("spread", game.getSpreadPlusFootnote()));
				item.add(new Label("favScore"));
				item.add(new Label("dogScore"));
		        item.add(new Label("fav.fullTeamName").add(new CssClassModifier(CssClassModifier.HomeAway, game.isFavHome())));
		        item.add(new Label("dog.fullTeamName").add(new CssClassModifier(CssClassModifier.HomeAway, !game.isFavHome())));
		        item.add(new Label("gameStartDisplay"));
		        
				item.add(new CssClassModifier(CssClassModifier.EvenOdd, item.getIndex()% 2 != 0));

			

			}
		};
		updateScoreForm.add(dataView);
		add(updateScoreForm);
		
		add(new EnterGameForm("enterGameForm", gameModel));
	}
	
	private class EnterGameForm extends Form
	{
		public EnterGameForm(String id, IModel model)
		{
			super(id, model);
			
			Game game = (Game)getModelObject();
			Week week = game.getWeek();
			//LeagueType leagueType = week.getLeagueType();
			//List<Team> teams = teamManager.getTeamsByLeagueType(leagueType);
			List<Team> teams = teamManager.getUnenteredTeamsForWeek(week);
			final DropDownChoice fav = new DropDownChoice("fav", teams, new ChoiceRenderer("fullTeamName", "id"));
			fav.setRequired(true);
			add(fav);
			
			final DropDownChoice dog = new DropDownChoice("dog", teams, new ChoiceRenderer("fullTeamName", "id"));
			dog.setRequired(true);
			add(dog);
			
			RequiredTextField spread = new RequiredTextField("spread", Double.class);
			add(spread);
			
			CheckBox favHome = new CheckBox("favHome");
			add(favHome);
			
			add(new DateTimeField("gameStart"));
			
			add(new Button("submit"));
		}

		@Override
		protected void onSubmit() {
			Game game = (Game)getModelObject();
			Week week = game.getWeek();			
//			gameManager.insertGame(game);
			gameManager.createUpdateGame(game);
			
			Game newGame = new Game();
			newGame.setWeek(week);
//			setModelObject(new CompoundPropertyModel(newGame));
			
			List<Team> teams = teamManager.getUnenteredTeamsForWeek(week);
			DropDownChoice fav = (DropDownChoice)get("fav");
			fav.setChoices(teams);

			DropDownChoice dog = (DropDownChoice)get("dog");
			dog.setChoices(teams);
			
			getModel().detach();
		}
		
	}

}

