package info.makeyourpicks.web.league.panels;

import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.PlayerLeague;
import info.makeyourpicks.web.layout.AbstractBasePanel;

import java.util.Iterator;

import org.apache.wicket.Page;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

public abstract class PlayersLeaguesPanel extends AbstractBasePanel
{

	public PlayersLeaguesPanel(String id, final IModel<Player> model)
	{
		super(id);
	
		 PlayerLeaguesDataProvider dataProvider = new PlayerLeaguesDataProvider()
		 {

			@Override
			public IModel<Player> getPlayerModel()
			{
				return model;
			}
			 
		 };
		 add(new LeaguesDataView("leaguesDataView", dataProvider));
	}
	
	private abstract class PlayerLeaguesDataProvider implements IDataProvider
	{
		
		public Iterator iterator(int start, int count)
		{
			return leagueManager.getPlayerLeaguesByPlayer(getPlayerModel().getObject()).subList(start, start+count).iterator();
		}

		public IModel model(final Object object)
		{
			return new CompoundPropertyModel(new LoadableDetachableModel()
			{
				@Override
				protected Object load()
				{
					return leagueManager.loadPlayerLeague(((PlayerLeague)object).getId());
				}
				
			});
		}

		public int size()
		{
			return leagueManager.getPlayerLeaguesByPlayer(getPlayerModel().getObject()).size();
		}

		public void detach()
		{
		// TODO Auto-generated method stub

		}
		
		public abstract IModel<Player> getPlayerModel();
	}
	
	private class LeaguesDataView extends DataView<PlayerLeague>
	{
		public LeaguesDataView(String id, IDataProvider<PlayerLeague> dataprovider)
		{
			super(id, dataprovider);
			
			
		}


			@Override
		protected void populateItem(final Item<PlayerLeague> item)
		{
			item.add(getSelectLink(item.getModel()));
	        item.add(new Label("league.leagueName"));
	        item.add(new Label("league.admin.username"));
	        item.add(new Label("league.season.leagueType.leagueTypeDisplay"));
	        item.add(new Label("league.active"));
	        item.add(new Label("numberPeople", String.valueOf(leagueManager.getNumberOfPlayersInLeague(item.getModelObject().getLeague()))));
	        item.add(new Label("league.spreads"));
	        item.add(new Label("sortOrder"));
//	            item.add(new Label("displayInNav", String.valueOf(playerLeague.isDisplayInNav())));
	        Link displayLink = new Link<PlayerLeague>("displayInNavLink")
	        {

				@Override
				public void onClick()
				{
					PlayerLeague playerLeague = item.getModelObject();
					playerLeague.changeDisplayValue();
					leagueManager.updatePlayerLeague(playerLeague);
				}
	        	
	        };
	        displayLink.add(new Label("displayInNavText"));
	        item.add(displayLink);

	        if (item.getIndex()%2 !=0)
	        	item.add(new SimpleAttributeModifier("class", "even"));
	       	else
	       		item.add(new SimpleAttributeModifier("class", "odd"));
	   	}

		public Link getSelectLink(final IModel<PlayerLeague> model) {
			return new Link("select")
			{	
				
				@Override
				public void onClick() {
//					League league = model.getObject().getLeague();
//					setResponsePage(LeagueConstants.homePageMap.get(league.getSeason().getLeagueType().getTypeOfLeague()));
					setResponsePage(getOnClickClass(model));
				}
				
			};
		}
	}
	
	public abstract Class<? extends Page> getOnClickClass(IModel<PlayerLeague> model);
}
