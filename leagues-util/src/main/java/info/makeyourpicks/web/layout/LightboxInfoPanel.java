package info.makeyourpicks.web.layout;

import org.apache.wicket.markup.html.basic.Label;

public class LightboxInfoPanel extends AbstractBasePanel {

	public LightboxInfoPanel(String id, String message)
	{
		super(id);
		
		add(new Label("message", message));
	}
}
