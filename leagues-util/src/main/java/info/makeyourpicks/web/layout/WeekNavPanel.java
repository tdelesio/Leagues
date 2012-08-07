package info.makeyourpicks.web.layout;

import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.Week;

import java.lang.reflect.Constructor;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;

public class WeekNavPanel extends AbstractBasePanel {

	public WeekNavPanel(String id, IModel weekModel, Class<Page> pageClass)
	{
		super(id);

//		add(new WeekSelectionForm("weekSelectionForm", new CompoundPropertyModel(weekModel),pageClass));
		add(new WeekSelectionForm("weekSelectionForm", weekModel,pageClass));
	}
	
	private class WeekSelectionForm extends Form
	{
	
		public WeekSelectionForm(String id, final IModel weekModel, final Class page)
		{
			super(id);
			//Game game = (Game)model.getObject();
			
			Season season = ((Week)weekModel.getObject()).getSeason();
			
			IModel gameModel = new CompoundPropertyModel(new LoadableDetachableModel()
			{

				@Override
				protected Object load()
				{
					Game game = new Game();
					game.setWeek((Week)weekModel.getObject());
					return game;
				}
				
			});
			setModel(gameModel);
			DropDownChoice ddChoice = new DropDownChoice("week", gameManager.getWeeksBySeason(season), new ChoiceRenderer("weekLabel", "weekNumber"));
			ddChoice.add(new AjaxFormComponentUpdatingBehavior("onChange")
			{

				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					Game game = (Game)getModelObject();
					Class[] parmType = new Class[1];
					parmType[0] = Integer.TYPE;
					try
					{
						Constructor ct = page.getConstructor(parmType);
						setResponsePage((Page)ct.newInstance(new Object[]{game.getWeek().getWeekNumber()}));
					}
					catch (Exception exception)
					{
						exception.printStackTrace();
					}
				}
				
			});
			add(ddChoice);
		}
	}
}
