package info.makeyourpicks.service.impl;

import info.makeyourpicks.dao.PlayerDao;
import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.service.ILeaguesEmailService;
import info.makeyourpicks.service.PlayerManager;

import java.util.List;

import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.delesio.service.impl.AbstractService;

public class PlayerManagerHibernate extends AbstractService implements PlayerManager {

	protected PlayerDao playerDao;
	protected ILeaguesEmailService emailService;
	protected PasswordEncoder passwordEncoder;

	
	private String usernameRetrievalSubject;
	private String usernameRetrievalTemplate;
	
	public void init()
	{
		
	}
	
	public void deleteUser(String name) {
		
		Player player = getPlayer(name);
		dao.deleteObject(player);

	}

	
	
	public void setPasswordEncoder(PasswordEncoder passwordEncoder)
	{
		this.passwordEncoder = passwordEncoder;
	}

	public List<Player> getAllPlayers()
	{
		return dao.loadAllObjects(Player.class);
	}

	public Player getPlayer(String name) {
		Player player = playerDao.findPlayerByName(name);
		return player;
//		return (Player)dao.loadObject(Player.class, 1);
	}

	public Player getPlayerByEmail(String email)
	{
		return playerDao.findPlayerByEmail(email);
	}
//	public void insertPlayerProfile(Player playerInfo)
//	{
//		dao.save(playerInfo);
//
//	}

//	public void updatePlayerProfile(Player playerInfo) {
//		dao.updateObject(playerInfo);
//	}

	public void createUpdatePlayer(Player player)
	{
		dao.saveOrUpdate(player);
	}
	
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		Player player = getPlayer(name);
		if (player == null)
		{
			throw new UsernameNotFoundException(name+"username not found.");
		}
		else
		{
			return player;
		}
	}

	public PlayerDao getPlayerDao() {
		return playerDao;
	}

	public void setPlayerDao(PlayerDao playerDao) {
		this.playerDao = playerDao;
	}


	public List<Player> getPlayersInLeague(League league) {
		return playerDao.findPlayersInLeague(league);
	}
	
	public Player loadPlayer(long playerId)
	{
		return (Player)dao.loadObject(Player.class, playerId);
	}
	
	@Deprecated
	public boolean requestPassword(Player player)
	{
		Player lookedupPlayer;
		if (player.getUsername()!=null && !player.getUsername().equals(""))
		{
			lookedupPlayer = getPlayer(player.getUsername());
		}
		else
		{
			lookedupPlayer = getPlayerByEmail(player.getEmail());
		}
		
		if (lookedupPlayer==null)
		{
			return false;
		}
//		ContactInfo contactInfo = new ContactInfo();
//		contactInfo.setFromAddress(EmailConstants.DEFAULT_EMAIL);
//		contactInfo.setToAddress(lookedupPlayer.getEmail());
//		contactInfo.setSubject(EmailConstants.LOST_PASSWORD_SUBJECT);
//		contactInfo.setLostPasswordBody(lookedupPlayer.getUsername(), lookedupPlayer.getPassword());
//		
//		sendMimeEmail(contactInfo);
		return true;
//		contactInfo.setInviteBody(league, fromEmail, EmailConstants.INVITE_MSG);
		
	}

	public boolean retrievePassword(Player emailOrUsername)
	{
		Player player = getPlayer(emailOrUsername.getUsername());
		if (player==null)
		{
			player = getPlayerByEmail(emailOrUsername.getEmail());
		}
		
		if (player==null)
		{
			return false;
		}
		else
		{
//			try
//			{
//				final Player p = player;
//				sendEmail(new MimeMessagePreparator() {
//					public void prepare(MimeMessage mimeMessage) throws Exception {
//						MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
//
//						Map context = new HashMap();
//						context.put("username", p.getUsername());
//						context.put("password", p.getPassword());
//						
//						message.setTo(p.getEmail());
//			            message.setFrom(getFromEmail()); 
//			            message.setSubject(getUsernameRetrievalSubject());
//			            message.setText(VelocityEngineUtils.mergeTemplateIntoString(
//			            		getVelocityEngine(), getUsernameRetrievalTemplate(), context), true);
//					}
//				});
//				return true;
//			}
//			catch(MailException mailException) 
//			{    
//				mailException.printStackTrace();        
//			}
			return emailService.sendRecoverEmail(player.getEmail(), player);
			
		}
		
	}

	public String getUsernameRetrievalSubject()
	{
		return usernameRetrievalSubject;
	}

	public void setUsernameRetrievalSubject(String usernameRetrievalSubject)
	{
		this.usernameRetrievalSubject = usernameRetrievalSubject;
	}

	public String getUsernameRetrievalTemplate()
	{
		return usernameRetrievalTemplate;
	}

	public void setUsernameRetrievalTemplate(String usernameRetrievalTemplate)
	{
		this.usernameRetrievalTemplate = usernameRetrievalTemplate;
	}

	public void setEmailService(ILeaguesEmailService emailService)
	{
		this.emailService = emailService;
	}

	public List<Player> getPlayersByFacebookId(long facebookID)
	{
		return playerDao.findPlayersByFacebookId(facebookID);
	}
	


}
