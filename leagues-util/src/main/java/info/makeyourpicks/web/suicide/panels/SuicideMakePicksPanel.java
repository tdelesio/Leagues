package info.makeyourpicks.web.suicide.panels;

import info.makeyourpicks.PoolHelper;
import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.Picks;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.Team;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.web.components.CssClassModifier;
import info.makeyourpicks.web.dataprovider.GamesDataProvider;
import info.makeyourpicks.web.layout.AbstractBasePanel;

import java.util.List;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 * @author PRC9041
 */
public class SuicideMakePicksPanel extends AbstractBasePanel {

	private WebMarkupContainer repeatingContainer;
//	private List<Team> pickedTeams;
	
	public SuicideMakePicksPanel(String id, final IModel<Week> weekModel) {
		super(id);

		add(new FeedbackPanel("feedbackpanel"));
		
		//Define the models
		LoadableDetachableModel<List<Team>> pickedTeamsModel = new LoadableDetachableModel<List<Team>>()
		{
			@Override
			protected List<Team> load() {
				return teamManager.getPickedTeamsForPlayerAndLeague(getPlayer(), getActiveLeague());
			}
		};
		
		LoadableDetachableModel<Integer> losesModel = new LoadableDetachableModel<Integer>()
		{
			@Override
			protected Integer load() {
				Week week = (Week) weekModel.getObject();
				Season season = week.getSeason();
				return picksManager.getPlayersSeasonStats(getPlayer(), getActiveLeague(), season).getLoses();
			}
		};
		
		IModel<Picks> pickModel = new LoadableDetachableModel<Picks>() {
			@Override
			protected Picks load() {
				return picksManager.getPickByPlayerLeagueAndWeek(getPlayer(), getActiveLeague(), (Week)weekModel.getObject());
			}
		};	

		repeatingContainer = new WebMarkupContainer("repeatingContainer");
		repeatingContainer.setOutputMarkupId(true);
		add(repeatingContainer);

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
		
		// add the games for the week
		RefreshingView gamesDataView = new SuicideDataView("repeating", gamesDataProvider, pickModel, pickedTeamsModel, losesModel, getActiveLeague().isSpreads());
		gamesDataView.setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());
		repeatingContainer.add(gamesDataView);		

//		Label eliminated = new Label("eliminated", "You have been eliminated!");
//		eliminated.setVisible(false);
//		add(eliminated);

//		boolean eliminated = (((Picks)pickModel.getObject()).isNoPick() && (Integer)losesModel.getObject() >= 3) || (!((Picks)pickModel.getObject()).isNoPick() && (Integer)losesModel.getObject() >= 2); 
//		if () {
			// form.setVisible(false);
//			gamesDataView.setVisible(false);
//			eliminated.setVisible(true);

//		}
		// else if ((picks.getGame()!=null&&picks.getGame().hasGameStarted()))
		// {
		// form.setEnabled(false);
		// }
		// add(form);

//		Label footnote = new Label("footnote",
//				"^ - Spreads are only used at the end of the year for a in tie breaker.");
//		footnote.add(new SimpleAttributeModifier("class", "spreadfootnote"));
//		footnote.setVisible(false);
//		repeatingContainer.add(footnote);

//		if (!getActiveLeague().isSpreads()) {
//			footnote.setVisible(true);
//		}
	}

	
	
	private class SuicideDataView extends DataView
	{
		private boolean showSpreads;
		private IModel pickModel;
		private IModel pickedTeamsModel;
		private IModel lossModel;
		public SuicideDataView(String id, IDataProvider dataProvider, IModel pickModel, IModel pickedTeams, IModel loseModel, boolean showSpreads)
		{
			super(id, dataProvider);
			this.pickModel = pickModel;
			this.pickedTeamsModel = pickedTeams;
			this.showSpreads = showSpreads;
			this.lossModel = loseModel;
		}

		@Override
		protected void populateItem(final Item item) {


			Game game = (Game) item.getModelObject();
			Picks pick = (Picks)pickModel.getObject();
			Team winningTeam = game.getWinner(getActiveLeague().isSpreads());
			List<Picks> pickedTeams = (List<Picks>)pickedTeamsModel.getObject();
			
			boolean eliminated = (((Picks)pickModel.getObject()).isNoPick() && (Integer)lossModel.getObject() >= 2) || (!((Picks)pickModel.getObject()).isNoPick() && (Integer)lossModel.getObject() >= 2);
			boolean isPickFav = pick.getTeam()!=null&&pick.getTeam().equals(game.getFav());
			boolean isPickDog = pick.getTeam()!=null&&pick.getTeam().equals(game.getDog());
			
			WebMarkupContainer favContainer = new WebMarkupContainer("favContainer");
			WebMarkupContainer dogContainer = new WebMarkupContainer("dogContainer");
						
			StringBuffer favCssClass = new StringBuffer("");
			StringBuffer dogCssClass = new StringBuffer("");
			// default the css class
			if (game.isFavHome()) {
				favCssClass.append("home ");
				dogCssClass.append("away ");
			} 			
			else
			{
				favCssClass.append("away ");
				dogCssClass.append("home ");
			}

			Image favCheck = new Image("favCheck");
			if (isPickFav&&winningTeam!=null&&winningTeam.equals(game.getDog()))
			{
				favCheck.setDefaultModel(PoolHelper.getImageModel(PoolHelper.RED_X));
				favCssClass.append("loser ");
			}
			else
			{
				favCheck.setDefaultModel(PoolHelper.getImageModel(PoolHelper.GREEN_CHECK));
			}
			favCheck.setVisible(false);
			
			Link favLink = new Link("fav") {

				@Override
				public void onClick() {
					Team team = (Team) getModelObject();
					Picks pick = (Picks)pickModel.getObject();
					Game game = (Game)item.getModelObject();
					pick.setGame(game);
					pick.setTeam(team);
					picksManager.updatePlayerPick(pick, false);
					setResponsePage(getPage().getClass());
					// target.addComponent(repeatingContainer);
				}
			};
			
			if (pick.getTeam() != null && pick.getTeam().equals(game.getFav())) {
//				favContainer.add(new SimpleAttributeModifier("class", "weekpick"));
				favCssClass.append("weekpick ");
				favCheck.setVisible(true);
			}
			
			// dog.setOutputMarkupId(true);
//			favLink.add(new SimpleAttributeModifier("class", dogCssClass));
			
			

			//image for the dog checkbox
			final Image dogCheck = new Image("dogCheck");
			if (isPickDog&&winningTeam!=null&&winningTeam.equals(game.getFav()))
			{
				dogCheck.setDefaultModel(PoolHelper.getImageModel(PoolHelper.RED_X));
				dogCssClass.append("loser ");
			}
			else
			{
				dogCheck.setDefaultModel(PoolHelper.getImageModel(PoolHelper.GREEN_CHECK));
			}
			dogCheck.setVisible(false);

			
			Link dogLink = new Link("dog") {
				@Override
				public void onClick() {
					Team team = (Team) getModelObject();
					Picks pick = (Picks)pickModel.getObject();
					Game game = (Game)item.getModelObject();

					pick.setGame(game);
					pick.setTeam(team);
					picksManager.updatePlayerPick(pick, false);
					setResponsePage(getPage().getClass());
					// target.addComponent(repeatingContainer);

				}

			};
			if (pick.getTeam() != null && pick.getTeam().equals(game.getDog())) {
//				dogContainer.add(new SimpleAttributeModifier("class","weekpick"));
				dogCssClass.append("weekpick ");
				dogCheck.setVisible(true);
			}
			// dog.setOutputMarkupId(true);
//			dogLink.add(new SimpleAttributeModifier("class", dogCssClass));
						
			if (game.hasGameStarted() || (pick.getGame()!=null&&pick.getGame().hasGameStarted())) {
				favLink.setEnabled(false);
				dogLink.setEnabled(false);
			}
			
			if ( (pick.getTeam()==null || !pick.getTeam().equals(game.getFav())) && pickedTeams.contains(game.getFav()))
			{
				favLink.setEnabled(false);
//				favLink.add(new SimpleAttributeModifier("class", "prevpicked"));
				favCssClass.append("prevpicked ");
//				favCheck.setVisible(true);
			}
			
			if ( (pick.getTeam()==null || !pick.getTeam().equals(game.getDog())) && pickedTeams.contains(game.getDog()))
			{
				dogLink.setEnabled(false);
				dogCssClass.append("prevpicked ");
//				dogLink.add(new SimpleAttributeModifier("class", "prevpicked"));
//				dogCheck.setVisible(true);
			}

			favContainer.setEnabled(!eliminated);
			dogContainer.setEnabled(!eliminated);
		
			item.add(favContainer);
			favContainer.add(new SimpleAttributeModifier("class", favCssClass));
			favContainer.add(favCheck);
			favContainer.add(favLink);
			favLink.add(new Label("favLabel", ((Game)item.getModelObject()).getFav().getFullTeamName()));
			favLink.add(new SimpleAttributeModifier("class", favCssClass));
			
			item.add(dogContainer);
			dogContainer.add(new SimpleAttributeModifier("class", dogCssClass));
			dogContainer.add(dogCheck);
			dogContainer.add(dogLink);
			dogLink.add(new Label("dogLabel", ((Game)item.getModelObject()).getDog().getFullTeamName()));
			dogLink.add(new SimpleAttributeModifier("class", dogCssClass));
			
//			item.add(new Label("favScore"));
//			item.add(new Label("dogScore"));
			item.add(new Label("spread"));
			item.add(new Label("gameStartDisplay"));
			item.add(new CssClassModifier(CssClassModifier.EvenOdd, item.getIndex() % 2 != 0));
			
		}
	}
}
