package info.makeyourpicks.test;

import info.makeyourpicks.dao.GameDao;
import info.makeyourpicks.dao.LeagueDao;
import info.makeyourpicks.dao.MessageBoardDao;
import info.makeyourpicks.dao.PlayerDao;
import info.makeyourpicks.dao.TeamDao;
import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.League;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.MessageBoard;
import info.makeyourpicks.model.Payment;
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

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.delesio.dao.IDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:context/unittest-context.xml"})
@TransactionConfiguration(defaultRollback=true)
public abstract class AbstractTestCase extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Resource(name="&mySessionFactory")	 
	protected LocalSessionFactoryBean factory;
	
	private Boolean printSql=false;
	
	@Resource(name="teamManager")
	protected TeamManager teamManager;
	
	@Resource(name="leagueManager")
	protected LeagueManager leagueManager;
	
	@Resource(name="playerManager")
	protected PlayerManager playerManager;
	
	@Resource(name="messageBoardManager")
	protected MessageBoardManager messageBoardManager;
	
	@Resource(name="gameManager")
	protected GameManager gameManager;
	
	@Resource(name="picksManager")
	protected PicksManager picksManager;
//	
//	//DAOs
	@Resource(name="dao")
	protected IDao dao;
	
	@Resource(name="leagueDao")
	protected LeagueDao leagueDao;
	
	@Resource(name="playerDao")
	protected PlayerDao playerDao;
	
	@Resource(name="teamDao")
	protected TeamDao teamDao;
	
	@Resource(name="messageBoardDao")
	protected MessageBoardDao messageBoardDao;
	
	@Resource(name="gameDao")
	protected GameDao gameDao;
//	
//	
	protected Player tim;
	protected Player jodie;
	protected Player casper;
	
	protected LeagueType footballLeagueType;
	protected LeagueType suicideLeagueType;
	
	protected Team giants;
	protected Team cowboys;
	protected Team eagles;
	protected Team redskins;
	protected Team jets;
	protected Team dolphins;
	protected Team patriots;
	protected Team bills;
	
	protected League football08League;
	protected League football09League;
	protected League football09FreeLeague;
	protected League suicide08League;
	protected League suicide09League;
	protected League suicide08FreeLeague;
	
	protected Season seasonFootball08;
	protected Season seasonFootball09;
	protected Season seasonSuicide08;
	protected Season seasonSuicide09;
	
	protected Week week1;
	protected Week week2;
	protected Week week1Suicide;
	protected Week week2Suicide;
	
	protected Game game1Week1;
	protected Game game2Week1;
	protected Game game3Week1;
	protected Game game4Week1;
	
	public void createDatabaseDefaults()
	{
		
		createLeagueTypes();
		createTeams();
		createSeasons();
		createPlayers();
		createLeagues();
		createWeeks();
		createGames();
		
		joinLeagues();
		
	}
	
	@Before
	public void recreateSchema()
	{
		System.out.println("**RECREATION SCHEMA**"); 
		factory.dropDatabaseSchema();
		factory.createDatabaseSchema();
		
		createDatabaseDefaults();
		
		if (printSql)
			System.out.println("*************BEGIN***************");
	}
	
	@Before
	public void printBeginMarker()
	{
		
	}
	
	@After
	public void printAfterMarker()
	{
		if (printSql)
			System.out.println("***********END*****************");
	}
	
	protected void createPlayers()
	{
		tim = player();
//		tim = (Player)tim.createTestObject();
		tim.setUsername("tim");
		tim.setPassword("rage311");
		tim.setMemberLevel(1);
		dao.save(tim);
		
		jodie = player();
//		jodie = (Player)jodie.createTestObject();
		jodie.setUsername("jodie");
		jodie.setPassword("football");
		jodie.setMemberLevel(2);
		dao.save(jodie);
		
		casper = player();
//		casper = (Player)casper.createTestObject();
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
	
	protected MessageBoard messageBoard(Player player)
	{
		MessageBoard messageBoard = new MessageBoard();
		messageBoard.setFromPlayer(player);
		messageBoard.setIcon("icon");
		messageBoard.setMessage("message");
		messageBoard.setPosted(new Timestamp(System.currentTimeMillis()));
		messageBoard.setStyle("style");
		return messageBoard;
	}
	
	protected void joinLeagues()
	{
		leagueManager.addPlayerToLeague(football09League, tim);
		leagueManager.addPlayerToLeague(football08League, tim);
	}
	
	protected Player player(String userId)
	{
		Player playerInfo = new Player(userId);
		playerInfo.setAddress1("address1");
		playerInfo.setCity("denville");
		playerInfo.setState("NJ");
		playerInfo.setZip("12345");
		return playerInfo;
	}
	
	protected Player player()
	{
		return player("userId"+String.valueOf(new Random().nextInt(1000)));
	}

	protected Payment payment()
	{
		Payment payment = new Payment();
		payment.setTransactionId("123456789");
		//payment.setTransactionDate(new Timestamp(System.currentTimeMillis()));
		payment.setAmountDue(100);
		payment.setAmountPaid(50);
		return payment;
	}
}