package info.makeyourpicks.web.football.pages;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;

import info.makeyourpicks.model.WinSummary;
import info.makeyourpicks.web.football.panels.WinSummaryPanel;
import info.makeyourpicks.web.layout.IHeaderWidget;
import info.makeyourpicks.web.layout.pages.LeagueMemberWebPage;

/**
 * @author PRC9041
 */
public class WinSummaryPage extends LeagueMemberWebPage implements IHeaderWidget {
	
	public static final String WICKET_ID = "winSummary";
	public WinSummaryPage()
	{
		super(false);
		
		add(CSSPackageResource.getHeaderContribution("styles/winSummary.css"));
		
		
		WinSummaryPanel winSummaryPanel = new WinSummaryPanel("winSummaryPanel");
		add(winSummaryPanel);
		
	}
	public Component getHeaderWidget() {
		
		StringBuffer title = new StringBuffer();
		List<WinSummary> winSummaries = picksManager.getWinSummary(getActiveLeague());
		int firstPlaceWins=0, moneyPlace=0, secondPlace=2;
		int yourWins=0;
		
		boolean second=false, money=false;
		for (WinSummary winSummary:winSummaries)
		{
			
			if (winSummary.getPlace()==1)
				firstPlaceWins=winSummary.getNumberOfWins();
			
//			if (!second && winSummary.getPlace()==2)
			
			
			if (getActiveLeague().getHighestPayingSpot() == winSummary.getPlace())
				moneyPlace = winSummary.getNumberOfWins();
			
			if (winSummary.getPlayer().equals(getPlayer()))
			{
				yourWins=winSummary.getNumberOfWins();
				title.append("You are in "+winSummary.getPlace()+" place.\n");
			}
		}
		
		title.append("You are "+(firstPlaceWins-yourWins)+" point(s) out of first.");
		if (getActiveLeague().isBanker())
		{
			
			title.append("\nYou are "+(moneyPlace-yourWins)+" point(s) out of the money.");
		}
		
		return new MultiLineLabel(IHeaderWidget.WICKET_ID, title.toString()	);
	}
	
	
	
}

