package com.delesio.dao;

import java.util.Locale;

import com.delesio.model.Translation;

public interface ITranslationDao
{
	public Translation findTranslation(String key, Locale locale);
}
