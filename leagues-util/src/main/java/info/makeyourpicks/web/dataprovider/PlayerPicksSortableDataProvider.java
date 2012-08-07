package info.makeyourpicks.web.dataprovider;

import info.makeyourpicks.model.Player;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

public abstract class PlayerPicksSortableDataProvider extends SortableDataProvider{

//	private List<Player> players;
	public PlayerPicksSortableDataProvider()
	{
//		 players = ServiceLocator.getPlayerManagerService().getPlayersInLeague(league);
	}

	public Iterator iterator(int start, int count) {
		
		return getPlayersInLeague().subList(start, start+count).iterator();
	}

	public IModel model(final Object object) {
		
		return new LoadableDetachableModel()
		{

			@Override
			protected Object load()
			{
				return getPlayer((Player)object);
			}
			
		};
	}

	public int size() {
		return getPlayersInLeague().size();
	}
	
	public abstract Player getPlayer(Player player);
	public abstract List<Player> getPlayersInLeague();
}
