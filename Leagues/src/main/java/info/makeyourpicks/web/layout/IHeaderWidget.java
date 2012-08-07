package info.makeyourpicks.web.layout;

import org.apache.wicket.Component;

public interface IHeaderWidget {

	public static final String WICKET_ID = "headerWidget";
	
	public Component getHeaderWidget();
}
