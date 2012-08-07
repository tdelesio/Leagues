package info.makeyourpicks.web.ncaa.pages;

import info.makeyourpicks.web.layout.pages.LeagueMemberWebPage;


/**
 * @author PRC9041
 */
public class ViewPicksPage extends LeagueMemberWebPage {
	
	public static final String WICKET_ID = "viewPicks";
	public ViewPicksPage()
	{
		
		super();
//		super.get("leaguePanel").setVisible(false);
////		getParent().getPage();
//		
//		String playerName = getRequest().getParameter("playersName");
//		boolean wasPassed=true;
//		if (playerName==null)
//		{
//			playerName = getPlayer().getUsername();
//			wasPassed=false;
//		}
//		
//		if (!allowPageRender(playerName, wasPassed))
//		{
//			throw new RestartResponseException(WinSummaryPage.class);
//		}
//		
//		add(new Label("playerName",playerName));
//		
//			//String cssClass = "brackettext";
//			Iterator<Game> games = Collections.EMPTY_LIST.iterator();//seasonManager.getEnteredGames(1, getActiveLeague().getLeagueType().getTypeOfLeague()).iterator();
//			Game seasonInfo;
//			for (int i=1;games.hasNext();i++)
//			{
//				seasonInfo = games.next();
////				add(new Label("fav"+i, seasonInfo.getFav().getFullTeamName()).add(new CSSClass(cssClass)));
////				add(new Label("dog"+i, seasonInfo.getDog().getFullTeamName()).add(new CSSClass(cssClass)));
////				System.out.println(seasonInfo.getFav().getCity()+" "+seasonInfo.getFav().getShortName());
////				System.out.println(seasonInfo.getDog().getCity()+" "+seasonInfo.getDog().getShortName());
//				add(new Label("fav"+i, seasonInfo.getFav().getCity()+" "+seasonInfo.getFav().getShortName()));
//				add(new Label("dog"+i, seasonInfo.getDog().getCity()+" "+seasonInfo.getDog().getShortName()));
////				add(new Label("fav_score"+i, String.valueOf(seasonInfo.getFavorite_score())));
////				add(new Label("dog_score"+i, String.valueOf(seasonInfo.getUnderdog_score())));
//
//			}
//			
//			PlayersPicks playersPicksInfo=null;
//			Picks picksInfo;
//			for (int i=1;i<=6;i++)
//			{
//				//playersPicksInfo = picksManager.getPlayerPicksForWeek(i, playerName,getActiveLeague().getLeagueName(), getActiveLeague().getLeagueType().getTypeOfLeague());
//				Iterator<Picks> picks = playersPicksInfo.getPlayersPicks().iterator();
//				for (int j=1;picks.hasNext();j++)
//				{
//					picksInfo = picks.next();
//				}
//			}	
//			
//			
//			
//			
//
//	}
//	
//	private boolean allowPageRender(String playerName, boolean wasPassed)
//	{
//		Week weekStartInfo  = null;
//
//		//weekStartInfo = weekStartManager.getWeekStart(1, getActiveLeague().getLeagueType().getTypeOfLeague());
//		
//		if (!wasPassed || playerName.equals(getPlayer().getUsername()))
//		{
//			return true;
//		}
//		else
//		{
//			if (weekStartInfo!=null&&weekStartInfo.hasCurrentWeekStarted())
//			{
//				return true;
//			}
//			else
//			{
//				return false;
//			}
//		}
	}
	public String getWicketID() {
		return WICKET_ID;
	}
	
	
}

