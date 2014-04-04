package info.makeyourpicks.service.impl;

import info.makeyourpicks.ValidationErrorEnum;
import info.makeyourpicks.dao.GameDao;
import info.makeyourpicks.dao.TeamDao;
import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.GameScore;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.Team;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.service.GameManager;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdScheduler;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.ContentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.delesio.cache.ICacheCreateAction;
import com.delesio.cache.ICacheProvider;
import com.delesio.cache.ehcache.EhCacheProvider;
import com.delesio.exception.ValidationException;
import com.delesio.rss.FeedFetcher;
import com.delesio.rss.FetcherEvent;
import com.delesio.rss.FetcherException;
import com.delesio.rss.FetcherListener;
import com.delesio.rss.impl.FeedFetcherCache;
import com.delesio.rss.impl.HashMapFeedInfoCache;
import com.delesio.rss.impl.HttpURLFeedFetcher;
import com.delesio.service.AbstractService;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

public class GameManagerHibernate extends AbstractLeagueService implements
		GameManager {

	private GameDao gameDao;
	private TeamDao teamDao;
	
	private boolean schedulerOn;
	private JobDetail jobDetail;
    private JobDetail updateScoresJobDetail;
    private StdScheduler scheduler;
    private String resource;
    private boolean internetMode;
    private long scoreUpdateTimeInMin;
    private int scoreUpdateRepeatTimes;
    private String scoreHandler;
    private String feedType;
    
	
	public void setTeamDao(TeamDao teamDao) {
		this.teamDao = teamDao;
	}



	public void setFeedType(String feedType) {
		this.feedType = feedType;
	}



	public void deleteGame(Game game) {
		dao.deleteObject(game);

	}

	public void deleteWeek(Week week) {
		dao.deleteObject(week);

	}

	@Transactional
	public void insertGameTX(Game game) throws ValidationException {
		
		validateGame(game);
		
		dao.save(game);
		
		((EhCacheProvider)cacheProvider).getCacheManager().getCache(PICKS_BY_LEAGUE).removeAll();

	}
	
	private void validateGame(Game game) throws ValidationException
	{
		if (game==null)
			throw new ValidationException(ValidationErrorEnum.GAME_IS_NULL);
		if (game.getFav() == null || game.getFav().getId() == 0)
			throw new ValidationException(ValidationErrorEnum.FAVORITE_IS_NULL);
		if (game.getDog() == null || game.getDog().getId() == 0)
			throw new ValidationException(ValidationErrorEnum.DOG_IS_NULL);
		if (game.getWeek() == null || game.getWeek().getId() == 0)
			throw new ValidationException(ValidationErrorEnum.WEEK_IS_NULL);
		if (game.getGameStart() == null)
			throw new ValidationException(ValidationErrorEnum.GAMESTART_IS_NULL);
		
		if (game.getFav().getId() == game.getDog().getId())
			throw new ValidationException(ValidationErrorEnum.TEAM_CANNOT_PLAY_ITSELF);
		
		
	}

	@Transactional
	public void insertWeekTX(Week week) {
		dao.save(week);

//		cacheProvider.remove(WEEK_BY_WEEK_NUMBER_AND_SEASON);
		((EhCacheProvider)cacheProvider).getCacheManager().getCache(WEEK_BY_WEEK_NUMBER_AND_SEASON).removeAll();
	}

	public void updateGame(Game game) {
		dao.updateObject(game);

	}

	public void updateWeek(Week week) {
		dao.updateObject(week);
		
//		cacheProvider.remove(WEEK_BY_WEEK_NUMBER_AND_SEASON);
		((EhCacheProvider)cacheProvider).getCacheManager().getCache(WEEK_BY_WEEK_NUMBER_AND_SEASON).removeAll();

	}
	
	public Week loadWeek(long weekId)
	{
		return (Week)dao.loadObject(Week.class, weekId);
	}
	
	public Game loadGame(long gameId)
	{
		return (Game)dao.loadObject(Game.class, gameId);
	}

//	private Map<Integer, Week> loadWeekCacheBySeason(final Season season)
//	{
//		return (Map<Integer, Week>) cacheProvider.getOrPut(WEEK_BY_WEEK_NUMBER_AND_SEASON, season, new ICacheCreateAction()
//		{
//
//			public Object createCacheObject()
//			{
//				Map<Integer, Week> map = new LinkedHashMap<Integer, Week>(19);
//				List<Week> weeks = gameDao.findWeekBySeason(season);
//				for (Week week : weeks)
//				{
//					int weekNumber = week.getWeekNumber();
//					map.put(weekNumber, week);
//				}
//				return map;
//			}
//			
//		});
//	}
//	public Week getWeek(int weekNumber, Season season) {
//		
////		return gameDao.findWeek(weekNumber, season);
//		return loadWeekCacheBySeason(season).get(weekNumber);
//	}

	public int getCurrentWeek(Season season) {
		return gameDao.getCurrentWeek(season);
	}

	public GameDao getGameDao() {
		return gameDao;
	}

	public void setGameDao(GameDao gameDao) {
		this.gameDao = gameDao;
	}
	
	@Transactional
	public List<Week> getWeeksBySeasonTX(Season season)
	{
		return getWeeksBySeason(season);
	}
	
	public List<Week> getWeeksBySeason(Season season)
	{
//		return new ArrayList<Week>(loadWeekCacheBySeason(season).values());
		
		List<Week> weeks = gameDao.findWeekBySeason(season);
		return weeks;
	}

	@Transactional
	public List<Game> getGamesByWeekTX(Week week) {
		return getGamesByWeek(week);
	}
	
	public List<Game> getGamesByWeek(Week week) {
		if (week == null)
		{
			return Collections.EMPTY_LIST;
		}
		
		List<Game> games = gameDao.findGamesByWeek(week);
		if (games == null)
		{
			return Collections.EMPTY_LIST;
		}
		else
		{
			return games;
		}
	}
	
	public Game getGameByTeamWeekLeague(Team team, Week week)
	{
		return gameDao.findGameByTeamWeekLeague(team, week);
	}

	public boolean hasAllGamesForWeekStarted(Week week)
	{
		List<Game> games = getGamesByWeek(week);
		for (Game game:games)
		{
			if (!game.hasGameStarted())
			{
				return false;
			}
		}
		return true;
	}
	
	public void createUpdateGame(Game game)
    {
        createUpdateGame(game, true);
    }
	
	public void createUpdateGame(Game game, boolean createClearCacheTrigger)
    {
        dao.saveOrUpdate(game);
        ((EhCacheProvider)cacheProvider).getCacheManager().getCache("picksByLeague").removeAll();
        if(createClearCacheTrigger)
            createGameTriggers(game);
    }
	
	public void reloadGameTriggers(Week week)
	{
		List<Game> games  = getGamesByWeek(week);
		for (Game game:games)
		{
			createGameTriggers(game);
		}
	}
	
	private void createGameTriggers(Game game)
    {
		if (!schedulerOn)
			return;
		
        if((new Date(System.currentTimeMillis())).after(game.getGameStart()))
            return;
        try
        {
            String jobName = String.valueOf(game.getGameStart().getTime());
            if(scheduler.getJobDetail(jobName, "clearCacheGroup") == null)
            {
                Trigger trigger = new SimpleTrigger(jobName, "clearCacheGroup", game.getGameStart());
                this.jobDetail.setName(jobName);
                this.jobDetail.setGroup("clearCacheGroup");
                scheduler.scheduleJob(this.jobDetail, trigger);
                System.out.println((new StringBuilder("Created Trigger for clearing games at ")).append(game.getGameStartDisplay()).toString());
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(game.getGameStart());
            calendar.add(10, 3);
            calendar.add(12, 30);
            jobName = String.valueOf(game.getGameStart().getTime());
            JobDetail jobDetail = scheduler.getJobDetail(jobName, "updateScoresGroup");
            if(jobDetail == null)
            {
                org.quartz.Trigger trigger = new SimpleTrigger(jobName, "updateScoresGroup", game.getGameStart(), calendar.getTime(), scoreUpdateRepeatTimes, scoreUpdateTimeInMin);
                updateScoresJobDetail.setName(jobName);
                updateScoresJobDetail.setGroup("updateScoresGroup");
                scheduler.scheduleJob(updateScoresJobDetail, trigger);
                System.out.println((new StringBuilder("Created Trigger for updating scores starting at ")).append(game.getGameStart()).append(" repeating ").append(scoreUpdateRepeatTimes).append(" times every ").append(scoreUpdateTimeInMin).append(" milliseconds and ending at ").append(calendar.getTime()).toString());
            }
        }
        catch(SchedulerException exception)
        {
            exception.printStackTrace();
        }
    }
	 
	private enum FeedType
	{
		RSS("rss"),
		XML("xml");
		
		private String type;
		
		private FeedType(String feedType)
		{
			this.type = feedType;
		}
		
		public String getFeedType()
		{
			return type;
		}
	}
	
	 public void executeGameScoreParsing()
	    {
	        
	        System.out.println("Updating Scores trigger executed...");
	        
	        Collection<GameScore> gameScores=null;
	        
	        if (FeedType.XML.getFeedType().equals(feedType))
	        {
	        	gameScores = executeXMLParsing();
	        }
	        else if (FeedType.RSS.getFeedType().equals(feedType))
	        {
	        	gameScores = executeRSSParsing();
	        }
	        else
	        {
	        	System.out.println("Automated Scoring not enabled.");
	        }
	       	
	        updateScoresByFeed(gameScores);
	    }
	 
	 private Collection<GameScore> executeRSSParsing()
	 {
		 List<GameScore> gameScores = new ArrayList<GameScore>();
		 try
			{
				URL feedUrl = new URL("http://feeds.feedburner.com/mpiii/nfl");
				FeedFetcherCache feedInfoCache = HashMapFeedInfoCache.getInstance();
				FeedFetcher fetcher = new HttpURLFeedFetcher(feedInfoCache);
		
				FetcherEventListenerImpl listener = new FetcherEventListenerImpl();
		
				fetcher.addFetcherEventListener(listener);
		
				try
				{
					SyndFeed feed = fetcher.retrieveFeed(feedUrl);
					List<SyndEntry> entries = feed.getEntries();
					for (SyndEntry entry:entries)
					{
						String title = entry.getTitleEx().getValue();
						if (title.indexOf(" at ")!=-1)
							continue;
						StringTokenizer tokenizer = new StringTokenizer(title, " ");
						String homeTeam="", awayTeam="";
						int homeScore=0, awayScore=0;
						while (tokenizer.hasMoreElements())
						{
							String token = (String)tokenizer.nextElement();
							
							try 
							{
								int score = Integer.parseInt(token);
								if (homeScore == 0)
									homeScore = score;
								else
									awayScore = score;	
					        
					        } catch (NumberFormatException ex) {
					            if (homeScore == 0)
					            {
					            	if (!homeTeam.equals(""))
					            		homeTeam+=" ";
					            	homeTeam += token;
					            }
					            else if (awayScore == 0)
					            {
					            	if (!awayTeam.equals(""))
					            		awayTeam+=" ";
					            	awayTeam += token;
					            }
					        }
						}
//						int index = title.indexOf(" at");
//						String awayTeam = title.substring(0, index);
						GameScore gameScore = new GameScore();
						gameScore.setVisitorTeamName(awayTeam);
						
//						index += 3;
//						String homeTeam = title.substring(index);
//						index = homeTeam.indexOf(" (");
//						homeTeam.substring(0, index);
						gameScore.setHomeTeamName(homeTeam); 
						gameScore.setHomeTeamScore(homeScore);
						gameScore.setVistitorTeamScore(awayScore);
						
						gameScores.add(gameScore);
						 
					}
				}
				catch (FetcherException exception)
				{
					exception.printStackTrace();
				}
			}
			catch (Exception exception)
			{
				exception.printStackTrace();
			}
			
			return gameScores;
	 }
	 
	 private Collection<GameScore> executeXMLParsing()
	 {
		 InputStream stream=null;
	        Map<String, GameScore> games=null;
		 try
	        {
		        if(internetMode)
		        {
		            URL url = new URL(resource);
		            URLConnection connection = url.openConnection();
		            stream = new DataInputStream(connection.getInputStream());
		        } else
		        {
		            File file = new File(resource);
		            stream = new FileInputStream(file);
		        }
		        games = new HashMap<String, GameScore>(17);
		        XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
		        reader.setEntityResolver(new  EntityResolver() {
					
					public InputSource resolveEntity(String publicId, String systemId)
							throws SAXException, IOException {
						return new InputSource(new ByteArrayInputStream(new byte[0]));
					}
				});
	
	
//		        reader.setContentHandler(new ScoresHandler(games));
		        Class cls = Class.forName(scoreHandler);
		        Class partypes[] = new Class[1];
		        partypes[0] = Map.class;
//	            partypes[0] = Integer.TYPE;
//	            partypes[1] = Integer.TYPE;
	            Constructor ct = cls.getConstructor(partypes);
	            Object arglist[] = new Object[1];
	            arglist[0] = games;
	            ContentHandler retobj = (ContentHandler)ct.newInstance(arglist);


	            reader.setContentHandler(retobj);
//		        reader.setContentHandler(new ScoresHandler(games));
		        reader.parse(new InputSource(stream));
	        }
	        catch (Exception exception)
	        {
	        	exception.printStackTrace();
	        }
	        finally
	        {
	        	try
	        	{
			        if(stream != null)
			            stream.close();
	        	}
	        	catch (Exception exception2){}
	        }
	        
	        return games.values();
	 }
	 
	 private void updateScoresByFeed(Collection<GameScore> gamesScores)
	 {
		 if (gamesScores==null)
			 return;
		 
	        Iterator<GameScore> iterator = gamesScores.iterator();
	        while(iterator.hasNext()) 
	        {
	            GameScore gameScore = (GameScore)iterator.next();
	            boolean favHome = true;
	            Team homeTeam = teamDao.findTeamByCity(gameScore.getHomeTeamName().toLowerCase());
	            if (homeTeam == null)
	            {
	            	homeTeam = teamDao.findTeamByFeedName(gameScore.getHomeTeamName().toLowerCase());
	            	if (homeTeam == null)
	            		System.out.println(gameScore.getHomeTeamName()+" not found");
	            }
	            
	            Team awayTeam = teamDao.findTeamByCity(gameScore.getVisitorTeamName().toLowerCase());
	            if (awayTeam == null)
	            {
	            	awayTeam = teamDao.findTeamByFeedName(gameScore.getVisitorTeamName().toLowerCase());
	            	if (awayTeam == null)
	            		System.out.println(gameScore.getVisitorTeamName()+" not found");
	            }
	            
	            if (awayTeam == null || homeTeam == null)
	            {
//	            	System.out.println(GAME );
	            }
	            else
	            {
		            Game game = gameDao.findGamesByTeams(homeTeam, awayTeam, new Date(System.currentTimeMillis()));
		            if (game == null)
		            {
		            	game = gameDao.findGamesByTeams(awayTeam, homeTeam, new Date(System.currentTimeMillis()));
		            	if (game == null)
		            	{
		            		System.out.println("Game not found");
		            	}
		            	else
		            	{
		            		favHome=false;
		            	}
		            }
		            
		            if (game!=null)
		            {
			            if(favHome)
			            {
			                game.setFavScore(gameScore.getHomeTeamScore());
			                game.setDogScore(gameScore.getVistitorTeamScore());
			            } else
			            {
			                game.setFavScore(gameScore.getVistitorTeamScore());
			                game.setDogScore(gameScore.getHomeTeamScore());
			            }
			            System.out.println("Updating Game: FAV:"+game.getFav().getFullTeamName()+": "+game.getFavScore()+" DOG:"+game.getDog().getFullTeamName()+": "+game.getDogScore());
			            createUpdateGame(game, false);
		            }
	            }
	            	
//	            Game game = gameDao.findGamesByCityNames(gameScore.getHomeTeamName().toLowerCase(), gameScore.getVisitorTeamName().toLowerCase(), gameScore.getGameStart());
//	            if(game == null)
//	            {
//	                game = gameDao.findGamesByCityNames(gameScore.getVisitorTeamName().toLowerCase(), gameScore.getHomeTeamName().toLowerCase(), gameScore.getGameStart());
//	                if(game == null)
//	                {
//	                    System.out.println((new StringBuilder("GAME NOT FOUND ")).append(gameScore.getHomeTeamName()).append(":").append(gameScore.getHomeTeamScore()).append(" ").append(gameScore.getVisitorTeamName()).append(":").append(gameScore.getVistitorTeamScore()).toString());
//	                    continue;
//	                }
//	                favHome = false;
//	            }
	            
	        }
//	        break MISSING_BLOCK_LABEL_394;
//	        Exception exception;
//	        exception;
//	        exception.printStackTrace();
	    }

//	    private boolean isValidData(String teamName)
//	    {
//	        return teamName != null && !teamName.equals("") && !teamName.startsWith("1Q ") && !teamName.startsWith("2Q ") && !teamName.startsWith("3Q ") && !teamName.startsWith("4Q ") && !teamName.startsWith("1H ") && !teamName.startsWith("2H ") && !teamName.endsWith("(ESPN)");
//	    }


	public void setJobDetail(JobDetail jobDetail) {
		this.jobDetail = jobDetail;
	}

	public void setUpdateScoresJobDetail(JobDetail updateScoresJobDetail) {
		this.updateScoresJobDetail = updateScoresJobDetail;
	}

	public void setScheduler(StdScheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public void setInternetMode(boolean internetMode) {
		this.internetMode = internetMode;
	}

	public void setScoreUpdateTimeInMin(long scoreUpdateTimeInMin) {
		this.scoreUpdateTimeInMin = scoreUpdateTimeInMin;
	}

	public void setScoreUpdateRepeatTimes(int scoreUpdateRepeatTimes) {
		this.scoreUpdateRepeatTimes = scoreUpdateRepeatTimes;
	}

	public void setScoreHandler(String scoreHandler) {
		this.scoreHandler = scoreHandler;
	}

	
	static class FetcherEventListenerImpl implements FetcherListener {
		/**
		 * @see com.sun.syndication.fetcher.FetcherListener#fetcherEvent(com.sun.syndication.fetcher.FetcherEvent)
		 */
		protected static final Log log = LogFactory.getLog(FetcherEventListenerImpl.class);

		public void fetcherEvent(FetcherEvent event) {
			String eventType = event.getEventType();
			if (FetcherEvent.EVENT_TYPE_FEED_POLLED.equals(eventType)) {
				log.info("\tEVENT: Feed Polled. URL = " + event.getUrlString());
			} else if (FetcherEvent.EVENT_TYPE_FEED_RETRIEVED.equals(eventType)) {
				log.info("\tEVENT: Feed Retrieved. URL = " + event.getUrlString());
			} else if (FetcherEvent.EVENT_TYPE_FEED_UNCHANGED.equals(eventType)) {
				log.info("\tEVENT: Feed Unchanged. URL = " + event.getUrlString());
			}
		}
	}


	public boolean isSchedulerOn() {
		return schedulerOn;
	}



	public void setSchedulerOn(boolean schedulerOn) {
		this.schedulerOn = schedulerOn;
	}
    
	
	
}
