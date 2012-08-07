package info.makeyourpicks.web.weight.panels;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.Picks;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.web.dataprovider.PicksDataProvider;
import info.makeyourpicks.web.dataview.MakePickDataView;
import info.makeyourpicks.web.layout.AbstractBasePanel;

public class MakePicksPanel extends AbstractBasePanel
{

	public MakePicksPanel(String id, final IModel<Week> model)
	{
		super(id);
		

		add(new FeedbackPanel("feedbackpanel"));
		
		//user makes his picks via this dataview
        PicksDataProvider dataProvider = new PicksDataProvider(PicksDataProvider.WEIGHT, false)
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
			public void handleFavoritePick(AjaxRequestTarget target, Picks picks)
			{
				target.addComponent(getPage().get("weightContainer"));
			}

			@Override
			public void handleUnderdogPick(AjaxRequestTarget target, Picks picks)
			{
				target.addComponent(getPage().get("weightContainer"));
			}

			@Override
			public void handleItems(final Item item)
			{
				item.setOutputMarkupId(true);
				
//				final IModel model = item.getModel();
				Picks pick = (Picks)item.getModelObject();
				Game game = pick.getGame();
				int totalSize = getDataProvider().size();
				int weight = totalSize-item.getIndex();
				
				Label weightTF = new Label("weight");
				item.add(weightTF);
				
				List<Picks> oldpicks = picksManager.getPicksByPlayerLeagueAndWeek(getPlayer(), getActiveLeague(), model.getObject());
				Collections.sort(oldpicks, PicksDataProvider.getWeightComparator(false));
				Picks replaceUPPick = null;
				for (Picks oldpick:oldpicks)
				{
					if (oldpick.getWeight()>pick.getWeight() && !oldpick.getGame().hasGameStarted())
					{
						replaceUPPick = oldpick;
					}
				}
				
				final Picks replaceUpPick = replaceUPPick;
				AjaxLink up = new AjaxLink("up")
				{

					@Override
					public void onClick(AjaxRequestTarget target)
					{
						Picks picks = (Picks)item.getModelObject();
						int weight = picks.getWeight();
						picks.setWeight(replaceUpPick.getWeight());
						replaceUpPick.setWeight(weight);						
						
						picksManager.updatePlayerPick(picks, false);
						picksManager.updatePlayerPick(replaceUpPick, false);
						
						target.addComponent(getPage().get("weightContainer"));
					}
					
				};
				Image upArrow = new Image("upArrow", new AbstractReadOnlyModel()
				{
					
					@Override
					public Object getObject() {
						//return "upArrow.jpg";
						return "up.jpg";
					}
					
				});
				up.add(upArrow);
				
				Collections.sort(oldpicks, PicksDataProvider.getWeightComparator(true));
				Picks replaceDOWNPick = null;
				for (Picks oldpick:oldpicks)
				{
					if (oldpick.getWeight()<pick.getWeight() && !oldpick.getGame().hasGameStarted())
					{
						replaceDOWNPick = oldpick;
					}
				}
				
				final Picks replaceDownPick = replaceDOWNPick;
				AjaxLink down = new AjaxLink("down")
				{
					@Override
					public void onClick(AjaxRequestTarget target)
					{
						Picks picks = (Picks)item.getModelObject();
						int weight = picks.getWeight();
						picks.setWeight(replaceDownPick.getWeight());
						replaceDownPick.setWeight(weight);
						
						picksManager.updatePlayerPick(picks, false);
						picksManager.updatePlayerPick(replaceDownPick, false);
						
						target.addComponent(getPage().get("weightContainer"));
					}
				};
				Image downArrow = new Image("downArrow", new AbstractReadOnlyModel()
				{
					
					@Override
					public Object getObject() {
//						return "downArrow.jpg";
						return "down.jpg";
					}
					
				});
				down.add(downArrow);
				
				item.add(up);
				item.add(down);
				
				if (pick.isNoPick() || pick.getTeam()==null)
				{
					up.setVisible(false);
					down.setVisible(false);
					weightTF.setVisible(false);
					pick.setWeight(weight);		
					if (pick.getId()==0)
					{
						picksManager.updatePlayerPick(pick, false);
					}
				}
				
				
				if (game.hasGameStarted())
				{
					up.setVisible(false);
					down.setVisible(false);					
				}
				
				if (weight == totalSize || game.hasGameStarted() || replaceUpPick==null)
				{
					up.setEnabled(false);
					up.setVisible(false);
				}
				
				if (weight == 1 || game.hasGameStarted() || replaceDownPick == null)
				{
					down.setEnabled(false);
					down.setVisible(false);
				}
			}  	
			
			
        };
        
        WebMarkupContainer weightContainer = new WebMarkupContainer("weightContainer");
        weightContainer.setOutputMarkupId(true);
//        SortableBehavior sortableBehavior = new SortableBehavior();
//        sortableBehavior.setConnectWith(".connectedSortable");
//        weightContainer.add(sortableBehavior);
//        weightContainer.setOutputMarkupId(true);
		add(weightContainer);
        weightContainer.add(makePickDataView);
        
	
	}
}
