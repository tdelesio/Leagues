package com.delesio.markup.html.form;

import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.model.IModel;

public abstract class PersonForm extends AddressForm {

	public PersonForm(String id, IModel model)
	{
		this(id, model, "");
	}
	
	public PersonForm(String id, IModel model, String modelPrefix)
	{
		super (id, model, modelPrefix);
		
		RequiredTextField email = new RequiredTextField("email");
		add(email);
		
		RequiredTextField firstName = new RequiredTextField("firstName");
		add(firstName);
		
		RequiredTextField lastName = new RequiredTextField("lastName");
		add(lastName);
		
		PasswordTextField password = new PasswordTextField("password");
		password.setRequired(true);
		password.setResetPassword(true);
		add(password);
		
		PasswordTextField repassword = new PasswordTextField("repassword");
		repassword.setRequired(true);
		add(repassword);
		
		add(new EqualPasswordInputValidator(password, repassword)); 
	}
}
