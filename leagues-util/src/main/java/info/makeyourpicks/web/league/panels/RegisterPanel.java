package info.makeyourpicks.web.league.panels;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.web.layout.AbstractBasePanel;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.EmailAddressValidator;

public abstract class RegisterPanel extends AbstractBasePanel
{

	private String passedLeagueName;
	
	public RegisterPanel(String id)
	{
		
		this(id, null);
	}
	
	public RegisterPanel(String id, String leagueName)
	{
		super(id);
		
		this.passedLeagueName = leagueName;
		
		IModel model = new CompoundPropertyModel(new LoadableDetachableModel()
        {

			@Override
			protected Object load()
			{
				return new Player();
			}
        	
        });
		
		init(model);
	}
	
	
	public RegisterPanel(String id, IModel<Player> model, String leagueName)
	{
		super(id);
		this.passedLeagueName = leagueName;
		
		init(model);
	}
	
	private void init(IModel<Player> model)
	{
        add(new RegisterProfileForm("profileForm", model));
        add(new FeedbackPanel("feedbackPanel"));
		
	}
	
	private class RegisterProfileForm extends Form<Player>
	{
		public RegisterProfileForm(String id, IModel model)
		{
			super(id, model);
			
			add(new RequiredTextField("username"));
			PasswordTextField password = new PasswordTextField("password");
			password.setRequired(true);
			password.setResetPassword(true);
			add(password);
			
			PasswordTextField repassword = new PasswordTextField("repassword");
			repassword.setRequired(true);
			add(repassword);
			
			add(new EqualPasswordInputValidator(password, repassword));
			
			RequiredTextField email = new RequiredTextField("email");
			email.setLabel(new Model("email"));
			email.add(EmailAddressValidator.getInstance());
			add(email);

			add(new Button("submit"));
		}

		@Override
		public void onSubmit() {
			Player playerInfo = (Player)getModelObject();
			
			Player existingPlayer = playerManager.getPlayer(playerInfo.getUsername());
			if (existingPlayer!=null)
			{
				error("The name you selected already exists.  Please choose another one.");
			}
			else
			{
				playerInfo.setMemberLevel(Player.USER);
				try
				{
					playerManager.createUpdatePlayer(playerInfo);
					
					if (passedLeagueName!=null)
					{
						League league = leagueManager.getLeague(passedLeagueName);
						leagueManager.addPlayerToLeague(league, playerInfo);
						
					}
					
					info("Your account has been created!");
//					setResponsePage(LoginPage.class);
					onRegisterSuccess();
				}
				catch (Exception systemException)
				{
					error("A System error has occured.  Please try your request again later.");
					
				}
			}
			
			
			
		}
		
		
	}
	
	public abstract void onRegisterSuccess();
}
