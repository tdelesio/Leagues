package info.makeyourpicks.web.league.panels;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.web.dataprovider.ActivePlayerLeaguesDataProvider;
import info.makeyourpicks.web.layout.AbstractBasePanel;
import info.makeyourpicks.web.layout.pages.LeagueMemberPage;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;

public abstract class ActivePlayerLeaguesPanel extends AbstractBasePanel
{

	public ActivePlayerLeaguesPanel(String id)
	{
		super(id);
		
		add(new ActivePlayerLeaguesDataView("leagues", new ActivePlayerLeaguesDataProvider()
		{

			@Override
			public LeagueManager getLeagueManager() {
				// TODO Auto-generated method stub
				return leagueManager;
			}

			@Override
			public Player getLoggedInPlayer() {
				return getPlayer();
			}
			
		}));
	}
	
	
	private class ActivePlayerLeaguesDataView extends DataView<League>
	{
		public ActivePlayerLeaguesDataView(String id, ActivePlayerLeaguesDataProvider dataProvider)
		{
			super(id, dataProvider);
		}
		
		@Override
		protected void populateItem(final Item<League> item)
		{
			final Link<League> actionLink = new Link<League>("select")
	        {
	            public void onClick()
	            {
//	               League modelObject = (League)getParent().getModel().getObject();
	               League league = (League)item.getModelObject();
	               getWebSession().setActiveLeague(league);
	            
//	               setResponsePage(LeagueConstants.homePageMap.get(league.getSeason().getLeagueType().getTypeOfLeague()));
	               setResponsePage(getLeagueHomePage(item.getModel()));
	            }
	        };
	        String leagueName = ((League)item.getModelObject()).getLeagueName();
	        actionLink.add(new Label("leagueTypeDisplay", leagueName));
	        if (getActiveLeague()!=null&&leagueName.equals(getActiveLeague().getLeagueName()))
	        {
	        	actionLink.add(cssOn);
	        }
			item.add(actionLink);
		}
	}
	
	
	public abstract Class<? extends Page> getLeagueHomePage(IModel<League> model);
}
