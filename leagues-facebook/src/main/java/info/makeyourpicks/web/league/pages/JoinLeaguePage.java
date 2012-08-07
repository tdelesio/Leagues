package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.model.League;
import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.web.LeagueFacebookApplication;
import info.makeyourpicks.web.layout.AbstractLeagueSecureFacebookPage;
import info.makeyourpicks.web.league.panels.JoinLeaguePanel;

import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author PRC9041
 */
public class JoinLeaguePage extends AbstractLeagueSecureFacebookPage {

	@SpringBean(name="leagueManager")
	protected LeagueManager leagueManager;
	
	public static final String WICKET_ID = "joinLeague";
//	private FeedbackPanel feedbackPanel;
	
	public JoinLeaguePage()
	{
		JoinLeaguePanel joinLeaguePanel = new JoinLeaguePanel("joinLeaguePanel")
		{

			@Override
			public Class<? extends Page> getOnSuccessJoinPage(IModel<League> leagueModel)
			{
				return ((LeagueFacebookApplication)getApplication()).homePageMap.get(leagueModel.getObject().getSeason().getLeagueType().getTypeOfLeague());
			}

			@Override
			public Page getRulesPage(IModel<League> leagueModel)
			{
				return new RulesPage(leagueModel);
			}
			
		};
		add(joinLeaguePanel);
		
	}
	
	

	
	
	
	@Override
	public String getWicketID() {
		return WICKET_ID;
	}
}

