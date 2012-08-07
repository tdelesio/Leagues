package info.makeyourpicks.web.admin.pages;

import info.makeyourpicks.web.admin.panels.AdminTopPanel;
import info.makeyourpicks.web.layout.pages.LeagueMemberWebPage;

import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.behavior.HeaderContributor;

/**
 * @author PRC9041
 */
@AuthorizeInstantiation("admin")
public abstract class AbstractAdminBasePage extends LeagueMemberWebPage {

	public AbstractAdminBasePage()
	{
		super(false);
		
//		add(HeaderContributor.forCss(
//			       "styles/league.css"
//			   )); 
		add(new AdminTopPanel("adminTopPanel"));
	}



	
	
	
}

