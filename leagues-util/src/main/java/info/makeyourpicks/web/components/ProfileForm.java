package info.makeyourpicks.web.components;

import info.makeyourpicks.model.Player;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.EmailAddressValidator;

public abstract class ProfileForm extends Form {


		public ProfileForm(String id, IModel model, boolean isRequried, boolean isLoggedIn)
		{
			super(id, model);
			
			RequiredTextField email = new RequiredTextField("email");
			email.setLabel(new Model("email"));
			email.add(EmailAddressValidator.getInstance());
			add(email);

			add(new Button("submit"));
			
			TextField firstName = new TextField("firstName");
			firstName.setLabel(new Model("firstName"));
			firstName.setRequired(isRequried);
			add(firstName);
			
			TextField lastName = new TextField("lastName");
			lastName.setLabel(new Model("lastName"));
			lastName.setRequired(isRequried);
			add(lastName);
			
			TextField address = new TextField("address1");
			address.setLabel(new Model("address1"));
			address.setRequired(isRequried);
			add(address);
			
			TextField city = new TextField("city");
			city.setLabel(new Model("city"));
			city.setRequired(isRequried);
			add(city);
			
			TextField state = new TextField("state");
			state.setLabel(new Model("state"));
			state.setRequired(isRequried);
			add(state);
			
			TextField zip = new TextField("zip");
			zip.setLabel(new Model("zip"));
			zip.setRequired(isRequried);
			//add(zip.add(NumberValidator.POSITIVE));
			add(zip);
			
//			boolean isLoggedIn = false;
//			if (getPlayer()!=null)
//			{
//				isLoggedIn=true;
//			}
			
			RequiredTextField username = new RequiredTextField("username");
			username.setEnabled(!isLoggedIn);
			add(username);
					
			PasswordTextField password = new PasswordTextField("password");
			password.setRequired(true);
			password.setResetPassword(true);
			password.setVisible(!isLoggedIn);
			add(password);
			
			PasswordTextField repassword = new PasswordTextField("repassword");
			repassword.setRequired(true);
			repassword.setVisible(!isLoggedIn);
			add(repassword);
			
			
			add(new EqualPasswordInputValidator(password, repassword));
		}
		
			public void onSubmit()
			{
				//setResponsePage(ProfilePage.class);
				submitForm(getModel());
			
				
			}
		
		public abstract void submitForm(IModel<Player> model);

}
