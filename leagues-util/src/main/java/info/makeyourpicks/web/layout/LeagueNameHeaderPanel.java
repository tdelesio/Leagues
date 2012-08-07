package info.makeyourpicks.web.layout;

import org.apache.wicket.markup.html.basic.Label;

public class LeagueNameHeaderPanel extends AbstractBasePanel {

	public LeagueNameHeaderPanel(String id)
	{
		super(id);
		add(new Label("activeLeagueName", getActiveLeague().getLeagueName()));
	}
	
}