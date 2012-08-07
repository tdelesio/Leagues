package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.web.LeagueWebApplication;
import info.makeyourpicks.web.layout.pages.LeagueMemberWebPage;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class RulesPage extends LeagueMemberWebPage
{
	@SpringBean(name="leagueManager")
	protected LeagueManager leagueManager;
	

	public static final String WICKET_ID = "rulesPage";
	

	public RulesPage(IModel model)
	{
		Form form = new Form("form", model)
		{

			@Override
			protected void onSubmit()
			{
				League league = (League)getModelObject();
				Player player = getPlayer();
				leagueManager.addPlayerToLeague(league, player);
				setActiveLeague(league);
		
				info("You have successfully join the league "+league.getLeagueName()+".");
				
//				setResponsePage(LeagueWebConstants.homePageMap.get(league.getSeason().getLeagueType().getTypeOfLeague()));
//				setResponsePage(((LeagueWebApplication)getApplication()).getLeagueHomePage(league.getSeason().getLeagueType().getTypeOfLeague()));
				setResponsePage(getHomePageForLeague(league.getSeason().getLeagueType().getTypeOfLeague()));
			}
			
		};
		form.add(new Button("submit"));
		add(form);
	}
	
	public RulesPage()
	{
		Form form = new Form("form", new Model(new League()));
		form.setVisible(false);
		
		form.add(new Button("submit"));
		add(form);
	}
}
