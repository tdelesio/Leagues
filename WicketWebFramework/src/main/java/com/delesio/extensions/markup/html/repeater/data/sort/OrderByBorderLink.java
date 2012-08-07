package com.delesio.extensions.markup.html.repeater.data.sort;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByLink;
import org.apache.wicket.markup.html.border.Border;

public class OrderByBorderLink extends Border
{
	public OrderByBorderLink(String id, String property, ISortStateLocator stateLocator,
			OrderByLink.ICssProvider cssProvider, OrderByLink.ICssProvider cssProviderForLink)
	{
		super(id);
		OrderByLink link = new OrderByLink("orderByLink", property, stateLocator,
				cssProviderForLink)
		{

			private static final long serialVersionUID = 1L;

			protected void onSortChanged()
			{
				OrderByBorderLink.this.onSortChanged();
			}
		};
		add(link);
		add(new OrderByLink.CssModifier(link, cssProvider));
		link.add(getBodyContainer());
	}

	/**
	 * This method is a hook for subclasses to perform an action after sort has changed
	 */
	protected void onSortChanged()
	{
		// noop
	}

	/**
	 * @param id
	 *            see {@link OrderByLink#OrderByLink(String, String, ISortStateLocator)}
	 * @param property
	 *            see {@link OrderByLink#OrderByLink(String, String, ISortStateLocator)}
	 * @param stateLocator
	 *            see {@link OrderByLink#OrderByLink(String, String, ISortStateLocator)}
	 */
	public OrderByBorderLink(String id, String property, ISortStateLocator stateLocator)
	{
		this(id, property, stateLocator, OrderByLink.DefaultCssProvider.getInstance());
	}
	
	public OrderByBorderLink(String id, String property, ISortStateLocator stateLocator,
			OrderByLink.ICssProvider cssProvider)
	{
		this(id, property, stateLocator, OrderByLink.DefaultCssProvider.getInstance(), OrderByLink.VoidCssProvider.getInstance());
	}
}
