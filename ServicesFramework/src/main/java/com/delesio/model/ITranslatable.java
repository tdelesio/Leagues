package com.delesio.model;

import java.util.List;

public interface ITranslatable
{

	public boolean isTranslated();
	public void translated();
	public List<String> getTranslationKeys();
	public void loadTranslation(Translation translation);
	
}
