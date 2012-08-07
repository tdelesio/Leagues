package info.makeyourpicks.web;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Payment;
import info.makeyourpicks.model.Player;

import org.apache.wicket.Request;
import org.apache.wicket.Session;
import org.apache.wicket.injection.web.InjectorHolder;
import org.springframework.security.AuthenticationManager;

import com.delesio.protocol.http.AbstractAuthenticatedWebSession;


public class LeagueSession extends AbstractAuthenticatedWebSession {

	private League activeLeague;
	private Payment payment;
	// constructor
	public LeagueSession(Request request) {
        super(request);
        InjectorHolder.getInjector().inject(this);
    }

	/**
	 * Hook into a successful authentication
	 * 
	 */
	public void onSuccessfulAuthentication() {

	}
	
	/**
	 * Returns the application AuthenticationManager
	 * 
	 * @return AuthenticationManager
	 */
	public AuthenticationManager getAuthenticationManager() {
		return ((AbstractLeagueApplication)getApplication()).getAuthenticationManager();
	}
	
    /**
     * Returns the currently logged in user, or null when no user is logged in
     * 
     * @return User
     */
    public Player getPlayer() throws RuntimeException
    {
 //   	String userName = ((User)getPrincipal()).getUsername().toLowerCase();
 //   	return ((LeagueApplication)getApplication()).getPlayerManager().getPlayer(userName);
    	return (Player)getCredentials();
    	
    }

	
	public static LeagueSession getWebSession() {
		return (LeagueSession)Session.get();
		
	}

	public League getActiveLeague() {
		return activeLeague;
	}

	public void setActiveLeague(League activeLeague) {
		this.activeLeague = activeLeague;
	}

	public Payment getPayment()
	{
		return payment;
	}

	public void setPayment(Payment payment)
	{
		this.payment = payment;
	}

	
	
}
