package info.makeyourpicks.model;

public enum YesNoEnum {

	YES("Yes", true),
	No("No", false);
	
	private boolean yesNo;
	private String desc;
	
	private YesNoEnum(String description, boolean yn)
	{
		this.desc = description;
		this.yesNo = yn;
	}
	
	public String toString()
	{
		return desc;
	}
	
	public boolean getValue()
	{
		return yesNo;
	}
	
	public String getName()
	{
		return desc;
	}
	
//	public static 
}
