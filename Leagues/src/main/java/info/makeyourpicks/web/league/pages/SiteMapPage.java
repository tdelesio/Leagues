package info.makeyourpicks.web.league.pages;

import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.delesio.web.model.SiteMapUrl;

import info.makeyourpicks.web.layout.pages.LeagueNonMemberWebPage;

/**
 * @author PRC9041
 */
public class SiteMapPage extends WebPage {

	@SpringBean(name="leagueTypes")
	private Map<String, Map<String, SiteMapUrl<? extends Page>>> mountableUrls;
	
	// markup type
	private static final String MARKUP_TYPE = "xml";
	
	public SiteMapPage()
	{
		
		RepeatingView repeatingView = new RepeatingView("urlList");
		add(repeatingView);
		
		if(mountableUrls != null)
			for (Map<String, SiteMapUrl<? extends Page>> subMap:mountableUrls.values())
				for(SiteMapUrl<? extends Page> siteMapURL:subMap.values())
//				for(String key : subMap.keySet())
				{
//					final SiteMapUrl<? extends Page> url = subMap.get(key);
//					
                    if(!"/".equals(siteMapURL.getLoc())) 
                    {
                    	WebMarkupContainer parent = new WebMarkupContainer(repeatingView.newChildId());
                    	repeatingView.add(parent);
//                    	
//                    	Link link = new Link("link") {
//
//							@Override
//							public void onClick() {
//								setResponsePage(url.getUrlClass());
//							}
//                    		
//						};
//						parent.add(link);
//						
//						link.add(new Label("caption", key));
						
						parent.add(new Label("locNode", siteMapURL.getFullURL()));
						parent.add(new Label("lastmodNode", siteMapURL.getLastModAsString()));
						parent.add(new Label("changefreqNode", siteMapURL.getChangeFreqAsString()));
						parent.add(new Label("priorityNode", siteMapURL.getPriority()));
                    }
				}
		
	}
	
	 @Override
	    public String getMarkupType() {
	    	return MARKUP_TYPE;
	    }
	
}

