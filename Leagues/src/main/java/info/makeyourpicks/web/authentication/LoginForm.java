package info.makeyourpicks.web.authentication;

import info.makeyourpicks.model.Player;
import info.makeyourpicks.web.league.pages.RegisterPage;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.springframework.security.LockedException;

import com.delesio.protocol.http.AbstractAuthenticatedWebSession;

public abstract class LoginForm extends Form {
	private static final long serialVersionUID = 1L;

	private static final int cookieExpire = 60*60*24*30;
	public LoginForm(final String id, IModel model, FeedbackPanel feedbackPanel)
	{
		this(id, model, feedbackPanel, true);
	}
	
	// constructor
	public LoginForm(final String id, IModel model, FeedbackPanel feedbackPanel, boolean showExtraFields) {
		super(id, model);
		
		// add form components
		RequiredTextField username = new RequiredTextField("username");
		add(username);
		
		PasswordTextField password = new PasswordTextField("password");
		password.setRequired(true);
		add(password);

		WebMarkupContainer extraContainer = new WebMarkupContainer("container");
		extraContainer.setVisible(showExtraFields);
		add(extraContainer);
		
		CheckBox rememberMe = new CheckBox("rememberMe");
		add(rememberMe);
		
		extraContainer.add(new PageLink(LostPasswordPage.WICKET_ID, LostPasswordPage.class));
		extraContainer.add(new PageLink("register", RegisterPage.class));
		add(new Button("ajaxButton"));
	}

	@Override
	public final void onSubmit() {
		Player player = (Player)getModelObject();
		if (signIn(player.getUsername(), player.getPassword())) {
			if (player.isRememberMe())
			{
//				Cookie cookie = new Cookie("mypid", player.getUsername());
//				cookie.setMaxAge(cookieExpire);
//				cookie.setPath("/");
//				cookie.setSecure(true);
//				((HttpServletResponse)getResponse()).addCookie(cookie);
				
			}
			onSignInSucceeded();
		} else {
			onSignInFailed();
		}
	}
	
	public boolean signIn(String usernameValue, String passwordValue) {
		boolean result = false;
		
		try
		{
			result = AbstractAuthenticatedWebSession.get().authenticate(usernameValue, passwordValue);
			if (!result)
			{
				error("Your login information is not correct.  Please check the username and/or password.");
			}
			return result;
		}
		catch (LockedException lockedException)
		{
			error("Your account has been locked for non-payment.  Please email your league admin.");
			return false;
		}
	}
	
	protected abstract void onSignInFailed();

	/**
	 * Hook into a successful sign inss
	 * 
	 */
	protected abstract void onSignInSucceeded();
}
