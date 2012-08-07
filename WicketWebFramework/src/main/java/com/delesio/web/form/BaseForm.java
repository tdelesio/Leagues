package com.delesio.web.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.StringValidator;

public class BaseForm extends Form
{

	public BaseForm(String id)
	{
		super(id);
	}
	
	public BaseForm(String id, IModel model)
	{
		super(id, model);
	}
	
	public void addRequiredTextField(String id, int maxLength)
	{
		RequiredTextField textField = new RequiredTextField(id);
		textField.add(StringValidator.maximumLength(maxLength));
		add(textField);
	}
}
