package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.web.LeagueWebApplication;
import info.makeyourpicks.web.layout.pages.LeagueMemberWebPage;

import java.util.HashMap;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import com.delesio.util.PaypalHelper;

public class ReviewPaymentPage extends LeagueMemberWebPage
{

	public static final String WICKET_ID = "reviewPaymentPage";
	
	
	public ReviewPaymentPage(PageParameters pageParameters)
	{
		super();
		add(new FeedbackPanel("feedbackpanel"));
		
		String token = pageParameters.getString("token");
		if (token!=null)
		{
			token = getPayment().getToken();
			PaypalHelper ppf = new PaypalHelper(((LeagueWebApplication)getApplication()).isPaypalSandbox());
			HashMap nvp = ppf.GetShippingDetails( token );
			
//			if (nvp == null)
//				return;
			
			
			
			String strAck = nvp.get("ACK").toString();
			if(strAck != null && (strAck.equalsIgnoreCase("Success") || strAck.equalsIgnoreCase("SuccessWithWarning")))
			{
				String email 			= nvp.get("EMAIL").toString(); // ' Email address of payer.
				String payerId 			= nvp.get("PAYERID").toString(); // ' Unique PayPal customer account identification number.
				
				getPayment().setPayerId(payerId);
				
				String payerStatus		= nullCheck(nvp.get("PAYERSTATUS")); // ' Status of payer. Character length and limitations: 10 single-byte alphabetic characters.
				String salutation		= nullCheck(nvp.get("SALUTATION")); // ' Payer's salutation.
				String firstName		= nullCheck(nvp.get("FIRSTNAME")); // ' Payer's first name.
				String middleName		= nullCheck(nvp.get("MIDDLENAME")); // ' Payer's middle name.
				String lastName			= nullCheck(nvp.get("LASTNAME")); // ' Payer's last name.
				String suffix			= nullCheck(nvp.get("SUFFIX")); // ' Payer's suffix.
				String cntryCode		= nullCheck(nvp.get("COUNTRYCODE")); // ' Payer's country of residence in the form of ISO standard 3166 two-character country codes.
				String business			= nullCheck(nvp.get("BUSINESS")); // ' Payer's business name.
				String shipToName		= nullCheck(nvp.get("SHIPTONAME")); // ' Person's name associated with this address.
				String shipToStreet		= nullCheck(nvp.get("SHIPTOSTREET")); // ' First street address.
				String shipToStreet2	= nullCheck(nvp.get("SHIPTOSTREET2")); // ' Second street address.
				String shipToCity		= nullCheck(nvp.get("SHIPTOCITY")); // ' Name of city.
				String shipToState		= nullCheck(nvp.get("SHIPTOSTATE")); // ' State or province
				String shipToCntryCode	= nullCheck(nvp.get("SHIPTOCOUNTRYCODE")); // ' Country code. 
				String shipToZip		= nullCheck(nvp.get("SHIPTOZIP")); // ' U.S. Zip code or other country-specific postal code.
				String addressStatus 	= nullCheck(nvp.get("ADDRESSSTATUS")); // ' Status of street address on file with PayPal   
				String invoiceNumber	= nullCheck(nvp.get("INVNUM")); // ' Your own invoice or tracking number, as set by you in the element of the same name in SetExpressCheckout request .
				String phonNumber		= nullCheck(nvp.get("PHONENUM")); // ' Payer's contact telephone number. Note:  PayPal returns a contact telephone number only if your Merchant account profile settings require that the buyer enter one. 
	
				
				add(new Label("email", email));
				add(new Label("payerStatus", payerStatus).setVisible(false));
				add(new Label("salutation", salutation));
				add(new Label("firstName", firstName));
				add(new Label("middleName", middleName));
				add(new Label("lastName", lastName));
				add(new Label("suffix", suffix));
				add(new Label("cntryCode", cntryCode).setVisible(false));
				add(new Label("business", business).setVisible(false));
				add(new Label("shipToName", shipToName).setVisible(false));
				add(new Label("shipToStreet", shipToStreet));
				add(new Label("shipToStreet2", shipToStreet2));
				add(new Label("shipToCity", shipToCity));
				add(new Label("shipToState", shipToState));
				add(new Label("shipToCntryCode", shipToCntryCode));
				add(new Label("shipToZip", shipToZip));
				add(new Label("addressStatus", addressStatus).setVisible(false));
				add(new Label("invoiceNumber", invoiceNumber).setVisible(false));
				add(new Label("phonNumber",phonNumber));
				
				Form form = new Form("form");
				form.add(new Button(PaymentConfirmationPage.WICKET_ID)
				{

					@Override
					public void onSubmit()
					{
						setResponsePage(PaymentConfirmationPage.class);
					}
					
				});
				add(form);
				/*
				' The information that is returned by the GetExpressCheckoutDetails call should be integrated by the partner into his Order Review 
				' page		
				*/
			}
			else
			{  
				// Display a user friendly Error on the page using any of the following error information returned by PayPal
				
				String ErrorCode = nullCheck(nvp.get("L_ERRORCODE0"));
				String ErrorShortMsg = nullCheck(nvp.get("L_SHORTMESSAGE0"));
				String ErrorLongMsg = nullCheck(nvp.get("L_LONGMESSAGE0"));
				String ErrorSeverityCode = nullCheck(nvp.get("L_SEVERITYCODE0"));
				error(ErrorLongMsg);
			}
		}
	}

	
//	/*
//	 ==================================================================
//	 PayPal Express Checkout - ORDER REVIEW : START SNIPPET
//	 ===================================================================
//	*/
//	/* 
//		This step indicates whether the user was sent here by PayPal 
//		if this value is null then it is part of the regular checkout flow in the cart
//	*/
//
//	String token = request.getAttribute("token");
//	if ( token != null)
//	{
//
//	//IMPORTANT NOTE: Please import Class paypalfunctions if not in the same package level.
//	// import paypalfunctions;
//
//		/*
//		'------------------------------------
//		' Calls the GetExpressCheckoutDetails API call
//		'
//		' The GetShippingDetails function is defined in PayPalFunctions.jsp
//		' included at the top of this file.
//		'-------------------------------------------------
//		*/
//		
//		String token = (String) session.getAttribute("token");
//		
//		paypalfunctions ppf = new paypalfunctions();
//		java.util.HashMap nvp = GetShippingDetails( token );
//		
//		if (nvp == null)
//			return;
//			
//		String strAck = nvp.get("ACK").toString();
//		if(strAck != null && !(strAck.equalsIgnoreCase("Success") || strAck.equalsIgnoreCase("SuccessWithWarning")))
//		{
//			String email 			= nvp.get("EMAIL").toString(); // ' Email address of payer.
//			String payerId 			= nvp.get("PAYERID").toString(); // ' Unique PayPal customer account identification number.
//			
//			session.setAttribute("PAYERID", payerId);
//			
//			String payerStatus		= nvp.get("PAYERSTATUS").toString(); // ' Status of payer. Character length and limitations: 10 single-byte alphabetic characters.
//			String salutation		= nvp.get("SALUTATION").toString(); // ' Payer's salutation.
//			String firstName		= nvp.get("FIRSTNAME").toString(); // ' Payer's first name.
//			String middleName		= nvp.get("MIDDLENAME").toString(); // ' Payer's middle name.
//			String lastName			= nvp.get("LASTNAME").toString(); // ' Payer's last name.
//			String suffix			= nvp.get("SUFFIX").toString(); // ' Payer's suffix.
//			String cntryCode		= nvp.get("COUNTRYCODE").toString(); // ' Payer's country of residence in the form of ISO standard 3166 two-character country codes.
//			String business			= nvp.get("BUSINESS").toString(); // ' Payer's business name.
//			String shipToName		= nvp.get("SHIPTONAME").toString(); // ' Person's name associated with this address.
//			String shipToStreet		= nvp.get("SHIPTOSTREET").toString(); // ' First street address.
//			String shipToStreet2	= nvp.get("SHIPTOSTREET2").toString(); // ' Second street address.
//			String shipToCity		= nvp.get("SHIPTOCITY").toString(); // ' Name of city.
//			String shipToState		= nvp.get("SHIPTOSTATE").toString(); // ' State or province
//			String shipToCntryCode	= nvp.get("SHIPTOCOUNTRYCODE").toString(); // ' Country code. 
//			String shipToZip		= nvp.get("SHIPTOZIP").toString(); // ' U.S. Zip code or other country-specific postal code.
//			String addressStatus 	= nvp.get("ADDRESSSTATUS").toString(); // ' Status of street address on file with PayPal   
//			String invoiceNumber	= nvp.get("INVNUM").toString(); // ' Your own invoice or tracking number, as set by you in the element of the same name in SetExpressCheckout request .
//			String phonNumber		= nvp.get("PHONENUM").toString(); // ' Payer's contact telephone number. Note:  PayPal returns a contact telephone number only if your Merchant account profile settings require that the buyer enter one. 
//
//			/*
//			' The information that is returned by the GetExpressCheckoutDetails call should be integrated by the partner into his Order Review 
//			' page		
//			*/
//		}
//		else
//		{  
//			// Display a user friendly Error on the page using any of the following error information returned by PayPal
//			
//			String ErrorCode = nvp.get("L_ERRORCODE0").toString();
//			String ErrorShortMsg = nvp.get("L_SHORTMESSAGE0").toString();
//			String ErrorLongMsg = nvp.get("L_LONGMESSAGE0").toString();
//			String ErrorSeverityCode = nvp.get("L_SEVERITYCODE0").toString();
//		}
//	}
//			
//	/*
//	 ==================================================================
//	 PayPal Express Checkout - ORDER REVIEW : END SNIPPET
//	 ===================================================================
//	*/

	private String nullCheck(Object o)
	{
		if (o==null)
		{
			return "";
		}
		else
		{
			return o.toString();
		}
	}
}
