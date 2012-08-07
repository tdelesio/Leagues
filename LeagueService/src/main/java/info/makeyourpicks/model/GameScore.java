package info.makeyourpicks.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameScore 
{
	private String homeTeamName;
    private String visitorTeamName;
    private int homeTeamScore;
    private int vistitorTeamScore;
    private Date gameStart;

    public GameScore()
    {
        super();
    }
    
    

    public String getHomeTeamName()
    {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName)
    {
        this.homeTeamName = homeTeamName;
    }

    public String getVisitorTeamName()
    {
        return visitorTeamName;
    }

    public void setVisitorTeamName(String visitorTeamName)
    {
        this.visitorTeamName = visitorTeamName;
    }

    public int getHomeTeamScore()
    {
        return homeTeamScore;
    }

    public void setHomeTeamScore(int homeTeamScore)
    {
        this.homeTeamScore = homeTeamScore;
    }

    public int getVistitorTeamScore()
    {
        return vistitorTeamScore;
    }

    public void setVistitorTeamScore(int vistitorTeamScore)
    {
        this.vistitorTeamScore = vistitorTeamScore;
    }

    public String getKey()
    {
        return (new StringBuilder(String.valueOf(homeTeamName))).append("_").append(visitorTeamName).toString();
    }

    public Date getGameStart()
    {
        return gameStart;
    }

    public void setGameStart(String gameStart)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy hh:mm:ss a");
        try
        {
            this.gameStart = simpleDateFormat.parse(gameStart);
        }
        catch(ParseException exception)
        {
            exception.printStackTrace();
        }
    }
}
