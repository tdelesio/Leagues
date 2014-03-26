package info.makeyourpicks.service.impl;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.service.ILeaguesEmailService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.velocity.app.VelocityEngine;

import com.delesio.email.EmailServiceJavaMail;
import com.delesio.model.Email;

public class LeaguesEmailService extends EmailServiceJavaMail implements ILeaguesEmailService
{
	private VelocityEngine velocityEngine;
	private String recoverTemplateName;
	private boolean persistOnFail=false;

	public boolean sendRecoverEmail(final String address, Player player)
	{
		final Map model = new HashMap();
		model.put("player", player);
		
		Email email = new Email();
		email.setTo(address);
		email.setFrom("admin@makeyourpicks.info");
		email.setSubject("Password Recovery");		
		email.setCode(PASSWORD_RECOVERY);
		email.setBcc("admin@makeyourpicks.info");
		email.setCc("admin@makeyourpicks.info");
		email.setReplyTo("admin@makeyourpicks.info");
		email.setText("Your password is: "+((Player)model.get("player")).getPassword());
		
		return sendEmail(email, persistOnFail);	
	}
	
//	private boolean buildEmail(final String address, final String bundleName,
//			final Map model, final Locale locale, final int code) {	
		
		// pull the resource bundle
//		final ResourceBundle resourceBundle = 
//			ResourceBundle.getBundle(bundleName, locale);
//		
//		final String from = resourceBundle.getString(FROM);
//		
//		if(from == null)
//			throw new IllegalArgumentException(FROM + " was not found in bundleName=["+bundleName+"]");
//		
//		final String subject = resourceBundle.getString(SUBJECT);
//		
//		if(subject == null)
//			throw new IllegalArgumentException(SUBJECT + " was not found in bundleName=["+bundleName+"]");
//		
//		final String template = resourceBundle.getString(TEMPLATE);
//		
//		if(template == null)
//			throw new IllegalArgumentException(TEMPLATE + " was not found in bundleName=["+bundleName+"]");
			
		// build email
		
//		try
//		{
//			email.setText(VelocityEngineUtils.mergeTemplateIntoString(
//					velocityEngine, template, model));
//		}
//		catch (VelocityException exception)
//		{
//			exception.printStackTrace();
//			return false;
//		}
		
//		return sendEmail(email, persistOnFail);		
//	}
	
	
	public boolean sendWeekSetupEmail(Set<Player> players, Week week)
	{
		Email email = new Email();
		email.setFrom("admin@makeyourpicks.info");
		email.setSubject("MYP Week "+week.getWeekNumber()+" is setup");		
		email.setCode(WEEK_SETUP);
//		email.setBcc("admin@makeyourpicks.info");
//		email.setCc("admin@makeyourpicks.info");
		email.setReplyTo("admin@makeyourpicks.info");
		email.setText("Week "+week.getWeekNumber()+" has been setup for your league.  Go to http://www.makeyourpicks.info/leagues to make your picks.");
		
		for (Player player:players)
		{
			email.setTo(player.getEmail());
			sendEmail(email, persistOnFail);
		}
		
		return true; 	
	}
	
	public boolean sendInviteEmail(String emailAddress, League league)
	{
//		final Map model = new HashMap();
//		model.put("player", player);		
		Email email = new Email();
		email.setTo(emailAddress);
		email.setFrom("admin@makeyourpicks.info");
		email.setSubject("Join This League!");		
		email.setCode(LEAGUE_INVITE);
		email.setBcc("admin@makeyourpicks.info");
		email.setCc("admin@makeyourpicks.info");
		email.setReplyTo("admin@makeyourpicks.info");
		
		
		StringBuffer content = new StringBuffer("Hey, ");
		content.append(emailAddress);
		content.append("\n");
		content.append("\n");
		content.append("You have been invited to a ");
		content.append(league.getSeason().displaySeason());
		content.append(" fantasy pool on MakeYourPicks.");  
		content.append("\n");
		content.append("\n");
		content.append("In order to join the league click on this link");
		content.append("\n");
		content.append("http://www.makeyourpicks.info/leagues/joinLeague.html?leagueName=");
		content.append(league.getLeagueName().replaceAll(" ", "%20"));
		content.append("&leaguePassword=");
		content.append(league.getPassword());
		content.append("\n");
		content.append("League Name:");
		content.append("\n");
		content.append(league.getLeagueName());
		content.append("\n");
		content.append("Password:");
		content.append("\n");
		content.append(league.getPassword());
		content.append("\n");
		content.append("League Admin,");
		content.append("Tim");
		email.setText(content.toString());
//System.out.println(content.toString());
//return true;
		return sendEmail(email, persistOnFail);	
	}
			
//			try
//			{
//				sendEmail(new MimeMessagePreparator() {
//					public void prepare(MimeMessage mimeMessage) throws Exception {
//						MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
//
//						Map context = new HashMap();
//						String toEmail = iter.next();
//						context.put("to_email", toEmail);
//						context.put("leagueName", league.getLeagueName());
//						context.put("leagueType", league.getLeagueType().getLeagueTypeDisplay());
//						context.put("leaguePassword", league.getPassword());
//						context.put("fromEmail", league.getAdmin().getEmail());
//						context.put("fromUsername", league.getAdmin().getUsername());
//						
//						message.setTo(toEmail);
//			            message.setFrom(getFromEmail()); 
//			            message.setSubject(getJoinSubject());
//			            message.setText(VelocityEngineUtils.mergeTemplateIntoString(
//			            		getVelocityEngine(), getJoinTemplate(), context), true);
//					}
//				});
//			}


	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}	
	public boolean isPersistOnFail() {
		return persistOnFail;
	}
	public void setPersistOnFail(boolean persistOnFail) {
		this.persistOnFail = persistOnFail;
	}

	public String getRecoverTemplateName()
	{
		return recoverTemplateName;
	}

	public void setRecoverTemplateName(String recoverTemplateName)
	{
		this.recoverTemplateName = recoverTemplateName;
	}
	
	
}
