package info.makeyourpicks.web.dataview;

import info.makeyourpicks.PoolHelper;
import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.Picks;
import info.makeyourpicks.model.Team;
import info.makeyourpicks.model.Week;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.repeater.IItemReuseStrategy;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;

public abstract class MakePickDataView extends DataView
{
	public MakePickDataView(String id, IDataProvider dataProvider)
	{
		super(id, dataProvider);
	}
	
	@Override
	protected void populateItem(final Item item) {
		final IModel model = item.getModel();
		final Picks pick = (Picks)model.getObject();
		Game game = pick.getGame();
		Team winningTeam = game.getWinner(useSpreads());
		
		StringBuffer rowClass = new StringBuffer("game ui-corner-all ");
		
		boolean isPickFav = pick.getTeam()!=null&&pick.getTeam().equals(game.getFav());
		boolean isPickDog = pick.getTeam()!=null&&pick.getTeam().equals(game.getDog());
		
		//the container for the favorite
		final WebMarkupContainer favContainer = new WebMarkupContainer("favContainer");
		favContainer.setOutputMarkupId(true);
		
		//image for favorite
		final Image favCheck = new Image("fav_check");
		favCheck.setOutputMarkupId(true);			
		favCheck.setOutputMarkupPlaceholderTag(true);	
		
		//the container for the dog
		final WebMarkupContainer dogContainer = new WebMarkupContainer("dogContainer");
		dogContainer.setOutputMarkupId(true);
		
		//image for dog
		final Image dogCheck = new Image("dog_check");
		dogCheck.setOutputMarkupId(true);		
		dogCheck.setOutputMarkupPlaceholderTag(true);
		
		//favorite link
		AjaxLink favLink = new AjaxLink("fav")
		{

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				favContainer.setEnabled(false);
				target.addComponent(favContainer);
				
				dogContainer.setEnabled(true);
				target.addComponent(dogContainer);

				pick.setTeam(pick.getGame().getFav());
									
				updatePlayerPick(pick);
				
				favCheck.setVisible(true);
				dogCheck.setVisible(false);
				
				handleFavoritePick(target, pick);
				
				favContainer.add(new SimpleAttributeModifier("class", "pickLink pickselected"));
				dogContainer.add(new SimpleAttributeModifier("class", "pickLink picknotselected"));
				
				target.addComponent(favContainer);
				target.addComponent(dogContainer);
				
			}
		};
		
		//dog link
		AjaxLink dogLink = new AjaxLink("dog")
		{
			@Override
			public void onClick(AjaxRequestTarget target)
			{	
				dogContainer.setEnabled(false);
				target.addComponent(dogContainer);
				
				favContainer.setEnabled(true);
				target.addComponent(favContainer);
				
				pick.setTeam(pick.getGame().getDog());

				updatePlayerPick(pick);
				
				favCheck.setVisible(false);
				dogCheck.setVisible(true);

				handleUnderdogPick(target, pick);
				
				favContainer.add(new SimpleAttributeModifier("class", "pickLink picknotselected"));
				dogContainer.add(new SimpleAttributeModifier("class", "pickLink pickselected"));
				
				target.addComponent(favContainer);
				target.addComponent(dogContainer);
			}
		};
		
		//default the css for the container and set the image visisble
		StringBuffer favContainerCssClass=new StringBuffer("pickLink "), dogContainerCssClass=new StringBuffer("pickLink ");
		if (pick==null||pick.getTeam()==null)
		{
			favCheck.setVisible(false);
			dogCheck.setVisible(false);				
		}
		else if (isPickFav)
		{
			favCheck.setVisible(true);
			dogCheck.setVisible(false);
			favContainerCssClass.append("pickselected ");
			dogContainerCssClass.append("picknotselected ");
			favContainer.setEnabled(false);
			dogContainer.setEnabled(true);
		}
		else
		{
			favCheck.setVisible(false);
			dogCheck.setVisible(true);
			favContainerCssClass.append("picknotselected ");
			dogContainerCssClass.append("pickselected ");
			favContainer.setEnabled(true);
			dogContainer.setEnabled(false);
		}
		
		//set image for favorite
		if (isPickFav&&winningTeam!=null&&winningTeam.equals(game.getDog()))
		{
			favCheck.setDefaultModel(PoolHelper.getImageModel(PoolHelper.RED_X));
		}
		else
		{
			favCheck.setDefaultModel(PoolHelper.getImageModel(PoolHelper.GREEN_CHECK));
		}

		//set image for dog
		if (isPickDog&&winningTeam!=null&&winningTeam.equals(game.getFav()))
		{
			dogCheck.setDefaultModel(PoolHelper.getImageModel(PoolHelper.RED_X));
		}
		else
		{
			dogCheck.setDefaultModel(PoolHelper.getImageModel(PoolHelper.GREEN_CHECK));
		}
		
		//set the css for the link
		StringBuffer favLinkCssClass=new StringBuffer("pickLink "), dogLinkCssClass = new StringBuffer("pickLink ");
		//default the css class
		if (game.isFavHome())
		{
			favLinkCssClass.append("home ");
			dogLinkCssClass.append("away ");
		}
		else
		{
			favLinkCssClass.append("away ");
			dogLinkCssClass.append("home ");
		}
		
		//add css for winner/loser for favorite
		if (winningTeam!=null)
		{
			if (winningTeam.equals(game.getFav()))
			{
				favLinkCssClass.append("won ");
				favContainerCssClass.append("won ");
				dogLinkCssClass.append("lost ");
				dogContainerCssClass.append("lost ");
								
				if (isPickFav)
				{
					favLinkCssClass.append("winner ");
					favContainerCssClass.append("winner ");
					rowClass.append("winner ");
				}
				else
				{
					dogLinkCssClass.append("loser ");
					dogContainerCssClass.append("loser ");
					rowClass.append("loser ");
				}
			}
			else
			{
				favLinkCssClass.append("lost ");
				favContainerCssClass.append("lost ");
				dogLinkCssClass.append("won ");
				dogContainerCssClass.append("won ");
				
				if (isPickFav)
				{
					favLinkCssClass.append("loser ");
					favContainerCssClass.append("loser ");
					rowClass.append("loser ");
				}
				else
				{
					dogLinkCssClass.append("winner ");
					dogContainerCssClass.append("winner ");
					rowClass.append("winner ");
				}
			}
		}
//		if (isPickFav&&&&winningTeam.equals(game.getFav()))
//		{
//			
//		}
//		else if (isPickFav&&winningTeam!=null&&!winningTeam.equals(game.getFav()))
//		{
//			
//		}
//		//add css for winner/loser for dog
//		if (isPickDog&&winningTeam!=null&&winningTeam.equals(game.getDog()))
//		{
//			dogLinkCssClass.append("winner ");
//			dogContainerCssClass.append("winner ");
//		}
//		else if (isPickDog&&winningTeam!=null&&!winningTeam.equals(game.getDog()))
//		{
//			dogLinkCssClass.append("loser ");
//			dogContainerCssClass.append("loser ");
//		}
		
		//disable the links if hte game has started
		if (game.hasGameStarted())
		{
			favLink.setEnabled(false);
			dogLink.setEnabled(false);
		}
		
		//add favorite link
		favLink.add(new Label("favLabel", game.getFav().getFullTeamName()));
		favLink.add(new SimpleAttributeModifier("class", favLinkCssClass));
		favContainer.add(favLink);
		favContainer.add(favCheck);
		favContainer.add(new SimpleAttributeModifier("class", favContainerCssClass.toString()));
		item.add(favContainer);
		//add favorite score		
		item.add(new Label("favScore", String.valueOf(game.getFavScore())));
		//add spread
		item.add(new Label("spreadMinus", pick.getSpreadsLabel()));
		item.add(new Label("spreadPlus", pick.getSpreadsLabel()));
		//add dog link
		dogLink.add(new Label("dogLabel", game.getDog().getFullTeamName()));
		dogLink.add(new SimpleAttributeModifier("class", dogLinkCssClass));
		dogContainer.add(dogLink);
		dogContainer.add(dogCheck);
		dogContainer.add(new SimpleAttributeModifier("class", dogContainerCssClass.toString()));
		item.add(dogContainer);
		//add dog score
		item.add(new Label("dogScore", String.valueOf(game.getDogScore())));
		//add game start
		item.add(new Label("gameStart", game.getGameStartDisplay()));
		

		if (game.hasScoresEntered())
		{
			rowClass.append("gameover "); 
		}
		else if (game.hasGameStarted())
		{
			rowClass.append("gamestarted ");					
		}
		item.add(new SimpleAttributeModifier("class", rowClass.toString()));
		
		//handle extra items
		handleItems(item);
	}
		
	@Override
	public IItemReuseStrategy getItemReuseStrategy() {
		return ReuseIfModelsEqualStrategy.getInstance();
	}
	
	public void handleFavoritePick(AjaxRequestTarget target, Picks picks)
	{
		
	}
	
	public void handleUnderdogPick(AjaxRequestTarget target, Picks picks)
	{
		
	}
	
	public abstract void updatePlayerPick(Picks pick);
	public boolean useSpreads()
	{
		return true;
	}
	
	public void handleItems(Item item)
	{
		
	}
}
