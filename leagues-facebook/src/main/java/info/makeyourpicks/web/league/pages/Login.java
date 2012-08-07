package info.makeyourpicks.web.league.pages;

import info.makeyourpicks.web.FacebookSession;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookJaxbRestClient;
import com.google.code.facebookapi.ProfileField;
import com.google.code.facebookapi.schema.FriendsGetResponse;
import com.google.code.facebookapi.schema.User;
import com.google.code.facebookapi.schema.UsersGetInfoResponse;
import com.google.code.facebookapi.schema.UsersGetStandardInfoResponse;

public class Login extends WebPage
{

	public Login() {
        try {
        	
        	BookmarkablePageLink login = new BookmarkablePageLink("login", FacebookEntryPage.class);
        	add(login);
        	
        	final FacebookJaxbRestClient client = (FacebookJaxbRestClient)((FacebookSession) getSession()).getClient();

            client.friends_get();

            FriendsGetResponse fbResponse = (FriendsGetResponse) client.getResponsePOJO();
            List<Long> friends = fbResponse.getUid();

            Set<ProfileField> c = new HashSet<ProfileField>();
            c.add(ProfileField.FIRST_NAME);
            c.add(ProfileField.LAST_NAME);
            c.add(ProfileField.NAME);
            c.add(ProfileField.SEX);

            client.users_getInfo(friends, c);
            UsersGetInfoResponse uResponse = (UsersGetInfoResponse) client.getResponsePOJO();
            
            List<User> users = uResponse.getUser();
            for (User user: users)
            {
            	System.out.println(user.getFirstName()+" "+user.getLastName()+" "+user.getSex()+" "+user.getBirthday());
            	
            }
            add(new ListView("friends", friends) {

                protected void populateItem(ListItem listItem) {
                	long id = (Long)listItem.getModelObject();
                	
               	
                    listItem.add(new Label("friend", String.valueOf(listItem.getModelObject())));
                  
                }
            });

        } catch (FacebookException e) {
            throw new RuntimeException(e);
        }
       }

}
