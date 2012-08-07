package com.delesio.model;

import java.util.Locale;

public class Translation extends AbstractPersistantObject
{

	private Locale locale;
	private String translationKey;
	private String translatedValue;
	
	@Override
	public IPersistable createTestObject()
	{
		// TODO Auto-generated method stub
		locale = new Locale("en", "US");
		return null;
		
	}

	public Locale getLocale()
	{
		return locale;
	}

	public void setLocale(Locale locale)
	{
		this.locale = locale;
	}

	public String getTranslationKey()
	{
		return translationKey;
	}

	public void setTranslationKey(String translationKey)
	{
		this.translationKey = translationKey;
	}

	public String getTranslatedValue()
	{
		return translatedValue;
	}

	public void setTranslatedValue(String translatedValue)
	{
		this.translatedValue = translatedValue;
	}

	
}
