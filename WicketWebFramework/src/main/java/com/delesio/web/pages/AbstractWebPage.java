package com.delesio.web.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

import com.delesio.protocol.http.AbstractAuthenticatedWebSession;

public abstract class AbstractWebPage extends WebPage
{
	public AbstractWebPage()
	{
		super();
	}
	
//	public abstract String getWicketID(); 
	protected abstract Label getPageTitle();
	protected abstract AbstractAuthenticatedWebSession getWebSession();
}
