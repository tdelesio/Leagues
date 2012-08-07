package info.makeyourpicks;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

public class PoolHelper {
	public static final int WEEK_COST = 5;
	public static final int ENTRY_COST = 35;
	public static final int MAINTENANCE_COST = 7;
	public static final int NUMBER_OF_WEEKS = 17;
	public static final double FIRST_PLACE_PERCENTAGE = 0.4;
	public static final double SECOND_PLACE_PERCENTAGE = 0.25;
	public static final double THIRD_PLACE_PERCENTAGE = 0.15;
	public static final double FOURTH_PLACE_PERCENTAGE = 0.10;
	public static final double SUICIDE_COST = 20;
	
	public static final int GREEN_CHECK = 1;
	public static final int RED_CHECK = 2;
	public static final int RED_X = 3;
	public static final int BLUE_CHECK = 4;
	public static IModel getImageModel(int requestImage)
	{
		if (requestImage==GREEN_CHECK)
		{
			return new AbstractReadOnlyModel()
			{
		
				@Override
				public Object getObject() {
					return "check.gif";
				}
				
			};
		}
		else if (requestImage==RED_CHECK)
		{
			return new AbstractReadOnlyModel()
			{

				@Override
				public Object getObject() {
					return "red-check.gif";
				}
				
			};
		}else if (requestImage==BLUE_CHECK)
		{
			return new AbstractReadOnlyModel()
			{

				@Override
				public Object getObject() {
					return "blue_check.gif";
				}
				
			};
		}
		else
		{
			return new AbstractReadOnlyModel()
			{
		
				@Override
				public Object getObject() {
					return "red-x.gif";
				}
				
			};
		}
	}
	public static double getPoolCost()
	{
		return MAINTENANCE_COST+(NUMBER_OF_WEEKS*WEEK_COST)+ENTRY_COST;
	}
	public static double getServerCost()
	{
		return (15 * 12) + 10;
	}
	public static double getTotalPrizeCache(int num)
	{
		return getFirstPlacePrize(num) + getSecondPlacePrize(num) + getThirdPlacePrize(num) + getFourthPlacePrize(num) + getFifthLastPlacePrize(num) + getFifthLastPlacePrize(num) + (getWeekWin(num) * 17);
	}
	public static double getFee(int numberOfPlayers)
	{
		return (double) numberOfPlayers * MAINTENANCE_COST;
	}
	public static double getTotalCash(int numberOfPlayers)
	{
		return (double) (((WEEK_COST * NUMBER_OF_WEEKS) + ENTRY_COST + MAINTENANCE_COST) * numberOfPlayers);
	}
	public static double getFirstPlacePrize(int numberOfPlayers)
	{
		return ((double) numberOfPlayers * ENTRY_COST) * FIRST_PLACE_PERCENTAGE;
	}
	public static double getSecondPlacePrize(int numberOfPlayers)
	{
		return ((double) numberOfPlayers * ENTRY_COST) * SECOND_PLACE_PERCENTAGE;
	}
	public static double getThirdPlacePrize(int numberOfPlayers)
	{
		return ((double) numberOfPlayers * ENTRY_COST) * THIRD_PLACE_PERCENTAGE;
	}
	public static double getFourthPlacePrize(int numberOfPlayers)
	{
		return ((double) numberOfPlayers * ENTRY_COST) * FOURTH_PLACE_PERCENTAGE;
	}
	public static double getFifthLastPlacePrize(int numberOfPlayers)
	{
		return ((((double) numberOfPlayers * ENTRY_COST) - (getFirstPlacePrize(numberOfPlayers) + getSecondPlacePrize(numberOfPlayers) + getThirdPlacePrize(numberOfPlayers) + getFourthPlacePrize(numberOfPlayers)))) / 2;
	}
	public static double getWeekWin(int numberOfPlayers)
	{
		return WEEK_COST * numberOfPlayers;
	}
	
	public static double getPlacementWinnings(int numberOfPlayers, int place, int ties)
	{
		double money;
		if (place == 1)
		{
			money = getFirstPlacePrize(numberOfPlayers);
			if (ties>=2)
			{
				money+=getSecondPlacePrize(numberOfPlayers);
			}
			if (ties>=3)
			{
				money+=getThirdPlacePrize(numberOfPlayers);
			}
			if (ties>=4)
			{
				money+=getFourthPlacePrize(numberOfPlayers);
			}
			if (ties>=5)
			{
				money+=getFifthLastPlacePrize(numberOfPlayers);
			}
			return money/ties;
		}
		else if (place == 2)
		{
			money = getSecondPlacePrize(numberOfPlayers);
			if (ties>=2)
			{
				money+=getThirdPlacePrize(numberOfPlayers);
			}
			if (ties>=3)
			{
				money+=getFourthPlacePrize(numberOfPlayers);
			}
			if (ties>=4)
			{
				money+=getFifthLastPlacePrize(numberOfPlayers);
			}
			return money/ties;
		}
		else if (place == 3)
		{
			money = getThirdPlacePrize(numberOfPlayers);
			if (ties>=2)
			{
				money+=getFourthPlacePrize(numberOfPlayers);
			}
			if (ties>=3)
			{
				money+=getFifthLastPlacePrize(numberOfPlayers);
			}
			return money/ties;
		}
		else if (place == 4)
		{
			money = getFourthPlacePrize(numberOfPlayers);
			if (ties>=2)
			{
				money+=getFifthLastPlacePrize(numberOfPlayers);
			}
			return money/ties;
		}
		else if (place == 5)
		{
			return getFifthLastPlacePrize(numberOfPlayers)/ties;
		}
		else 
			return 0;
	}
	
//	public static Week getWeekForParentLeague(League activeLeague, LeagueType leagueType, Week week, int selectedWeekNumber)
//	{
//		//check to see if a week has been setup
//		if (week==null)
//		{
//			String parentLeagueType = leagueType.getParentTypeOfLeague();
//			Season season = activeLeague.getSeason();
//			if (parentLeagueType==null)
//			{
//				//error("There are currently no weeks setup.");
//				week = new Week();
//				week.setSeason(activeLeague.getSeason());
//			}
//			else
//			{
//				season = ServiceLocator.getLeagueManagerService().getSeason(parentLeagueType, season.getStartYear(), season.getEndYear());
//				leagueType = season.getLeagueType();
////				leagueType = ServiceLocator.getLeagueManagerService().getLeagueType(parentLeagueType);
//				week = ServiceLocator.getGameManagerService().getWeek(selectedWeekNumber, season);
//				if (week==null)
//				{
//					//error("There are currently no weeks setup.");
//					week = new Week();
//					week.setSeason(activeLeague.getSeason());
//				}
//			}
//		}
//		return week;
//	}
}
