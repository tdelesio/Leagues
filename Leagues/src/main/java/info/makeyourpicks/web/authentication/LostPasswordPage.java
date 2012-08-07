package info.makeyourpicks.web.authentication;

import info.makeyourpicks.model.Player;
import info.makeyourpicks.service.PlayerManager;
import info.makeyourpicks.web.layout.pages.LeagueNonMemberWebPage;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class LostPasswordPage extends LeagueNonMemberWebPage {

	@SpringBean(name="playerManager")
	protected PlayerManager playerManager;
	
	public static final String WICKET_ID = "lostPasswordPage";
	public LostPasswordPage()
	{
		super();
		
		add(new FeedbackPanel("feedbackpanel"));
		Form form = new Form("lostPasswordForm", new CompoundPropertyModel(new Player()))
		{
			@Override
			protected void onSubmit() {
				boolean success = playerManager.retrievePassword((Player)getModelObject());
				if (success)
				{
					info("An email has been sent to the account with the username and password.");
				}
				else
				{
					error("There is no record of that email address or username on file.  Please try again.");
				}
			}
		};
		
		form.add(new TextField("email"));
		form.add(new TextField("username"));
		form.add(new Button("submit"));
		
		add(form);
	}
	
	
	

}
