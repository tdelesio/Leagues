package info.makeyourpicks.web.admin.panels;

import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.Team;
import info.makeyourpicks.web.components.CssClassModifier;
import info.makeyourpicks.web.layout.AbstractBasePanel;

import java.util.Iterator;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 * @author PRC9041
 */
public class EnterTeamsPanel extends AbstractBasePanel {
	
	public EnterTeamsPanel(String id)
	{
		super(id);
	}
	
	public EnterTeamsPanel(String id, final IModel seasonModel)
	{
		super(id, seasonModel);
//		Team team = (Team)model.getObject();
		
//		RefreshingView dataView = new TeamsDataView("repeating", seasonModel);
//		add(dataView);
		
		IDataProvider dataProvider = new IDataProvider()
		{

			public Iterator iterator(int first, int count)
			{
				Season season = (Season)seasonModel.getObject();
				return teamManager.getTeamsByLeagueType(season.getLeagueType()).subList(first, count+first).iterator();
			}

			public IModel model(final Object object)
			{
				return new CompoundPropertyModel(new LoadableDetachableModel()
				{

					@Override
					protected Object load()
					{
						return teamManager.loadTeam(((Team)object).getId());
					}
					
				});
			}

			public int size()
			{
				Season season = (Season)seasonModel.getObject();
				return teamManager.getTeamsByLeagueType(season.getLeagueType()).size();
			}

			public void detach()
			{
				// TODO Auto-generated method stub
				
			}
			
		};
		
		DataView dataView = new DataView("repeating", dataProvider)
		{

			@Override
			protected void populateItem(Item item)
			{
				item.add(new Label("city"));
		        item.add(new Label("teamName"));
		        item.add(new Label("shortName"));
		        item.add(new Label("theme"));
		        
				item.add(new CssClassModifier(CssClassModifier.EvenOdd, item.getIndex() % 2 != 0));
			}
			
		};
		add(dataView);
		
		final IModel teamModel = new CompoundPropertyModel(new LoadableDetachableModel()
		{

			@Override
			protected Object load()
			{
//				LeagueType leagueType = (LeagueType)leagueTypeModel.getObject();
				Season season = (Season)seasonModel.getObject();
				LeagueType leagueType = season.getLeagueType();
				Team team = new Team();
				team.setLeagueType(leagueType);
				return team;
			}
			
		});
		add(new EnterTeamForm("enterTeamForm", teamModel));
	}
	
	private class EnterTeamForm extends Form
	{
	
		public EnterTeamForm(String id, IModel model)
		{
			super(id, model);

			add(new Label("leagueType.leagueTypeDisplay"));
			add(new RequiredTextField("city"));
			add(new RequiredTextField("teamName"));
			add(new TextField("shortName"));
			add(new TextField("theme"));
			add(new Button("add"));
			
		}

		@Override
		protected void onSubmit() {
			super.onSubmit();
			
			Team team = (Team)getModelObject();
			teamManager.insertTeam(team);
			
//			Team newTeam = new Team();
//			newTeam.setLeagueType(team.getLeagueType());
			getModel().detach();
//			setModel(new CompoundPropertyModel(newTeam));
			
		}
}
}
