package info.makeyourpicks.dao;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Payment;

import java.util.List;

public interface IPaymentDao
{

	public List<Payment> findPaymentsByLeague(League league);
}
