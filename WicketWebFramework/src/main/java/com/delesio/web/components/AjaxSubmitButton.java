package com.delesio.web.components;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;

/**
 * AjaxSubmitButton.java
 *
 * @version Mar 13, 2008
 * @author Anthony DePalma
 */
public class AjaxSubmitButton extends AjaxButton {
	protected Component component;
	
	// constructor
	public AjaxSubmitButton(String id, Form form, Component component) {
		super(id, form);
		this.component = component;
	}
	
	@Override
	protected void onSubmit(AjaxRequestTarget target, Form form) {
		// repaint the feedback panel so that it is hidden
		target.addComponent(component);
	}
	
	@Override
	protected void onError(AjaxRequestTarget target, Form form) {
		// repaint the feedback panel so errors are shown
		target.addComponent(component);
	}
	
}
