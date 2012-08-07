package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.AbstractSuicidePageTestCase;
import info.makeyourpicks.web.LeagueSession;

import org.junit.Test;

public class PaymentHistoryPageTest extends AbstractSuicidePageTestCase{

	@Test
	public void testPaymentHistoryPage() {
		login();
		
		LeagueSession.getWebSession().setActiveLeague(suicide);
		
		tester.startPage(PaymentHistoryPage.class);
		tester.assertRenderedPage(PaymentHistoryPage.class);
	}

}
