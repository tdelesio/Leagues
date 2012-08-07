package info.makeyourpicks.web.authentication;


import info.makeyourpicks.web.LeagueSession;
import info.makeyourpicks.web.LeagueWebApplication;
import info.makeyourpicks.web.layout.pages.LeagueNonMemberWebPage;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.behavior.HeaderContributor;

public class LoginPage extends LeagueNonMemberWebPage
{
	public static final String WICKET_ID = "loginPage";
	// constructor
	public LoginPage() {  
//		add(HeaderContributor.forCss(
//			       "styles/login.css"
//			   )); 
		
		LeagueSession leagueSession = LeagueSession.getWebSession();   
		
		//add(new HeaderBasePanel("header"));
		
		if (leagueSession.isSignedIn())
		{
			// if they are already logged in return to the home page
//			setResponsePage(((LeagueApplication)getApplication()).getLoggedInHomePage());
//			setRedirect(true);
			throw new RestartResponseException(((LeagueWebApplication)getApplication()).getLoggedInHomePage());
		}
		else
		{
			// add the login panel
			add(new LoginPanel("loginPanel"));
			
			// add a link to create a new account
//			add(new Link("linkToCreateMyAccount") {
//				public void onClick() {
//					setResponsePage(RegisterPage.class);
//				}
//			}); 
		}
	}

	
	
}

