package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.PlayerLeague;
import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.web.layout.pages.LeagueMemberWebPage;
import info.makeyourpicks.web.league.panels.PlayersLeaguesPanel;
import info.makeyourpicks.web.league.panels.ProfilePanel;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author PRC9041
 */
public class ProfilePage extends LeagueMemberWebPage {
	
	public static final String WICKET_ID = "profile";
	
	@SpringBean(name="leagueManager")
	protected LeagueManager leagueManager;
	
	private FeedbackPanel feedbackPanel;
	public ProfilePage()
	{
		super();
		IModel playerModel = new CompoundPropertyModel(new LoadableDetachableModel()
		{

			@Override
			protected Object load()
			{
				return getPlayer();
			}
			
		});
		init(playerModel);
	}
	
	public ProfilePage(IModel model)
	{
		super();
		init(model);
		
	}

	private void init(final IModel<Player> model)
	{
		feedbackPanel = new FeedbackPanel("feedbackPanel");
        add(feedbackPanel);
        
//        Player player = (Player)model.getObject();
//        PlayerLeaguesDataProvider dataProvider = new PlayerLeaguesDataProvider()
//        {
//        	@Override
//			public LeagueManager getLeagueManager()
//			{
//				return leagueManager;
//			}
//
//			@Override
//			public Player getPlayer()
//			{
//				return model.getObject();
//			}
//        	
//        	
//        };
//        add(new LeaguesDataView("repeating", dataProvider)
//        {
//
//			@Override
//			public Link getSelectLink(final IModel<PlayerLeague> model) {
//				return new Link("select")
//				{	
//					
//					@Override
//					public void onClick() {
//						League league = model.getObject().getLeague();
//						setResponsePage(LeagueConstants.homePageMap.get(league.getSeason().getLeagueType().getTypeOfLeague()));
//					}
//					
//				};
//			}
//
//			@Override
//			public LeagueManager getLeagueManager()
//			{
//				return leagueManager;
//			}
//			
//			
//        	
//        });
        
        add(new PlayersLeaguesPanel("leaguePlayersPanel", model)
        {

			@Override
			public Class<? extends Page> getOnClickClass(
					IModel<PlayerLeague> model)
			{
//				return LeagueWebConstants.homePageMap.get(model.getObject().getLeague().getSeason().getLeagueType().getTypeOfLeague());
//				return ((LeagueWebApplication)getApplication()).getLeagueHomePage(model.getObject().getLeague().getSeason().getLeagueType().getTypeOfLeague());
				return getHomePageForLeague(model.getObject().getLeague().getSeason().getLeagueType().getTypeOfLeague());
			}
        	 
        });
//        add(new DetailedProfileForm("profileForm", model));
        add(new ProfilePanel("profilePanel", model, false));
	}


	
	
}

