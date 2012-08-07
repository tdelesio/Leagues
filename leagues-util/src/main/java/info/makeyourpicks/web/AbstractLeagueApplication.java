package info.makeyourpicks.web;

import info.makeyourpicks.service.GameManager;
import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.service.MessageBoardManager;
import info.makeyourpicks.service.PicksManager;
import info.makeyourpicks.service.PlayerManager;
import info.makeyourpicks.service.TeamManager;

import org.apache.wicket.Page;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.protocol.http.WebRequestCycleProcessor;
import org.apache.wicket.protocol.http.request.CryptedUrlWebRequestCodingStrategy;
import org.apache.wicket.protocol.http.request.WebRequestCodingStrategy;
import org.apache.wicket.request.IRequestCodingStrategy;
import org.apache.wicket.request.IRequestCycleProcessor;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.odlabs.wiquery.core.commons.WiQueryCoreHeaderContributor;
import org.odlabs.wiquery.core.commons.WiQueryInstantiationListener;
import org.odlabs.wiquery.ui.themes.IThemableApplication;
import org.odlabs.wiquery.ui.themes.WiQueryCoreThemeResourceReference;
import org.springframework.security.AuthenticationManager;

import com.delesio.protocol.http.AbstractAuthenticatedWebApplication;

public abstract class AbstractLeagueApplication extends AbstractAuthenticatedWebApplication implements IThemableApplication {
	private AuthenticationManager authenticationManager;
	private PlayerManager playerManager;
	private TeamManager teamManager;
	private LeagueManager leagueManager;
	private GameManager gameManager;
	private PicksManager picksManager;
	private MessageBoardManager messageBoardManager;
	private boolean paypalSandbox;
	
	private SpringComponentInjector springComponentInjector;
	private WiQueryInstantiationListener wickextPluginInstantiationListener;
	
	private ResourceReference theme;

	
	//private IResourceStreamLocator streamLocator = new SubPackageResourceStreamLocator("/html/");
	// private String htmlResourcePath;
	public AbstractLeagueApplication() {
		
		theme = new ResourceReference(AbstractLeagueApplication.class, "jquery-ui-1.8.4.custom.css");
		

	}

	
	@Override
	protected IRequestCycleProcessor newRequestCycleProcessor() {
			return new WebRequestCycleProcessor() {
				@Override
				protected IRequestCodingStrategy newRequestCodingStrategy() {
					return new CryptedUrlWebRequestCodingStrategy(new WebRequestCodingStrategy());
				}
			};
		}

	
	@Override
	protected void init() {
		super.init();

		// enable Spring dependency injection
		if (springComponentInjector == null) {
			this.springComponentInjector = new SpringComponentInjector(this);
		}
		addComponentInstantiationListener(springComponentInjector);

		wickextPluginInstantiationListener = new WiQueryInstantiationListener();
        addComponentInstantiationListener(wickextPluginInstantiationListener);
        
		// add a custom ResourceStreamLocator
		//getResourceSettings().setResourceStreamLocator(streamLocator);		
		getApplicationSettings().setPageExpiredErrorPage(getHomePage());
		
//		WiQueryCoreHeaderContributor.setTheme(

	}

	@Override
	protected abstract Class getWebSessionClass();
	@Override
	public abstract Class<? extends WebPage> getAccessDeniedPage();
	@Override
	protected abstract Class<? extends WebPage> getSignInPageClass();
	@Override
	public abstract Class<? extends WebPage> getSuccessfulAuthenticationPage();
	@Override
	public abstract Class<? extends Page> getHomePage();
	public abstract Class<? extends Page> getLoggedInHomePage();
	protected abstract String getCssFileName();
//	protected abstract Panel getFooter();
//	protected abstract Panel getHeader();
//	protected abstract Panel getLeftNav();
	

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(
			AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public PlayerManager getPlayerManager() {
		return playerManager;
	}

	public void setPlayerManager(PlayerManager playerManager) {
		this.playerManager = playerManager;
	}

	public TeamManager getTeamManager() {
		return teamManager;
	}

	public void setTeamManager(TeamManager teamManager) {
		this.teamManager = teamManager;
	}

	public LeagueManager getLeagueManager() {
		return leagueManager;
	}

	public void setLeagueManager(LeagueManager leagueManager) {
		this.leagueManager = leagueManager;
	}

	public GameManager getGameManager() {
		return gameManager;
	}

	public void setGameManager(GameManager gameManager) {
		this.gameManager = gameManager;
	}

	public PicksManager getPicksManager() {
		return picksManager;
	}

	public void setPicksManager(PicksManager picksManager) {
		this.picksManager = picksManager;
	}

	public SpringComponentInjector getSpringComponentInjector() {
		return springComponentInjector;
	}

	public void setSpringComponentInjector(
			SpringComponentInjector springComponentInjector) {
		this.springComponentInjector = springComponentInjector;
	}

	public MessageBoardManager getMessageBoardManager() {
		return messageBoardManager;
	}

	public void setMessageBoardManager(MessageBoardManager messageBoardManager) {
		this.messageBoardManager = messageBoardManager;
	}

	public boolean isPaypalSandbox()
	{
		return paypalSandbox;
	}

	public void setPaypalSandbox(boolean paypalSandbox)
	{
		this.paypalSandbox = paypalSandbox;
	}

	public Label getDefaultPageTitle()
	{
		return new Label("pageTitle", "MakeYourPicks");
	}


	public ResourceReference getTheme(Session session) {
		return theme;
	}
	
	public void setTheme(ResourceReference theme) {
		this.theme = theme;
	}
	
}
