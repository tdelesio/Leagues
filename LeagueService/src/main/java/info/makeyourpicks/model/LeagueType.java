package info.makeyourpicks.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Proxy;

import com.delesio.model.AbstractSequenceModel;

@Entity
@Table(name="leaguetypes")
@Proxy(lazy=false)
@XmlRootElement
public class LeagueType extends AbstractPersistantObject {
	@Column(nullable=false)
	private String typeOfLeague="";
	
	@Column(nullable=false)
	private String leagueTypeDisplay;
	
	private String parentTypeOfLeague;
	
	@Transient
	private Date start = new Date();
	
	@Transient
	private Date end = new Date();
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MM/dd/yyyy @ HH:mm z");
//	private Set teams=new HashSet();
//	private Set leagues=new HashSet();
//	private Map weeks = new HashMap();
	/**
	 * Copy Constructor
	 *
	 * @param leagueType a <code>LeagueType</code> object
	 */
	public LeagueType(LeagueType leagueType) 
	{
	    this.typeOfLeague = leagueType.typeOfLeague;
	    this.leagueTypeDisplay = leagueType.leagueTypeDisplay;
	    this.start = new Date(leagueType.start.getTime());
	    this.end = new Date(leagueType.end.getTime());
	    this.simpleDateFormat = leagueType.simpleDateFormat;
//	    this.teams = new HashSet(leagueType.teams);
//	    this.leagues = new HashSet(leagueType.leagues);
//	    this.weeks = new HashMap(leagueType.weeks);
	}



	public LeagueType()
	{
		
	}
	
	public LeagueType(long leagueId)
	{
		super.id = leagueId;
	}
	
	
	public LeagueType(String leagueType)
	{
		setTypeOfLeague(leagueType);
	}
	
	
	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}
	
	
	
	public String getLeagueTypeDisplay() {
		return leagueTypeDisplay;
	}

	public String getStartDisplay()
	{
		return simpleDateFormat.format(start);
	}
	
	public String getEndDisplay()
	{
		return simpleDateFormat.format(end);
	}
	
	public void setTypeOfLeague(String leagueType) {
		this.typeOfLeague = leagueType;
	}
	public void setLeagueTypeDisplay(String leagueTypeDisplay) {
		this.leagueTypeDisplay = leagueTypeDisplay;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public void setEnd(Date end) {
		this.end = end;
	}

	public String getTypeOfLeague() {
		return typeOfLeague;
	}

	public String getDescriptionStatus()
	{
		if (typeOfLeague==null||typeOfLeague.equalsIgnoreCase(""))
		{
			return "default";
		}
		else
		{
			return typeOfLeague;
		}
	}

	public String getParentTypeOfLeague() {
		return parentTypeOfLeague;
	}



	public void setParentTypeOfLeague(String parentTypeOfLeague) {
		this.parentTypeOfLeague = parentTypeOfLeague;
	}
	
	
	public boolean hasParent()
	{
		if (parentTypeOfLeague==null||parentTypeOfLeague.equals(""))
		{
			return false;
		}
		else
		{
			return true;
		}
	}

}
