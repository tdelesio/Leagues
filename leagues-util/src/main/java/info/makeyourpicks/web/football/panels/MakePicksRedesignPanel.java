package info.makeyourpicks.web.football.panels;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import info.makeyourpicks.PoolHelper;
import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Picks;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Team;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.web.components.InfoModalWindow;
import info.makeyourpicks.web.dataprovider.PicksDataProvider;
import info.makeyourpicks.web.dataview.MakePickRedesignDataView;
import info.makeyourpicks.web.layout.AbstractBasePanel;

public class MakePicksRedesignPanel extends AbstractBasePanel 
{

	private final DropDownChoice doublePick;
	
	public MakePicksRedesignPanel(String id, final IModel<Week> model)
	{
		super(id);
		
		add(new FeedbackPanel("feedbackpanel"));
		
		String ch = "Make picks for week "+model.getObject().getWeekNumber();
		Label contentHeader = new Label("contentHeader", ch)	;
		add(contentHeader);
		
		//get the activeleague
		League activeLeague = getActiveLeague();
		Player player = getPlayer();		
		
		final Form form = new Form("makePicks");
		form.setOutputMarkupId(true);
		add(form);
		
		final InfoModalWindow modal = new InfoModalWindow("modal2", "This pick counts for 2 points instead of one.");
		form.add(modal);
        form.add(modal.getDisplayLink("showModal2"));
        
        //user makes his picks via this dataview
        PicksDataProvider dataProvider = new PicksDataProvider()
        {

			@Override
			public List<Game> getGamesByWeek()
			{
				return gameManager.getGamesByWeek(model.getObject());
			}

			@Override
			public Picks getPickByPlayerLeagueWeekAndGame(Game game)
			{
				return picksManager.getPickByPlayerLeagueWeekAndGame(getPlayer(), getActiveLeague(), model.getObject(), game);
			}

			@Override
			public Picks loadPick(long id)
			{
				return picksManager.loadPicks(id);
			}
        	
        };
        MakePickRedesignDataView makePickDataView = new MakePickRedesignDataView("pickRow", dataProvider)
        {

			@Override
			public void handleFavoritePick(AjaxRequestTarget target, Picks picks)
			{
				handleDoublePickDropDown(target, model, picks);
			}

			@Override
			public void handleUnderdogPick(AjaxRequestTarget target, Picks picks)
			{
				handleDoublePickDropDown(target, model, picks);
			}

			@Override
			public void updatePlayerPick(Picks pick)
			{
				picksManager.updatePlayerPick(pick, false);
			}

			@Override
			public boolean useSpreads()
			{
				return getActiveLeague().isSpreads();
			}

			@Override
			public void handleItems(Item item)
			{
				Picks pick = (Picks)item.getModelObject();
				Game game = pick.getGame();
				boolean isPickFav = pick.getTeam()!=null&&pick.getTeam().equals(game.getFav());
				boolean isPickDog = pick.getTeam()!=null&&pick.getTeam().equals(game.getDog());
				
//				final Image dogCheck = (Image)item.get("dogContainer:dog_check");
//				if (pick.getWeight()==2&&isPickDog)
//				{
//					dogCheck.setDefaultModel(PoolHelper.getImageModel(PoolHelper.BLUE_CHECK));
//				}
//				
//				final Image favCheck = (Image)item.get("favContainer:fav_check");
//				if (pick.getWeight()==2&&isPickFav)
//				{
//					favCheck.setDefaultModel(PoolHelper.getImageModel(PoolHelper.BLUE_CHECK));
//				}
				
				
			}  	
			
			
        };
        
        form.add(makePickDataView);
        
        List<Team> doubleTeams = teamManager.getPickedTeamsForPlayerWeekAndLeague(player, model.getObject(), activeLeague);
		final Picks doublePicks = picksManager.getDoublePickForPlayerLeagueAndWeek(getPlayer(), getActiveLeague(), model.getObject());
		doublePick = new DropDownChoice("team", new PropertyModel(doublePicks, "team"), doubleTeams, new ChoiceRenderer("fullTeamName", "id"));
		
		doublePick.setOutputMarkupId(true);
		doublePick.add(new AjaxFormComponentUpdatingBehavior("onChange")
		{

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				
				picksManager.setDoublePick(getPlayer(), getActiveLeague(), doublePicks.getWeek(), doublePicks.getTeam());
				target.addComponent(form);
			}
			
		});
		if ((doublePicks.getGame()!=null&&doublePicks.getGame().hasGameStarted()))
		{
			doublePick.setEnabled(false);
		}
		else
		{
			List<Team> pickedDoubleTeams = teamManager.getPickedDoubleTeamsForPlayerWeekAndLeague(player, model.getObject(), activeLeague);
			doubleTeams.removeAll(pickedDoubleTeams);
		}
		form.add(doublePick);
	}
	
	private void handleDoublePickDropDown(AjaxRequestTarget target, IModel<Week> model, Picks pick)
	{
		Picks doublePicks = picksManager.getDoublePickForPlayerLeagueAndWeek(getPlayer(), getActiveLeague(), pick.getWeek());
		if (doublePicks.equals(pick))
		{
			doublePick.setModelObject(null);
			doublePicks.setWeight(1);
			picksManager.updatePlayerPick(doublePicks, true);
			
		}
		
		//handle the setting of double picks
		List<Team> doubleTeams = teamManager.getPickedTeamsForPlayerWeekAndLeague(getPlayer(), model.getObject(), getActiveLeague());
		List<Team> pickedDoubleTeams = teamManager.getPickedDoubleTeamsForPlayerWeekAndLeague(getPlayer(), model.getObject(), getActiveLeague());

		doubleTeams.removeAll(pickedDoubleTeams);
		doublePick.setChoices(doubleTeams);
		target.addComponent(doublePick);
	}
}
