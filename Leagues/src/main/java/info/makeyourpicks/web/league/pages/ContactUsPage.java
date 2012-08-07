package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.model.Player;
import info.makeyourpicks.web.layout.pages.LeagueNonMemberPage;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 * @author PRC9041
 */
public class ContactUsPage extends LeagueNonMemberPage {

	public static final String WICKET_ID = "contactUsPage";
	private static List categories = new ArrayList();
	static 
	{
		categories.add("Technical Problem");
		categories.add("Advertising");
		categories.add("Question");
		categories.add("Suggestions");
	};
	public ContactUsPage()
	{
		super();
		
		add(new FeedbackPanel("feedbackpanel"));
		final LoadableDetachableModel contactModel = new LoadableDetachableModel()
		{

			@Override
			protected Object load()
			{
//				ContactInfo contactInfo = new ContactInfo();
////				if (getPlayer()!=null)
////				{
////					contactInfo.setFromAddress(getPlayer().getEmail());
////				}
//				return contactInfo;
				return new Player();
			}
			
		};
		Form form = new Form("contactusForm", new CompoundPropertyModel(contactModel));
		add(form);
		
		RequiredTextField fromEmail = new RequiredTextField("fromAddress");
		if (getPlayer()!=null)
		{
			fromEmail.setEnabled(false);
		}
		form.add(fromEmail);
		
		DropDownChoice dropDownChoice = new DropDownChoice("category", categories);
		dropDownChoice.setRequired(true);
		form.add(dropDownChoice);
		
		TextArea textArea = new TextArea("message");
		textArea.setRequired(true);
		form.add(textArea);
		
		form.add(new Button("submit")
		{

			@Override
			public void onSubmit()
			{
				//leagueManager.contactUs((ContactInfo)contactModel.getObject());
			}
			
		});
	}

	
	
}

