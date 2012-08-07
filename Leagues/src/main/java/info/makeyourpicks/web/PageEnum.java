package info.makeyourpicks.web;

public enum PageEnum {

	HOME("home"),
	MAKE_PICKS("makePicks"),
	VIEW_PICKS("viewPicks"),
	WIN_SUMMARY("winSummary"),
	LEAGUE_DETAILS("leagueDetails"),
	MESSAGE_BOARD("messageBoard"),
	CREATE_LEAGUE("createLeague"),
	JOIN_LEAGUE("joinLeague"),
	MEMBER_PAGES("memberPages");
	
	private String pageName;
	
	private PageEnum(String name)
	{
		this.pageName = name;
	}
	
	public String getValue()
	{
		return pageName;
	}
}
