package info.makeyourpicks.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Set;

import com.delesio.model.AbstractSequenceModel;

public class MessageBoard extends AbstractPersistantObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5507367895014738308L;
	private Player fromPlayer;
	private Timestamp posted;
	private String message="";
	private String style="";
	private String icon;
	private League leagueName;
	private boolean entireLeague=false;
	
	private Set toPlayers;
	private Player toPlayer;
	
	public MessageBoard()
	{
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getPosted() {
		return posted;
	}

	public void setPosted(Timestamp posted) {
		this.posted = posted;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}


	public Player getFromPlayer() {
		return fromPlayer;
	}

	public void setFromPlayer(Player fromPlayer) {
		this.fromPlayer = fromPlayer;
	}

	public League getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(League leagueName) {
		this.leagueName = leagueName;
	}

	public Set getToPlayers() {
		return toPlayers;
	}

	public void setToPlayers(Set toPlayers) {
		this.toPlayers = toPlayers;
	}

	public Player getToPlayer() {
		return toPlayer;
	}

	public void setToPlayer(Player toPlayer) {
		this.toPlayer = toPlayer;
	}

	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm z");
	public String getPostedDisplay()
	{
		return simpleDateFormat.format(posted);
	}

	public boolean isEntireLeague()
	{
		return entireLeague;
	}

	public void setEntireLeague(boolean entireLeague)
	{
		this.entireLeague = entireLeague;
	}


	

	

}
