package info.makeyourpicks.web.admin.panels;

import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.web.admin.pages.CreateWeekPage;
import info.makeyourpicks.web.admin.pages.UpdateWeekPage;
import info.makeyourpicks.web.layout.AbstractBasePanel;

import java.util.Iterator;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 * @author PRC9041
 */
public class SetupWeekPanel extends AbstractBasePanel {

	
	public SetupWeekPanel(String id, final IModel seasonModel)
	{
		super(id);
		add(new Link("createWeek")
		{

			@Override
			public void onClick() {
				setResponsePage(new CreateWeekPage(seasonModel));
			}
			
		});
		
		IDataProvider dataProvider = new IDataProvider()
		{

			public Iterator iterator(int first, int count)
			{
				return gameManager.getWeeksBySeason((Season)seasonModel.getObject()).subList(first, first+count).iterator();
			}

			public IModel model(final Object object)
			{
				return new CompoundPropertyModel(new LoadableDetachableModel()
				{

					@Override
					protected Object load()
					{
						return gameManager.loadWeek(((Week)object).getId());
					}
					
				});
			}

			public int size()
			{
				return gameManager.getWeeksBySeason((Season)seasonModel.getObject()).size();
			}

			public void detach()
			{
				// TODO Auto-generated method stub
				
			}
			
		};
		
		DataView dataView = new DataView("repeating", dataProvider)
		{

			@Override
			protected void populateItem(final Item item)
			{
				item.add(new Label("weekNumber"));
		        item.add(new Label("weekStartTimeDisplay"));
		        item.add(new Link("updateWeek")
		        {

					@Override
					public void onClick() {
						setResponsePage(new UpdateWeekPage(item.getModel()));
					}
		        	
		        });
		        
		        item.add(new AttributeModifier("class", true,
						new AbstractReadOnlyModel() {
							public Object getObject() {
								return (item.getIndex() % 2 != 0) ? "even" : "odd";
							}
						}));

			}
			
		};
		add(dataView);
	}
	
	
}

