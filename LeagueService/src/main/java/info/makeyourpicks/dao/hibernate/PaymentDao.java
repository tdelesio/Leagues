package info.makeyourpicks.dao.hibernate;

import java.util.List;

import info.makeyourpicks.dao.IPaymentDao;
import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Payment;

import com.delesio.dao.hibernate.AbstractDao;

public class PaymentDao extends AbstractDao implements IPaymentDao
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
		return getHibernateTemplate().find(getQuery("", "where p.league.id=?"),league.getId());
	}

}
