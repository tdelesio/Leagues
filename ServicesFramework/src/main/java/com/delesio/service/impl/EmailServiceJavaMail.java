package com.delesio.service.impl;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.delesio.model.Email;
import com.delesio.service.IEmailService;

/**
 * EmailServiceJavaMailImpl.java
 *
 * @version Feb 8, 2008
 * @author Anthony DePalma
 */
public class EmailServiceJavaMail extends AbstractService implements IEmailService {
	private JavaMailSender mailSender;
	private int defaultAttempts = 1;	

	@Override
	public void init() {
//		super.init();
		
		if(mailSender == null)
			throw new IllegalArgumentException("mailSender should not be null");		
	}
	
	/**
	 * Returns a MimeMessagePreparator built from an Email object
	 * 
	 * @param email
	 * @return MimeMessagePreparator
	 */
	private static final MimeMessagePreparator getMimeMessagePreparator(final Email email) {
		return new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				
				if(email.getBcc() != null)
					message.setBcc(email.getBcc()); 
				
				if(email.getCc() != null)
					message.setCc(email.getCc());
				
				if(email.getFrom() != null)
					message.setFrom(email.getFrom()); 
				
				if(email.getReplyTo() != null)
					message.setReplyTo(email.getReplyTo());
				
				if(email.getSubject() != null)
					message.setSubject(email.getSubject()); 
				
				if(email.getText() != null)
					message.setText(email.getText(), email.isHtml());
				
				if(email.getTo() != null)
					message.setTo(email.getTo()); 
	      	}
		};
	}

	/**
	 * Saves an email for later delivery
	 * 
	 * @param email
	 */
	public void saveEmail(final Email email) {
		dao.save(email);
	}
	
	/**
	 * Sends a email
	 * 
	 * @param email
	 * @return true if the mail was successfully sent; false otherwise
	 */
	public boolean sendEmail(final Email email) {
		return sendEmail(email, defaultAttempts, false);
	}
	 
	/**
	 * Sends a email
	 * 
	 * @param email
	 * @param persistOnFail
	 * @return true if the mail was successfully sent; false otherwise
	 */
	public boolean sendEmail(final Email email, final boolean persistOnFail) {
		return sendEmail(email, defaultAttempts, persistOnFail);
	}
	
	/**
	 * Sends a email
	 * 
	 * @param email
	 * @param attempts
	 * @return true if the mail was successfully sent; false otherwise
	 */
	public boolean sendEmail(final Email email, final int attempts) {
		return sendEmail(email, attempts, false);
	}

	/**
	 * Sends a email with the specified amount of attempts in the event of a MailException
	 * 
	 * @param email
	 * @param attempts
	 * @param persistOnFail
	 * @return true if the mail was successfully sent; false otherwise
	 */
	public boolean sendEmail(final Email email, int attempts, final boolean persistOnFail) {
		MimeMessagePreparator mimeMessagePreparator = getMimeMessagePreparator(email);
		Exception exception = null;
		
		for(int i = 0; i<attempts; i++)
		{
			try
			{
				sendEmail(mimeMessagePreparator);
				return true;
			}
			catch(MailException mailException)
			{
				exception = mailException;
				email.addAttempToSend();
			}			
		}
		
		onUnsuccessfulSend(email, exception, persistOnFail);
		return false;
	}	
	
	/**
	 * Hook into an unsuccessful attempt
	 * 
	 * @param email
	 * @param mailException
	 * @param persistOnFail
	 */
	protected void onUnsuccessfulSend(final Email email, final Exception exception, final boolean persistOnFail) {
//		logger.info("["+this.getClass().getSimpleName()+"]" +
//				" Exception trying to send email with subject=["+email.getSubject()+"] to=["+email.getTo()+"]" +
//				" - " + exception.getMessage());	
		
		if(persistOnFail)
		{
			// save the reason it failed
			email.setFailureMessage(exception.getMessage());
			
			// save the unsent email
			dao.save(email);
		}
	}
	
	/**
	 * Sends a email prepared through a MimeMessagePreparator
	 * 
	 * @param mimeMessagePreparator
	 */
	private void sendEmail(final MimeMessagePreparator mimeMessagePreparator) throws MailException {
		this.mailSender.send(mimeMessagePreparator);	
	}

	// getters and setters
	public int getDefaultAttempts() {
		return defaultAttempts;
	}
	public void setDefaultAttempts(int defaultAttempts) {
		this.defaultAttempts = defaultAttempts;
	}
	public JavaMailSender getMailSender() {
		return mailSender;
	}
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}		
	
}