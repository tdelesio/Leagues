package info.makeyourpicks.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.delesio.model.AbstractSequenceModel;

public class Week extends AbstractPersistantObject implements Serializable, Comparable<Week>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3026006278317084242L;
	private int weekNumber;
	
	private Date weekStart;
	private Season season;
//	private LeagueType leagueType = new LeagueType();
//	private Set games =new HashSet();
//	private Set picks = new HashSet();
	
	//private DateTime dateTime;
	private int month;
	private int day;
	private int year;
	private int hour;
	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MM/dd/yyyy @ HH:mm z");
	
	
	public Week()
	{
	}
	
	public Week(long weekId)
	{
		super.id = weekId;
	}
	
	public String getCurrentTime()
	{
		return "Today is: "+simpleDateFormat.format(new Date(System.currentTimeMillis()));
	}
	public String getDisplay()
	{
		if (weekStart==null)
		{
			return "No start time set.";
		}
		else
		{
			java.util.Date date = new java.util.Date(weekStart.getTime());
			
			return "Week "+weekNumber+" Picks are due:\n"+ simpleDateFormat.format(date);
		}
	}
	


	public String getWeekLabel()
	{
		return "Week "+weekNumber;
	}
	

	public Date getWeekStart() {
		return weekStart;
	}

	public void setWeekStart(Date weekStart) {
		this.weekStart = weekStart;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public String getWeekStartTimeDisplay()
	{
		return simpleDateFormat.format(new Date(weekStart.getTime()));
	}
	
	public void buildTimeStamp()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		//calendar.set(Calendar.MINUTE, 30);
	
		java.util.Date utilDate = calendar.getTime();
		weekStart = new Timestamp(utilDate.getTime());
	}

	
	public boolean hasCurrentWeekStarted()
	{
		if (weekStart!=null)
		{
			if (System.currentTimeMillis() > weekStart.getTime())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	public int getWeekNumber() {
		return weekNumber;
	}
	public void setWeekNumber(int selectedWeek) {
		this.weekNumber = selectedWeek;
	}
//	public LeagueType getLeagueType() {
//		return leagueType;
//	}
//	public void setLeagueType(LeagueType leagueType) {
//		this.leagueType = leagueType;
//	}

	public Season getSeason()
	{
		return season;
	}

	public void setSeason(Season season)
	{
		this.season = season;
	}

	public int compareTo(Week o) {
		if (weekNumber == o.getWeekNumber())
			return 0;
		else if (weekNumber < o.getWeekNumber())
			return -1;
		else
			return 1;
	}
	
	

}
