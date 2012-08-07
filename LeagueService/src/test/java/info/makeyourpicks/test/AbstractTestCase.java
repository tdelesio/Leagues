package info.makeyourpicks.test;

import info.makeyourpicks.dao.GameDao;
import info.makeyourpicks.dao.LeagueDao;
import info.makeyourpicks.dao.MessageBoardDao;
import info.makeyourpicks.dao.PlayerDao;
import info.makeyourpicks.dao.TeamDao;
import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.League;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.Team;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.service.GameManager;
import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.service.MessageBoardManager;
import info.makeyourpicks.service.PicksManager;
import info.makeyourpicks.service.PlayerManager;
import info.makeyourpicks.service.TeamManager;
import info.makeyourpicks.service.impl.LeagueManagerHibernate;
import info.makeyourpicks.service.impl.MessageBoardManagerHibernate;
import info.makeyourpicks.service.impl.PicksManagerHibernate;
import info.makeyourpicks.service.impl.PlayerManagerHibernate;
import info.makeyourpicks.service.impl.TeamManagerHibernate;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.delesio.dao.IDao;
import com.delesio.model.IPersistable;

public abstract class AbstractTestCase extends TestCase{

	// initial variable
	protected static boolean isInitial = true;
	
	private static ApplicationContext context;
//	private static ApplicationContext loadTestContext;
	
	protected static TeamManager teamManager;
	protected static LeagueManager leagueManager;
	protected static PlayerManager playerManager;
	protected static MessageBoardManager messageBoardManager;
	protected static GameManager gameManager;
	protected static PicksManager picksManager;
	
	//DAOs
	protected static IDao dao;
	protected static LeagueDao leagueDao;
	protected static PlayerDao playerDao;
	protected static TeamDao teamDao;
	protected static MessageBoardDao messageBoardDao;
	protected static GameDao gameDao;
	
	
	protected static Player tim;
	protected static Player jodie;
	protected static Player casper;
	
	protected static LeagueType footballLeagueType;
	protected static LeagueType suicideLeagueType;
	
	protected static Team giants;
	protected static Team cowboys;
	protected static Team eagles;
	protected static Team redskins;
	protected static Team jets;
	protected static Team dolphins;
	protected static Team patriots;
	protected static Team bills;
	
	protected static League football08League;
	protected static League football09League;
	protected static League football09FreeLeague;
	protected static League suicide08League;
	protected static League suicide09League;
	protected static League suicide08FreeLeague;
	
	protected static Season seasonFootball08;
	protected static Season seasonFootball09;
	protected static Season seasonSuicide08;
	protected static Season seasonSuicide09;
	
	protected static Week week1;
	protected static Week week2;
	protected static Week week1Suicide;
	protected static Week week2Suicide;
	
	protected static Game game1Week1;
	protected static Game game2Week1;
	protected static Game game3Week1;
	protected static Game game4Week1;
	
	public AbstractTestCase()
	{
		if(isInitial)
		{
			loadContext();
			setupJNDIReferences();
			init();
			isInitial = false;					
		}
	}
	protected void loadContext() {
		context = new ClassPathXmlApplicationContext(new String[] { 
				"info/makeyourpicks/context/property-context.xml",
				"info/makeyourpicks/context/service-context.xml",
				"info/makeyourpicks/context/database-context.xml",
//				"info/makeyourpicks/context/league-context.xml",
				"info/makeyourpicks/context/unittest-context.xml"
		});
	}
	
	public void setupJNDIReferences()
	{
//		loadTestContext = new ClassPathXmlApplicationContext(new String[] { 
//				
//		});
		
//		SimpleNamingContextBuilder builder=null;
//		try
//		{
//			builder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
//		}
//		catch (NamingException exception)
//		{
//			fail();
//		}
//
//		DataSource ds = (DataSource) context.getBean("unitTestdataSource");
//		builder.bind("java:comp/env/jdbc/leagues", ds);
	}
	public void init() 
	{		
		dao = (IDao)context.getBean("dao");
		leagueDao = (LeagueDao)context.getBean("leagueDao");
		playerDao = (PlayerDao)context.getBean("playerDao");
		teamDao = (TeamDao)context.getBean("teamDao");
		gameDao = (GameDao)context.getBean("gameDao");
		messageBoardDao = (MessageBoardDao)context.getBean("messageBoardDao");
		
		leagueManager = (LeagueManagerHibernate)context.getBean("leagueManager");
		playerManager = (PlayerManagerHibernate)context.getBean("playerManager");
		messageBoardManager = (MessageBoardManagerHibernate)context.getBean("messageBoardManager");
		gameManager = (GameManager)context.getBean("gameManager");
		picksManager = (PicksManagerHibernate)context.getBean("picksManager");
		teamManager = (TeamManagerHibernate)context.getBean("teamManager");
	};
	
	
	/**
	 * Set up test
	 * 
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * Tear down test
	 * 
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Automatically tests and asserts a Persistable object
	 * 
	 * @param persistable
	 */
	protected void saveUpdateDeleteTestObject(IPersistable IPersistable) {
		// save object
		saveTestObject(IPersistable);
		
		// update object
		updateTestObject(IPersistable);
		
		// delete object
		deleteTestObject(IPersistable);
	}

	/**
	 * Save a IPersistable object
	 * 
	 * @param IPersistable
	 */
	protected void saveTestObject(IPersistable IPersistable) {
		saveOrUpdateTestObject(IPersistable);
	}

	/**
	 * Update a IPersistable object
	 * 
	 * @param IPersistable
	 */
	protected void updateTestObject(IPersistable IPersistable) {
		saveOrUpdateTestObject(IPersistable);
	}

	/**
	 * Save or Update a IPersistable object
	 * 
	 * @param IPersistable
	 */
	protected void saveOrUpdateTestObject(IPersistable IPersistable) {
		// assert not null
		assertNotNull(IPersistable);
		
		// save object
		dao.saveOrUpdate(IPersistable);
			
		// assert object loaded
		assertNotNull(loadTestObject(IPersistable));	
	}
	
	/**
	 * Load a IPersistable object from the database
	 * 
	 * @param IPersistable
	 * @return Object
	 */
	protected Object loadTestObject(IPersistable IPersistable) {
		// assert not null
		assertNotNull(IPersistable);
		
		return dao.loadObject(IPersistable.getClass(), IPersistable.getPrimaryKey());
	}
	
	/**
	 * Delete a IPersistable object from the database
	 * 
	 * @param IPersistable
	 * @return Object
	 */
	protected void deleteTestObject(IPersistable IPersistable) {
		// assert not null
		assertNotNull(IPersistable);
		
		dao.deleteObject(IPersistable);
				
		// assert object deleted
		assertNull(loadTestObject(IPersistable));
	}
	
	/**
	 * Delete a List of IPersistable objects from the database
	 * 
	 * @param list
	 */
	protected void deleteTestObjects(List list) {
		for(Iterator it = list.iterator(); it.hasNext();)
			deleteTestObject((IPersistable)it.next());		
	}
		
	
	protected void createPlayers()
	{
		tim = new Player();
		tim = (Player)tim.createTestObject();
		tim.setUsername("tim");
		tim.setPassword("rage311");
		tim.setMemberLevel(1);
		dao.save(tim);
		
		jodie = new Player();
		jodie = (Player)jodie.createTestObject();
		jodie.setUsername("jodie");
		jodie.setPassword("football");
		jodie.setMemberLevel(2);
		dao.save(jodie);
		
		casper = new Player();
		casper = (Player)casper.createTestObject();
		casper.setUsername("casper");
		casper.setPassword("casper");
		dao.save(casper);
	}

	protected void createLeagueTypes()
	{
		footballLeagueType = new LeagueType();
		footballLeagueType.setEnd(new Date(System.currentTimeMillis()));
		footballLeagueType.setStart(new Date(System.currentTimeMillis()));
		footballLeagueType.setLeagueTypeDisplay("NFL Football");
		footballLeagueType.setTypeOfLeague("football");
		dao.save(footballLeagueType);
		
		suicideLeagueType = new LeagueType();
		suicideLeagueType.setEnd(new Date(System.currentTimeMillis()));
		suicideLeagueType.setStart(new Date(System.currentTimeMillis()));
		suicideLeagueType.setLeagueTypeDisplay("NFL Suicide");
		suicideLeagueType.setTypeOfLeague("suicide");
		suicideLeagueType.setParentTypeOfLeague("football");
		dao.save(suicideLeagueType);
	}
	
	protected void createTeams()
	{
		giants = new Team();
		giants.setCity("New York");
		giants.setShortName("NYG");
		giants.setTeamName("Giants");
		giants.setLeagueType(footballLeagueType);
		teamManager.insertTeam(giants);
		
		eagles = new Team();
		eagles.setCity("Philidelphia");
		eagles.setShortName("PHI");
		eagles.setTeamName("Eagles");
		eagles.setLeagueType(footballLeagueType);
		teamManager.insertTeam(eagles);
		
		redskins = new Team();
		redskins.setCity("Washington");
		redskins.setShortName("WAS");
		redskins.setTeamName("Redskins");
		redskins.setLeagueType(footballLeagueType);
		teamManager.insertTeam(redskins);
		
		cowboys = new Team();
		cowboys.setCity("Dallas");
		cowboys.setShortName("DAL");
		cowboys.setTeamName("Cowboys");
		cowboys.setLeagueType(footballLeagueType);
		teamManager.insertTeam(cowboys);
		
		jets = new Team();
		jets.setCity("New York");
		jets.setShortName("NYJ");
		jets.setTeamName("Jets");
		jets.setLeagueType(footballLeagueType);
		teamManager.insertTeam(jets);
		
		patriots = new Team();
		patriots.setCity("New England");
		patriots.setShortName("NE");
		patriots.setTeamName("Patriots");
		patriots.setLeagueType(footballLeagueType);
		teamManager.insertTeam(patriots);
		
		dolphins = new Team();
		dolphins.setCity("Miami");
		dolphins.setShortName("MIA");
		dolphins.setTeamName("Dolphins");
		dolphins.setLeagueType(footballLeagueType);
		teamManager.insertTeam(dolphins);
		
		bills = new Team();
		bills.setCity("Buffalo");
		bills.setShortName("BUF");
		bills.setTeamName("Bills");
		bills.setLeagueType(footballLeagueType);
		teamManager.insertTeam(bills);
	}
	
	protected void createSeasons()
	{
		seasonFootball08 = new Season();
		seasonFootball08.setEndYear(2009);
		seasonFootball08.setStartYear(2008);
		seasonFootball08.setLeagueType(footballLeagueType);
		dao.save(seasonFootball08);
		
		seasonFootball09 = new Season();
		seasonFootball09.setEndYear(2010);
		seasonFootball09.setStartYear(2009);
		seasonFootball09.setLeagueType(footballLeagueType);
		dao.save(seasonFootball09);
		
		seasonSuicide08 = new Season();
		seasonSuicide08.setEndYear(2009);
		seasonSuicide08.setStartYear(2008);
		seasonSuicide08.setLeagueType(suicideLeagueType);
		dao.save(seasonSuicide08);
		
		seasonSuicide09 = new Season();
		seasonSuicide09.setEndYear(2010);
		seasonSuicide09.setStartYear(2009);
		seasonSuicide09.setLeagueType(suicideLeagueType);
		dao.save(seasonSuicide09);
	}
	
	protected void deleteSeasons()
	{
		dao.deleteObject(seasonFootball08);
		dao.deleteObject(seasonFootball09);
		dao.deleteObject(seasonSuicide08);
		dao.deleteObject(seasonSuicide09);
	}
	
	protected void createLeagues()
	{
		football08League = new League();
		football08League.setActive(true);
		football08League.setAdmin(tim);
		football08League.setFree(false);
		football08League.setLeagueName("08-09 NFL Football");
		football08League.setMaxSize(50);
		football08League.setMoney(true);
		football08League.setPassword("money");
		football08League.setSeason(seasonFootball08);
		football08League.setSpreads(true);
		dao.save(football08League);
		
		football09League = new League();
		football09League.setActive(true);
		football09League.setAdmin(tim);
		football09League.setFree(false);
		football09League.setLeagueName("09-10 NFL Football");
		football09League.setMaxSize(50);
		football09League.setMoney(true);
		football09League.setPassword("money");
		football09League.setSeason(seasonFootball09);
		football09League.setSpreads(true);
		dao.save(football09League);
		
		football09FreeLeague = new League();
		football09FreeLeague.setActive(true);
		football09FreeLeague.setAdmin(tim);
		football09FreeLeague.setFree(true);
		football09FreeLeague.setLeagueName("09-10 NFL Free Football");
		football09FreeLeague.setMaxSize(50);
		football09FreeLeague.setMoney(false);
		football09FreeLeague.setSeason(seasonFootball09);
		football09FreeLeague.setSpreads(true);
		dao.save(football09FreeLeague);

		suicide08League = new League();
		suicide08League.setActive(true);
		suicide08League.setAdmin(tim);
		suicide08League.setFree(false);
		suicide08League.setLeagueName("08-09 Suicide");
		suicide08League.setMaxSize(50);
		suicide08League.setMoney(true);
		suicide08League.setPassword("money");
		suicide08League.setSeason(seasonSuicide08);
		suicide08League.setSpreads(false);
		dao.save(suicide08League);
		
		suicide09League = new League();
		suicide09League.setActive(true);
		suicide09League.setAdmin(tim);
		suicide09League.setFree(false);
		suicide09League.setLeagueName("09-10 Suicide");
		suicide09League.setMaxSize(50);
		suicide09League.setMoney(true);
		suicide09League.setPassword("money");
		suicide09League.setSeason(seasonSuicide09);
		suicide09League.setSpreads(true);
		dao.save(suicide09League);
		
		suicide08FreeLeague = new League();
		suicide08FreeLeague.setActive(true);
		suicide08FreeLeague.setAdmin(tim);
		suicide08FreeLeague.setFree(true);
		suicide08FreeLeague.setLeagueName("08-09 Free Suicide");
		suicide08FreeLeague.setMaxSize(50);
		suicide08FreeLeague.setMoney(true);
		suicide08FreeLeague.setPassword("money");
		suicide08FreeLeague.setSeason(seasonSuicide08);
		suicide08FreeLeague.setSpreads(false);
		dao.save(suicide08FreeLeague);
		
		
	}
	
	protected void deleteLeagues()
	{
		dao.deleteObject(suicide08FreeLeague);
		dao.deleteObject(suicide09League);
		dao.deleteObject(suicide08League);
		dao.deleteObject(football08League);
		dao.deleteObject(football09FreeLeague);
		dao.deleteObject(football09League);
	}
	
	protected void deletePlayers()
	{
		dao.deleteObject(tim);
		dao.deleteObject(jodie);
		dao.deleteObject(casper);
	}
	
	protected void deleteLeagueTypes()
	{
		dao.deleteObject(footballLeagueType);
		dao.deleteObject(suicideLeagueType);
	}
	protected void deleteTeams()
	{
		dao.deleteObject(redskins);
		dao.deleteObject(giants);
		dao.deleteObject(eagles);
		dao.deleteObject(cowboys);
		dao.deleteObject(jets);
		dao.deleteObject(patriots);
		dao.deleteObject(dolphins);
		dao.deleteObject(bills);
	}
	
	protected void createWeeks()
	{
		week1 = new Week();
		week1.setWeekStart(new Date(System.currentTimeMillis()));
		week1.setSeason(seasonFootball09);
		week1.setWeekNumber(1);
		dao.save(week1);
		
		week2 = new Week();
		week2.setWeekStart(new Date(System.currentTimeMillis()));
		week2.setSeason(seasonFootball09);
		week2.setWeekNumber(2);
		dao.save(week2);
		
		week1Suicide = new Week();
		week1Suicide.setWeekStart(new Date(System.currentTimeMillis()));
		week1Suicide.setSeason(seasonSuicide09);
		week1Suicide.setWeekNumber(2);
		dao.save(week1Suicide);
		
		week2Suicide = new Week();
		week2Suicide.setWeekStart(new Date(System.currentTimeMillis()));
		week2Suicide.setSeason(seasonSuicide09);
		week2Suicide.setWeekNumber(2);
		dao.save(week2Suicide);
	}
	
	protected void deleteWeeks()
	{
		dao.deleteObject(week1);
		dao.deleteObject(week2);
		dao.deleteObject(week1Suicide);
		dao.deleteObject(week2Suicide);
	}
	
	protected void createGames()
	{
		game1Week1 = new Game();
		game1Week1.setDog(cowboys);
		game1Week1.setFav(giants);
		game1Week1.setGameStart(new Date(System.currentTimeMillis()+(7*24*60*60*1000)));
		game1Week1.setSpread(5.5);
		game1Week1.setWeek(week1);
		dao.save(game1Week1);
		
		game2Week1 = new Game();
		game2Week1.setDog(eagles);
		game2Week1.setFav(redskins);
		game2Week1.setGameStart(new Date(System.currentTimeMillis()+(7*24*60*60*1000)));
		game2Week1.setSpread(2.5);
		game2Week1.setWeek(week1);
		dao.save(game2Week1);
		
		game3Week1 = new Game();
		game3Week1.setDog(jets);
		game3Week1.setFav(bills);
		game3Week1.setGameStart(new Date(System.currentTimeMillis()+(7*24*60*60*1000)));
		game3Week1.setSpread(0.5);
		game3Week1.setWeek(week1);
		dao.save(game3Week1);
		
		game4Week1 = new Game();
		game4Week1.setDog(patriots);
		game4Week1.setFav(dolphins);
		game4Week1.setGameStart(new Date(System.currentTimeMillis()+(7*24*60*60*1000)));
		game4Week1.setSpread(7.5);
		game4Week1.setWeek(week1);
		dao.save(game4Week1);
	}
	
	protected void deleteGames()
	{
		dao.deleteObject(game1Week1);
		dao.deleteObject(game2Week1);
		dao.deleteObject(game3Week1);
		dao.deleteObject(game4Week1);
	}
}