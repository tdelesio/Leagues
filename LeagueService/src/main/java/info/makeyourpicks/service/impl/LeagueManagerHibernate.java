package info.makeyourpicks.service.impl;

import info.makeyourpicks.dao.IPaymentDao;
import info.makeyourpicks.dao.LeagueDao;
import info.makeyourpicks.model.League;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Payment;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.PlayerLeague;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.service.GameManager;
import info.makeyourpicks.service.ILeaguesEmailService;
import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.service.PlayerManager;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.delesio.cache.action.ICacheCreateAction;
import com.delesio.service.impl.AbstractService;
import com.sun.corba.se.pept.transport.ContactInfo;

public class LeagueManagerHibernate extends AbstractService implements
		LeagueManager {

	protected LeagueDao leagueDao;
	protected IPaymentDao paymentDao;
	protected ILeaguesEmailService emailService;
	protected PlayerManager playerManager;
	protected GameManager gameManager;
	
	private String joinSubject;
	private String joinTemplate;
	private String receiptSubject;
	private String receiptTemplate;
	
	protected void init()
	{
	}
	
	public void createPayment(Payment payment) {
		dao.save(payment);
	}
	
	public List findMasterLeagueTypes()
	{
		return leagueDao.findMasterLeagueTypes();
	}
	
//	public void createLeague(League league)
//	{
//		dao.save(league);	
//	}

	public void deleteLeague(League leagueInfo){
		dao.deleteObject(leagueInfo);
	}
	
//	public void updateLeague(League leagueInfo){
//		dao.updateObject(leagueInfo);
//
//	}
	
	public void createLeague(final League league)
	{
		
		TransactionTemplate transactionTemplate = new TransactionTemplate(this.transactionManager);
		transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);   
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			public void doInTransactionWithoutResult(TransactionStatus status) {
				playerManager.createUpdatePlayer(league.getAdmin());
				createOrUpdateLeague(league);
				
				Week week = new Week();
				week.setSeason(league.getSeason());
				week.setWeekNumber(1);
				
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
				calendar.set(Calendar.DATE, 1);
				calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
				week.setWeekStart(calendar.getTime());
				gameManager.insertWeek(week);				
				addPlayerToLeague(league, league.getAdmin());
			}
		});	
		
		try
		{
			sendInviteEmail(league.getInvitedPlayersEmailAddress(), league);
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}
	
	public void createOrUpdateLeague(League league)
	{
		dao.saveOrUpdate(league);
	}
	
	public League getLeague(long leagueId)
	{
		return (League) dao.loadObject(League.class, leagueId);
	}
	
	public void addPlayerToLeague(League league, Player player)
	{
		PlayerLeague playerLeague = new PlayerLeague();
		playerLeague.setPlayer(player);
		playerLeague.setLeague(league);
		
		addPlayerToLeague(playerLeague);
		
		cacheProvider.remove(LEAGUE_FOR_PLAYER, player);
	}
	
	public void addPlayerToLeague(PlayerLeague playerLeague)
	{
		dao.save(playerLeague);
		
		cacheProvider.remove(LEAGUE_FOR_PLAYER, playerLeague.getPlayer());
	}
	
	public void removePlayerFromLeague(PlayerLeague playerLeague)
	{
		dao.deleteObject(playerLeague);
		
		cacheProvider.remove(LEAGUE_FOR_PLAYER, playerLeague.getPlayer());
	}
	
	public void removePlayerFromLeague(League league, Player player)
	{
		leagueDao.removePlayerFromLeague(league, player);
		
		cacheProvider.remove(LEAGUE_FOR_PLAYER, player);
	}

	public List<League> getFreeLeagues() {
		return leagueDao.findFreeLeagues();
	}

	public List<LeagueType> getLeagueTypes() {
		return dao.loadAllObjects(LeagueType.class);
	}

	public List<League> getLeagues() {
		return dao.loadAllObjects(League.class);
	}

 
	public boolean validateLeague(League leagueInfo) {
		return getLeague(leagueInfo.getLeagueName()) != null;
	}

	
	
	public void sendInviteEmail(List<String> emails, final League league)
	{		
		final Iterator<String> iter = emails.iterator();
		while (iter.hasNext())
		{			
			emailService.sendInviteEmail(iter.next(), league);
		}		
	}

	public void createOrUpdateLeagueType(LeagueType leagueType) {
		dao.saveOrUpdate(leagueType);
		
	}

	public void deleteLeagueType(LeagueType leagueType) {
		dao.deleteObject(leagueType);
		
	}
	
	public LeagueType getLeagueType(String _leagueType)
	{
		return leagueDao.findLeagueType(_leagueType);
	}
	

	public League getLeague(String leagueName)
	{
		return leagueDao.findLeague(leagueName);
	}

	public LeagueDao getLeagueDao() {
		return leagueDao;
	}

	public void setLeagueDao(LeagueDao leagueDao) {
		this.leagueDao = leagueDao;
	}

	public List<League> getLeaguesBySeason(Season season)
	{
		return leagueDao.findLeaguesBySeason(season);
	}
	
	public List<League> getLeaguesForPlayer(final Player player)
	{
//		return (List<League>)cacheProvider.getOrPut(LEAGUE_FOR_PLAYER, player, new ICacheCreateAction()
//		{
//
//			public Object createCacheObject()
//			{
				return leagueDao.findLeaguesByPlayer(player);
//			}
//			
//		});
	}
	
	public List<League> getLeaguesForNavigation(final Player player)
	{
		return (List<League>)cacheProvider.getOrPut(LEAGUE_FOR_PLAYER, player, new ICacheCreateAction()
		{

			public Object createCacheObject()
			{
				return leagueDao.findLeaguesByPlayer(player, true);
			}
			
		});
		
	}

	public int getNumberOfPlayersInLeague(final League league) {
//		return (Integer)cacheProvider.getOrPut("league", league.getId(), new CacheCreateObjectAction()
//		{
//			public Object createCacheObject() {
				return leagueDao.findNumberOfPlayersInLeague(league);
//			}
//		});
	}
	


	public void makePayment(final Payment payment)
	{
		final League league = payment.getLeague();
		
		TransactionTemplate transactionTemplate = new TransactionTemplate(this.transactionManager);
		transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);   
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			public void doInTransactionWithoutResult(TransactionStatus status) {

				dao.updateObject(payment);
				league.setActive(true);
				createOrUpdateLeague(league);
			}
		});
		
	}

	public List<Payment> getPaymentsByLeague(League league)
	{
		return paymentDao.findPaymentsByLeague(league);
	}

	public IPaymentDao getPaymentDao()
	{
		return paymentDao;
	}

	public void setPaymentDao(IPaymentDao paymentDao)
	{
		this.paymentDao = paymentDao;
	}

	public String getJoinSubject()
	{
		return joinSubject;
	}

	public void setJoinSubject(String joinSubject)
	{
		this.joinSubject = joinSubject;
	}

	public String getJoinTemplate()
	{
		return joinTemplate;
	}

	public void setJoinTemplate(String joinTemplate)
	{
		this.joinTemplate = joinTemplate;
	}

	public String getReceiptSubject()
	{
		return receiptSubject;
	}

	public void setReceiptSubject(String receiptSubject)
	{
		this.receiptSubject = receiptSubject;
	}

	public String getReceiptTemplate()
	{
		return receiptTemplate;
	}

	public void setReceiptTemplate(String receiptTemplate)
	{
		this.receiptTemplate = receiptTemplate;
	}

	public void contactUs(final ContactInfo contactInfo)
	{

		
	}

	public LeagueType getLeagueType(long id)
	{
		return (LeagueType)dao.loadObject(LeagueType.class, id);
	}

	public LeagueType getLeagueTypeForSeason(Season season)
	{
		return leagueDao.getLeagueTypeForSeason(season);
	}
	
	public Season getSeason(LeagueType leagueType, int startYear ,int endYear)
	{
		return leagueDao.getSeason(leagueType, startYear, endYear);
	}

	public Season getSeason(String typeOfLeague, int startYear, int endYear)
	{
		return leagueDao.getSeason(typeOfLeague, startYear, endYear);
	}

	public List<Season> getCurrentSeasons()
	{
		
		return leagueDao.getCurrentSeasons();}
	
	public List<Season> getSeasons()
	{
		return dao.loadAllObjects(Season.class);
	}

	public void setEmailService(ILeaguesEmailService emailService)
	{
		this.emailService = emailService;
	}
	
	public List<Season> getParentSeasons()
	{
		return leagueDao.getParentSeasons();
	}

	public void createUpdateSeason(Season season)
	{
		dao.saveOrUpdate(season);
	}

	public Season getSeason(long id)
	{
		return (Season)dao.loadObject(Season.class, id);
	}
	

	public List<PlayerLeague> getPlayerLeaguesByPlayer(Player player)
	{
		return leagueDao.findPlayerLeaguesByPlayer(player);
	}

	public void updatePlayerLeague(PlayerLeague playerLeague)
	{
		dao.saveOrUpdate(playerLeague);
		cacheProvider.remove(LEAGUE_FOR_PLAYER, playerLeague.getPlayer());
	}

	public PlayerLeague loadPlayerLeague(long id)
	{
		return (PlayerLeague)dao.loadObject(PlayerLeague.class, id);
	}

	public void setPlayerManager(PlayerManager playerManager) {
		this.playerManager = playerManager;
	}
	
	public List<LeagueType> getChildLeagueTypesFromParent(String parentLeagueType)
	{
		return leagueDao.getLeagueTypesByParent(parentLeagueType);
	}
	
	public Set<Player> getPlayerEmailListForReadySeason(Season season)
	{
		List<League> leagues = getLeaguesBySeason(season);
		List<LeagueType> childLeagueTypes = getChildLeagueTypesFromParent(season.getLeagueType().getTypeOfLeague());
		for (LeagueType leagueType:childLeagueTypes)
		{
			Season childSeason = getSeason(leagueType, season.getStartYear(), season.getEndYear());
			List<League> childLeagues = getLeaguesBySeason(childSeason);
			leagues.addAll(childLeagues);
		}
		
		Set<Player> players = new HashSet<Player>();
		for (League league:leagues)
		{
			players.addAll(playerManager.getPlayersInLeague(league));
		}
		
		return players;
	}
	
	public boolean sendWeekSetupEmailToAllPlayers(Week week)
	{
		Set<Player> players = getPlayerEmailListForReadySeason(week.getSeason());
		return emailService.sendWeekSetupEmail(players, week);
	}

	public void setGameManager(GameManager gameManager) {
		this.gameManager = gameManager;
	}
	
	
}
