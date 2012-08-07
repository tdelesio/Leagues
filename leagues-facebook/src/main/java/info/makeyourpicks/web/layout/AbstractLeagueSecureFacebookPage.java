package info.makeyourpicks.web.layout;

import info.makeyourpicks.web.FacebookSession;
import info.makeyourpicks.web.LeagueFacebookApplication;
import info.makeyourpicks.web.LeagueSession;
import info.makeyourpicks.web.league.pages.ActivateLeaguePage;
import info.makeyourpicks.web.league.pages.LeagueOnHoldPage;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import com.google.code.facebookapi.FacebookJaxbRestClient;

public abstract class AbstractLeagueSecureFacebookPage extends AbstractLeagueSecurePage
{
	public AbstractLeagueSecureFacebookPage()
	{
		super();
	}
	
	public AbstractLeagueSecureFacebookPage(int weekNumber)
	{
		super(weekNumber);
	}
	
	@Override
	protected IModel getSeasonModel()
	{
		return null;
	}
	
	@Override
	protected IModel getWeekModel()
	{
		return null;
	}
	
	protected FacebookJaxbRestClient getFacebookClient()
	{
		return (FacebookJaxbRestClient)((FacebookSession) getSession()).getClient();
	}

	@Override
	protected Class<? extends Page> getActivateLeaguePage()
	{
		return ActivateLeaguePage.class;
	}

	@Override
	protected Class<? extends Page> getLeagueOnHoldPage()
	{
		return LeagueOnHoldPage.class;
	}

	@Override
	protected String getCssFileName()
	{
		return ((LeagueFacebookApplication)getApplication()).getCssFileName();
	}

	@Override
	protected Panel getFooter()
	{
		return ((LeagueFacebookApplication)getApplication()).getFooter();
	}

	@Override
	protected Panel getHeader()
	{
		return ((LeagueFacebookApplication)getApplication()).getHeader();
	}

	@Override
	protected Panel getLeftNav()
	{
		return ((LeagueFacebookApplication)getApplication()).getLeftNav();
	}

	@Override
	protected Label getPageTitle()
	{
		return ((LeagueFacebookApplication)getApplication()).getDefaultPageTitle();
	}

	@Override
	protected LeagueSession getWebSession() {
		return (LeagueSession)super.getSession();
	}

	@Override
	public abstract String getWicketID();
	

}
