package info.makeyourpicks.web.layout;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Payment;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.web.LeagueSession;

import java.util.List;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.delesio.web.pages.AbstractWebPage;

public abstract class AbstractLeaguePage extends AbstractWebPage
{

//	@SpringBean(name="leagueManager")
//	protected LeagueManager leagueManager;
//	
//	@SpringBean(name="teamManager")
//	protected TeamManager teamManager;
//	
//	@SpringBean(name="picksManager")
//	protected PicksManager picksManager;
//	
//	@SpringBean(name="gameManager")
//	protected GameManager gameManager;
//	
//	@SpringBean(name="playerManager")
//	protected PlayerManager playerManager;
	
	public AbstractLeaguePage()
	{
		add(CSSPackageResource.getHeaderContribution(getCssFileName()));
		
		add(getHeader());
		add(getLeftNav());
		add(getFooter());
		
		add(getPageTitle());
	}
	
	protected abstract Panel getHeader();
	protected abstract Panel getFooter();
	protected abstract Panel getLeftNav();
	protected abstract String getCssFileName();
	
	@Override
	protected abstract Label getPageTitle();

	@Override
	protected abstract LeagueSession getWebSession();

//	@Override
//	public abstract String getWicketID();


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
}
