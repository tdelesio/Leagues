package com.delesio.markup.html.form;

import java.util.Locale;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class LocaleChoiceRenderer extends ChoiceRenderer
{

	@SuppressWarnings("unused")
	private Locale locale;
	/**
	 * 
	 * Constructor.
	 */

	public LocaleChoiceRenderer(Locale locale) {
		this.locale = locale;
	}

	/**
	 * 
	 * @see wicket.markup.html.form.IChoiceRenderer#getDisplayValue(Object)
	 */

	public Object getDisplayValue(Object object) {

		Locale locale = (Locale) object;

		String display = locale.getDisplayName(locale);

		return display;

	}


}
