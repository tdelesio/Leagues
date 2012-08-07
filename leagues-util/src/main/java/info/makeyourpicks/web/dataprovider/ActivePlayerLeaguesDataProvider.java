package info.makeyourpicks.web.dataprovider;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.service.LeagueManager;

import java.util.Iterator;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

public abstract class ActivePlayerLeaguesDataProvider implements IDataProvider
{
	
	public Iterator iterator(int first, int count)
	{
		return getLeagueManager().getLeaguesForNavigation(getLoggedInPlayer()).subList(first, first+count).iterator();
	}

	public IModel model(final Object object)
	{
		return new LoadableDetachableModel()
		{

			@Override
			protected Object load()
			{
				return getLeagueManager().getLeague(((League)object).getId());
			}
			
		};
	}

	public int size()
	{
		return getLeagueManager().getLeaguesForNavigation(getLoggedInPlayer()).size();
	}

	public void detach()
	{
	
	}
	

	public abstract Player getLoggedInPlayer();
	public abstract LeagueManager getLeagueManager();
}