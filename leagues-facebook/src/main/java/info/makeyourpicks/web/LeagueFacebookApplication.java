package info.makeyourpicks.web;

import java.util.HashMap;
import java.util.Map;

import info.makeyourpicks.web.layout.FacebookFooter;
import info.makeyourpicks.web.layout.FacebookHeader;
import info.makeyourpicks.web.layout.FacebookLeftNavPanel;
import info.makeyourpicks.web.league.pages.FacebookEntryPage;
import info.makeyourpicks.web.league.pages.HomePage;
import info.makeyourpicks.web.league.pages.Login;

import javax.security.auth.login.FailedLoginException;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.RedirectToUrlException;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.IUnauthorizedComponentInstantiationListener;
import org.apache.wicket.authorization.UnauthorizedInstantiationException;
import org.apache.wicket.authorization.strategies.page.AbstractPageAuthorizationStrategy;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Panel;

import com.google.code.facebookapi.FacebookJaxbRestClient;
import com.google.code.facebookapi.FacebookParam;
import com.google.code.facebookapi.IFacebookRestClient;


public class LeagueFacebookApplication extends AbstractLeagueApplication implements IUnauthorizedComponentInstantiationListener 
{

	public static final String CLIENT = "auth.client";

	//application id 186934348466
    private String _apiKey = "4bf7945f696613e8387b504e2201da7b"; //replace this
    private String _secretKey = "fd2e83331d003ad99c27331081bae4a2"; //replace this

    public final static Map<String, Class> homePageMap;
    static 
	{
		homePageMap = new HashMap<String, Class>(5);
		homePageMap.put("suicide", info.makeyourpicks.web.suicide.pages.MakePicksPage.class);
		homePageMap.put("football", info.makeyourpicks.facebook.football.pages.MakePicksPage.class);
//		homePageMap.put("ncaa", info.makeyourpicks.web.ncaa.pages.MakePicksPage.class);
		homePageMap.put("ncaa-weight", info.makeyourpicks.web.weight.pages.MakePicksPage.class);
		homePageMap.put("nfl-weight", info.makeyourpicks.web.weight.pages.MakePicksPage.class);
	}
    
    protected void init() {
        super.init();

        getSecuritySettings().setAuthorizationStrategy(new FaceBookAuthorizationStrategy());
        getSecuritySettings().setUnauthorizedComponentInstantiationListener(this);
        
//        mountBookmarkablePage("entry.html", FacebookEntryPage.class);
    
    }


    public void onUnauthorizedInstantiation(Component component) {
        if (component instanceof Page) {
            Page page = (Page) component;

            if (((FacebookSession) page.getSession()).getClient() != null) {
                return;
            }

            FacebookSession session = (FacebookSession) page.getSession();
            try {
                IFacebookRestClient authClient = LeagueFacebookApplication.getAuthenticatedClient(page.getRequest(), _apiKey, _secretKey);
                session.setClient(authClient);
            } catch (FailedLoginException fle) {
                //user not logged in
                forceLogin(page);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new UnauthorizedInstantiationException(component.getClass());
        }
    }

    private void forceLogin(Page page) {

//        page.getRequestCycle().setRequestTarget(new RedirectRequestTarget("http://www.facebook.com/login.php?api_key=" + _apiKey + "&v=1.0"));
        throw new RedirectToUrlException("http://www.facebook.com/login.php?api_key=" + _apiKey + "&v=1.0");

    }

    public Session newSession(Request request, Response response) {
        return new FacebookSession(request);
    }

    private static class FaceBookAuthorizationStrategy extends AbstractPageAuthorizationStrategy {
        protected boolean isPageAuthorized(final Class pageClass) {
            return false;
        }
    }

    public static IFacebookRestClient getAuthenticatedClient(Request request, String apiKey, String secretKey) throws Exception {
        String authToken = request.getParameter("auth_token");
        String sessionKey = request.getParameter(FacebookParam.SESSION_KEY.toString());
        IFacebookRestClient fbClient = null;
        if (sessionKey != null) {
            fbClient = new FacebookJaxbRestClient(apiKey, secretKey, sessionKey);
        } else if (authToken != null) {
            fbClient = new FacebookJaxbRestClient(apiKey, secretKey);
            //establish session
            fbClient.auth_getSession(authToken);
        } else {
            throw new FailedLoginException("Session key not found");
        }
//        fbClient.setIsDesktop(false);
        return fbClient;
    }


	@Override
	public Class<? extends WebPage> getAccessDeniedPage()
	{
		return Login.class;
	}


	@Override
	public Class<? extends Page> getHomePage()
	{
		return FacebookEntryPage.class;
//		return Login.class;
	}


	@Override
	protected Class<? extends WebPage> getSignInPageClass()
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Class<? extends WebPage> getSuccessfulAuthenticationPage()
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected Class getWebSessionClass()
	{
		return FacebookSession.class;
	}


	@Override
	public String getCssFileName()
	{
		return "styles/master.css";
	}

	@Override
	public Panel getFooter()
	{
		return new FacebookFooter("footer");
	}

	@Override
	public Panel getHeader()
	{
		return new FacebookHeader("header");
	}

	@Override
	public Panel getLeftNav()
	{
		return new FacebookLeftNavPanel("leaguePanel");
	}


	@Override
	public Class<? extends Page> getLoggedInHomePage()
	{
		return HomePage.class;
	}

	
    
}
