package info.makeyourpicks.model;

public enum PickemTieBreakerEnum {

	SPLIT("Split", 1),
	PUSH_TO_NEXT_WEEK("Push to next week", 2),
	MONDAY_NIGHT_SCORE("Monday Night Score", 3),
	FIFTY_FIFTY_SPLIT("50/50 split.", 4);
	
	private String description;
	private int type;
	
	PickemTieBreakerEnum(String description, int type)
	{
		this.description = description;
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public String toString()
	{
		return description;
	}
}
