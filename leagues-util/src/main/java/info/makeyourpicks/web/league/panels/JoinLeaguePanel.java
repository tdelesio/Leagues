package info.makeyourpicks.web.league.panels;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.web.layout.AbstractBasePanel;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.wicket.Page;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;

public abstract class JoinLeaguePanel extends AbstractBasePanel
{

	public JoinLeaguePanel(String id)
	{
		super(id);
		

		HttpSession session = ((WebRequestCycle)RequestCycle.get()).getWebRequest().getHttpServletRequest().getSession(false);
		final String leagueName = (String)session.getAttribute("leagueName");
		final String leaguePassword = (String)session.getAttribute("leaguePassword");

		WebMarkupContainer container = new WebMarkupContainer("container");
		boolean showFreeLeagues=true;
		if (leagueName!=null && leaguePassword!=null)
		{
			showFreeLeagues=false;
		}
		
		FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");
        add(feedbackPanel);
        
        FreeLeaguesDataView freeLeaguesDataView = new FreeLeaguesDataView("freeLeaguesDataView", new FreeLeaguesDataProvider());
        container.add(freeLeaguesDataView);
        container.setVisible(showFreeLeagues);
        add(container);
        
        IModel leagueModel = new CompoundPropertyModel(new LoadableDetachableModel()
        {

			@Override
			protected Object load()
			{
				League league = new League();
		        league.setLeagueName(leagueName);
		        league.setPassword(leaguePassword);
		        return league;
			}
        	
        });
        
        
        add(new LeagueForm("joinLeagueForm", leagueModel));
	
	}
	
	private class FreeLeaguesDataView extends DataView<League>
	{
		public FreeLeaguesDataView(String id, FreeLeaguesDataProvider dataProvider)
		{
			super(id, dataProvider);
		}

		@Override
		protected void populateItem(final Item<League> item)
		{
			int numPlayersInLeague = leagueManager.getNumberOfPlayersInLeague(item.getModelObject());
			
			Link joinLink = new Link("joinLink")
			{
				@Override
				public void onClick()
				{
//					League leagueInfo = (League)getParent().getDefaultModelObject();
                	int leagueSize = leagueManager.getNumberOfPlayersInLeague(item.getModelObject());
                	if (item.getModelObject().isLeagueFull(leagueSize))
                	{
                		error("Sorry league is full.  Please select another one.");
                	}
                	else
                	{
	                	joinLeague(item.getModelObject());
	                	
	                	if (item.getModelObject().createNewFreeLeague(leagueSize))
	                	{
	                		League newLeague = new League(item.getModelObject());
	                		newLeague.generateFreeLeagueName();
	                		leagueManager.createOrUpdateLeague(newLeague);
	                		//newLeague.getPlayers().clear();
	                	}
                	}
				}
				
			};
			item.add(joinLink);
			item.add(new Label("leagueName"));
            item.add(new Label("admin.username"));         
            item.add(new Label("season.leagueType.leagueTypeDisplay"));
            item.add(new Label("numberPeople", String.valueOf(numPlayersInLeague)));
            item.add(new SimpleAttributeModifier("class", (item.getIndex()% 2 != 0) ? "even" : "odd"));
			
            
            if (((FreeLeaguesDataProvider)getDataProvider()).getPlayerLeagues().contains(item.getModelObject()) || item.getModelObject().isLeagueFull(numPlayersInLeague))
            {
            	joinLink.setVisible(false);
            }
            else
            {
            	joinLink.setVisible(true);
            }
            
		}
		
		
	}
	
	private class FreeLeaguesDataProvider implements IDataProvider<League>
	{
		private List<League> playerLeagues;
		public FreeLeaguesDataProvider()
		{
			playerLeagues = leagueManager.getLeaguesForPlayer(getPlayer());
		}

		public List<League> getPlayerLeagues()
		{
			return playerLeagues;
		}
		
		public Iterator<? extends League> iterator(int start, int count)
		{
			return leagueManager.getFreeLeagues().subList(start, start+count).iterator();
		}

		public IModel<League> model(final League league)
		{
			return new CompoundPropertyModel<League>(new LoadableDetachableModel<League>()
			{

				@Override
				protected League load()
				{
					return leagueManager.getLeague(league.getId());
				}
				
			});
		}

		public int size()
		{
			return leagueManager.getFreeLeagues().size();
		}

		public void detach()
		{
			
		}
		
		
	}
	
	private class LeagueForm extends Form<League> {

		@SpringBean(name="leagueManager")
		private LeagueManager leagueManager;
		
		public LeagueForm(String id, IModel model)
		{
			super(id, model);
			
			RequiredTextField leagueName = new RequiredTextField("leagueName");
			leagueName.setLabel(new Model("League Name"));
			add(leagueName);
			
			RequiredTextField password = new RequiredTextField("password");
//			password.setRequired(true);
			add(password);
			
			add(new Button("submit"));
		}
		
		@Override
    	public void onSubmit() 
    	{
//			League leagueInfo = (League)getModelObject();
			League leagueFromDB = leagueManager.getLeague(getModelObject().getLeagueName());

			if (leagueFromDB==null)
			{
				error("Invalid league name.  Please try again.");
			}
			else if (leagueFromDB.getPassword().equals(getModelObject().getPassword()))
			{
				if (leagueFromDB.isLeagueFull(leagueManager.getNumberOfPlayersInLeague(getModelObject())))
				{
					error("Sorry league is full.  Please contact "+leagueFromDB.getAdmin().getEmail()+" and have them increase the number of players.");
				}
				else
				{
					joinLeague(leagueFromDB);
				}
				
			}
			else
			{
				error("Invalid password entered.  Please contact "+getModelObject().getAdmin().getEmail()+" for a valid password");
			}
			
    		
    	}
		
		 	
	}
	
	private void joinLeague(final League league)
	{
		Iterator<League> leagues = leagueManager.getLeaguesForPlayer(getPlayer()).iterator();
		boolean hasJoined=false;
		while (leagues.hasNext())
		{
			if (leagues.next().getLeagueName().equalsIgnoreCase(league.getLeagueName()))
			{
				hasJoined=true;
				break;
			}
		}
		if (hasJoined)
		{
			error("You have already joined this league.  Please enter another league name/password.");
		}
//		else if (leagueModel.getObject().isMoney())
//		{
////			setResponsePage(new RulesPage(new CompoundPropertyModel(league)));
//			setResponsePage(getRulesPage(leagueModel));
//		}
		else
		{
			Player player = getPlayer();
			leagueManager.addPlayerToLeague(league, player);
			setActiveLeague(league);
	
			info("You have successfully join the league "+league.getLeagueName()+".");
			
//			setResponsePage(LeagueWebConstants.homePageMap.get(league.getSeason().getLeagueType().getTypeOfLeague()));
			LoadableDetachableModel<League> leagueModel = new LoadableDetachableModel<League>() {

				@Override
				protected League load() {
					return league;
				}
				
			};
			setResponsePage(getOnSuccessJoinPage(leagueModel));
		}
	}
	
	public abstract Page getRulesPage(IModel<League> leagueModel);
	public abstract Class<? extends Page> getOnSuccessJoinPage(IModel<League> leagueModel);
}
