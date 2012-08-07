package info.makeyourpicks.web.forms;

import org.apache.wicket.extensions.yui.calendar.DateTimeField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.IModel;

public abstract class WeekForm extends Form {

	public WeekForm(String id, IModel model) {
		super(id, model);

		add(new Label("season.displaySeason"));
		add(new RequiredTextField("weekNumber", Integer.class));
		add(new DateTimeField("weekStart"));
		add(new Button("add"));

	}

	public abstract void onSubmit();
//	@Override
//	protected void onSubmit() {
//		super.onSubmit();
//
//		Week week = (Week) getModelObject();
//		gameManager.insertWeek(week);
//
//		Week newWeek = new Week();
//		newWeek.setLeagueType(newWeek.getLeagueType());
//		setModel(new CompoundPropertyModel(newWeek));
//
//	}

}
