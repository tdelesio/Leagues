package info.makeyourpicks.web.admin.pages;

import info.makeyourpicks.model.Game;
import info.makeyourpicks.service.GameManager;

import org.apache.wicket.extensions.yui.calendar.DateTimeField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author PRC9041
 */
public class EnterScoresPage extends AbstractAdminBasePage
{	
	@SpringBean(name="gameManager")
	private GameManager gameManager;
	
	public EnterScoresPage(IModel gameModel) {
		super();

		add(new FeedbackPanel("feedbackpanel"));

		Form form = new Form("updateGameForm", gameModel)
		{

			@Override
			protected void onSubmit() {
				Game game = (Game)getModelObject();
				gameManager.updateGame(game);
				
				IModel weekModel = new CompoundPropertyModel(new LoadableDetachableModel()
				{

					@Override
					protected Object load()
					{
						Game game = (Game)getModelObject();
						return game.getWeek();
					}
					//new DetachableWeekInfo(game.getWeek()));					
				});
						
				setResponsePage(new UpdateWeekPage(weekModel));
			}
			
		};

		form.add(new Label("week.weekNumber"));
		form.add(new RequiredTextField("favScore", Integer.class));
		form.add(new RequiredTextField("dogScore", Integer.class));
		form.add(new Label("fav.fullTeamName"));
		form.add(new Label("dog.fullTeamName"));
		form.add(new RequiredTextField("spread", Double.class));
		form.add(new CheckBox("favHome"));
		form.add(new DateTimeField("gameStart"));
		
		form.add(new Button("submit"));
		add(form);
		
	}
	

}
