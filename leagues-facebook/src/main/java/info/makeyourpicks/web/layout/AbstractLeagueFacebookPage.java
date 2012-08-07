package info.makeyourpicks.web.layout;

import info.makeyourpicks.web.FacebookSession;
import info.makeyourpicks.web.LeagueFacebookApplication;
import info.makeyourpicks.web.LeagueSession;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import com.google.code.facebookapi.FacebookJaxbRestClient;

public abstract class AbstractLeagueFacebookPage extends AbstractLeaguePage {
	
	
	
	public AbstractLeagueFacebookPage()
	{
		super();

	}
	
	@Override
	protected FacebookSession getWebSession() {
		return (FacebookSession)super.getSession();
	}
	
	public abstract String getWicketID();
	
	@Override
	protected Label getPageTitle()
	{
		return ((LeagueFacebookApplication)getApplication()).getDefaultPageTitle();
	}

	protected FacebookJaxbRestClient getFacebookClient()
	{
		return (FacebookJaxbRestClient)getWebSession().getClient();
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

	
}
