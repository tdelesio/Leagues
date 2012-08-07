package com.delesio.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.delesio.cache.action.ICacheCreateAction;
import com.delesio.dao.ITranslationDao;
import com.delesio.model.ITranslatable;
import com.delesio.model.Translation;
import com.delesio.service.ITranslationService;

public class TranslationService extends AbstractService implements
		ITranslationService
{

	private ITranslationDao translationDao;
	private List<Locale> locales;
	public static final String TRANSLATION_CACHE_KEY = "translation_cache_key";
	@Override
	protected void init()
	{
		
	}
	
	public void createOrUpdateTranslation(Translation translation)
	{
		dao.saveOrUpdate(translation);
	}

	public void deleteTranslation(Translation translation)
	{
		dao.deleteObject(translation);
	}

	
	public Translation getTranslation(String key, Locale locale)
	{
		return translationDao.findTranslation(key, locale);
	}

	@SuppressWarnings("unused")
	private class TranslationCacheKey
	{
		private String key;
		private Locale locale;
	
		public TranslationCacheKey(String key, Locale locale)
		{
			this.key = key;
			this.locale = locale;
		}
		
		public TranslationCacheKey(Translation translation)
		{
			this.key = translation.getTranslationKey();
			this.locale = translation.getLocale();
		}
	}
	
	private Map<TranslationCacheKey, Translation> getTranslationMap()
	{
		return (Map<TranslationCacheKey, Translation>)cacheProvider.getOrPut(TRANSLATION_CACHE_KEY, TRANSLATION_CACHE_KEY, new ICacheCreateAction()
		{
			public Object createCacheObject()
			{
		
				Iterator<Translation> translations = dao.loadAllObjects(Translation.class).iterator();
				Translation translation;
				Map<TranslationCacheKey, Translation> translationMap = new HashMap<TranslationCacheKey, Translation>();
				TranslationCacheKey cacheKey;
				while (translations.hasNext())
				{
					translation = translations.next();
					cacheKey = new TranslationCacheKey(translation);
					translationMap.put(cacheKey, translation);
				}
				
				return translationMap;
			}
		});
		
		
	}
	
	public ITranslatable translate(Locale locale, ITranslatable translatableObject)
	{
		Iterator<String> keys = translatableObject.getTranslationKeys().iterator();
		String key;
		TranslationCacheKey cacheKey;
		Translation translation;
		Map<TranslationCacheKey, Translation> translationMap = getTranslationMap();
		while (keys.hasNext())
		{
			key = keys.next();
			cacheKey = new TranslationCacheKey(key, locale);
			translation = translationMap.get(cacheKey);
			
			translatableObject.loadTranslation(translation);
		}
		
		return translatableObject;
	}

	public List<Locale> getLocales()
	{
		return locales;
	}

	public void setLocales(List<Locale> locales)
	{
		this.locales = locales;
	}

	public ITranslationDao getTranslationDao()
	{
		return translationDao;
	}

	public void setTranslationDao(ITranslationDao translationDao)
	{
		this.translationDao = translationDao;
	}

	
}
