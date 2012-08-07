package info.makeyourpicks.web;

import org.apache.wicket.Request;

import com.google.code.facebookapi.IFacebookRestClient;


public class FacebookSession extends LeagueSession
{

	 private IFacebookRestClient client;

    public FacebookSession(Request request) {
        super(request);
    }

    public synchronized IFacebookRestClient getClient() {
        return client;
    }

    public synchronized void setClient(IFacebookRestClient client) {
        this.client = client;
    }

}
