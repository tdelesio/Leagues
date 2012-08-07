package com.delesio.service;

import java.util.List;
import java.util.Locale;

import com.delesio.model.ITranslatable;
import com.delesio.model.Translation;

public interface ITranslationService
{

	public ITranslatable translate(Locale locale, ITranslatable translatableObject);

	public List<Locale> getLocales();
	public void createOrUpdateTranslation(Translation translation);
	public Translation getTranslation(String key, Locale locale);
	public void deleteTranslation(Translation translation);
}
