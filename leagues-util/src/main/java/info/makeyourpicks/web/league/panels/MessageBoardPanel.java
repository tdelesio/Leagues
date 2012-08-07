package info.makeyourpicks.web.league.panels;

import info.makeyourpicks.model.MessageBoard;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.web.layout.AbstractBasePanel;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

/**
 * @author PRC9041
 */
public class MessageBoardPanel extends AbstractBasePanel {
		
	public MessageBoardPanel(String id)
	{
		this(id, true);
	}
	public MessageBoardPanel(String id, boolean showInput)
	{
		super(id);
		
		FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");
        add(feedbackPanel);
        
        MessageBoard messageBoard = new MessageBoard();
        messageBoard.setToPlayer(getPlayer());
        //add(new MessagesDataView("repeating", messageBoard).set);
        
        IDataProvider messageDataProvider = new IDataProvider()
        {
        	public void detach() {
        		// TODO Auto-generated method stub
        	}

        	public Iterator iterator(int start, int count) {
        		return messageBoardManager.getAllMessagesForUser(getPlayer()).subList(start, start+count).iterator();
        	}

        	public IModel model(final Object object) {
        		return new CompoundPropertyModel(new LoadableDetachableModel()
        		{

					@Override
					protected Object load()
					{
						return messageBoardManager.loadMessageBoard(((MessageBoard)object).getId());
					}        			
        		});
        	}

        	public int size() {
        		return messageBoardManager.getAllMessagesForUser(getPlayer()).size();
        	}
        };
        
        DataView dataView = new DataView("repeating", messageDataProvider)
        {
        	protected void populateItem(Item item) {
        		MessageBoard messageBoard = (MessageBoard)item.getModelObject();
//        		item.setModel(new CompoundPropertyModel(new DetachableMessage(messageBoard)));

        		item.add(new Label("fromPlayer.username"));
                item.add(new Label("postedDisplay"));
                item.add(new Label("message"));
        	}
        };
        dataView.setItemsPerPage(5);
        add(dataView);
        
        add(new PagingNavigator("navigator", dataView));
        
        
        IModel messageBoardModel = new CompoundPropertyModel(new LoadableDetachableModel()
        {

			@Override
			protected Object load()
			{
				MessageBoard messageBoardInfo = new MessageBoard();
		        messageBoardInfo.setLeagueName(getActiveLeague());
		        messageBoardInfo.setFromPlayer(getPlayer());
		        messageBoardInfo.setToPlayer(new Player(getActiveLeague().getLeagueName()));
		        return messageBoardInfo;
			}
        	
        });
        add(new MessageBoardForm("messageBoardForm", messageBoardModel).setVisible(showInput));
        
        messageBoardManager.markMessagesAsRead(getPlayer());
     
	}
	
	public class MessageBoardForm extends Form {

		public MessageBoardForm(String id, IModel messageBoardModel)
		{
			super(id, messageBoardModel);
			
			List<Player> players = playerManager.getPlayersInLeague(getActiveLeague());
			Player leagueBroadcast = new Player();
			leagueBroadcast.setUsername(getActiveLeague().getLeagueName());
			players.add(leagueBroadcast);
			
			
			DropDownChoice comboChoiceDropDown = new DropDownChoice("toPlayer", players, new ChoiceRenderer("username", "username"));
			comboChoiceDropDown.setRequired(true);
			comboChoiceDropDown.setLabel(new Model("Player Name"));
			comboChoiceDropDown.setRequired(true);
			add(comboChoiceDropDown);
			
			TextArea message = new TextArea("message");
			message.setLabel(new Model("Comment"));
			add(message);
			
			add(new Button("submit"));
			
			CheckBox entireLeague = new CheckBox("entireLeague");
			if (getPlayer().getMemberLevel()==Player.ADMIN)
			{
				entireLeague.setVisible(true);
			}
			else
			{
				entireLeague.setVisible(false);
			}
			add(entireLeague);
			
		}
		
		public void onSubmit() 
		{
			MessageBoard messageBoardInfo = (MessageBoard)getModelObject();
			messageBoardManager.postMessage(messageBoardInfo);
			
			messageBoardInfo.setMessage("");
			//setResponsePage(LeagueConstants.homePageMap.get(getActiveLeague().getLeagueType().getTypeOfLeague()));
		}
	}
	
}

