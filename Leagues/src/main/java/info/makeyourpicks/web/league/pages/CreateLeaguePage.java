package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.model.PickemTieBreakerEnum;
import info.makeyourpicks.model.League;
import info.makeyourpicks.model.YesNoEnum;
import info.makeyourpicks.web.authentication.LoginPage;
import info.makeyourpicks.web.layout.pages.LeagueNonMemberWebPage;
import info.makeyourpicks.web.league.panels.CreateLeagueStep1Panel;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

/**
 * @author PRC9041
 */
public class CreateLeaguePage extends LeagueNonMemberWebPage {

	public static final String WICKET_ID="createLeague";
	
	public String getWicketID()
	{
		return WICKET_ID;
	}
	
	public CreateLeaguePage() 
	{
		League league = new League();
//		league.setDoubleEnabled(false);
		league.setBankerEnum(YesNoEnum.No);
		league.setDoubleEnabledEnum(YesNoEnum.YES);
		league.setSpreadsEnum(YesNoEnum.YES);
		league.setDoubleTypeEnum(PickemTieBreakerEnum.SPLIT);
		league.setFirstPlacePercent(65);
		league.setSecondPlacePercent(25);
		league.setThirdPlacePercent(10);
		league.setWeeklyFee(5);
		league.setEntryFee(35);
		
		IModel<League> leagueModel = new CompoundPropertyModel<League>(league);
		
		init(leagueModel);
	}
	
	public CreateLeaguePage(IModel<League> leagueModel)
	{
		init(leagueModel);
	}
	
	private void init(IModel<League> leagueModel)
	{
		CreateLeagueStep1Panel createLeagueStep1Panel = new CreateLeagueStep1Panel("step1Panel", leagueModel)
		{

			@Override
			public Page getStep2Page(IModel<League> leagueModel)
			{
				return new CreateLeagueStep2Page(leagueModel);
			}
			
		};
        add(createLeagueStep1Panel);
   
        
//        WebMarkupContainer loginContainer = new WebMarkupContainer("loginContainer");
//        loginContainer.setOutputMarkupId(true);
//        add(loginContainer);
//        
//        Link login = new Link(LoginPage.WICKET_ID)
//        {
//
//			@Override
//			public void onClick()
//			{
//				setResponsePage(LoginPage.class);
//			}
//        	
//        };
//        loginContainer.add(login);
        
	}
	
	@Override
	protected Label getPageTitle() {
		return new Label("pageTitle", "MakeYourPicks - Create your own league.");
	}
	
}

