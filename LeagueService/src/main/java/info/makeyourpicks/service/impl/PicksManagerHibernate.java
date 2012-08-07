package info.makeyourpicks.service.impl;

import info.makeyourpicks.dao.PickDao;
import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.League;
import info.makeyourpicks.model.PickemTieBreakerEnum;
import info.makeyourpicks.model.Picks;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.SeasonStats;
import info.makeyourpicks.model.Team;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.model.WeekWinner;
import info.makeyourpicks.model.WinSummary;
import info.makeyourpicks.service.GameManager;
import info.makeyourpicks.service.PicksManager;
import info.makeyourpicks.service.PlayerManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.delesio.cache.action.ICacheCreateAction;
import com.delesio.cache.impl.EhCacheProvider;
import com.delesio.service.impl.AbstractService;

public class PicksManagerHibernate extends AbstractService implements
		PicksManager {

	private PickDao pickDao;
	private PlayerManager playerManager;
	private GameManager gameManager;
	
	@Override
	protected void init() {

	}

	
	public void setPlayerManager(PlayerManager playerManager)
	{
		this.playerManager = playerManager;
	}
	
	public void setGameManager(GameManager gameManager)
	{
		this.gameManager = gameManager;
	}


	private class PicksCacheMap<K,V> extends ConcurrentHashMap<K,V>
	{
		private boolean dirty=false;
		
		public void dirty()
		{
			this.dirty=true;
		}
		
		public boolean isDirty()
		{
			return dirty;
		}
	}
	
	private PicksCacheMap<Week, PicksCacheMap<Game,PicksCacheMap<Player, List<Picks>>>> getCachedPicks(final League league)
	{
		return (PicksCacheMap<Week, PicksCacheMap<Game, PicksCacheMap<Player, List<Picks>>>>)cacheProvider.getOrPut(PICKS_BY_LEAGUE, league, new ICacheCreateAction(){

			public Object createCacheObject()
			{
				List<Picks> picks = pickDao.findPicksByLeague(league);
				
				PicksCacheMap<Week, PicksCacheMap<Game,PicksCacheMap<Player, List<Picks>>>> cache = new PicksCacheMap<Week, PicksCacheMap<Game, PicksCacheMap<Player, List<Picks>>>>();
	 			for (Picks pick: picks)
				{
					Week week = pick.getWeek();
					Player player = pick.getName();
					Game game = pick.getGame();
					
					
					PicksCacheMap<Game, PicksCacheMap<Player, List<Picks>>> weekCache = cache.get(week);
					if (weekCache == null)
					{
						weekCache = new PicksCacheMap<Game, PicksCacheMap<Player, List<Picks>>>();
						cache.put(week, weekCache);
					}
					
					
					PicksCacheMap<Player, List<Picks>> gameCache = weekCache.get(game);
					if (gameCache == null)
					{
						gameCache = new PicksCacheMap<Player, List<Picks>>();
						weekCache.put(game, gameCache);
					}
					
					List<Picks> playerPicks = gameCache.get(player);
					if (playerPicks == null)
					{
						playerPicks = new ArrayList<Picks>();
						gameCache.put(player, playerPicks);
					}
					
					playerPicks.add(pick);
				}
				
				return cache;
			}
			
		});
	}
	
	private List<Picks> searchCacheWeek(List<Picks> picks, PicksCacheMap<Game, PicksCacheMap<Player, List<Picks>>> weeks, Game game, Player player)
	{
		if (game != null)
		{
			PicksCacheMap<Player, List<Picks>> cache = weeks.get(game);
			return searchCacheGame(picks, cache, player);
		}
		else
		{
			for (PicksCacheMap<Player, List<Picks>> cache : weeks.values())
			{
				picks = searchCacheGame(picks, cache, player);
			}
			
		}
		
		return picks;
	}	
	
	private void updateCache(Picks pick)
	{
		League league = pick.getLeague();
		Week week = pick.getWeek();
		Game game = pick.getGame();
		Player player = pick.getName();
		
		try
		{
			List<Picks> picks = getCachedPicks(league).get(week).get(game).get(player);
			if (picks.contains(pick))
			{
				int updateIndex = picks.indexOf(pick);
				picks.set(updateIndex, pick);
			}
			else
			{
				picks.add(pick);
			}
		}
		catch (Exception exception)
		{
			clearPickCache();
		}
		
//		getCachedPicks(league).get(week).get(game).put(player, picks);
	}
	
	private List<Picks> searchCacheGame(List<Picks> picks, PicksCacheMap<Player, List<Picks>> games, Player player)
	{
		if (games==null)
		{
			return picks;
		}
		if (player != null)
		{
			List<Picks> playerPicks = games.get(player);			
			if (playerPicks != null)
			{
				picks.addAll(playerPicks);
			}
		}
		else
		{
			for (List<Picks> pick : games.values())
			{
				if (pick!=null)
				{
					picks.addAll(pick);
				}
			}
		}
		
		return picks;
	}
	
	private List<Picks> searchCache(League league, Week week, Game game, Player player)
	{
		List<Picks> picks = new ArrayList<Picks>();
		Map<Week, PicksCacheMap<Game, PicksCacheMap<Player, List<Picks>>>> cache = getCachedPicks(league);
		
		if (week!=null)
		{
			PicksCacheMap<Game, PicksCacheMap<Player, List<Picks>>> weeks = cache.get(week);
			if (weeks == null)
			{
				return Collections.EMPTY_LIST;
			}
			else
			{
				return searchCacheWeek(picks ,weeks, game, player);
			}
		}
		else
		{
			for (PicksCacheMap<Game, PicksCacheMap<Player, List<Picks>>> weeks : cache.values())
			{
				picks = searchCacheWeek(picks, weeks, game, player);
			}
		}
		
		return picks;
	}
	
	private List<Picks> searchCache(League league, Week week, Player player)
	{
		return searchCache(league, week, null, player);
	}
	private List<Picks> searchCache(League league, Week week, Game game)
	{
		return searchCache(league, week, game, null);
	}
	private List<Picks> searchCache(League league, Week week)
	{
		return searchCache(league, week, null, null);
	}
	private List<Picks> searchCache(League league)
	{
		return searchCache(league, null, null, null);
	}
	private List<Picks> searchCache(Picks picks)
	{
		return searchCache(picks.getLeague(), picks.getWeek(), picks.getGame(), picks.getName());
	}
	
	public List<Picks> getPicksByLeague(League league)
	{
//		return pickDao.findPicksByLeague(league);
		return searchCache(league);
	}
	
		public void insertPlayerPick(Picks pick) {
		dao.save(pick);
		
//		List<Picks> picks = searchCache(pick);
//		picks.add(pick);
		updateCache(pick);

	}

	public void updatePlayerPick(Picks pick, boolean forceUpdate) {
		if (pick.getGame().hasGameStarted())
			return;
		
		if (forceUpdate)
		{
			dao.updateObject(pick);
		}
		else
		{
			dao.saveOrUpdate(pick);
		}
	
		updateCache(pick);
//		List<Picks> picks = searchCache(pick);
//		picks.remove(pick);
//		picks.add(pick);
//		int updateIndex = picks.indexOf(pick);
//		picks.set(updateIndex, pick);

	}

	public void deletePicks(Picks pick) {
		dao.deleteObject(pick);
		
		List<Picks> picks = searchCache(pick);
		picks.remove(pick);
	}

	public PickDao getPickDao() {
		return pickDao;
	}

	public void setPickDao(PickDao pickDao) {
		this.pickDao = pickDao;
	}

	public List<Picks> getPicksByLeagueAndWeek(League league, Week week) {
//		return pickDao.findPicksByLeagueAndWeek(league, week);
		return searchCache(league, week);
	}

//	public List<Picks> getPicksByWeek(Week week) {
////		return pickDao.findPicksByWeek(week);
//		return searchCache(week);
//	}
//
//	public List<Picks> getPicksByPlayer(Player player) {
////		return pickDao.findPicksByPlayer(player);
//		return searchCache(player);
//	}
//
//	public List<Picks> getPicksByPlayerAndWeek(Player player, Week week) {
////		return pickDao.findPicksByPlayerAndWeek(player, week);
//		return searchCache(week, player);
//	}

	public List<Picks> getPicksByPlayerLeagueAndWeek(Player player,
			League league, Week week) {
//		return pickDao.findPicksByPlayerLeagueAndWeek(player, league, week);
		return searchCache(league, week, player);
	}
	
	public Picks getPickByPlayerLeagueWeekAndGame(Player player,
			League league, Week week, Game game) 
	{
//		List<Picks> picks = pickDao.findPickByPlayerLeagueWeekAndGame(player, league, week, game);
		List<Picks> picks = searchCache(league, week, game, player);
		if (picks==null||picks.isEmpty())
		{
			return new Picks(player, league, week, game, true);
		}
		else
		{
			return picks.get(0);
		}
	}
	public Picks getPickByPlayerLeagueAndWeek(Player player, League league, Week week) {
//		List<Picks> picks = getPicksByPlayerLeagueAndWeek(player, league, week);
		List<Picks> picks = searchCache(league, week, player);
		if (picks==null||picks.isEmpty())
		{
			return new Picks(player, league, week, null, true);
		}
		else
		{
			return picks.get(0);
		}
	}

	public Picks loadPicks(long pickId) {
		return (Picks)dao.loadObject(Picks.class, pickId);
	}
	
	public double percentChangeToWinForWeek(Player player, League league, Week week)
	{
		List<Picks> playerPicks = getPicksByPlayerLeagueAndWeek(player, league, week);
		int gamesLeft=0;
//		int chanceByPlayer=0;
//		List<Player> winningPlayers = getWinningPlayersForWeek(league, week);
		List<Player> allPlayers = playerManager.getPlayersInLeague(league);
		Map<Player, Integer> chanceByPlayer=new HashMap<Player, Integer>(allPlayers.size());
		if (week.hasCurrentWeekStarted())
		{
			for (Picks picks:playerPicks)
			{
				if (!picks.getGame().hasScoresEntered() && !picks.getGame().hasGameStarted())
				{
					//scores have not been enter and game hasn't been started
					//check every winner now
					gamesLeft++;
					for (Player winningPlayer:allPlayers)
					{
						int chance=0;
						Picks winningPlayerPick = getPickByPlayerLeagueWeekAndGame(winningPlayer, league, week, picks.getGame());
						if (winningPlayerPick.getTeam()!=null&&!winningPlayerPick.getTeam().equals(picks.getTeam()))
						{
							//pick is the not the same increase chance
							chance++;
						}
						
						if (winningPlayerPick.getWeight()>1)
						{
							chance--;
						}
						
						if (picks.getWeight()>1)
						{
							chance++;
						}
						
						if (chance>0)
						{
							Integer value = chanceByPlayer.get(winningPlayer);
							if (value == null)
								value = new Integer(0);
							int v = value.intValue();
							v+=chance;
							chanceByPlayer.put(winningPlayer, new Integer(v));	
						}
					}
				}
			}
		}
		
		if (gamesLeft>0)
		{
			int maxChance=0;
			for (Player player2 : chanceByPlayer.keySet())
			{
				int value = chanceByPlayer.get(player2);
				if (value >maxChance)
					maxChance = value;	
			}
			return (maxChance / gamesLeft)*100;
		}
		else
		{
			List<Player> winningPlayers = getWinningPlayersForWeek(league, week);
			if (winningPlayers.contains(player))
				return 100;
			else
				return 0;
		}
			
		
	}
	
	public List<Player> getWinningPlayersForWeek(League league, Week week)
	{
		int winningWeek = getWinningScoreForLeagueAndWeek(league, week);
		List<Player> winners = new ArrayList<Player>();
		List<Player> players = getPlayersForLeagueSortedByWeekWins(league, week);
		for (Player player:players)
		{
			if (player.getWins() == winningWeek)
				winners.add(player);
			else
				break;
		}
		
		return winners;
	}
	
	public List<Player> getPlayersForLeagueSortedByWeekWins(League league, Week week)
	{
		List<Player> players = playerManager.getPlayersInLeague(league);
		for (Player player:players)
		{
			SeasonStats seasonStats = getPlayersStatsForWeek(player, league, week);
			player.setWins(seasonStats.getWins());
		}
		
		Collections.sort(players, new Comparator<Player>() {

			public int compare(Player arg0, Player arg1) {
				if (arg0.getWins()>arg1.getWins())
					return -1;
				else if (arg0.getWins()==arg1.getWins())
					return 0;
				else
					return 1;
			}
			
		});
		
		return players;
	}
	
	public SeasonStats getPlayersStatsForWeek(Player player, League league, Week week)
	{
		SeasonStats seasonStats = new SeasonStats();
		seasonStats.setUsername(player.getUsername());
		Iterator<Picks> picks = getPicksByPlayerLeagueAndWeek(player, league, week).iterator();
		Picks pick;
		while (picks.hasNext())
		{
			pick = picks.next();
		
			if (pick.getWinnerStatus()==Picks.WINNER)
			{
				seasonStats.addWin(pick.getWeight());
			} 
			else if (pick.getWinnerStatus()==Picks.LOSER)
			{
				seasonStats.addLoses(1);
			}
		}
		
		return seasonStats;
	}
	 
	public SeasonStats getPlayersSeasonStats(Player player, League league, Season parentSeason)
	{
		List<Week> weeks = gameManager.getWeeksBySeason(parentSeason);
		Picks pick;
		SeasonStats seasonStats = new SeasonStats();
		for (Week week:weeks)
		{
			List<Picks> picks = getPicksByPlayerLeagueAndWeek(player, league, week); 
			Iterator<Picks> picksIter = picks.iterator();
			if (picks.isEmpty() && gameManager.hasAllGamesForWeekStarted(week))
			{
				seasonStats.addLoses(1);
			}
			while (picksIter.hasNext())
			{
				pick = picksIter.next();
				if (pick.getGame()==null || !pick.getGame().hasGameStarted())
				{
					
				}
			
				if (pick.getWinnerStatus()==Picks.WINNER)
				{
					seasonStats.addWin(pick.getWeight());
				} 
				else if (pick.getWinnerStatus()==Picks.LOSER)
				{
					seasonStats.addLoses(1);
				}
//				else if (pick.isNoPick())
//				{
//					seasonStats.addLoses(1);
//				}
				
//				if (league.isSpreads())
//				{
					if (pick.isNoPick())
					{
						continue;
					}
					else if (pick.getGame().getDog().equals(pick.getTeam()))
					{
						seasonStats.subtractSpread(pick.getGame().getSpread());
					}
					else
					{
						seasonStats.addSpread(pick.getGame().getSpread());
					}
				}
//			}
		}
		return seasonStats;
	}
	
	public Picks getDoublePickForPlayerLeagueAndWeek(Player player, League league, Week week)
	{
		List<Picks> picks = pickDao.findDoublePickByPlayerLeagueAndWeek(player, league, week);
		if (picks.isEmpty())
		{
			return new Picks(player, league, week, null, true);
		}
		else
		{
			return picks.get(0);
		}
	}
	
	public void setDoublePick(Player player, League league, Week week, Team team)
	{
		
		List<Picks> picks = pickDao.findPickByPlayerLeagueWeekAndTeam(player, league, week, team);		
		Picks pick = picks.get(0);
		if (pick.getGame().hasGameStarted())
			return;
		
		Picks oldPick = getDoublePickForPlayerLeagueAndWeek(player, league, week);
		pick.setWeight(2);
		
		dao.updateObject(pick);
		
		if (!oldPick.isNoPick())
		{
			oldPick.setWeight(1);
			dao.updateObject(oldPick);
		}
	}
	
	public int getWinningScoreForLeagueAndWeek(League league, Week week)
	{
		List<Player> players = playerManager.getPlayersInLeague(league);
		int highestNumber=0;
		for (Player player: players)
		{
			SeasonStats seasonStats = getPlayersStatsForWeek(player, league, week);
			if (seasonStats.getWins()>0&&seasonStats.getWins()>highestNumber)
			{
				highestNumber = seasonStats.getWins();
			}
			
		}
		return highestNumber;
	}
	
	public void clearPickCache()
	{
		((EhCacheProvider)cacheProvider).getCacheManager().getCache(PICKS_BY_LEAGUE).removeAll();
	}

	public Picks getPicksByPlayerLeagueWeekAndWeight(Player player, League league, Week week, int weight)
	{
		return pickDao.findPicksByPlayerLeagueWeekAndWeight(player, league, week, weight);
	}
	
	public WinSummary getWinSummaryForPlayer(League league, Player player)
	{
		List<WinSummary> winSummaries = getWinSummary(league);
		for (WinSummary winSummary:winSummaries)
		{
			if (winSummary.getPlayer().equals(player))
				return winSummary;
		}
		
		return null;
	}
	public List<WinSummary> getWinSummary(League league)
	{
		Map<Week, Integer> weekSplits = new HashMap<Week, Integer>(17);
		Map<Week, WeekWinner> weekTotal=null;
		WinSummary winSummary;
		int totalWins;
		
		//get all the weeks
		List<Week> weeks = gameManager.getWeeksBySeason(league.getSeason());
		
		//get all the players in the league
		List<Player> playersInLeague = playerManager.getPlayersInLeague(league);
		
		//create a map that 
		Map<Week, Integer> weekWinners = new HashMap<Week, Integer>(weeks.size());
		//a winsummary for every player
		List<WinSummary> winSummaries = new ArrayList<WinSummary>(playersInLeague.size());
		
		//loop through all the players in the leauge
		for (Player player: playersInLeague)
		{
			totalWins=0;
			
			//create a win summary for the player
			winSummary = new WinSummary(player);
			weekTotal = new HashMap<Week, WeekWinner>(weeks.size());
			for (Week week:weeks)
			{
				//get the number of wins the player had for the week
				SeasonStats seasonStats = getPlayersStatsForWeek(player, league, week);
				int winsForWeek = seasonStats.getWins();
				
				//put the numbers of wins the player had in a map with the key of week
				weekTotal.put(week, new WeekWinner(winsForWeek));
				
				//increment the total wins
				totalWins+=winsForWeek;
				
				Integer winningWeekTotal = weekWinners.get(week);
				//trying to build the map of the winning weeks
				if (winningWeekTotal == null || winningWeekTotal<winsForWeek)
				{
					weekWinners.put(week, winsForWeek);
				}
			}
			
			winSummary.setWeekTotal(weekTotal);
			winSummary.setNumberOfWins(totalWins);
			winSummaries.add(winSummary);
			
		}
		
		Collections.sort(winSummaries);
		
		int place = 1;
		int wins = -1;
		int tieCounter=1;
		int[] tieHolder = new int[] {0, 0, 0, 0, 0};
		for (WinSummary placeWin : winSummaries)
		{
			if (wins==-1)
			{
				wins = placeWin.getNumberOfWins();
			}
			else if (wins == placeWin.getNumberOfWins())
			{
				tieCounter++;
			}
			else
			{
				place += tieCounter;
				tieCounter=1;
				wins = placeWin.getNumberOfWins();
			}
			placeWin.setPlace(place);
			if (place == 1)
				tieHolder[0]++;
			else if (place == 2)
				tieHolder[1]++;
			else if (place == 3)
				tieHolder[2]++;
			else if (place == 4)
				tieHolder[3]++;
			else if (place == 5)
				tieHolder[4]++;

			Map<Week, WeekWinner> weeks2 = placeWin.getWeekTotal();
			for (Week key :weeks2.keySet())
			{
				int winningNumber = weekWinners.get(key);
				WeekWinner weekWinner = weeks2.get(key);
				if (winningNumber == weekWinner.getWins())
				{
					weekWinner.setWinner(true);
					int weekTiesCounter=0;
					if (weekSplits.get(key) != null)
						weekTiesCounter = weekSplits.get(key).intValue();
				
					weekTiesCounter++;
					weekSplits.put(key, weekTiesCounter);
					
				}
			}
		}
		
		for (int i=0;i<5;i++)
		{
			if (tieHolder[i]>1)
			{
				for (int j=0;j<tieHolder[i];j++)
				{
					int index = (i+j);
					winSummaries.get(index).setNumberOfPeopleSplitWith(tieHolder[i]);
				}
				
			}
			
		}
		
		if (league.isBanker())
			calculateWinnings(league, winSummaries, playersInLeague.size(), weekSplits);
		return winSummaries;
	}
	
	
	
	protected void calculateWinnings(League league, List<WinSummary> winSummaries, int numberOfPlayersInLeague, Map<Week, Integer> weekSplits)
	{
		double entryFeeTotalWin = numberOfPlayersInLeague*league.getEntryFee();
		double weeklyWin = numberOfPlayersInLeague*league.getWeeklyFee();
		double placementPoolWin=0;
		double firstPlaceWin = entryFeeTotalWin*league.getFirstPlacePercent()*.01;
		double secondPlaceWin = entryFeeTotalWin*league.getSecondPlacePercent()*.01;
		double thirdPlaceWin = entryFeeTotalWin*league.getThirdPlacePercent()*.01;
		double fourthPlaceWin = entryFeeTotalWin*league.getFourthPlacePercent()*.01;
		double fifthPlaceWin = entryFeeTotalWin*league.getFifthPlacePercent()*.01;
		
		//calculate season totals
		for (WinSummary winSummary:winSummaries)
		{			
			if (winSummary.getPlace()==1&&league.getFirstPlacePercent()!=0)
			{
				if (winSummary.getNumberOfPeopleSplitWith()==2)
				{
					placementPoolWin = (firstPlaceWin+secondPlaceWin)/2;
				}
				else if (winSummary.getNumberOfPeopleSplitWith()==3)
				{
					placementPoolWin = (firstPlaceWin+secondPlaceWin+thirdPlaceWin)/3;
				}
				else if (winSummary.getNumberOfPeopleSplitWith()==4)
				{
					placementPoolWin = (firstPlaceWin+secondPlaceWin+thirdPlaceWin+fourthPlaceWin)/4;
				}
				else if (winSummary.getNumberOfPeopleSplitWith()>=5)
				{
					placementPoolWin = (firstPlaceWin+secondPlaceWin+thirdPlaceWin+fourthPlaceWin+fifthPlaceWin)/winSummary.getNumberOfPeopleSplitWith();
				}
				else
				{
					placementPoolWin = firstPlaceWin;
				}
			}
			else if (winSummary.getPlace()==2&&league.getSecondPlacePercent()!=0) 
			{
				if (winSummary.getNumberOfPeopleSplitWith()==2)
				{
					placementPoolWin = (secondPlaceWin+thirdPlaceWin)/2;
				}
				else if (winSummary.getNumberOfPeopleSplitWith()==3)
				{
					placementPoolWin = (secondPlaceWin+thirdPlaceWin+fourthPlaceWin)/3;
				}
				else if (winSummary.getNumberOfPeopleSplitWith()>=4)
				{
					placementPoolWin = (secondPlaceWin+thirdPlaceWin+fourthPlaceWin+fifthPlaceWin)/winSummary.getNumberOfPeopleSplitWith();
				}
				else
				{
					placementPoolWin = secondPlaceWin;
				} 
			}
			else if (winSummary.getPlace()==3&&league.getThirdPlacePercent()!=0)
			{
				if (winSummary.getNumberOfPeopleSplitWith()==2)
				{
					placementPoolWin = (thirdPlaceWin+fourthPlaceWin)/2;
				}
				else if (winSummary.getNumberOfPeopleSplitWith()>=3)
				{
					placementPoolWin = (thirdPlaceWin+fourthPlaceWin+fifthPlaceWin)/winSummary.getNumberOfPeopleSplitWith();
				}
				else
				{
					placementPoolWin = thirdPlaceWin;
				} 
			}
			else if (winSummary.getPlace()==4&&league.getFourthPlacePercent()!=0)
			{
				if (winSummary.getNumberOfPeopleSplitWith()>=2)
				{
					placementPoolWin = (fourthPlaceWin+fifthPlaceWin)/winSummary.getNumberOfPeopleSplitWith();
				}
				else
				{
					placementPoolWin = fourthPlaceWin;
				} 
			}
			else if (winSummary.getPlace()==5&&league.getFifthPlacePercent()!=0)
			{
				if (winSummary.getNumberOfPeopleSplitWith()>=2)
				{
					placementPoolWin = fifthPlaceWin/winSummary.getNumberOfPeopleSplitWith();
				}
				else
				{
					placementPoolWin = fifthPlaceWin;
				}
				 
			}
			
			
			
//			int numberOfWeeksWon = winSummary.getNumberOfWins();
//			if (numberOfWeeksWon!=0)
//			{
//				placementPoolWin += numberOfWeeksWon * weeklyWin;
//			}
			winSummary.setEntryPrizeWon(placementPoolWin);
			
			Map<Week, WeekWinner> weekWins = winSummary.getWeekTotal();
			List<Week> weeks = new ArrayList<Week>(weekWins.keySet());
			Collections.sort(weeks);
			int numberOfPushWeeks=0;
			double kitty=0;
			for (Week week: weeks)
			{
				WeekWinner weekWinner = weekWins.get(week);
				int splitsForWeek = weekSplits.get(week);
				double moneyToBeSplit = (weeklyWin/weekSplits.get(week))/2;
				if (splitsForWeek>1)
				{
					numberOfPushWeeks++; 
					kitty += moneyToBeSplit;
				}
				if (weekWinner.isWinner())
				{
					if (league.getDoubleType() == PickemTieBreakerEnum.SPLIT.getType())
						winSummary.addWeekMoney(weeklyWin/weekSplits.get(week));
					else if (league.getDoubleType() == PickemTieBreakerEnum.FIFTY_FIFTY_SPLIT.getType())
					{
						
						if (splitsForWeek>1)
						{
							if (week.getWeekNumber()==17)
							{
								double moneyWon = kitty+(weeklyWin/splitsForWeek);
								winSummary.addWeekMoney(moneyWon);
							}
							else
							{
								winSummary.addWeekMoney(moneyToBeSplit);
							}
						}
						else if (splitsForWeek==1)
						{
							winSummary.addWeekMoney(kitty);
							winSummary.addWeekMoney(weeklyWin);
//							kitty=0;
							numberOfPushWeeks=0;
						}
					}
					else if (league.getDoubleType() == PickemTieBreakerEnum.PUSH_TO_NEXT_WEEK.getType())
					{
//						if (splitsForWeek>1)
//						{
//							kitty+=weeklyWin;
//						}
//						else 
						if (splitsForWeek==1)
						{
							double moneyWon = (weeklyWin*numberOfPushWeeks)+weeklyWin;
							winSummary.addWeekMoney(moneyWon);
							numberOfPushWeeks=0;
//							kitty=0;
						}
						else if (week.getWeekNumber()==17&&numberOfPushWeeks>1)
						{
							double moneyWon = ((weeklyWin*numberOfPushWeeks))/splitsForWeek;
							winSummary.addWeekMoney(moneyWon);
							numberOfPushWeeks=0;
						}
					}
					else if (league.getDoubleType() == PickemTieBreakerEnum.MONDAY_NIGHT_SCORE.getType())	
					{
						
					}
					weekWinner.setTiesForWeek(weekSplits.get(week));
				}
				if (splitsForWeek==1)
				{
					numberOfPushWeeks=0;
					kitty=0;
				}
			}
			placementPoolWin=0;
		}
	}
}	
