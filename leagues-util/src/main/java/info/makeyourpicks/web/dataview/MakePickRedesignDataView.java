package info.makeyourpicks.web.dataview;

import info.makeyourpicks.PoolHelper;
import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.Picks;
import info.makeyourpicks.model.Team;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.web.ImageEnum;
import info.makeyourpicks.web.images.icons.nfl.NFLIcon;

import java.util.List;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.repeater.IItemReuseStrategy;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;

public abstract class MakePickRedesignDataView extends DataView
{
	public MakePickRedesignDataView(String id, IDataProvider dataProvider)
	{
		super(id, dataProvider);
	}
	
	@Override
	protected void populateItem(final Item item) 
	{
		item.setOutputMarkupId(true);
		
		final Picks pick = (Picks)item.getModelObject();
		final Game game = pick.getGame();
		Team winningTeam = game.getWinner(useSpreads());
		
		StringBuffer rowClass = new StringBuffer("game ui-corner-all ");
		
		boolean isPickFav = pick.getTeam()!=null&&pick.getTeam().equals(game.getFav());
		boolean isPickDog = pick.getTeam()!=null&&pick.getTeam().equals(game.getDog());
		
		final boolean isFavHome = game.isFavHome();
		
		//set the css for the link
//		StringBuffer favLinkCssClass=new StringBuffer("pickLink "), dogLinkCssClass = new StringBuffer("pickLink ");
		
		MultiLineLabel gameStart = new MultiLineLabel("gameStart", game.getGameStartMultiLineDisplay());
		
//		String homeTeam, awayTeam, spreadSignValue ;
		
//		if (isFavHome)
//		{
//			homeTeam = game.getFav().getFullTeamName();
//			awayTeam = game.getDog().getFullTeamName(); 
//			spreadSignValue = "-";
//		}
//		else
//		{
//			homeTeam = game.getDog().getFullTeamName();
//			awayTeam = game.getFav().getFullTeamName();
//			spreadSignValue = "+";
//		}
		
		final Image favImage = new Image("favImage");
		final Image dogImage = new Image("dogImage");
		final Image favIcon = new Image("favIcon");
		final Image dogIcon = new Image("dogIcon");
		
		favIcon.setImageResourceReference(new ResourceReference(NFLIcon.class, game.getFav().getShortName().toLowerCase()+".gif"));
		dogIcon.setImageResourceReference(new ResourceReference(NFLIcon.class, game.getDog().getShortName().toLowerCase()+".gif"));
		//DefaultModel(model);
		
		favImage.setDefaultModel(ImageEnum.PICKNOTSELECTED.getModel());
		dogImage.setDefaultModel(ImageEnum.PICKNOTSELECTED.getModel());
		
		//set image for favorite
		if (isPickFav)
		{
			favImage.setDefaultModel(ImageEnum.PICKSELECTED.getModel());
			
		}
		else if (isPickDog)
		{
			dogImage.setDefaultModel(ImageEnum.PICKSELECTED.getModel());
		}
	
					
//		final Label favLabel = new Label("favLabel", game.getFav().getTeamName());
//		final Label dogLabel = new Label("dogLabel", game.getDog().getTeamName());
		final Label favLabel = new Label("favLabel", game.getFav().getFullTeamName());
		final Label dogLabel = new Label("dogLabel", game.getDog().getFullTeamName());
		
		final Label favScore = new Label("favScore", "("+game.getFavScore()+")");
		final Label dogScore = new Label("dogScore", "("+game.getDogScore()+")");
		
		if (game.isFavHome())
		{
			favLabel.add(new SimpleAttributeModifier("class", "home"));
		}
		else
		{
			dogLabel.add(new SimpleAttributeModifier("class", "home"));
		}
		
		Label spread = new Label("spread", pick.getSpreadsLabel());
		
//		Label spreadSign = new Label("spreadSign", spreadSignValue);
		final WebMarkupContainer favLinkContainer = new WebMarkupContainer("favLinkContainer");
		final WebMarkupContainer dogLinkContainer = new WebMarkupContainer("dogLinkContainer");
		
		favLinkContainer.setOutputMarkupId(true);
		dogLinkContainer.setOutputMarkupId(true);
		
		final AjaxLink favLink = new AjaxLink("fav") 
		{	
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				pick.setTeam(game.getFav());
				
				updatePlayerPick(pick);
				
				favImage.setDefaultModel(ImageEnum.PICKSELECTED.getModel());
				dogImage.setDefaultModel(ImageEnum.PICKNOTSELECTED.getModel());
	
				handleFavoritePick(target, pick);
				
//				target.addComponent(item);
				target.addComponent(favLinkContainer);
				target.addComponent(dogLinkContainer);
				
			}
		};
		
		final AjaxLink dogLink = new AjaxLink("dog") 
		{
			
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				pick.setTeam(game.getDog());
				
				updatePlayerPick(pick);
				
				favImage.setDefaultModel(ImageEnum.PICKNOTSELECTED.getModel());
				dogImage.setDefaultModel(ImageEnum.PICKSELECTED.getModel());
				
				handleUnderdogPick(target, pick);
				
//				target.addComponent(item);
				target.addComponent(favLinkContainer);
				target.addComponent(dogLinkContainer);
			}
		};
		
		if (game.hasGameStarted())
		{
			favLink.setEnabled(false);
			dogLink.setEnabled(false);
			if (isPickFav)
				favImage.setDefaultModel(ImageEnum.GAMESTARTED.getModel());
			else if (isPickDog)
				dogImage.setDefaultModel(ImageEnum.GAMESTARTED.getModel());
		}
		
		if (winningTeam!=null)
		{
			if (winningTeam.equals(game.getFav()))
			{
//				favLinkCssClass.append("won ");
//				dogLinkCssClass.append("lost ");
								
				if (isPickFav)
				{
//					favLinkCssClass.append("winner ");
					rowClass.append("winner ");
					favImage.setDefaultModel(ImageEnum.WINPICK.getModel());
				}
				else
				{
//					dogLinkCssClass.append("loser ");
					rowClass.append("loser ");
					dogImage.setDefaultModel(ImageEnum.LOSTPICK.getModel());
				}
			}
			else
			{
//				favLinkCssClass.append("lost ");
//				dogLinkCssClass.append("won ");
				
				if (isPickFav)
				{
//					favLinkCssClass.append("loser ");
					rowClass.append("loser ");
					favImage.setDefaultModel(ImageEnum.LOSTPICK.getModel());
				}
				else
				{
//					dogLinkCssClass.append("winner ");
					rowClass.append("winner ");
					dogImage.setDefaultModel(ImageEnum.WINPICK.getModel());
				}
			}
		}
		
		if (!game.hasScoresEntered())
		{
			favScore.setVisible(false);
			dogScore.setVisible(false);
		}
		
		
		
		favLinkContainer.add(favLink);
		dogLinkContainer.add(dogLink);
		
		favLink.add(favImage);
		dogLink.add(dogImage);
		
		item.add(favIcon);
		item.add(dogIcon);
		item.add(spread);
		item.add(favLabel);
		item.add(dogLabel);
		item.add(gameStart);
		item.add(favLinkContainer);
		item.add(dogLinkContainer);
		item.add(favScore);
		item.add(dogScore);
//		item.add(spreadSign);
		
		String evenOdd = (item.getIndex() % 2 != 0) ? "even " : "odd ";
		rowClass.append(evenOdd);
		item.add(new SimpleAttributeModifier("class", rowClass.toString()));
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
