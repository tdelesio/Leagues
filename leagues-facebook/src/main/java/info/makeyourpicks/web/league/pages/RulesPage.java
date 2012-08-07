package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.web.LeagueFacebookApplication;
import info.makeyourpicks.web.layout.AbstractLeagueSecureFacebookPage;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class RulesPage extends AbstractLeagueSecureFacebookPage
{
	@SpringBean(name="leagueManager")
	protected LeagueManager leagueManager;
	

	public static final String WICKET_ID = "rulesPage";
	@Override
	public String getWicketID()
	{
		return WICKET_ID;
	}

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
				
				setResponsePage(LeagueFacebookApplication.homePageMap.get(league.getSeason().getLeagueType().getTypeOfLeague()));
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
