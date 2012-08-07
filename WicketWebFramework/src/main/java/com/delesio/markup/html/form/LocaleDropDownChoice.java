package com.delesio.markup.html.form;

import java.util.List;
import java.util.Locale;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;

public final class LocaleDropDownChoice extends DropDownChoice
{

	public LocaleDropDownChoice(String id, List<Locale> locales, IModel model, Locale locale) {

		super(id, locales, new LocaleChoiceRenderer(locale));
		// set the model that gets the current locale, and that is used for
		// updating the current locale to property 'locale' of FormInput
		setModel(model);
	}

	/**
	 * @see wicket.markup.html.form.DropDownChoice#wantOnSelectionChangedNotifications()
	 */
	protected boolean wantOnSelectionChangedNotifications() {
		// we want roundtrips when a the user selects another item
		return true;
	}

	/**
	 * @see wicket.markup.html.form.DropDownChoice#onSelectionChanged(java.lang.Object)
	 */
	public void onSelectionChanged(Object newSelection) {
		// note that we don't have to do anything here, as our property
		// model allready calls FormInput.setLocale when the model is
		// updated
		// setLocale((Locale)newSelection); // so we don't need to do this
	}
	
}
