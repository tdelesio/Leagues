package info.makeyourpicks.web.layout;

import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.web.authentication.LoginPage;
import info.makeyourpicks.web.league.pages.ProfilePage;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.IItemReuseStrategy;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.markup.repeater.util.ModelIteratorAdapter;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.ui.rememberme.RememberMeServices;


/**
 * @author PRC9041
 */
public class HeaderSecure extends AbstractHeader {

	@SpringBean(name="rememberMeServices")
	protected RememberMeServices rememberMeServices;
	
	public HeaderSecure(String id)
	{
		this (id, null, null);
	}
	
	public HeaderSecure(String id, IModel<Week> weekModel, Class weekSelectionRedirectPage)
	{
		this(id, weekModel, weekSelectionRedirectPage, null);
	}
	
	public HeaderSecure(String id, IModel<Week> weekModel, Class weekSelectionRedirectPage, Component headerWidget)
	{
		super(id);
		
		if (headerWidget != null)
			add(headerWidget);
		else
			add(new WebMarkupContainer(IHeaderWidget.WICKET_ID));
		
		add(new Label("leagueName", getActiveLeague().getLeagueName()));
		add(new Label("welcome", "Welcome "+getPlayer().getFullName()));
		
		 //profile link
        Link profilePageLink = new Link(ProfilePage.WICKET_ID)
        {
        	@Override
			public void onClick()
			{
				setResponsePage(ProfilePage.class);
			}
        };
        add(profilePageLink);
        
      //logout link
		Link logout = new Link("logout") {
            public void onClick() {
            	ServletWebRequest servletWebRequest = (ServletWebRequest) getRequest();
        	    HttpServletRequest request = servletWebRequest.getHttpServletRequest();
        	    WebResponse webResponse = (WebResponse) getResponse();
        	    HttpServletResponse response = webResponse.getHttpServletResponse();
            		rememberMeServices.loginFail(request, response);
                    getWebSession().signout();
                    setResponsePage(LoginPage.class);
            }};
		add(logout);
		
		WeekNavPanel weekNavPanel = new WeekNavPanel("weekNavPanel", weekModel, weekSelectionRedirectPage);
		if (weekSelectionRedirectPage == null)
			weekNavPanel.setVisible(false);
		add(weekNavPanel);
		
//		Label money = new Label("money", "You have 340 points");
//		money.setVisible(false);
//		add(money);
//		
//		Label place = new Label("place", "You are current placed 3/34");
//		place.setVisible(false);
//		add(place);
	}
	
//	private class ScoresDataView extends RefreshingView
//	{
//
//		private Week week;
//		private List<Game> g;
//		public ScoresDataView(String id, Week week)
//		{
//			super(id);
//			this.week=week;
//		}
//		
//		@Override
//		protected Iterator getItemModels() 
//		{
//			if (week == null)
//			{
//				g = Collections.EMPTY_LIST;
//			}
//			else
//			{
//				g = gameManager.getGamesByWeek(week);
//			}
//			
//			return new ModelIteratorAdapter(g.iterator())
//            {
//
//                protected IModel model(final Object object)
//                {
//                	return new LoadableDetachableModel()
//                	{
//
//						@Override
//						protected Object load()
//						{
//							return gameManager.loadGame(((Game)object).getId());
//						}
//                		
//                	};
//                }
//
//            };
//		}
//		
//		@Override
//		protected Item newItem(String id, int index, IModel model) {
//			 return new OddEvenItem(id, index, model);
//		}


//		@Override
//		protected void populateItem(Item item) {
//			
//			ScorePanel scorePanel = new ScorePanel("scorePanel", item.getModel());
////			scorePanel.add(new SimpleAttributeModifier("class", "scorePanel"));
//			item.add(scorePanel);
//		}
//			
//		@Override
//		public IItemReuseStrategy getItemReuseStrategy() {
//			return ReuseIfModelsEqualStrategy.getInstance();
//		}
		
		
	
//	}
}

