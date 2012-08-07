package info.makeyourpicks.web.layout;

import info.makeyourpicks.model.Game;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

public class ScorePanel extends AbstractBasePanel {

	public ScorePanel(String id, IModel gameModel)
	{
		super(id, gameModel);
		
		Game game = (Game)gameModel.getObject();
		add(new Label("fav.shortName", game.getFav().getShortName()).add(new SimpleAttributeModifier("class", "headerteamname")));
		add(new Label("dog.shortName", game.getDog().getShortName()).add(new SimpleAttributeModifier("class", "headerteamname")));
		add(new Label("favScore", String.valueOf(game.getFavScore())).add(new SimpleAttributeModifier("class", "headerscore")));
		add(new Label("dogScore", String.valueOf(game.getDogScore())).add(new SimpleAttributeModifier("class", "headerscore")));
		add(new Label("spread", String.valueOf(game.getSpread())).add(new SimpleAttributeModifier("class", "headerspread")));
	}
}
