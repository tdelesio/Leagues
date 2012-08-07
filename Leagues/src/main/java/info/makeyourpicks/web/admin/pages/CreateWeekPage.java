package info.makeyourpicks.web.admin.pages;

import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.service.GameManager;
import info.makeyourpicks.web.forms.WeekForm;

import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class CreateWeekPage extends AbstractAdminBasePage {

	@SpringBean(name = "gameManager")
	private GameManager gameManager;
	
	
	public CreateWeekPage(IModel seasonModel)
	{
		super();
//		Week week = (Week)weekModel.getObject();
//		final LeagueType leagueType = (LeagueType)leagueTypeModel.getObject();
		final Season season = (Season)seasonModel.getObject();
		IModel weekModel = new CompoundPropertyModel(new LoadableDetachableModel()
		{

			@Override
			protected Object load()
			{
				Week week = new Week();
				int currentWeek = gameManager.getCurrentWeek(season);
				week.setSeason(season);
				week.setWeekNumber(currentWeek+1);
				return week;
			}
			
		});
		
		
		add(new WeekForm("weekForm", weekModel)
		{
			@Override
			public void onSubmit() {
		
				Week week = (Week) getModelObject();
				gameManager.insertWeek(week);
		
				Week newWeek = new Week();
//				newWeek.setLeagueType(newWeek.getLeagueType());
				newWeek.setSeason(newWeek.getSeason());
				setModel(new CompoundPropertyModel(newWeek));
		
				setResponsePage(SetupWeekPage.class);
			}
		});
	}
	
}
