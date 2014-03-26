package info.makeyourpicks.dao.hibernate;

import info.makeyourpicks.dao.IPaymentDao;
import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Payment;

import java.util.List;

import org.hibernate.Query;

import com.delesio.dao.hibernate.HibernateDao;

public class PaymentDao extends HibernateDao implements IPaymentDao
{

	private String getQuery(String prefix, String suffix)
	{
		StringBuffer buffer = new StringBuffer(prefix);
		buffer.append("from Payment p ");
		buffer.append(suffix);
		return buffer.toString();
	}
	public List<Payment> findPaymentsByLeague(League league)
	{
//		return getHibernateTemplate().find(getQuery("", "where p.league.id=?"),league.getId());
		
		Query query = getQuery("where p.league.id=?");
		query.setParameter(0, league.getId());
		
		return query.list();
	}

}
