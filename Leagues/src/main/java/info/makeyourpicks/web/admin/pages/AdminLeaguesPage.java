package info.makeyourpicks.web.admin.pages;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Season;

import java.util.Iterator;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;


/**
 * @author PRC9041
 */
public class AdminLeaguesPage extends AbstractAdminBasePage {

	public AdminLeaguesPage()
	{
		super();
		
		//add(new PageLink("createLeagueTypePage", CreateLeagueTypePage.class));
		add(new Link("createLeagueType")
		{

			@Override
			public void onClick()
			{
				setResponsePage(AdminLeagueTypePage.class);
			}
			
		});
		
		add(new Link("createLeague")
		{

			@Override
			public void onClick()
			{
				setResponsePage(AdminLeaguePage.class);
			}
			
		});
		
		add(new Link("createSeasonPage")
		{

			@Override
			public void onClick()
			{
				setResponsePage(CreateSeasonPage.class);
			}
			
		});
		
		
			
		
		//LEAGUE TYPES		
		IDataProvider<LeagueType> leagueTypeDataProvider = new IDataProvider<LeagueType>()
		{
			public void detach()
			{				
			}

			public Iterator iterator(int first, int count)
			{
				return leagueManager.getLeagueTypes().subList(first, count+first).iterator();
			}

			

			public int size()
			{
				return leagueManager.getLeagueTypes().size();
			}

			public IModel<LeagueType> model(final LeagueType object)
			{
				return new CompoundPropertyModel(new LoadableDetachableModel()
				{

					@Override
					protected Object load()
					{
						return leagueManager.getLeagueType( ((LeagueType)object).getId());
					}
					
				});
			}
			
			
		};
		
		DataView<LeagueType> leagueTypeDataView = new DataView<LeagueType>("repeatingTypes", leagueTypeDataProvider)
		{
			@Override
			protected void populateItem(final Item<LeagueType> item)
			{
//				final LeagueType leagueType = item.getModelObject();
				item.add(new Link<LeagueType>("select")
	            {

					@Override
					public void onClick()
					{
						setResponsePage(new AdminLeagueTypePage(item.getModel()));
					}
	            	
	            });
	            item.add(new Label("typeOfLeague"));
	            item.add(new Label("leagueTypeDisplay"));
	            item.add(new Label("startDisplay"));
	            item.add(new Label("endDisplay"));
	            
	            item.add(new SimpleAttributeModifier("class", (item.getIndex() % 2 != 0) ? "even" : "odd"));
			}
		};
		add(leagueTypeDataView);
		
		//LEAGUES
		IDataProvider<League> leagueDataProvider = new IDataProvider<League>()
		{
			public void detach()
			{
				
			}

			public Iterator iterator(int first, int count)
			{
				return leagueManager.getLeagues().subList(first, count+first).iterator();
			}

			

			public int size()
			{
				return leagueManager.getLeagues().size();
			}

			public IModel<League> model(final League object)
			{
				return new CompoundPropertyModel(new LoadableDetachableModel()
				{

					@Override
					protected Object load()
					{
						return leagueManager.getLeague( object.getId());
					}
					
				});
			}
			
			
		};
		
		DataView<League> leagueDataView = new DataView<League>("leagueDataView", leagueDataProvider)
		{
			@Override
			protected void populateItem(final Item<League> item)
			{
				item.add(new Link("select")
				{

					@Override
					public void onClick()
					{
						setResponsePage(new AdminLeaguePage(item.getModel()));
					}
					
				});
	            item.add(new Label("leagueName"));
	            item.add(new Label("admin.username"));
	            item.add(new Label("season.leagueType.leagueTypeDisplay"));
	            item.add(new Label("maxSize"));
	            item.add(new Label("active"));
	            item.add(new Label("free"));
	            item.add(new Label("season.leagueType.startDisplay"));
	            item.add(new Label("season.leagueType.endDisplay"));
	            item.add(new Label("numberPeople", String.valueOf(leagueManager.getNumberOfPlayersInLeague(item.getModelObject()))));
	            item.add(new Label("spreads"));
	            
	            item.add(new SimpleAttributeModifier("class", (item.getIndex() % 2 != 0) ? "even" : "odd"));
			}
		};
		add(leagueDataView);
		
		//SEASONS
		IDataProvider seasonDataProvider = new IDataProvider()
		{

			public void detach()
			{
				
			}

			public Iterator iterator(int first, int count)
			{
				return leagueManager.getSeasons().subList(first, count+first).iterator();
			}

			public IModel model(final Object object)
			{
				return new CompoundPropertyModel(new LoadableDetachableModel()
				{

					@Override
					protected Object load()
					{
						return leagueManager.getSeason( ((Season)object).getId());
					}
					
				});
			}

			public int size()
			{
				return leagueManager.getSeasons().size();
			}
			
		};
		
		DataView seasonDataView = new DataView("seasonDataView", seasonDataProvider)
		{
			@Override
			protected void populateItem(final Item item)
			{
				item.add(new Label("startYear"));
				item.add(new Label("endYear"));
				item.add(new Label("leagueType.leagueTypeDisplay"));
				
				Link edit = new Link("editSeason")
				{

					@Override
					public void onClick()
					{
						setResponsePage(new CreateSeasonPage(item.getModel()));
					}
					
				};
				item.add(edit);
			}
		};
		add(seasonDataView);
	}

}

