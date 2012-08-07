package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.model.Payment;
import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.web.LeagueFacebookApplication;
import info.makeyourpicks.web.layout.AbstractLeagueSecureFacebookPage;

import java.util.HashMap;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.target.basic.RedirectRequestTarget;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.delesio.util.PaypalHelper;

/**
 * @author PRC9041
 */
public class ActivateLeaguePage extends AbstractLeagueSecureFacebookPage {
 

	@SpringBean(name="leagueManager")
	protected LeagueManager leagueManager;
	
	public static final String WICKET_ID = "activateLeague";
	
	public ActivateLeaguePage()
	{
		super();
		
		final int players = leagueManager.getNumberOfPlayersInLeague(getActiveLeague());
		
		final LoadableDetachableModel model = new LoadableDetachableModel()
		{

			@Override
			protected Object load()
			{
				Payment payment = new Payment();
				payment.setLeague(getActiveLeague());
				final double minCost;
				if (players<10)
				{
					minCost = 10;
				}
				else
				{
					minCost = players;
				}
				payment.setAmountDue(minCost);
				payment.setLeague(getActiveLeague());
				
				return payment;
			}
			
		};
		
				        
		Form creditcardForm = new Form("form", new CompoundPropertyModel(model))
		{
			@Override
			protected void onSubmit() {
				
				Payment payment = (Payment)model.getObject();
				leagueManager.createPayment(payment);
				
				PaypalHelper paypalHelper = new PaypalHelper(((LeagueFacebookApplication)getApplication()).isPaypalSandbox());
				HashMap nvp = paypalHelper.CallShortcutExpressCheckout (payment.getFormatedAmount(), String.valueOf(payment.getId()));
		        if(nvp!=null&&nvp.get("ACK") !=null && ((nvp.get("ACK").toString()).equalsIgnoreCase("Success")))
		        {
		        	payment.setToken(nvp.get("TOKEN").toString());
		        	setPayment(payment);
		        	getRequestCycle().setRequestTarget(new RedirectRequestTarget(paypalHelper.getPaypalURL(nvp.get("TOKEN").toString())));
		        }
		        else
		        {
		                // Display a user friendly Error on the page using any of the following error information returned by PayPal

		        	String ErrorCode = nvp.get("L_ERRORCODE0").toString();
		            String ErrorShortMsg = nvp.get("L_SHORTMESSAGE0").toString();
		            String ErrorLongMsg = nvp.get("L_LONGMESSAGE0").toString();
		            String ErrorSeverityCode = nvp.get("L_SEVERITYCODE0").toString();
		            
		            error(ErrorLongMsg);
		        }
				
//				HttpClient client = new HttpClient();
//
//				//API Signature Security
//				PostMethod post = new PostMethod("https://api-3t.sandbox.paypal.com/nvp");
//				//PostMethod post = new PostMethod("https://api-3t.paypal.com/nvp");
//				//API Certificate Security
//				//PostMethod post = new PostMethod("https://api.sandbox.paypal.com/nvp");
//				//PostMethod post = new PostMethod("https://api.paypal.com/nvp");
//				
//				post.addParameter( "USER", "admin_1218463971_biz_api1.makeyourpicks.info");
//				post.addParameter( "PWD", "1218463977");
//				post.addParameter("SIGNATURE","An5ns1Kso7MWUdW4ErQKJJJ4qi4-Aa-n4TqqhOnQqnBHp41o1n.GVCYh");
//				post.addParameter("VERSION", "51.0");
//				post.addParameter("METHOD", "SetExpressCheckout");
//				post.addParameter("AMT", payment.getFormatedAmount());
//				post.addParameter("DESC", getActiveLeague().getLeagueName());
//				post.addParameter("RETURNURL", "http://www.makeyourpicks.info/league/reviewPayment.html");
//				post.addParameter("CANCELURL", "http://www.makeyourpicks.info/league/activateLeague.html");
//				post.addParameter("REQCONFIRMSHIPPING", "0");
//				post.addParameter("NOSHIPPING", "1");
//				post.addParameter("CURRENCYCODE", "USD");
//
//				try {
//				  client.executeMethod(post);
//				  
//				  
//				  System.out.println(post.getStatusLine() );
//				  
//				  System.out.println(post.getResponseBodyAsString());
//				}catch( Exception nex ){
//				   nex.printStackTrace();
//				} finally {
//				   post.releaseConnection();
//				} 
//				
//				setRedirect(true);
//				League league = getActiveLeague();
//				league.setActive(true);
//				league.setPaidFor(leagueManager.getNumberOfPlayersInLeague(league));
//				
//				leagueManager.updateLeague(league);
//				
//				Payment payment = new Payment();
//				Random random = new Random();
//				payment.setTransactionId(String.valueOf(random.nextInt(999999999)));
//				payment.setTransactionDate(new Date(System.currentTimeMillis()));
//				payment.setAmount(league.getPaidFor());
//				payment.setLeague(league);
//				
//				leagueManager.createPayment(payment);
//				
//				setResponsePage(LeagueDetailsPage.class);
			}
		
		};
//		creditcardForm.se
//		creditcardForm.add(new HiddenField("amount"));
//		creditcardForm.add(new HiddenField("item_name"));
		add(creditcardForm);
		
		creditcardForm.add(new FeedbackPanel("feedbackpanel"));
		creditcardForm.add(new Button("submit"));
		
		creditcardForm.add(new Label("league.leagueName"));
		creditcardForm.add(new Label("amountDue"));
		creditcardForm.add(new Label("players", String.valueOf(players)));
		
		
	}

	@Override
	public String getWicketID() {
		return WICKET_ID;
	}
	
}

