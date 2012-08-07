package info.makeyourpicks.web.layout.pages;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Payment;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.web.AbstractLeagueApplication;
import info.makeyourpicks.web.LeagueSession;

import org.apache.wicket.markup.html.basic.Label;

import com.delesio.web.pages.AbstractWebPage;

public abstract class LeaguePage extends AbstractWebPage {

	protected Player getPlayer()
	{
		return getWebSession().getPlayer();
	}
	
	protected League getActiveLeague()
	{
		return getWebSession().getActiveLeague();
	}
	
	protected void setActiveLeague(League league)
	{
		getWebSession().setActiveLeague(league);
	}
	
	protected Payment getPayment()
	{
		return getWebSession().getPayment();
	}
	
	protected void setPayment(Payment payment)
	{
		getWebSession().setPayment(payment);
	}

	@Override
	protected LeagueSession getWebSession() {
		return (LeagueSession)super.getSession();
	}

	@Override
	protected Label getPageTitle()
	{
		return ((AbstractLeagueApplication)getApplication()).getDefaultPageTitle();
	}

	
}
