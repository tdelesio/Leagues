package com.delesio.extensions.markup.html.repeater.data.table.filter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.ChoiceFilteredPropertyColumn;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class MonthChoiceFilteredPropertyColumn extends
		ChoiceFilteredPropertyColumn
{

	public MonthChoiceFilteredPropertyColumn(IModel displayModel, String propertyExpression, IModel filterChoices)
	{
		super(displayModel, propertyExpression, filterChoices);
	}
	
	public MonthChoiceFilteredPropertyColumn(IModel displayModel, String sortProperty, String propertyExpression, IModel filterChoices)
	{
		super(displayModel, sortProperty, propertyExpression, filterChoices);
	}			

	@Override
	protected IChoiceRenderer getChoiceRenderer()
	{
		return new IChoiceRenderer()
		{

			public Object getDisplayValue(Object arg0)
			{
				if (arg0==null)
				{
					return "";
				}
				Date date = (Date)arg0;
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
				return simpleDateFormat.format(date);
			}

			public String getIdValue(Object object, int index)
			{
					return Integer.toString(index);
			}
			
		};
	}
}
