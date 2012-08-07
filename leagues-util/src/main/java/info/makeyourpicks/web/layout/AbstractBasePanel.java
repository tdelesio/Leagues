package info.makeyourpicks.web.layout;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.service.GameManager;
import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.service.MessageBoardManager;
import info.makeyourpicks.service.PicksManager;
import info.makeyourpicks.service.PlayerManager;
import info.makeyourpicks.service.TeamManager;
import info.makeyourpicks.web.LeagueSession;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public abstract class AbstractBasePanel extends Panel{
	
	@SpringBean(name="leagueManager")
	protected LeagueManager leagueManager;
	
	@SpringBean(name="teamManager")
	protected TeamManager teamManager;
	
	@SpringBean(name="picksManager")
	protected PicksManager picksManager;
	
	@SpringBean(name="gameManager")
	protected GameManager gameManager;
	
	@SpringBean(name="playerManager")
	protected PlayerManager playerManager;
	
	@SpringBean(name="messageBoardManager")
	protected MessageBoardManager messageBoardManager;
	
	public AbstractBasePanel(String id)
	{
		super(id);
	}
	
	public AbstractBasePanel(String id, IModel model)
	{
		super(id, model);
	}
	
	public LeagueSession getWebSession() {
		return LeagueSession.getWebSession();
	}
	
	protected Player getPlayer()
	{
		return getWebSession().getPlayer();
	}
	
	protected League getActiveLeague()
	{
		return getWebSession().getActiveLeague();
	}
	
	protected void setActiveLeague(League activeLeague)
	{
		getWebSession().setActiveLeague(activeLeague);
	}
	
	protected static final AttributeModifier cssOn = new AttributeModifier("class", true, new AbstractReadOnlyModel()
    {
        public Object getObject()
        {
            return "on";
        }
    });
}
