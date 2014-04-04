package info.makeyourpicks.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.delesio.model.AbstractSequenceModel;

public class Game extends AbstractPersistantObject {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4285672690455048961L;
	private Week week = new Week();
	private double spread = 0.5;
	private int favScore=0;
	private int dogScore=0;
	private boolean favHome=true;
	private Team fav = new Team();
	private Team dog = new Team();
	private Date gameStart=new Date();
	
	private String gameStartDate;
	private String gameStartTime;
	
	public Game()
	{
	}
	
	public Game(long gameId)
	{
		super.id = gameId;
	}
		
	public Team getWinner(boolean useSpeads)
	{
		Team value=null;
		double s=0;
		if (useSpeads)
		{
			s=spread;
		}
		if (dogScore==0 && favScore==0)
		{
			value =  null;
		}
		else if (dogScore+s>favScore)
		{
			value =  dog;
		}
		else if (dogScore+s<favScore)
		{
			value =  fav;
		}
		return value;
	}
	
	public boolean hasScoresEntered()
	{
		if (favScore==0&&dogScore==0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	public String getEnteredScoreDisplay()
	{
		return "";
	}

	public double getSpread() {
		return spread;
	}

	public void setSpread(double spread) {
		
		this.spread = spread;
	}


	public Week getWeek() {
		return week;
	}

	public void setWeek(Week week) {
		this.week = week;
	}

	
	public Team getDog() {
		return dog;
	}

	public void setDog(Team dog) {
		this.dog = dog;
	}

	public Team getFav() {
		return fav;
	}

	public void setFav(Team fav) {
		this.fav = fav;
	}


	public String getGameDisplay()
	{
		return fav.getFullTeamName()+" - "+dog.getFullTeamName();
	}

	public boolean isFavHome() {
		return favHome;
	}

	public void setFavHome(boolean favHome) {
		this.favHome = favHome;
	}

	public int getFavScore() {
		return favScore;
	}

	public void setFavScore(int favScore) {
		this.favScore = favScore;
	}

	public int getDogScore() {
		return dogScore;
	}

	public void setDogScore(int dogScore) {
		this.dogScore = dogScore;
	}

	public Date getGameStart() {
		return gameStart;
	}

	public void setGameStart(Date gameStart) {
		this.gameStart = gameStart;
	}
	
	public boolean isGameStarted()
	{
		return hasGameStarted();
	}
	public void setGameStarted()
	{
		
	}
	public boolean hasGameStarted()
	{
		if (gameStart==null)
		{
			return week.hasCurrentWeekStarted();
		}
		else
		{
			if (System.currentTimeMillis() > gameStart.getTime())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}

	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MM/dd/yyyy hh:mm z");
	private SimpleDateFormat multiLineDateFormat = new SimpleDateFormat("EEE MM/dd/yyyy \nhh:mm z");
	public String getGameStartDisplay()
	{
		return simpleDateFormat.format(gameStart);
	}
	
	public String getGameStartMultiLineDisplay()
	{
		return multiLineDateFormat.format(gameStart);
	}
	
	public String getSpreadPlusFootnote()
	{
		return getSpread()+"^";
	}

	public String getGameStartDate() {
		return gameStartDate;
	}

	public void setGameStartDate(String gameStartDate) {
		this.gameStartDate = gameStartDate;
	}

	public String getGameStartTime() {
		return gameStartTime;
	}

	public void setGameStartTime(String gameStartTime) {
		this.gameStartTime = gameStartTime;
	}
	
	

}
