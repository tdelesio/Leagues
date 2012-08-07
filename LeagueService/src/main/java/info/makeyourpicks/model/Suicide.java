package info.makeyourpicks.model;

import java.io.Serializable;

import com.delesio.model.AbstractPersistantObject;
import com.delesio.model.IPersistable;

@Deprecated
public class Suicide extends AbstractPersistantObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5377346725790957831L;
	private String name="";
	private boolean win=false;
	private int week;
	private String leagueName;
	private Team team = new Team();
	
	
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team teamInfo) {
		this.team = teamInfo;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	

	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

	@Override
	public IPersistable createTestObject() {
		Suicide suicide = new Suicide();
		suicide.setLeagueName("testleaue");
		suicide.setName("test");
		suicide.setTeam((Team)new Team().createTestObject());
		suicide.setWeek(1);
		suicide.setWin(false);
		return suicide;
	}


	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	
	

}
