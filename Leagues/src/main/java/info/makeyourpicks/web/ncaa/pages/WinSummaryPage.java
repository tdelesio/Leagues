package info.makeyourpicks.web.ncaa.pages;

import info.makeyourpicks.web.layout.pages.LeagueMemberWebPage;


/**
 * @author PRC9041
 */
public class WinSummaryPage extends LeagueMemberWebPage {

	public static final String WICKET_ID = "winSummary";
	public WinSummaryPage()
	{
		super();
		
//		League activeLeague = getActiveLeague();
//		List<Player> players = playerManager.getPlayersInLeague(activeLeague);
		//List<Week> weeks = weekStartManager.getAllWeeks(activeLeague.getLeagueType().getTypeOfLeague());
		//List<PlayersPicksInfo> playersPicksInfos;
		
		//Iterator<SeasonPicks> seasonPicks = picksManager.getSeasonsPickTotal(activeLeague.getLeagueName(), activeLeague.getLeagueType().getTypeOfLeague(), weeks, players).iterator();

//		RepeatingView repeating = new RepeatingView("repeating");
//        add(repeating);
//        
//        int index=0;
//        while (seasonPicks.hasNext())
//        {
//        	WebMarkupContainer item = new WebMarkupContainer(repeating.newChildId());
//            repeating.add(item);
//            SeasonPicks seasonInfo = seasonPicks.next();
            
//	            Link viewPicks = new Link("viewPicks")
//	            {
//
//					@Override
//					public void onClick() {
//						setResponsePage(ViewPicksPage.class);
//						
//					}
//	            	
//	            };
//	            viewPicks.add(new Label("playerName", seasonInfo.getName()));
            
//	            add(viewPicks);
//            item.add(new BookmarkablePageLink("selectPlayer", ViewPicksPage.class, new PageParameters("playersName="+seasonInfo.getName())).add(new Label("playerName", seasonInfo.getName())));
//            List<Integer> iter = seasonInfo.getWins();
//            for (int i=0;i<7;i++)
//            {
//            	int wins=0;
//            	if (i<iter.size())
//            	{
//            		wins = iter.get(i);
//            	}
//            	item.add(new Label("weekNumber"+i, String.valueOf(wins)));
//            }
//            
//            item.add(new Label("totalWins", String.valueOf(seasonInfo.getTotalWins())));
//            final int idx = index;
//            item.add(new AttributeModifier("class", true, new AbstractReadOnlyModel()
//            {
//                public Object getObject()
//                {
//                    return (idx % 2 == 1) ? "even" : "odd";
//                }
//            }));
//
//            index++;
//        }
//		
	}

	public String getHomePageMessage() {
		return "";
	}

	public String getWicketID() {
		return WICKET_ID;
	}
	
	
}

