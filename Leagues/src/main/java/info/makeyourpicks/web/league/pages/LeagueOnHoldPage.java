package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.web.layout.pages.LeagueMemberWebPage;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

public class LeagueOnHoldPage extends LeagueMemberWebPage
{

	public static final String WICKET_ID = "leagueOnHoldPage";
	
	public LeagueOnHoldPage()
	{
		
		IModel model = new CompoundPropertyModel(new LoadableDetachableModel()
		{

			@Override
			protected Object load()
			{
				League activeLeage = getActiveLeague();
				Player player = activeLeage.getAdmin();
				return player;
			}
			
		});
		
		setDefaultModel(model);
		
		add(new Label("email"));
		add(new Label("username"));
		add(new Label("firstName"));
		add(new Label("lastName"));
		
		add(new PageLink("contact", ContactUsPage.class));
		add(new PageLink("faq", FAQPage.class));
	}

}
