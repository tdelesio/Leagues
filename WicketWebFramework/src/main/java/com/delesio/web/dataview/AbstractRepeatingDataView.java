package com.delesio.web.dataview;

import org.apache.wicket.markup.repeater.RepeatingView;

public abstract class AbstractRepeatingDataView extends RepeatingView {

	private final static String SELECT_NAME = "select";
	
	public AbstractRepeatingDataView(String id)
	{
		super(id);
	}
	
	public String getSelectName()
	{
		return SELECT_NAME;
	}
}
