package info.makeyourpicks.model;

import com.delesio.model.AbstractSequenceModel;


public class PlayerLeague extends AbstractPersistantObject {

	private League league;
	private Player player;
	private int sortOrder=0;
	private boolean displayInNav=true;


	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getSortOrder()
	{ 
		return sortOrder;
	}

	public void setSortOrder(int sortOrder)
	{
		this.sortOrder = sortOrder;
	}

	public boolean isDisplayInNav()
	{
		return displayInNav;
	}

	public void setDisplayInNav(boolean displayInNav)
	{
		this.displayInNav = displayInNav;
	}

	public void changeDisplayValue()
	{
		if (displayInNav)
		{
			displayInNav=false;
		}
		else
		{
			displayInNav=true;
		}
	}
	
	public String getDisplayInNavText()
	{
		if (displayInNav)
			return "Yes";
		else
			return "No";
	}
}
