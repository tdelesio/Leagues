package info.makeyourpicks.service;

import info.makeyourpicks.model.MessageBoard;
import info.makeyourpicks.model.Player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MessageBoardManagerTest extends AbstractServiceTestCase{

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testPostMessagePrivate() {
		MessageBoard messageBoard = new MessageBoard();
		messageBoard = (MessageBoard)messageBoard.createTestObject();
		messageBoard.setLeagueName(league1);
		messageBoard.setFromPlayer(player);
		messageBoard.setToPlayer(player);
		
		messageBoardManager.postMessage(messageBoard);
		
		assertEquals(messageBoard, messageBoardManager.getAllMessagesForUser(player).iterator().next());
		
		messageBoardManager.deleteMessage(messageBoard);
		assertTrue(messageBoardManager.getAllMessagesForUser(player).isEmpty());
	}
	
	@Test
	public void testPostMessageLeague() {
		MessageBoard messageBoard = new MessageBoard();
		messageBoard = (MessageBoard)messageBoard.createTestObject();
		messageBoard.setLeagueName(league1);
		messageBoard.setFromPlayer(player);
		Player leagueBroadcast = new Player();
		leagueBroadcast.setUsername(league1.getLeagueName());
		messageBoard.setToPlayer(leagueBroadcast);
		
		messageBoardManager.postMessage(messageBoard);
		
		assertEquals(messageBoard, messageBoardManager.getAllMessagesForUser(player).iterator().next());
		
		messageBoardManager.deleteMessage(messageBoard);
		assertTrue(messageBoardManager.getAllMessagesForUser(player).isEmpty());
	}
	
	@Test
	public void testPostMessageAll() {
		MessageBoard messageBoard = new MessageBoard();
		messageBoard = (MessageBoard)messageBoard.createTestObject();
		messageBoard.setLeagueName(league1);
		messageBoard.setFromPlayer(player);
		Player allBroadcast = new Player();
		allBroadcast.setUsername("All Leagues");
		messageBoard.setToPlayer(allBroadcast);
		
		messageBoardManager.postMessage(messageBoard);
		
		assertEquals(messageBoard, messageBoardManager.getAllMessagesForUser(player).iterator().next());
		
		messageBoardManager.deleteMessagesForPlayer(messageBoard, player);
		messageBoardManager.deleteMessage(messageBoard);
		assertTrue(messageBoardManager.getAllMessagesForUser(player).isEmpty());
	}
}
