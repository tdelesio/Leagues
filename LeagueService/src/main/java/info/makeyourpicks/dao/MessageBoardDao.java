package info.makeyourpicks.dao;

import info.makeyourpicks.model.MessageBoard;
import info.makeyourpicks.model.Player;

import java.util.List;

public interface MessageBoardDao {

//	public List findPublicMessages(League league);
//	public List findPrivateMessages(Player player);
	public List<MessageBoard> findMessagesForPlayer(Player player);
	public void deleteMessageForPlayer(MessageBoard messageBoard, Player player);
	public void deleteAllMessages(MessageBoard messageBoard);
	public List<MessageBoard> findNewMessages(Player player);
	public void markMessagesAsRead(Player player);
}
