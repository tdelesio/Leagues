package info.makeyourpicks;

import java.util.ArrayList;
import java.util.List;

public class EmailConstants {

	public final static String DEFAULT_EMAIL = "admin@makeyourpicks.info";
	public final static String BILLING_EMAIL = "billing@makeyourpicks.info";
	
	public final static String INVITE_SUBJECT = "Join my Pool";
	public final static String INVITE_MSG = "I thought you would be interested in joining my pool.";
	public final static String LOST_PASSWORD_SUBJECT = "lost password";
	public final static List<String> CATEGORIES = new ArrayList<String>();
	
	static
	{
			CATEGORIES.add("Billing");
			CATEGORIES.add("Questions");
			CATEGORIES.add("Tech Support");
			CATEGORIES.add("Bugs");
	};
}
