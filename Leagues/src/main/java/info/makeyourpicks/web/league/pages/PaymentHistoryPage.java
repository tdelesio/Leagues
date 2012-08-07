package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Payment;
import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.web.layout.pages.LeagueMemberWebPage;

import java.util.Iterator;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author PRC9041
 */
public class PaymentHistoryPage extends LeagueMemberWebPage {
	public static final String WICKET_ID = "paymentHistory";
	
	@SpringBean(name="leagueManager")
	protected LeagueManager leagueManager;
	
	public PaymentHistoryPage()
	{
		super();
		
		RepeatingView repeating = new RepeatingView("repeating");
        add(repeating);
        
        League league = getActiveLeague();
        Iterator<Payment> payments = leagueManager.getPaymentsByLeague(league).iterator();
        
        int index=0;
        while (payments.hasNext())
        {
        	WebMarkupContainer item = new WebMarkupContainer(repeating.newChildId());
            repeating.add(item);
            final Payment payment = payments.next();
            
            item.add(new Label("date", payment.getTransactionDateDisplay()));
            item.add(new Label("transaction", payment.getTransactionId()));
            item.add(new Label("amount", payment.getAmountDisplay()));
            
            final int idx = index;
            item.add(new AttributeModifier("class", true, new AbstractReadOnlyModel()
            {
                public Object getObject()
                {
                    return (idx % 2 != 0) ? "even" : "odd";
                }
            }));
        }
	}

}

