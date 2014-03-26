package info.makeyourpicks.service;

import info.makeyourpicks.model.MessageBoard;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.test.AbstractTestCase;

import org.junit.Assert;
import org.junit.Test;

public class MessageBoardManagerTest extends AbstractTestCase{



	@Test
	public void testPostMessagePrivate() {
		MessageBoard messageBoard = new MessageBoard();
		messageBoard = (MessageBoard)messageBoard(tim);
		messageBoard.setLeagueName(football09League);
		messageBoard.setFromPlayer(tim);
		messageBoard.setToPlayer(jodie);
		
		messageBoardManager.postMessage(messageBoard);
		
		Assert.assertEquals(messageBoard, messageBoardManager.getAllMessagesForUser(jodie).iterator().next());
		
		messageBoardManager.deleteMessage(messageBoard);
		Assert.assertTrue(messageBoardManager.getAllMessagesForUser(jodie).isEmpty());
	}
	
	@Test
	public void testPostMessageLeague() {
		MessageBoard messageBoard = new MessageBoard();
		messageBoard = (MessageBoard)messageBoard(tim);
		messageBoard.setLeagueName(football09League);
		messageBoard.setFromPlayer(jodie);
		Player leagueBroadcast = new Player();
		leagueBroadcast.setUsername(football09League.getLeagueName());
		messageBoard.setToPlayer(leagueBroadcast);
		
		messageBoardManager.postMessage(messageBoard);
		
		Assert.assertEquals(messageBoard, messageBoardManager.getAllMessagesForUser(tim).iterator().next());
		
		messageBoardManager.deleteMessage(messageBoard);
		Assert.assertTrue(messageBoardManager.getAllMessagesForUser(tim).isEmpty());
	}
	
//	@Test
//	public void testPostMessageAll() {
//		MessageBoard messageBoard = new MessageBoard();
//		messageBoard = (MessageBoard)messageBoard(tim);
//		messageBoard.setLeagueName(football08League);
//		messageBoard.setFromPlayer(jodie);
//		Player allBroadcast = new Player();
//		allBroadcast.setUsername("All Leagues");
//		messageBoard.setToPlayer(allBroadcast);
//		
//		messageBoardManager.postMessage(messageBoard);
//		
//		Assert.assertEquals(messageBoard, messageBoardManager.getAllMessagesForUser(tim).iterator().next());
//		
//		messageBoardManager.deleteMessagesForPlayer(messageBoard, jodie);
//		messageBoardManager.deleteMessage(messageBoard);
//		Assert.assertTrue(messageBoardManager.getAllMessagesForUser(tim).isEmpty());
//	}
}
