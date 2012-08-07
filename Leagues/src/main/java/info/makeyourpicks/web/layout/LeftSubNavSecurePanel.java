package info.makeyourpicks.web.layout;

import info.makeyourpicks.LeagueTypesEnum;
import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.web.PageEnum;
import info.makeyourpicks.web.admin.pages.SetupWeekPage;
import info.makeyourpicks.web.dataprovider.ActivePlayerLeaguesDataProvider;
import info.makeyourpicks.web.football.pages.MakePicksPage;
import info.makeyourpicks.web.league.pages.CreateLeaguePage;
import info.makeyourpicks.web.league.pages.JoinLeaguePage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.odlabs.wiquery.ui.accordion.Accordion;

import com.delesio.web.model.SiteMapUrl;

public class LeftSubNavSecurePanel extends AbstractBasePanel {

//	public abstract String getLeagueType();
	
	@SpringBean(name="leagueTypes")
	protected Map<String, Map<String, SiteMapUrl<? extends Page>>> mountableUrls;
	
	public LeftSubNavSecurePanel(String id, final IModel<League> model)
	{
		super(id);
		
		final League league = model.getObject();
		final String leagueType = league.getSeason().getLeagueType().getTypeOfLeague();
		Link<String> makePicks = new Link<String>("makePicksUrl") {
			
			@Override
			public void onClick() {
				getWebSession().setActiveLeague(league);
				setResponsePage(mountableUrls.get(leagueType).get(PageEnum.MAKE_PICKS.getValue()).getUrlClass());
			}
		};
		
		Link<String> viewPicks = new Link<String>("viewPicksUrl") {
			
			@Override
			public void onClick() {
				getWebSession().setActiveLeague(league);
				setResponsePage(mountableUrls.get(leagueType).get(PageEnum.VIEW_PICKS.getValue()).getUrlClass());
			}
		};
		
		Link<String> winSummary = new Link<String>("winSummaryUrl") {
			
			@Override
			public void onClick() {
				getWebSession().setActiveLeague(league);
				setResponsePage(mountableUrls.get(leagueType).get(PageEnum.WIN_SUMMARY.getValue()).getUrlClass());
			}
		};
		
		Link<String> leagueDetails = new Link<String>("leagueDetailsUrl") {
			
			@Override
			public void onClick() {
				getWebSession().setActiveLeague(league);
				Map<String, SiteMapUrl<? extends Page>> map = mountableUrls.get(leagueType);
				setResponsePage(mountableUrls.get(leagueType).get(PageEnum.LEAGUE_DETAILS.getValue()).getUrlClass());
			}
		};
		
		Link<String> messageBoard = new Link<String>("MessageBoardUrl") {
			
			@Override
			public void onClick() {
				getWebSession().setActiveLeague(league);
				setResponsePage(mountableUrls.get(leagueType).get(PageEnum.MESSAGE_BOARD.getValue()).getUrlClass());
			}
		};
		  
		 add(makePicks);
		 add(viewPicks);
		 add(winSummary);
		 add(messageBoard);
		 add(leagueDetails);
	}
}
