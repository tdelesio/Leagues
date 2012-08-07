package info.makeyourpicks.web.weight.panels;

import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.Picks;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.web.dataprovider.PicksDataProvider;
import info.makeyourpicks.web.dataview.MakePickDataView;
import info.makeyourpicks.web.layout.AbstractBasePanel;

import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

public abstract class ViewPicksPanel extends AbstractBasePanel
{

	public ViewPicksPanel(String id, final IModel<Week> weekModel, final IModel<Player> playerModel)
	{
		super(id);
		
		add(new FeedbackPanel("feedbackpanel"));
		
		//user makes his picks via this dataview
        PicksDataProvider dataProvider = new PicksDataProvider()
        {
 
			@Override
			public List<Game> getGamesByWeek()
			{
				return gameManager.getGamesByWeek(weekModel.getObject());
			}

			@Override
			public Picks getPickByPlayerLeagueWeekAndGame(Game game)
			{
				return picksManager.getPickByPlayerLeagueWeekAndGame((Player)playerModel.getObject(), getActiveLeague(), weekModel.getObject(), game);
			}

			@Override
			public Picks loadPick(long id)
			{
				return picksManager.loadPicks(id);
			}
        	
        };
        
        MakePickDataView makePickDataView = new MakePickDataView("simple", dataProvider)
        {
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
			public void handleItems(final Item item)
			{
				final IModel model = item.getModel();
				Picks pick = (Picks)model.getObject();
				Game game = pick.getGame();
				
				Label weightLabel = new Label("weight");
				item.add(weightLabel);
				
				if (!game.hasGameStarted())
				{
					item.setVisible(false);
				}
				
				item.setEnabled(false);
			}  	
			
			
        };
        
        WebMarkupContainer weightContainer = new WebMarkupContainer("weightContainer");
		weightContainer.setOutputMarkupId(true);
		add(weightContainer);
		
        weightContainer.add(makePickDataView);
        
//        final IModel playPickModel = new CompoundPropertyModel(new LoadableDetachableModel()
//        {
//        	@Override
//			protected Object load()
//			{
//        		Picks picks = new Picks();
//        		picks.setName((Player)playerModel.getObject());
//        		return picks;
//			}
//        });
//        
//        setModel(playPickModel);
//        
//        DropDownChoice downChoice = new DropDownChoice("name", playerManager.getPlayersInLeague(getActiveLeague()), new ChoiceRenderer("username", "id"));
//        add(downChoice);
        
        LoadableDetachableModel playersModel = new LoadableDetachableModel()
        {
        	protected Object load()
			{
        		return playerManager.getPlayersInLeague(getActiveLeague());
			}
        };
        

        final DropDownChoice downChoice = new DropDownChoice("name", playerModel, playersModel, new ChoiceRenderer("username", "id"));
        add(downChoice);
        
        downChoice.add(new AjaxFormComponentUpdatingBehavior("onChange")
		{

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
//				setResponsePage(new ViewPicksPage(playerModel));
				setResponsePage(getViewPicksPage(playerModel));
				

			}
			
		});
	}
	
	public abstract Page getViewPicksPage(IModel<Player> playerModel); 
}
