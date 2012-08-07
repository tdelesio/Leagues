package info.makeyourpicks.service.handlers;

import info.makeyourpicks.model.GameScore;

import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GamblersPalaceScoresHandler extends DefaultHandler
{

	private Map games;
    private GameScore game;
    private boolean isNFLGame;
    private StringBuffer contentBuffer;
//    final GameManagerHibernate this$0;

    public GamblersPalaceScoresHandler(Map games)
    {
        super();
        isNFLGame = false;
        contentBuffer = new StringBuffer();
        this.games = games;
    }
    
    public void characters(char ch[], int start, int length)
        throws SAXException
    {
        contentBuffer.append(ch, start, length);
    }

    public void endElement(String uri, String localName, String name)
        throws SAXException
    {
        String value = contentBuffer.toString();
        contentBuffer = new StringBuffer();
        if(isNFLGame)
            if(name.equals("Game") && game != null && games.get(game.getKey()) == null)
            {
                games.put(game.getKey(), game);
                isNFLGame = false;
            } else
            if("VisitorTeam".equals(name))
            {
//                if(GameManagerHibernate.access$0(GameManagerHibernate.this, value))
            	if (value.equals(game.getVisitorTeamName()))
                    game.setVisitorTeamName(value);
                else
                    isNFLGame = false;
            } else
            if("HomeTeam".equals(name))
            {
//                if(GameManagerHibernate.access$0(GameManagerHibernate.this, value))
                if (value.equals(game.getHomeTeamName()))	
                    game.setHomeTeamName(value);
                else
                    isNFLGame = false;
            } else
            if("VisitorScore".equals(name))
                game.setVistitorTeamScore(Integer.parseInt(value));
            else
            if("HomeScore".equals(name))
                game.setHomeTeamScore(Integer.parseInt(value));
            else
            if("GameDate".equals(name))
                game.setGameStart(value);
    }

    public void startElement(String uri, String localName, String name, Attributes attributes)
        throws SAXException
    {
        if(name.equals("Game") && "1".equals(attributes.getValue("IdLeague")))
        {
            isNFLGame = true;
            game = new GameScore();
        }
    }

    
}
