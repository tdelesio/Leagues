package info.makeyourpicks.web.league.pages;

import java.util.List;

import info.makeyourpicks.model.Player;
import info.makeyourpicks.service.PlayerManager;
import info.makeyourpicks.web.LeagueFacebookApplication;
import info.makeyourpicks.web.layout.AbstractLeagueFacebookPage;
import info.makeyourpicks.web.league.panels.RegisterPanel;

import org.acegisecurity.LockedException;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.odlabs.wiquery.ui.accordion.Accordion;

import com.delesio.protocol.http.AbstractAuthenticatedWebSession;
import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookJaxbRestClient;

public class FacebookEntryPage extends AbstractLeagueFacebookPage
{

	@SpringBean(name="playerManager")
	protected PlayerManager playerManager;
	
	public FacebookEntryPage() {
        try {
        	FacebookJaxbRestClient client = getFacebookClient();

        	final long id = client.users_getLoggedInUser();
        	List<Player> players = playerManager.getPlayersByFacebookId(id);
        	if (players != null && !players.isEmpty())
        	{
        		if (players.size()==1)
        		{
        			getWebSession().authenticate(players.get(0).getUsername(), players.get(0).getPassword());
        			setResponsePage(((LeagueFacebookApplication)getApplication()).getLoggedInHomePage());
        		}
        		else
        		{
        			//need to show a drop down box of the players
        		}
        	}
        	else
        	{
        		Accordion accordion = new Accordion("accordion");
        		add(accordion);
        		
        		IModel<Player> model = new CompoundPropertyModel<Player>(
    				new LoadableDetachableModel<Player>()
    				{

						@Override
						protected Player load()
						{
							Player player = new Player();
							player.setFacebookId(id);
							return player;
						}
    					
    				});
        		
        		LoginSyncForm facebookSyncForm = new LoginSyncForm("facebookSyncForm", model);
        		accordion.add(facebookSyncForm);
        		
        		RegisterPanel registerPanel = new RegisterPanel("registerPanel", model, null)
        		{

					@Override
					public void onRegisterSuccess()
					{
						setResponsePage(((LeagueFacebookApplication)getApplication()).getLoggedInHomePage());
					}
        			
        		};
        		accordion.add(registerPanel);
        	}
           
        } catch (FacebookException e) {
            throw new RuntimeException(e);
        }
       }

	
	private class LoginSyncForm extends Form<Player>
	{
		public LoginSyncForm(String id, IModel<Player> model)
		{
			super(id, model);
			
			// add form components
			RequiredTextField username = new RequiredTextField("username");
			add(username);
			
			PasswordTextField password = new PasswordTextField("password");
			password.setRequired(true);
			add(password);
			
			add(new Button("ajaxButton"));
		}
		
		
		
		@Override
		protected void onSubmit()
		{
			Player player = getModelObject();
			long facebookid = player.getFacebookId();
				if (signIn(player.getUsername(), player.getPassword())) 
				{
					//save facebook id
					player = getPlayer();
					player.setFacebookId(facebookid);
					playerManager.createUpdatePlayer(player);
					setResponsePage(((LeagueFacebookApplication)getApplication()).getLoggedInHomePage());
				} 
				else {
					
				}
			
		}



		public boolean signIn(String usernameValue, String passwordValue) {
			boolean result = false;
			
			try
			{
				result = AbstractAuthenticatedWebSession.get().authenticate(usernameValue, passwordValue);
				if (!result)
				{
					error("Your login information is not correct.  Please check the username and/or password.");
				}
				return result;
			}
			catch (LockedException lockedException)
			{
				error("Your account has been locked for non-payment.  Please email your league admin.");
				return false;
			}
		}
	}

	@Override
	public String getWicketID()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
