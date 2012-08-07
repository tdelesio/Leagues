package info.makeyourpicks.web.authentication;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.web.AbstractLeagueApplication;
import info.makeyourpicks.web.LeagueWebApplication;
import info.makeyourpicks.web.PageEnum;
import info.makeyourpicks.web.layout.AbstractBasePanel;
import info.makeyourpicks.web.league.pages.MessageBoardPage;
import info.makeyourpicks.web.league.pages.MyPoolWebPage;
import info.makeyourpicks.web.league.pages.RegisterPage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.LockedException;
import org.springframework.security.ui.rememberme.RememberMeServices;

import com.delesio.protocol.http.AbstractAuthenticatedWebSession;

/**
 * Reusable user sign in panel with username and password as well as support for
 * cookie persistence of the both. When the SignInPanel's form is submitted, the
 * method signIn(String, String) is called, passing the username and password
 * submitted. The signIn() method should authenticate the user's session. The
 * default implementation calls AuthenticatedWebSession.get().signIn().
 * 
 * @author Anthony DePalma
 * @version Mar 7, 2008
 */


public class LoginPanel extends AbstractBasePanel {

	@SpringBean(name="rememberMeServices")
	protected RememberMeServices rememberMeServices;

	
	public LoginPanel(final String id, boolean showLinks)
	{
		super(id);
		
		FeedbackPanel feedback = new FeedbackPanel("feedbackPanel");
		feedback.setOutputMarkupId(true);
		
		// Create feedback panel and add to page
		add(feedback);
		
		add(new LoginForm("loginForm", new CompoundPropertyModel<Player>(new Player()), feedback, showLinks));
	}
	
	// constructor
	public LoginPanel(final String id) {
		this(id, true);
	}


	
	
	
	
	private class LoginForm<T> extends Form<T> {
		private static final long serialVersionUID = 1L;

		private static final int cookieExpire = 60*60*24*30;
//		public LoginForm(final String id, IModel<T> model, FeedbackPanel feedbackPanel)
//		{
//			this(id, model, feedbackPanel, true);
//		}
		
		// constructor
		public LoginForm(final String id, IModel<T> model, FeedbackPanel feedbackPanel, boolean showExtraFields) {
			super(id, model);
			
			// add form components
			RequiredTextField<String> username = new RequiredTextField<String>("username");
			add(username);
			
			PasswordTextField password = new PasswordTextField("password");
			password.setRequired(true);
			add(password);

			WebMarkupContainer extraContainer = new WebMarkupContainer("container");
			extraContainer.setVisible(showExtraFields);
			add(extraContainer);
			
			CheckBox rememberMe = new CheckBox("rememberMe");
			add(rememberMe);
			
//			extraContainer.add(new PageLink(LostPasswordPage.WICKET_ID, LostPasswordPage.class));
//			extraContainer.add(new PageLink("register", RegisterPage.class));
			extraContainer.add(new Link("register")
			{

				@Override
				public void onClick()
				{
					setResponsePage(RegisterPage.class);
				}				
				
			});
			
			extraContainer.add(new Link("lostPasswordPage")
			{

				@Override
				public void onClick()
				{
					setResponsePage(LostPasswordPage.class);
				}				
				
			});
			
			add(new Button("ajaxButton"));
		}

		@Override
		public final void onSubmit() {
			Player player = (Player)getModelObject();
			if (signIn(player.getUsername(), player.getPassword())) {
				if (player.isRememberMe())
				{
//					Cookie cookie = new Cookie("mypid", player.getUsername());
//					cookie.setMaxAge(cookieExpire);
//					cookie.setPath("/");
//					cookie.setSecure(true);
//					((HttpServletResponse)getResponse()).addCookie(cookie);
					
				}
				onSignInSucceeded();
			} else {
				signInFail();
			}
		}
		
		public boolean signIn(String usernameValue, String passwordValue) {
			boolean result = false;
			
			try
			{
				result = AbstractAuthenticatedWebSession.get().authenticate(usernameValue, passwordValue);
				if (!result)
				{
					error("Your login information is not correct.  Please check the username and/or password.");
				}
				return result;
			}
			catch (LockedException lockedException)
			{
				error("Your account has been locked for non-payment.  Please email your league admin.");
				return false;
			}
		}
		
		public void onSignInSucceeded()
		{

			ServletWebRequest servletWebRequest = (ServletWebRequest) getRequest();
		    HttpServletRequest request = servletWebRequest.getHttpServletRequest();

		    WebResponse webResponse = (WebResponse) getResponse();
		    HttpServletResponse response = webResponse.getHttpServletResponse();


			rememberMeServices.loginSuccess(request, response, getPlayer());
			
			boolean hasUnReadmail = messageBoardManager.hasNewMessages(getPlayer());
//			if (hasUnReadmail)
//			{
////				getPlayer().setUnreadMail(true);
//				
//			}
			List<League> leagues = leagueManager.getLeaguesForNavigation(getPlayer());
//			boolean continueToOrginalDestination = continueToOriginalDestination();
//			if (continueToOrginalDestination)
//			{
//				setRedirect(true);
//			}
//			else
//			{
			if (leagues.size() > 0)
			{
				League activeLeague = leagues.iterator().next();
				setActiveLeague(activeLeague);
				
				if (hasUnReadmail)
					setResponsePage(LeagueWebApplication.get().getPageForLeague(activeLeague, PageEnum.MESSAGE_BOARD.getValue()));
				else
					setResponsePage(LeagueWebApplication.get().getPageForLeague(activeLeague, PageEnum.HOME.getValue()));
			}
			else
			{
				setResponsePage(MyPoolWebPage.class);
			}
				
//				
//			
//			
//					
//					if (hasUnReadmail)
//					{
//						setResponsePage(MessageBoardPage.class);
//					}
//					else
//					{
//						setResponsePage( ((AbstractLeagueApplication)getApplication()).getLoggedInHomePage());
//					}
//		
//				} else {
//					setResponsePage(((AbstractLeagueApplication)getApplication()).getLoggedInHomePage());
//				}
//			}
		
		}
		
		public void signInFail()
		{
//			error("Login Failed.");
		}
		
	}

//	public abstract Class<? extends AbstractLeagueSecurePage> getMessageBoardPage();
//	public abstract Class<? extends AbstractLeaguePage> getLostPasswordPage();
//	public abstract Class<? extends AbstractLeaguePage> getRegisterPage();
}
