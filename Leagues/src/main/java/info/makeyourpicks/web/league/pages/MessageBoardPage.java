package info.makeyourpicks.web.league.pages;

import org.apache.wicket.markup.html.CSSPackageResource;

import info.makeyourpicks.web.layout.pages.LeagueMemberWebPage;
import info.makeyourpicks.web.league.panels.MessageBoardPanel;

/**
 * @author PRC9041
 */
public class MessageBoardPage extends LeagueMemberWebPage {

	public static final String WICKET_ID = "messageBoard";
	public MessageBoardPage()
	{
		super();
		
		add(CSSPackageResource.getHeaderContribution("styles/messageBoard.css"));
		
		add(new MessageBoardPanel("messageBoardPanel"));
	}
	
	
}

