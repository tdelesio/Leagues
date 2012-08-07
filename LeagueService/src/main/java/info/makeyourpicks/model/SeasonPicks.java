package info.makeyourpicks.model;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class SeasonPicks implements Comparable<SeasonPicks>, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1361672345850459959L;
	private String name;
	private List<Integer> wins = new ArrayList<Integer>(17);
	private int totalWins=0;
	private int place;
	private int ties;
	private double placementWinings;
	private double weekWinnings=0;
	private double amountPaid=0;
	private double suicideWinnings;
	
	public void addWeekWinning(double _weekWinnings)
	{
		weekWinnings+=_weekWinnings;
	}
	
	public double getTotal()
	{
		return (placementWinings+weekWinnings+suicideWinnings) - (amountPaid);
	}
	
	public double getPlacementWinings() {
		return placementWinings;
	}

	public void setPlacementWinings(double placementWinings) {
		this.placementWinings = placementWinings;
	}

	public int getTies() {
		return ties;
	}

	public void setTies(int ties) {
		this.ties = ties;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Integer> getWins() {
		return wins;
	}

	public void setWins(List<Integer> wins) {
		this.wins = wins;
	}
	
	public void addWin(int numberWins)
	{
		wins.add(new Integer(numberWins));
		totalWins+=numberWins;
	}

	public int getTotalWins() {
		return totalWins;
	}

	public void setTotalWins(int totalWins) {
		this.totalWins = totalWins;
	}

	public int compareTo(SeasonPicks o) {
		return (new Integer(o.getTotalWins()).compareTo(new Integer(getTotalWins())));
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		SeasonPicks seasonPicksInfo = new SeasonPicks();
		seasonPicksInfo.setName(this.getName());
		seasonPicksInfo.setTotalWins(this.getTotalWins());
		seasonPicksInfo.setWins(this.getWins());
		
		return seasonPicksInfo;
	}

	public int getPlace() {
		return place;
	}

	public void setPlace(int place) {
		this.place = place;
	}

	public double getWeekWinnings() {
		return weekWinnings;
	}

	public void setWeekWinnings(double weekWinnings) {
		this.weekWinnings = weekWinnings;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public double getSuicideWinnings() {
		return suicideWinnings;
	}

	public void setSuicideWinnings(double suicideWinnings) {
		this.suicideWinnings = suicideWinnings;
	}

	
}
