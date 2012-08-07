package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.service.PlayerManager;
import info.makeyourpicks.web.authentication.LoginPage;
import info.makeyourpicks.web.layout.pages.LeagueNonMemberWebPage;
import info.makeyourpicks.web.league.panels.RegisterPanel;

import org.apache.wicket.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author PRC9041
 */

public class RegisterPage extends LeagueNonMemberWebPage {

	public static final String WICKET_ID = "registerPage";
	@SpringBean(name="playerManager")
	protected PlayerManager playerManager;
	
	@SpringBean(name="leagueManager")
	protected LeagueManager leagueManager;
	
	
	
	public RegisterPage()
	{
		super();

		Request request = getRequest();
		String passedLeagueName = request.getParameter("leagueName");
        

        RegisterPanel registerPanel = new RegisterPanel("registerPanel", passedLeagueName)
        {

			@Override
			public void onRegisterSuccess()
			{
				setResponsePage(LoginPage.class);
			}
        	
        };
        add(registerPanel);
	}
	

	
}

