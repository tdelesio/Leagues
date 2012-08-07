package info.makeyourpicks.web.layout;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.web.LeagueWebApplication;
import info.makeyourpicks.web.admin.pages.SetupWeekPage;
import info.makeyourpicks.web.authentication.LoginPage;
import info.makeyourpicks.web.layout.pages.LeagueMemberWebPage;
import info.makeyourpicks.web.league.pages.ActivateLeaguePage;
import info.makeyourpicks.web.league.pages.ContactUsPage;
import info.makeyourpicks.web.league.pages.CreateLeaguePage;
import info.makeyourpicks.web.league.pages.FAQPage;
import info.makeyourpicks.web.league.pages.HomePage;
import info.makeyourpicks.web.league.pages.JoinLeaguePage;
import info.makeyourpicks.web.league.pages.MessageBoardPage;
import info.makeyourpicks.web.league.pages.MyPoolWebPage;
import info.makeyourpicks.web.league.pages.PaymentHistoryPage;
import info.makeyourpicks.web.league.pages.ProfilePage;
import info.makeyourpicks.web.league.pages.RegisterPage;
import info.makeyourpicks.web.league.pages.RulesPage;
import info.makeyourpicks.web.league.panels.ActivePlayerLeaguesPanel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.ui.rememberme.RememberMeServices;

/**
 * @author PRC9041
 */
public class LeftNavPanel extends AbstractBasePanel {
	
	@SpringBean(name="rememberMeServices")
	protected RememberMeServices rememberMeServices;
	
	public LeftNavPanel(String id)
	{
		super(id);
		
		boolean showMemberNav = true;
		Player player = getPlayer();
		if (player==null)
		{
			showMemberNav=false;
			player = new Player();
		}
		
		League activeLeague = getActiveLeague();
		
		
		
		/**
		 * WELCOME
		 */
		//welcome player messsage
//		add(new Label("welcome", "Welcome "+player.getFullName()).setVisible(showMemberNav));
		
		
        /**
         * MEMBERS
         */
        //create league
        Link createLeaguePage = new BookmarkablePageLink(CreateLeaguePage.WICKET_ID, CreateLeaguePage.class);
        add(createLeaguePage);

      //join league link
		Link joinLink = new Link(JoinLeaguePage.WICKET_ID)
		{
			@Override
			public void onClick()
			{
				setResponsePage(JoinLeaguePage.class);
			}
		};
		add(joinLink);      
		
		//admin link
		Link adminPage = new Link("adminPage")
		{
			@Override
			public void onClick()
			{
				setResponsePage(SetupWeekPage.class);
			}			
		};
		add(adminPage);
		
		Link myPools = new BookmarkablePageLink("myPools", MyPoolWebPage.class);
		myPools.setVisible(showMemberNav);
		add(myPools);
        
        /**
         * NON-MEMBER NAV
         */
//        add(new BookmarkablePageLink(HomePage.WICKET_ID, HomePage.class));
//        add(new BookmarkablePageLink(LoginPage.WICKET_ID, LoginPage.class).setVisible(!showMemberNav));
//        add(new BookmarkablePageLink(RegisterPage.WICKET_ID, RegisterPage.class).setVisible(!showMemberNav));
        
        
		
//        joinLink.setVisible(showMemberNav);
//		profilePageLink.setVisible(showMemberNav);
		
		//visibility based on member level
		if (player.getMemberLevel()==Player.ADMIN)
		{
			adminPage.setVisible(true);
		}
		else if (player.getMemberLevel()==Player.LEAGUE_ADMIN)
		{
		}
		else
		{
			adminPage.setVisible(false);
		}
		

	}
	


	public LeagueManager getLeagueManager() {
		return leagueManager; 
	}

	public void setLeagueManager(LeagueManager leagueManager) {
		this.leagueManager = leagueManager;
	}
	
}

