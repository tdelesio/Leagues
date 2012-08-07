package info.makeyourpicks.pages.error;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * @author PRC9041
 */
public class InternalErrorPage extends WebPage {

	public InternalErrorPage()
	{
		add(new FeedbackPanel("feedback"));
	}
}

