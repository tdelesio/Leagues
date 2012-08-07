package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.model.League;
import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.web.layout.pages.LeagueMemberWebPage;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author PRC9041
 */
public class InviteFriendsPage extends LeagueMemberWebPage {

	@SpringBean(name="leagueManager")
	protected LeagueManager leagueManager;
	
	public static final String WICKET_ID = "addPlayersToLeague";
	public InviteFriendsPage()
	{
		super();
		
		add(new FeedbackPanel("feedbackpanel"));
		IModel activeLeagueModel = new CompoundPropertyModel(new LoadableDetachableModel()
		{

			@Override
			protected Object load()
			{
				return getActiveLeague();
			}
			
		});
		add(new InviteFriendsForm("inviteForm", activeLeagueModel));
	}
	
	private class InviteFriendsForm extends Form
	{
		public InviteFriendsForm(String id, IModel model)
		{
			super(id, model);
			
			TextArea players = new TextArea("playersToAddToLeague");
			add(players);
			add(new Button("submit"));
		}
		
		@Override
		protected void onSubmit() {
			getParent().get("inviteForm").setVisible(false);
			League league = (League)getModelObject();
			try
			{
				leagueManager.sendInviteEmail(league.getInvitedPlayersEmailAddress(), league);
			}
			catch (Exception exception)
			{
				exception.printStackTrace();
			}
        	
			info("Players have been invited.");
		}
	}

	
}

