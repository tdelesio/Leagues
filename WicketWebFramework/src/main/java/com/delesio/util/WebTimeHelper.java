package com.delesio.util;

import org.apache.wicket.model.LoadableDetachableModel;

public class WebTimeHelper extends TimeHelper
{
	public static LoadableDetachableModel getDetachableMonths()
	{
		return new LoadableDetachableModel()
		{
			@Override
			public Object load()
			{
				return getMonths();
			}
		};
	}
}
