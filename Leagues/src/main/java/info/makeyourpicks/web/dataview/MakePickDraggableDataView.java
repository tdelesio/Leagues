package info.makeyourpicks.web.dataview;

import javax.swing.text.StyledEditorKit.UnderlineAction;

import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.Picks;
import info.makeyourpicks.model.Team;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.IItemReuseStrategy;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.odlabs.wiquery.ui.draggable.DraggableBehavior;
import org.odlabs.wiquery.ui.draggable.DraggableContainment;
import org.odlabs.wiquery.ui.draggable.DraggableContainment.ContainmentEnum;
import org.odlabs.wiquery.ui.droppable.DroppableAjaxBehavior;

public abstract class MakePickDraggableDataView extends DataView
{
	public MakePickDraggableDataView(String id, IDataProvider dataProvider)
	{
		super(id, dataProvider);
	}
	
	@Override
	protected void populateItem(final Item item) {
		final IModel model = item.getModel();
		final Picks pick = (Picks)model.getObject();
		Game game = pick.getGame();
		Team winningTeam = game.getWinner(useSpreads());
		
		boolean isPickFav = pick.getTeam()!=null&&pick.getTeam().equals(game.getFav());
		boolean isPickDog = pick.getTeam()!=null&&pick.getTeam().equals(game.getDog());
		
		//the container for the favorite
//		final WebMarkupContainer favContainer = new WebMarkupContainer("favContainer");
//		favContainer.setOutputMarkupId(true);

		//the container for the dog
//		final WebMarkupContainer dogContainer = new WebMarkupContainer("dogContainer");
//		dogContainer.setOutputMarkupId(true);
		
		//default the css for the container and set the image visisble
		StringBuffer favContainerCssClass=new StringBuffer("pickLink "), dogContainerCssClass=new StringBuffer("pickLink ");
		
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
		if (isPickFav&&winningTeam!=null&&winningTeam.equals(game.getFav()))
		{
			favLinkCssClass.append("winner ");
			favContainerCssClass.append("winner ");
		}
		else if (isPickFav&&winningTeam!=null&&!winningTeam.equals(game.getFav()))
		{
			favLinkCssClass.append("loser ");
			favLinkCssClass.append("loser ");
		}
		//add css for winner/loser for dog
		if (isPickDog&&winningTeam!=null&&winningTeam.equals(game.getDog()))
		{
			dogLinkCssClass.append("winner ");
			dogContainerCssClass.append("winner ");
		}
		else if (isPickDog&&winningTeam!=null&&!winningTeam.equals(game.getDog()))
		{
			dogLinkCssClass.append("loser ");
			dogContainerCssClass.append("loser ");
		}
		
		//disable the links if hte game has started
//		if (game.hasGameStarted())
//		{
//			favLink.setEnabled(false);
//			dogLink.setEnabled(false);
//		}
				

//		List<Team> teams = new ArrayList<Team>(2);
//		teams.add(game.getFav());
//		teams.add(game.getDog());
//		ListView<Team> listView = new ListView<Team>("listView", teams) {
//			private static final long serialVersionUID = 1L;
//
//			/* (non-Javadoc)
//			 * @see org.apache.wicket.markup.html.list.ListView#populateItem(org.apache.wicket.markup.html.list.ListItem)
//			 */
//			@Override
//			protected void populateItem(ListItem<Team> item) {
//				item.add(new Label("item", item.getModelObject().getFullTeamName()));
//				item.setOutputMarkupId(true);
//			}
//	    };

		
		//add favorite link
		Label favorite = new Label("favLabel", game.getFav().getFullTeamName());
		
		WebMarkupContainer dragPanel = new WebMarkupContainer("dragPanel");
		
		Label underdog = new Label("dogLabel", game.getDog().getFullTeamName());
		dragPanel.add(underdog);
		
		item.add(favorite);
		item.add(dragPanel);
		
		DraggableBehavior draggableBehavior = new DraggableBehavior();
		DraggableContainment containment = new DraggableContainment(ContainmentEnum.DOCUMENT);
		draggableBehavior.setContainment(containment);
//		favorite.add(draggableBehavior);
		underdog.add(draggableBehavior);
		
//		favContainer.add(new SimpleAttributeModifier("class", favContainerCssClass.toString()));
//		item.add(favContainer);
		//add spread
//		item.add(new Label("spread", pick.getSpreadsLabel()));
		//add dog link
		
//		dogContainer.add(new SimpleAttributeModifier("class", dogContainerCssClass.toString()));
//		item.add(dogContainer);
		//add game start
//		item.add(new Label("gameStart", game.getGameStartDisplay()));
		
//	    DraggableBehavior favDraggableBehavior = new DraggableBehavior();
//	    favorite.add(favDraggableBehavior);
//	    
//	    DraggableBehavior dogDraggableBehavior = new DraggableBehavior();
//	    underdog.add(dogDraggableBehavior);
		
		DroppableAjaxBehavior droppableBehavior = new DroppableAjaxBehavior()
		{
			@Override
			public void onDrop(Component droppedComponent,
					AjaxRequestTarget ajaxRequestTarget) {

			}
		};
		

		WebMarkupContainer dropZone = new WebMarkupContainer("dropZone");
		dropZone.add(droppableBehavior);
		dropZone.add(new Label("dropZoneText", "Drop Here"));
		item.add(dropZone);
		
		
//		favDraggableBehavior.setConnectToSortable("sortableWicket");
//		favDraggableBehavior.
//		dogDraggableBehavior.setConnectToSortable("sortableWicket");
		
//		WebMarkupContainer dropZone = new WebMarkupContainer("dropZone");
//	    dropZone.add(new DroppableAjaxBehavior() {
//	    	private static final long serialVersionUID = 1L;
//	    	
//			/* (non-Javadoc)
//			 * @see org.odlabs.wiquery.ui.droppable.DroppableAjaxBehavior#onDrop(org.apache.wicket.Component, org.apache.wicket.ajax.AjaxRequestTarget)
//			 */
//			@SuppressWarnings("unchecked")
//			@Override
//			public void onDrop(Component droppedComponent,
//					AjaxRequestTarget ajaxRequestTarget) {
//				
//				if(droppedComponent != null){
////					ListItem<Fruit> item = (ListItem<Fruit>)droppedComponent;
////					Fruit fruit = item.getModelObject();
////					Integer nb = 1;
////					if(selection.containsKey(fruit.getName())){
////						nb = selection.get(fruit.getName()) + 1;
////					}
////					selection.put(fruit.getName(), nb);
////					
////					StringBuffer buffer = new StringBuffer();
////					buffer.append("<ul>");
////					for(String key : selection.keySet()){
////						buffer.append("<li>");
////						buffer.append(selection.get(key));
////						buffer.append(" " + key + "(s)");
////						buffer.append("</li>");
////					}
////					buffer.append("</ul>");
////					selectionLabel.setDefaultModelObject(buffer.toString());
////					ajaxRequestTarget.addComponent(selectionLabel);
//				}				
//			}
//		});
////	    dropZone.add(new Image("cartImage", new ResourceReference(DroppablePage.class, "cart.jpg")));
//	    item.add(dropZone);

		
		
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
