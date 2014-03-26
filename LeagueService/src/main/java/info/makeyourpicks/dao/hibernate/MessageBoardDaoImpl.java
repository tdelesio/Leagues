package info.makeyourpicks.dao.hibernate;

import info.makeyourpicks.dao.MessageBoardDao;
import info.makeyourpicks.model.MessageBoard;
import info.makeyourpicks.model.Player;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.delesio.dao.hibernate.HibernateDao;

public class MessageBoardDaoImpl extends HibernateDao implements
		MessageBoardDao {

//	public List findPublicMessages(League league) {
//		return hibernateTemplate.find("from MessageBoard where multibroadcast=true or (broadcast = true and league_id=?)", league.getId());
//	}
//	public List findPrivateMessages(Player player) {
//		return hibernateTemplate.find("from MessageBoard where to_email_id=?", player.getId());
//	}
	
	public List<MessageBoard> findMessagesForPlayer(Player player)
	{
//		return (List<MessageBoard>)hibernateTemplate.find("select playermessages.message as messages from PlayerMessages playermessages inner join fetch playermessages.message.fromPlayer inner join fetch playermessages.message.leagueName where playermessages.player.id=? order by playermessages.message.posted desc", player.getId());
		
		Query query = getQuery("select playermessages.message as messages from PlayerMessages playermessages inner join fetch playermessages.message.fromPlayer inner join fetch playermessages.message.leagueName where playermessages.player.id=? order by playermessages.message.posted desc");
		query.setParameter(0, player.getId());
		
		return query.list();
	}
	
	public void deleteMessageForPlayer(final MessageBoard messageBoard, final Player player)
	{
		
		Query query = getQuery("delete from PlayerMessages where message_id=? and player_id=?");
		query.setLong(0, messageBoard.getId());
		query.setLong(1, player.getId());
		
		query.executeUpdate();
		
//		getHibernateTemplate().execute(new HibernateCallback() {
//			   public Object doInHibernate(Session session) throws HibernateException, SQLException {
//				   Query query = session.createQuery("delete from PlayerMessages where message_id=? and player_id=?");
//					query.setLong(0, messageBoard.getId());
//					query.setLong(1, player.getId());
//					return query.executeUpdate();
//				   }
//				  });
	}

	public void deleteAllMessages(final MessageBoard messageBoard) {
		
//		getHibernateTemplate().execute(new HibernateCallback() {
//			   public Object doInHibernate(Session session) throws HibernateException, SQLException {
//				   Query query = session.createQuery("delete from PlayerMessages where message_id=?");
//					query.setLong(0, messageBoard.getId());
//					return query.executeUpdate();
//				   }
//				  });
		
		Query query = getQuery("delete from PlayerMessages where message_id=?");
		query.setParameter(0, messageBoard.getId());
		
		
		query.executeUpdate();
	}
	
	public List<MessageBoard> findNewMessages(Player player)
	{
//		return hibernateTemplate.find("from PlayerMessages playermessages inner join fetch playermessages.message inner join fetch playermessages.player inner join fetch playermessages.message.leagueName where player_read = false and playermessages.player.id=?", player.getId());
		
		Query query = getQuery("from PlayerMessages playermessages inner join fetch playermessages.message inner join fetch playermessages.player inner join fetch playermessages.message.leagueName where player_read = false and playermessages.player.id=?");
		query.setParameter(0, player.getId());
		
		return query.list();
	}
	
	public void markMessagesAsRead(final Player player)
	{
		
		
//		getHibernateTemplate().execute(new HibernateCallback() {
//			   public Object doInHibernate(Session session) throws HibernateException, SQLException {
//				   Query query = session.createQuery("update PlayerMessages set player_read = true where player_id=?");
//					query.setLong(0, player.getId());
//					return query.executeUpdate();
//				   }
//				  });
		
		
		Query query = getQuery("update PlayerMessages set player_read = true where player_id=?");
		query.setParameter(0, player.getId());
		
		
		query.executeUpdate();
	}
	
}
