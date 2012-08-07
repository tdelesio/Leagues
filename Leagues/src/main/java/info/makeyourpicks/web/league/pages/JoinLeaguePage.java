package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.model.League;
import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.web.PageEnum;
import info.makeyourpicks.web.layout.pages.LeagueNonMemberWebPage;
import info.makeyourpicks.web.league.panels.JoinLeaguePanel;

import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.delesio.web.model.SiteMapUrl;

/**
 * @author PRC9041
 */

@AuthorizeInstantiation({"user","admin"})
public class JoinLeaguePage extends LeagueNonMemberWebPage {

	@SpringBean(name="leagueManager")
	protected LeagueManager leagueManager;
	
	@SpringBean(name="leagueTypes")
	protected Map<String, Map<String, SiteMapUrl<? extends Page>>> mountableUrls;
	
	public static final String WICKET_ID = "joinLeague";
//	private FeedbackPanel feedbackPanel;
	
	public JoinLeaguePage()
	{
		JoinLeaguePanel joinLeaguePanel = new JoinLeaguePanel("joinLeaguePanel")
		{

			@Override
			public Class<? extends Page> getOnSuccessJoinPage(IModel<League> leagueModel)
			{
//				return LeagueWebConstants.homePageMap.get(leagueModel.getObject().getSeason().getLeagueType().getTypeOfLeague());
//				return ((LeagueWebApplication)getApplication()).getLeagueHomePage(leagueModel.getObject().getSeason().getLeagueType().getTypeOfLeague());
//				return getHomePageForLeague(leagueModel.getObject().getSeason().getLeagueType().getTypeOfLeague());
				return mountableUrls.get(leagueModel.getObject().getSeason().getLeagueType().getTypeOfLeague()).get(PageEnum.HOME.getValue()).getUrlClass();
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
	protected Label getPageTitle() {
		return new Label("pageTitle", "MakeYourPicks - Join a friends league or a public league.");
	}
	

}

