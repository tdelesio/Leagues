package info.makeyourpicks.service.impl;

import info.makeyourpicks.dao.MessageBoardDao;
import info.makeyourpicks.model.MessageBoard;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.PlayerMessages;
import info.makeyourpicks.service.MessageBoardManager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageBoardManagerHibernate extends PlayerManagerHibernate implements
		MessageBoardManager {

	protected MessageBoardDao messageBoardDao;

	public void deleteMessage(MessageBoard messageBoard) {
		messageBoardDao.deleteAllMessages(messageBoard);
		dao.deleteObject(messageBoard);
	}

	public MessageBoard getMessageBoard(long id)
	{
		return (MessageBoard)dao.loadObject(MessageBoard.class, id);
	}
	
	public List<MessageBoard> getAllMessagesForUser(Player player) {
//		List<MessageBoard> messages = getAllPublicMessagesForUser(league);
//		messages.addAll(getAllPrivateMessagesForUser(player));
		
//		return messages;
		
		return messageBoardDao.findMessagesForPlayer(player);
	}

//	public List<MessageBoard> getAllPrivateMessagesForUser(Player player) {
//		return messageBoardDao.findPrivateMessages(player);
//	}
//
//	public List<MessageBoard> getAllPublicMessagesForUser(League league) {
//		return messageBoardDao.findPublicMessages(league);
//	}

	private List<PlayerMessages> loadPlayerMessages(List<Player> players, MessageBoard messageBoard)
	{
		List<PlayerMessages> playerMessages;
		Iterator<Player> iterPlayers;
		iterPlayers = players.iterator();
		playerMessages = new ArrayList<PlayerMessages>(players.size());
		while (iterPlayers.hasNext())
		{
			PlayerMessages playerMessage = new PlayerMessages(iterPlayers.next(), messageBoard);
			playerMessages.add(playerMessage);
		}
		
		return playerMessages;
	}
	
	public void postMessage(MessageBoard messageBoardInfo) {
		messageBoardInfo.setPosted(new Timestamp(System.currentTimeMillis()));
		List<PlayerMessages> playerMessages;
		
		dao.save(messageBoardInfo);
		if (messageBoardInfo.isEntireLeague())
		{
			playerMessages = loadPlayerMessages(getAllPlayers(), messageBoardInfo);
		}
		else if (messageBoardInfo.getToPlayer().getUsername().equals(messageBoardInfo.getLeagueName().getLeagueName()))
		{
			playerMessages = loadPlayerMessages(getPlayersInLeague(messageBoardInfo.getLeagueName()), messageBoardInfo);
		}
		else
		{
			List<Player> players = new ArrayList<Player>(1);
			players.add(messageBoardInfo.getToPlayer());
			playerMessages = loadPlayerMessages(players, messageBoardInfo);
		}
		
		dao.saveAllObjects(playerMessages);

	}
	
	public void deleteMessagesForPlayer(MessageBoard messageBoard, Player player)
	{
		messageBoardDao.deleteMessageForPlayer(messageBoard, player);
	}

	public MessageBoardDao getMessageBoardDao() {
		return messageBoardDao;
	}

	public void setMessageBoardDao(MessageBoardDao messageBoardDao) {
		this.messageBoardDao = messageBoardDao;
	}
	
	public MessageBoard loadMessageBoard(long id)
	{
		return (MessageBoard)dao.loadObject(MessageBoard.class, id);
	}
	
	public List<MessageBoard> getNewMessages(Player player)
	{
		return messageBoardDao.findNewMessages(player);
	}
	
	public boolean hasNewMessages(Player player)
	{
		return !getNewMessages(player).isEmpty();
	}
	
	public void markMessagesAsRead(Player player)
	{
		messageBoardDao.markMessagesAsRead(player);
	}

}
