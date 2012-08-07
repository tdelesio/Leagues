package info.makeyourpicks.web.dataprovider;

import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.Picks;
import info.makeyourpicks.model.Week;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import com.delesio.model.AbstractPersistantObject;

public abstract class PicksDataProvider extends SortableDataProvider
{

//	private IModel weekModel;
//	public PicksDataProvider(IModel weekModel)
//	{
//		this.weekModel = weekModel;
//	}
	
	public static final String ID = "ID";
	public static final String WEIGHT = "weight";
	public PicksDataProvider()
	{
		setSort(ID, true);
	}
	
	public PicksDataProvider(String sort, boolean ascending)
	{
		setSort(sort, ascending);
	}
	
	public Iterator iterator(int first, int count)
	{
		List<Game> games = getGamesByWeek();
		List<Picks> picks = new ArrayList<Picks>(games.size());
		Picks pick;
		for (Game game: games)
		{
			pick = getPickByPlayerLeagueWeekAndGame(game);
			picks.add(pick);
		}
		
		final SortParam sp = getSort();
		if (sp.getProperty().equals(WEIGHT))
		{
			Collections.sort(picks, getWeightComparator(sp.isAscending()));
		}
		return picks.subList(first, (count+first)).iterator();
	}

	public IModel model(final Object object)
	{
		return new CompoundPropertyModel(new LoadableDetachableModel()
		{
			@Override
			protected Object load()
			{
				Picks obj = (Picks)object;
				Picks picks = loadPick(obj.getId());
				if (picks == null)
				{
					picks = new Picks(obj.getName(), obj.getLeague(), obj.getWeek(), obj.getGame(), true);
				}
				return picks;
			}
			
		});
	}

	public int size()
	{
		return getGamesByWeek().size();
	}

	public void detach()
	{
		// TODO Auto-generated method stub
		
	}
	
	public abstract List<Game> getGamesByWeek();
	public abstract Picks loadPick(long id);
	public abstract Picks getPickByPlayerLeagueWeekAndGame(Game game);
	
	public static Comparator<Picks> getWeightComparator(final boolean isAscending)
	{
		return new Comparator<Picks>()
		{
	
			public int compare(Picks o1, Picks o2)
			{
				if (o1.getWeight()>o2.getWeight())
				{
					if (isAscending)
					{
						return 1;
					}
					else
					{
						return -1;
					}
				}
				else if (o1.getWeight()<o2.getWeight())
				{
					if (isAscending)
					{
						return -1;
					}
					else
					{
						return 1;
					}
				}
				else
				{
					return 0;
				}
			}
			
		};
	}

}
