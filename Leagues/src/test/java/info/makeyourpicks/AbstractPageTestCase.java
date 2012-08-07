package info.makeyourpicks;

import info.makeyourpicks.model.Player;
import info.makeyourpicks.test.AbstractTestCase;
import info.makeyourpicks.web.LeagueWebApplication;
import info.makeyourpicks.web.authentication.LoginPage;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.injection.annot.test.AnnotApplicationContextMock;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.springframework.security.providers.ProviderManager;
import org.springframework.security.providers.dao.DaoAuthenticationProvider;

public abstract class AbstractPageTestCase extends AbstractTestCase {
//extends AbstractJpaTests  {

	protected static WicketTester tester;
	protected static Player player;
	protected static boolean isLoggedIn=false;
	
	public AbstractPageTestCase()
	{
		if(isInitial)
		{
			loadContext();
			init();
			isInitial = false;			
		}
	}
	
	@Override
	public void init() {
		super.init();
		
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(playerManager);
		ProviderManager providerManager = new ProviderManager();
		List list = new ArrayList();
		list.add(daoAuthenticationProvider);
		providerManager.setProviders(list);
		
		// 2. setup mock injection environment
		AnnotApplicationContextMock appctx = new AnnotApplicationContextMock();
		appctx.putBean("leagueManager", leagueManager);
		appctx.putBean("playerManager", playerManager);
		//appctx.putBean("messageBoardManager", messageBoardManager);
		appctx.putBean("gameManager", gameManager);
		appctx.putBean("picksManager", picksManager);
		appctx.putBean("teamManager", teamManager);
		appctx.putBean("messageBoardManager", messageBoardManager);
		appctx.putBean("authenticationManager", providerManager);
		
		
		LeagueWebApplication wicketPersistanceApplication = new LeagueWebApplication();
		wicketPersistanceApplication.setAuthenticationManager(providerManager);
		wicketPersistanceApplication.setGameManager(gameManager);
		wicketPersistanceApplication.setLeagueManager(leagueManager);
		wicketPersistanceApplication.setPicksManager(picksManager);
		wicketPersistanceApplication.setPlayerManager(playerManager);
		wicketPersistanceApplication.setTeamManager(teamManager);
		wicketPersistanceApplication.setMessageBoardManager(messageBoardManager);
		
		wicketPersistanceApplication.setSpringComponentInjector(new SpringComponentInjector(wicketPersistanceApplication, appctx, true));
		//IResourceStreamLocator streamLocator = new AbsolutePathResourceStreamLocator("C:/development/eclipse/svn-league/Leagues/src/main/resources/", "/html/");
		//new CustomPathResourceStreamLocator()
		//wicketPersistanceApplication.setStreamLocator(streamLocator);
		tester = new WicketTester(wicketPersistanceApplication);

	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		player = new Player();
		player = (Player)player.createTestObject();
		playerManager.createUpdatePlayer(player);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		playerManager.deleteUser(player.getUsername());
	}
	
	protected void login()
	{
		if (isLoggedIn)
		{
			return;
		}
		//start and render the test page
		tester.startPage(LoginPage.class);

		//assert rendered page class
		tester.assertRenderedPage(LoginPage.class);
		
		FormTester form = tester.newFormTester("loginPanel:loginForm");
		form.setValue("username", player.getUsername());
		form.setValue("password", player.getPassword());
		form.submit();
		
		tester.assertNoErrorMessage();
		isLoggedIn=true;
	}
	
	
	
}
