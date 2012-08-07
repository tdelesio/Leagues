package com.delesio.service;

import com.delesio.model.Email;

/**
 * IEmailService.java
 * 
 * @version Oct 23, 2007
 * @author Anthony DePalma
 */
public interface IEmailService {

 /**
  * Saves an email for later delivery
  * 
  * @param email
  */
 public void saveEmail(final Email email);
 
 /**
  * Sends a email
  * 
  * @param email
  * @return true if the mail was successfully sent; false otherwise
  */
 public boolean sendEmail(final Email email);
 
 /**
  * Sends a email
  * 
  * @param email
  * @param persistOnFail
  * @return true if the mail was successfully sent; false otherwise
  */
 public boolean sendEmail(final Email email, final boolean persistOnFail);
 
 /**
  * Sends a email
  * 
  * @param email
  * @param attempts
  * @return true if the mail was successfully sent; false otherwise
  */
 public boolean sendEmail(final Email email, final int attempts);
 
 /**
  * Sends a email with the specified amount of attempts in the event of a MailException
  * 
  * @param email
  * @param attempts
  * @param persistOnFail
  * @return true if the mail was successfully sent; false otherwise
  */
 public boolean sendEmail(final Email email, final int attempts, final boolean persistOnFail);
 
}