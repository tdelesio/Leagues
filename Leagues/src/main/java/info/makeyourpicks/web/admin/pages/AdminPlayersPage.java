package info.makeyourpicks.web.admin.pages;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.service.PlayerManager;
import info.makeyourpicks.web.league.pages.ProfilePage;

import java.util.Iterator;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;


/**
 * @author PRC9041
 */


public class AdminPlayersPage extends AbstractAdminBasePage {

	@SpringBean(name="playerManager")
	protected PlayerManager playerManager;
	
	public AdminPlayersPage()
	{
		super();
		 
//		add(new PlayersLeaguesPanel("leaguePlayersPanel"));
		add(new PlayersDataView("playersDataView", new PlayersDataProvider()));
	}
	
	private class PlayersDataView extends DataView<Player>
	{

		public PlayersDataView(String id, PlayersDataProvider dp)
		{
			super(id, dp);
		}
		@Override
		protected void populateItem(final Item<Player> item)
		{
			final Player playerInfo = item.getModelObject();
			
			item.add(new Label("username"));
            item.add(new Label("email"));
            item.add(new Label("firstName"));
            item.add(new Label("lastName"));
            
            CheckBox locked = new CheckBox("locked", new PropertyModel(playerInfo, "locked"));
            locked.setOutputMarkupId(true);
            locked.add(new AjaxFormComponentUpdatingBehavior("onChange")
			{

				@Override
				protected void onUpdate(AjaxRequestTarget target) {
//					Player player = (Player)getModelObject();
					playerManager.createUpdatePlayer(playerInfo);
					
				}
				
			});
            
            
            item.add(locked);
            Link displayProfileLink = new Link("displayProfileLink")
            {

				@Override
				public void onClick() {
					setResponsePage(new ProfilePage(item.getModel()));
				}
            };
            
            item.add(displayProfileLink);
            
            item.add(new SimpleAttributeModifier("class", (item.getIndex() % 2 != 0) ? "even" : "odd"));
			
		}
		
	}
	
	private class PlayersDataProvider implements IDataProvider<Player>
	{

		public Iterator<? extends Player> iterator(int start, int count)
		{
			return playerManager.getAllPlayers().subList(start, start+count).iterator();
		}

		public IModel<Player> model(final Player player)
		{
			return new CompoundPropertyModel<Player>(new LoadableDetachableModel<Player>()
			{

				@Override
				protected Player load()
				{
					return playerManager.loadPlayer(player.getId());
				}
				
			});
		}

		public int size()
		{
			return playerManager.getAllPlayers().size();
		}

		public void detach()
		{
		
		}
		
	}
	
	
}

