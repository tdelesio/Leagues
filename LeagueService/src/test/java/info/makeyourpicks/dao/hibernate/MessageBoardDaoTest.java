package info.makeyourpicks.dao.hibernate;

import info.makeyourpicks.model.MessageBoard;
import info.makeyourpicks.model.PlayerMessages;

import java.util.List;

import org.junit.Test;

public class MessageBoardDaoTest extends LeagueDaoTest {

	protected static MessageBoard messageBoard = new MessageBoard();
	protected static PlayerMessages playerMessages = new PlayerMessages();
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		messageBoard = (MessageBoard)messageBoard.createTestObject();
		messageBoard.setLeagueName(league);
		messageBoard.setFromPlayer(player);
		
		
		playerMessages.setPlayer(player);
		playerMessages.setMessage(messageBoard);
		dao.save(messageBoard);
		dao.save(playerMessages);
		
		
	}

	@Override
	protected void tearDown() throws Exception {
		
		dao.deleteObject(playerMessages);
		dao.deleteObject(messageBoard);

		//dao.deleteObject(messageBoard.getFromPlayer());
		super.tearDown();
	}
	
//	@Test
//	public void test_findPrivateMessages()
//	{
//		assertEquals(true, messageBoardDao.findPrivateMessages(messageBoard.getTo()).size()>0);
//	}
//	
//	@Test
//	public void test_findPublicMessagesForLeague()
//	{
//		messageBoard.setTo("*");
//		dao.saveOrUpdate(messageBoard);
//		assertEquals(true, messageBoardDao.findPublicMessages(messageBoard.getLeagueName()).size()>0);
//	}
//	
//	@Test
//	public void test_findPublicMessagesForStar()
//	{
//		messageBoard.setTo("*");
//		messageBoard.setLeagueName("*");
//		dao.saveOrUpdate(messageBoard);
//		assertEquals(true, messageBoardDao.findPublicMessages("somevalue").size()>0);
//	}
	
	@Test
	public void test_findMessagesForPlayer()
	{
		List<MessageBoard> messages = messageBoardDao.findMessagesForPlayer(player);
		assertEquals(messageBoard, messages.get(0));
	}
	
}
