package info.makeyourpicks;


public enum LeagueTypesEnum {

	PICKEM("football"),
	SUICIDE("suicide"),
	NCAA("ncaa"),
	BOWLS("ncaa-weight");
	
	private String leagueType;
//	private Map<PagesEnum, Class<? extends WebPage>> homePageMap;
	
//	private Map<String, Map<String, Class<? extends LeagueMemberWebPage>>> homePageMap;
	
	private enum PagesEnum {
		HOME("home"),
		MAKEPICKS("makePicks"),
		VIEWPICKS("viewPicks");
		
		private String name;
		private PagesEnum(String name)
		{
			this.name = name;
		}
	}
	
	private LeagueTypesEnum(String leagueType)
	{
		this.leagueType = leagueType;
		
//		homePageMap = new HashMap<PagesEnum, Class<? extends WebPage>>();
//		
//		if (leagueType.equals(getLeagueType()))
//		{
//			homePageMap.put(PagesEnum.HOME, MakePicksPage.class);
//		}
		
	}

	
	
	public String getLeagueType() {
		return leagueType;
	}



	public void setLeagueType(String leagueType) {
		this.leagueType = leagueType;
	}



	public static LeagueTypesEnum getByStatus(String id){
		for (LeagueTypesEnum leagueTypesEnum : values()){
			if(id.equals(leagueTypesEnum.getLeagueType())) return leagueTypesEnum;
		}
		return null;
	}
}
