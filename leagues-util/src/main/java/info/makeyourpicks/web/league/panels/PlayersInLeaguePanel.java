package info.makeyourpicks.web.league.panels;

import java.util.Iterator;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.web.layout.AbstractBasePanel;

public class PlayersInLeaguePanel extends AbstractBasePanel
{

	public PlayersInLeaguePanel(String id, final IModel<League> model)
	{
		super(id);
		
		init(model);
	}
	
	private void init(final IModel<League> model)
	{
		
		Label removePlayerHeader = new Label("removePlayerHeader", "Remove Player");
        add(removePlayerHeader);
        
        Label lockedHeader = new Label("lockedHeader", "Locked?");
        add(lockedHeader);
        
		 if (getPlayer().getMemberLevel()<=Player.LEAGUE_ADMIN && getPlayer().equals(model.getObject().getAdmin()))	
         {
         	removePlayerHeader.setVisible(true);
         	lockedHeader.setVisible(true);
         }
         else
         {
         	removePlayerHeader.setVisible(false);
         	lockedHeader.setVisible(false);
         }
		 
		add(new LeaguePlayersDataView("leaguePlayersDataView", new LeaguePlayersDataProvider()
		{

			@Override
			public League getLeague()
			{
				return model.getObject();
			}
			
		}));
		
	}
	
	private class LeaguePlayersDataView extends DataView<Player>
	{

		public LeaguePlayersDataView(String id, LeaguePlayersDataProvider dp)
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
            Link removePlayer = new Link("removePlayer")
            {

				@Override
				public void onClick() {
					leagueManager.removePlayerFromLeague(((LeaguePlayersDataProvider)getDataProvider()).getLeague(), playerInfo);
//					setResponsePage(LeagueDetailsPage.class);
				}
            	
            };
//            getPlayer().getMemberLevel()<=Player.LEAGUE_ADMIN && getPlayer().equals(((LeaguePlayersDataProvider)getDataProvider()).getLeague().getAdmin())
            if (getPlayer().isAdmin() || getPlayer().isLeagueAdmin(((LeaguePlayersDataProvider)getDataProvider()).getLeague()))	
            {
            	removePlayer.setVisible(true);
            	locked.setVisible(true);
            }
            else
            {
            	removePlayer.setVisible(false);
            	locked.setVisible(false);
            }
            item.add(removePlayer);
            
            item.add(new SimpleAttributeModifier("class", (item.getIndex() % 2 != 0) ? "even" : "odd"));
			
		}
		
	}
	
	private abstract class LeaguePlayersDataProvider implements IDataProvider<Player>
	{

		public Iterator<? extends Player> iterator(int start, int count)
		{
			return playerManager.getPlayersInLeague(getLeague()).subList(start, start+count).iterator();
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
			return playerManager.getPlayersInLeague(getLeague()).size();
		}

		public void detach()
		{
		
		}
		
		public abstract League getLeague();
	}
}
