package com.delesio.web.model;

import org.apache.wicket.markup.html.WebComponent;

public interface ICalendarDay {

	public WebComponent getComponentForDay(String id, int dayOfMonth);
	public String getCSSClass();
}
