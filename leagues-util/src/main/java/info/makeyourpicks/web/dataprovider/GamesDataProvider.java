package info.makeyourpicks.web.dataprovider;

import info.makeyourpicks.model.Game;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

public abstract class GamesDataProvider implements IDataProvider {

	public void detach() {
		
	}

	public Iterator iterator(int start, int count) {
		return getGames().subList(start, start+count).iterator();
	}

	public IModel model(final Object object) {
		return new CompoundPropertyModel(new LoadableDetachableModel()
		{
			@Override
			protected Object load()
			{
				return loadGame((Game)object);
			}
			
		});
	}

	public int size() {
		return getGames().size();
	}
	
	public abstract List<Game> getGames();
	public abstract Game loadGame(Game obj);
	

}
