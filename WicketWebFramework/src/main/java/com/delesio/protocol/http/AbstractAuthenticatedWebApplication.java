package com.delesio.protocol.http;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authorization.IUnauthorizedComponentInstantiationListener;
import org.apache.wicket.authorization.UnauthorizedInstantiationException;
import org.apache.wicket.authorization.strategies.role.IRoleCheckingStrategy;
import org.apache.wicket.authorization.strategies.role.RoleAuthorizationStrategy;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.ui.rememberme.RememberMeServices;

/**
 * 
 * AbstractAuthenticatedWebApplication.java
 * 
 * 
 * 
 * @version Mar 7, 2008
 * 
 * @author Anthony DePalma
 */

public abstract class AbstractAuthenticatedWebApplication extends
		WebApplication implements IRoleCheckingStrategy,
		IUnauthorizedComponentInstantiationListener {

	// authentication manager
	private AuthenticationManager authenticationManager;

	// authentication manager
	private RememberMeServices rememberMeServices;

	// url mappings to classes

//	private Map urlMappings;
	// subclass of authenticated web session to instantiate

	private SpringComponentInjector springComponentInjector;
	
	private final WeakReference<Class<? extends AbstractAuthenticatedWebSession>> webSessionClassRef;

	/**
	 * 
	 * Returns the casted AbstractAuthenticatedWebApplication
	 * 
	 * 
	 * 
	 * @return AbstractAuthenticatedWebApplication
	 */

	public static AbstractAuthenticatedWebApplication get() {

		return (AbstractAuthenticatedWebApplication) Application.get();

	}

	// constructor

	public AbstractAuthenticatedWebApplication() {

		// Get web session class to instantiate

		this.webSessionClassRef = new WeakReference<Class<? extends AbstractAuthenticatedWebSession>>(

		getWebSessionClass());

	}

	@Override
	protected void init() {

		super.init();

		// Set authorization strategy and unauthorized instantiation listener

		getSecuritySettings().setAuthorizationStrategy(
				new RoleAuthorizationStrategy(this));

		getSecuritySettings().setUnauthorizedComponentInstantiationListener(
				this);

		// enable Spring dependency injection
//		addComponentInstantiationListener(new SpringComponentInjector(this));
		
		// enable Spring dependency injection
		if (springComponentInjector == null) {
			this.springComponentInjector = new SpringComponentInjector(this);
		}
		addComponentInstantiationListener(springComponentInjector);

		// set the access denied page

		getApplicationSettings().setAccessDeniedPage(getAccessDeniedPage());

//		// mount REST urls
//
//		if (urlMappings != null)
//
//		{
//
//			String url;
//
//			Class urlClass;
//
//			for (Iterator it = urlMappings.keySet().iterator(); it.hasNext();)
//
//			{
//
//				url = (String) it.next();
//
//				urlClass = (Class) urlMappings.get(url);
//
//				mountBookmarkablePage(url, urlClass);
//
//			}
//
//		}

	}

	/**
	 * 
	 * 
	 * 
	 * @see IRoleCheckingStrategy#hasAnyRole(Roles)
	 */

	public final boolean hasAnyRole(final Roles roles) {

		final Roles sessionRoles = AbstractAuthenticatedWebSession.get()
				.getRoles();

		return sessionRoles != null && sessionRoles.hasAnyRole(roles);

	}

	/**
	 * 
	 * 
	 * 
	 * @see IUnauthorizedComponentInstantiationListener#onUnauthorizedInstantiation(Component)
	 */

	public void onUnauthorizedInstantiation(final Component component) {

		// if a page is instantiated without authorization

		if (component instanceof Page)

		{

			// if the user could not be signed in through the remember me
			// cookie, intercept their request

			if (!AbstractAuthenticatedWebSession.get().isSignedIn())

			{

				// Redirect to intercept page to let the user sign in

				throw new RestartResponseAtInterceptPageException(
						getSignInPageClass());

			}

			// call this method if the user is signed in but does not have
			// proper authorization

			onUnauthorizedPage((Page) component);

		}

		else

		{

			// The component was not a page, so throw an exception

			throw new UnauthorizedInstantiationException(component.getClass());

		}

	}

	@Override
	public Session newSession(final Request request, final Response response) {
		try
		{
			// instantiate the session
			AbstractAuthenticatedWebSession session = webSessionClassRef.get().getDeclaredConstructor(Request.class).newInstance(request);
			// call the init method
			session.init(request, response);
			return session;
		}
		catch (Exception e)
		{
			throw new WicketRuntimeException(
					"Unable to instantiate web session " +
					webSessionClassRef.get(), e);
		}

	}

	/**
	 * 
	 * Called when an authenticated user tries to navigate to a page that they
	 * are not authorized to
	 * 
	 * access. You might want to override this to navigate to some explanatory
	 * page or to the
	 * 
	 * application's home page.
	 * 
	 * 
	 * 
	 * @param Page
	 */

	protected void onUnauthorizedPage(final Page page) {

		throw new UnauthorizedInstantiationException(page.getClass());

	}

	/**
	 * 
	 * Returns the session class to use in the web application
	 * 
	 * 
	 * 
	 * @return AuthenticatedWebSession
	 */

	protected abstract Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass();

	/**
	 * 
	 * Returns the page to redirect to when a user needs to signin
	 * 
	 * 
	 * 
	 * @return Class
	 */

	protected abstract Class<? extends WebPage> getSignInPageClass();

	/**
	 * 
	 * Returns the page to redirect to after access to a page was denied
	 * 
	 * 
	 * 
	 * @return Class
	 */

	public abstract Class<? extends WebPage> getAccessDeniedPage();

	/**
	 * 
	 * Returns the page to redirect to after a successful login
	 * 
	 * 
	 * 
	 * @return Class
	 */

	public abstract Class<? extends WebPage> getSuccessfulAuthenticationPage();

	// getters and setters

	public AuthenticationManager getAuthenticationManager() {

		return authenticationManager;

	}

	public void setAuthenticationManager(
			AuthenticationManager authenticationManager) {

		this.authenticationManager = authenticationManager;

	}

//	public Map getUrlMappings() {
//
//		return urlMappings;
//
//	}
//
//	public void setUrlMappings(Map urlMappings) {
//
//		this.urlMappings = urlMappings;
//
//	}

	public RememberMeServices getRememberMeServices() {

		return rememberMeServices;

	}

	public void setRememberMeServices(RememberMeServices rememberMeServices) {

		this.rememberMeServices = rememberMeServices;

	}
	
	public SpringComponentInjector getSpringComponentInjector() {
		return springComponentInjector;
	}

	public void setSpringComponentInjector(
			SpringComponentInjector springComponentInjector) {
		this.springComponentInjector = springComponentInjector;
	}

}