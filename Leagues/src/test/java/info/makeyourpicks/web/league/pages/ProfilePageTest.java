package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.AbstractPageTestCase;

import org.apache.wicket.model.CompoundPropertyModel;
import org.junit.Test;

public class ProfilePageTest extends AbstractPageTestCase{

	@Test
	public void testProfilePage() {
		login();
		
		tester.startPage(ProfilePage.class);
		tester.assertRenderedPage(ProfilePage.class);
		
	}

	@Test
	public void testProfilePageIModel() {
		login();
		tester.setupRequestAndResponse();
		tester.startPage(new ProfilePage(new CompoundPropertyModel(player)));
		tester.assertRenderedPage(ProfilePage.class);
		
	}

}
