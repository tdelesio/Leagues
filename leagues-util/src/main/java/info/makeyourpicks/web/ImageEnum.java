package info.makeyourpicks.web;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

public enum ImageEnum {

	PICKSELECTED("blk_chkmrk.png"),
	PICKNOTSELECTED("empty_chkbox.png"),
	GAMESTARTED("grey_chkmrk.png"),
	LOSTPICK("redX_mrk.png"),
	WINPICK("green_chkmrk.png");
	
	private IModel model;
	
	private ImageEnum(final String name)
	{
		model = new AbstractReadOnlyModel()
		{
	
			@Override
			public Object getObject() {
				return name;
			}
			
		};
	}
	
	public IModel getModel()
	{
		return model;
	}
}
