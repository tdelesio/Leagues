package info.makeyourpicks.web.layout;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.web.admin.pages.SetupWeekPage;
import info.makeyourpicks.web.dataprovider.ActivePlayerLeaguesDataProvider;
import info.makeyourpicks.web.league.pages.CreateLeaguePage;
import info.makeyourpicks.web.league.pages.JoinLeaguePage;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.odlabs.wiquery.core.commons.WiQueryResourceManager;
import org.odlabs.wiquery.ui.accordion.Accordion;
import org.odlabs.wiquery.ui.accordion.AccordionActive;

public class LeftNavSecurePanel extends AbstractBasePanel {

	private int counter;
	private int index;
	public LeftNavSecurePanel(String id)
	{
		super(id);
		
		
		final Accordion accordion = new Accordion("wiAccordion")
		{
//			@Override
//			   public void contribute(WiQueryResourceManager manager){
////			    	super.contribute(manager);
////			      manager.addCssResource(new ResourceReference("/styles/screen2.css"));
//				manager.addCssResource(new ResourceReference("styles/jquery/css/custom-theme/jquery-ui-1.8.4.custom.css"));
//				manager.addJavaScriptResource(new JavascriptResourceReference("styles/jquery/js/jquery-1.4.2.min.js"));
//				manager.addJavaScriptResource(new JavascriptResourceReference("styles/jquery/js/jquery-ui-1.8.4.custom.min.js"));
//			   }
		};
//		accordion.add(new SimpleAttributeModifier("style", "background-color: #fcd15d;"));
		//A private method that returns a list of Strings getSections
		
		
		List<String> carModelList = new ArrayList<String>();
		carModelList.add("NFL 2010");
		carModelList.add("Suicide 2010");

		 

//		ListView accordionListView = new ListView("accordionListView", carModelList)
//
//		{
//
//		              @Override
//
//		              protected void populateItem(ListItem listItem){
//
//		                            String sectionName = (String) listItem.getModelObject();
//
//		                            listItem.setRenderBodyOnly(true);
//
//		                            listItem.add(
//
//		new Label("sectionNameLbl",sectionName).setRenderBodyOnly(true));
//
//		              }
//
//		  };
		
		ActivePlayerLeaguesDataProvider activePlayerLeaguesDataProvider = new ActivePlayerLeaguesDataProvider()
		{

			@Override
			public LeagueManager getLeagueManager() {
				// TODO Auto-generated method stub
				return leagueManager;
			}

			@Override
			public Player getLoggedInPlayer() {
				return getPlayer();
			}
		};
		
		DataView<League> accordionListView = new DataView<League>("accordionListView", activePlayerLeaguesDataProvider)
		{
			@Override
			protected void populateItem(final Item<League> item)
			{
				
				if (item.getModelObject().equals(getActiveLeague()))
				{
					accordion.setActive(new AccordionActive(counter));
				}
				item.setRenderBodyOnly(true);
				
				String leagueName = ((League)item.getModelObject()).getLeagueName();
		        item.add(new Label("sectionNameLbl", leagueName).setRenderBodyOnly(true));
		        
		        LeftSubNavSecurePanel leftSubNavSecurePanel = new LeftSubNavSecurePanel("subNavPanel", item.getModel());
		        leftSubNavSecurePanel.setRenderBodyOnly(true);
		        leftSubNavSecurePanel.add(new SimpleAttributeModifier("class", "subNavContainer"));
		        item.add(leftSubNavSecurePanel);
		        counter++;
			}
		};
		  accordion.add(  accordionListView);
		  accordion.setAutoHeight(false);
		  
//		  accordion.set
		  add(accordion);//Finally add it to the Page
		  
		//create league
	        Link createLeaguePage = new BookmarkablePageLink(CreateLeaguePage.WICKET_ID, CreateLeaguePage.class);
	        add(createLeaguePage);

	      //join league link
			Link joinLink = new Link(JoinLeaguePage.WICKET_ID)
			{
				@Override
				public void onClick()
				{
					setResponsePage(JoinLeaguePage.class);
				}
			};
			add(joinLink);
			
			//admin link
			Link adminPage = new Link("adminPage")
			{
				@Override
				public void onClick()
				{
					setResponsePage(SetupWeekPage.class);
				}			
			};
			
			if (getPlayer().getMemberLevel() != Player.ADMIN)
				adminPage.setVisible(false);
			add(adminPage);
		  
		  
	}
}
