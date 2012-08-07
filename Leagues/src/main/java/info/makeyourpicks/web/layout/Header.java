package info.makeyourpicks.web.layout;

import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.web.authentication.LoginPage;
import info.makeyourpicks.web.league.pages.ProfilePage;
import info.makeyourpicks.web.league.pages.RegisterPage;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
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
public class Header extends AbstractHeader {

	@SpringBean(name="rememberMeServices")
	protected RememberMeServices rememberMeServices;
	
	public Header(String id)
	{
		super(id);
		
		Link loginLink = new BookmarkablePageLink(LoginPage.WICKET_ID, LoginPage.class);
		add(loginLink);
		
		Link registerLink = new BookmarkablePageLink(RegisterPage.WICKET_ID, RegisterPage.class);
		add(registerLink);
		
		String name;
		if (getPlayer()==null)
		{
			name="";
		}
		else
		{
			name = getPlayer().getFullName();
		}
		Label welcome = new Label("welcome", "Welcome "+name);
		add(welcome);
		
		Label spaceHolder = new Label("spaceHolder", "|");
		add(spaceHolder);
		
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
		
		if (getPlayer() == null)
		{
			//not logged in
			welcome.setVisible(false);
			profilePageLink.setVisible(false);
			logout.setVisible(false);
			spaceHolder.setVisible(false);
			
		}
		else
		{
			loginLink.setVisible(false);
			registerLink.setVisible(false);
		}
		
	}
	
	private class ScoresDataView extends RefreshingView
	{

		private Week week;
		private List<Game> g;
		public ScoresDataView(String id, Week week)
		{
			super(id);
			this.week=week;
		}
		
		@Override
		protected Iterator getItemModels() 
		{
			if (week == null)
			{
				g = Collections.EMPTY_LIST;
			}
			else
			{
				g = gameManager.getGamesByWeek(week);
			}
			
			return new ModelIteratorAdapter(g.iterator())
            {

                protected IModel model(final Object object)
                {
                	return new LoadableDetachableModel()
                	{

						@Override
						protected Object load()
						{
							return gameManager.loadGame(((Game)object).getId());
						}
                		
                	};
                }

            };
		}
//		
//		@Override
//		protected Item newItem(String id, int index, IModel model) {
//			 return new OddEvenItem(id, index, model);
//		}


		@Override
		protected void populateItem(Item item) {
			
			ScorePanel scorePanel = new ScorePanel("scorePanel", item.getModel());
//			scorePanel.add(new SimpleAttributeModifier("class", "scorePanel"));
			item.add(scorePanel);
		}
			
		@Override
		public IItemReuseStrategy getItemReuseStrategy() {
			return ReuseIfModelsEqualStrategy.getInstance();
		}
		
		
	
	}
}

