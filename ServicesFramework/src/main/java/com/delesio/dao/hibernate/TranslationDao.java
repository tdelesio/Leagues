package com.delesio.dao.hibernate;

import java.util.Locale;

import com.delesio.dao.ITranslationDao;
import com.delesio.model.Translation;

public class TranslationDao extends AbstractDao implements ITranslationDao
{

	public Translation findTranslation(String key, Locale locale)
	{
		return (Translation)extractSingleElement(getHibernateTemplate().find("from Translation t where t.translationKey=? and t.locale=?", new Object[]{key, locale}));
	}

}
