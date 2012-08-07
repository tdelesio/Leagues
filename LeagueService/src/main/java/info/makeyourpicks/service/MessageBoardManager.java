package info.makeyourpicks.service;

import info.makeyourpicks.model.MessageBoard;
import info.makeyourpicks.model.Player;

import java.util.List;

public interface MessageBoardManager{
	
	/**
	 * This method will get all messages for the user.
	 * @param email
	 * @return List<MessageBoard>
	 * @throws SystemException
	 */
	@SuppressWarnings("unchecked")
	public List<MessageBoard> getAllMessagesForUser(Player player);
	
	
	/**
	 * This method will post messages to the message board.
	 * @param messageBoardInfo
	 * @throws SystemException
	 */
	public void postMessage(MessageBoard messageBoardInfo);

	/**
	 * Deletes the message based on messageid
	 * @param id
	 * @throws SystemException
	 */
	public void deleteMessage(MessageBoard message);
	public void deleteMessagesForPlayer(MessageBoard messageBoard, Player player);
	public MessageBoard loadMessageBoard(long id);
	public void markMessagesAsRead(Player player);
	public boolean hasNewMessages(Player player);
	
//	public List<MessageBoard> getAllPublicMessagesForUser(League league);
//	public List<MessageBoard> getAllPrivateMessagesForUser(Player player);
}
