package info.makeyourpicks.web.weight.pages;

import info.makeyourpicks.model.Player;
import info.makeyourpicks.web.weight.panels.ViewPicksPanel;

import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

public class ViewPicksPage extends WeightBasePage
{

	public static final String WICKET_ID = "viewPicks"; 
	@Override
	public String getWicketID()
	{
		return WICKET_ID;
	}

	public ViewPicksPage()
	{
		init(new LoadableDetachableModel()
		{

			@Override
			protected Object load()
			{
				return getPlayer();
			}
			
		});
	}
	public ViewPicksPage(IModel<Player> playerModel)
	{
		
		init(playerModel);
	}
	
	private void init(final IModel<Player> playerModel)
	{
	
		ViewPicksPanel viewPicksPanel = new ViewPicksPanel("viewPicksPanel", getWeekModel(), playerModel)
		{

			@Override
			public Page getViewPicksPage(IModel<Player> playerModel)
			{
				return new ViewPicksPage(playerModel);
			}
			
		};
		add(viewPicksPanel);
	}
}
