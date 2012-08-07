package info.makeyourpicks.web.league.panels;

import info.makeyourpicks.model.Player;
import info.makeyourpicks.web.components.ProfileForm;
import info.makeyourpicks.web.layout.AbstractBasePanel;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.EmailAddressValidator;

public class ProfilePanel extends AbstractBasePanel
{

	public ProfilePanel(String id, IModel<Player> model)
	{
		this(id, model, false);
	}
	
	public ProfilePanel(String id, IModel<Player> model, boolean isRequried)
	{
		super(id);
		
		
		boolean isLoggedIn=false;
		if (getPlayer()!=null)
			isLoggedIn=true;
		add(new ProfileForm("profileForm", model, isRequried, isLoggedIn)
		{

			@Override
			public void submitForm(IModel<Player> model) {
				playerManager.createUpdatePlayer(model.getObject());
				info("Profile Updated");
				
			}
			
		});
	}
	
	 
}
