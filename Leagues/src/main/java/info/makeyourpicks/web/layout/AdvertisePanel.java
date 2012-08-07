package info.makeyourpicks.web.layout;

import java.util.Random;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.model.AbstractReadOnlyModel;

/**
 * @author PRC9041
 */
public class AdvertisePanel extends AbstractBasePanel {

	public static final String MAKEYOURPICKS = "/leagues/styles/banners/makeYourPicks.jpg";
	public static final String ENACT = "/leagues/styles/banners/Enact.jpg";
	public static final String THE_ID = "/leagues/styles/banners/The-ID.jpg";
	
	public AdvertisePanel(String id)
	{
		super(id);
		
		final int randomNum = new Random().nextInt(3);
		
		Image ad1 = new Image("ad1");
		ad1.add(new AttributeModifier("src", true, new AbstractReadOnlyModel() {
			private static final long serialVersionUID = 1L;

			/**
			 * {@inheritDoc}
			 */
			@Override
			public final Object getObject() {
				// based on some condition return the image source
//				return "/leagues/styles/banner.jpg";
				if (randomNum == 0)
				{
					return ENACT;
				}
				else if (randomNum == 1)
				{
					return THE_ID;
				}
				else
				{
					return MAKEYOURPICKS;
				}
				
			}
		}));
		ad1.setOutputMarkupId(true);
		
		ExternalLink link1=new ExternalLink( "ad1link", new AbstractReadOnlyModel()
		{

			@Override
			public Object getObject() {
				if (randomNum == 1)
				{
					return "http://www.theinteractivedept.com";
				}
				else if (randomNum==0)
				{
					return "http://www.enacttracks.com";
				}
				else
				{
					return "/leagues/contactUs.html";
				}
			}
			
		});
		link1.add(ad1);
		
		link1.setVisible(false);
		add(link1);

	}
}

