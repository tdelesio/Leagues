package com.delesio.markup.html.form;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.StringValidator;

public abstract class AddressForm extends Form
{
	public AddressForm(String id, IModel model)
	{
		this(id, model, "");
	}
	
	public AddressForm(String id, IModel model, String modelPrefix)
	{
		super(id, model);
		
		RequiredTextField address1 = new RequiredTextField(modelPrefix+"address1");
		add(address1);
		
		TextField address2 = new TextField(modelPrefix+"address2");
		add(address2);
		
		RequiredTextField city = new RequiredTextField(modelPrefix+"city");
		city.add(StringValidator.maximumLength(50));
		add(city);
		
		RequiredTextField stateCode = new RequiredTextField(modelPrefix+"stateCode");
		stateCode.add(StringValidator.maximumLength(2));
		add(stateCode);
		
		RequiredTextField zip = new RequiredTextField(modelPrefix+"zip");
		zip.add(StringValidator.maximumLength(5));
		add(zip);
		
		RequiredTextField country = new RequiredTextField(modelPrefix+"country");
		country.add(StringValidator.maximumLength(2));
		add(country);

//		add(new Button("submit"));
		AjaxButton ajaxButton = new AjaxButton("submit")
		{

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form form) {
				onAjaxSubmit(target, form);
			}
			
			@Override
            protected void onError(AjaxRequestTarget target, Form form) {
                //Ops we got some errors, show them in a feedback panel
                onAjaxError(target, form);
            } 
			
		};
		add(ajaxButton);
	}
	
	protected abstract void onAjaxSubmit(AjaxRequestTarget target, Form form);
	protected abstract void onAjaxError(AjaxRequestTarget target, Form form);
}
