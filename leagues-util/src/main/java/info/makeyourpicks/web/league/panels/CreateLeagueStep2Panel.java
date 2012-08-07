package info.makeyourpicks.web.league.panels;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.web.components.ProfileForm;
import info.makeyourpicks.web.layout.AbstractBasePanel;
import info.makeyourpicks.web.layout.pages.LeaguePage;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import com.delesio.protocol.http.AbstractAuthenticatedWebSession;

/**
 * @author PRC9041
 */
public abstract class CreateLeagueStep2Panel extends AbstractBasePanel {
	
	public CreateLeagueStep2Panel(String id, final IModel<League> model)
	{
		super(id, model);
		
//		Form<League> form = new Form<League>("step2Form", model)
//		{
//
//			@Override
//			protected void onSubmit() {
////				setResponsePage(new CreateLeagueStep3Page(model));
//				setResponsePage(getStep3Page(getModel()));
//			}
//			
//		};
		
		IModel<Player> playerModel = new CompoundPropertyModel<Player>(new LoadableDetachableModel<Player>() {

			@Override
			protected Player load() {
				Player p = getPlayer();
				if (p == null)
					p = new Player();
				return p;
			}
			
		});
		

		boolean isLoggedIn=false;
		if (getPlayer()!=null)
			isLoggedIn=true;
		
		
		ProfileForm form = new ProfileForm("step2Form", playerModel, true, isLoggedIn) {
			
			@Override
			public void submitForm(IModel<Player> playermodel) {
				Player player = playermodel.getObject();
				League league = model.getObject();
				league.setAdmin(player);
				
				//create league
				leagueManager.createLeague(league);
				
				//active league
				setActiveLeague(league);
				
				//log user in
				AbstractAuthenticatedWebSession.get().authenticate(player.getUsername(), player.getPassword());
//				playerManager.createUpdatePlayer(player);
//				leagueManager.createOrUpdateLeague(league);
				setResponsePage(getStep3Page(model));
				
			}
		};
		add(form);
		
		TextArea<String> emailAddresses = new TextArea<String>("emailAddresses");
		form.add(emailAddresses);
		
		TextArea<String> emailText = new TextArea<String>("emailText");
		form.add(emailText);
		
		
	}
	
	public abstract Class<? extends Page> getStep3Page(IModel<League> leagueModel);
}

