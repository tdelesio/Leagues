package info.makeyourpicks.service;

import java.util.List;
import java.util.Set;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Week;

public interface ILeaguesEmailService
{
	public static final int PASSWORD_RECOVERY = 1;
	public static final int LEAGUE_INVITE = 2;
	public static final int WEEK_SETUP = 3;
	public boolean sendRecoverEmail(final String address, Player player);
	public boolean sendInviteEmail(final String emailAddress, League league);
	public boolean sendWeekSetupEmail(Set<Player> players, Week week);
}
