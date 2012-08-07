package com.delesio.extensions.markup.html;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;

import com.delesio.web.model.ICalendarDay;

public abstract class CalendarDataPanel extends Panel {

	public CalendarDataPanel(String id, Locale locale, Date date) {
		super(id);

		
		add(new Label("sun", "Sunday"));
		add(new Label("mon", "Monday"));
		add(new Label("tue", "Tuesday"));
		add(new Label("wed", "Wednesday"));
		add(new Label("thu", "Thursday"));
		add(new Label("fri", "Friday"));
		add(new Label("sat", "Saturday"));
		
		
		
		Calendar cal = Calendar.getInstance(locale);
		cal.setTime(date);
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int curDay = cal.get(Calendar.DAY_OF_MONTH);
		int curYear = cal.get(Calendar.YEAR);
		int curMonth = cal.get(Calendar.MONTH);

		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, 1 - cal.get(Calendar.DAY_OF_MONTH));

		int dayOfFirstWeek = cal.get(Calendar.DAY_OF_WEEK);

		cal.setTime(new Date());
		int theYear = cal.get(Calendar.YEAR);
		int theMonth = cal.get(Calendar.MONTH);
		int today = -1;
		if (curYear == theYear && curMonth == theMonth) {
			today = cal.get(Calendar.DAY_OF_MONTH);
		}

		final String idName = "column"; 
		RepeatingView repeating = new RepeatingView("calendarData");
        add(repeating);
        
		/* count number of rows */
		int numOfRows = ((days + dayOfFirstWeek) >= 35) ? 6 : 5;
		int day = 1;
		int count = 1;
		curMonth = curMonth + 1;
		Map<Integer, Object> monthMap = getMonthMap(theMonth+1, theYear);
		for (int r = 1; r <= numOfRows; r++) 
		{
//			WebMarkupContainer item = new WebMarkupContainer(newChildId());
			WebMarkupContainer item = new WebMarkupContainer(repeating.newChildId());
			
			repeating.add(item);
			
			for (int i = 1; i <= 7; i++) 
			{
				if (count < dayOfFirstWeek || count >= (days + dayOfFirstWeek)) {
					item.add(new Label(idName+String.valueOf(i),""));
				}
				else
				{
//					item.add(calendarDay.getComponentForDay(idName+String.valueOf(i), day));
					if (monthMap.get(day)==null)
					{
						item.add(getComponentWhenOpen(idName+String.valueOf(i), day));
					}
					else
					{
						item.add(getComponentWhenOccupied(idName+String.valueOf(i), day));
					}
					day++;
				}
//				else if (day == today) {
////					item.add(_TODAY_TD + _cA(base_url, curYear, curMonth, day)
////							+ TD_);
//					item.add(new Label(idName+String.valueOf(i),String.valueOf(day)));
//					day++;
//				} else if (day == curDay) {
////					item.add(_CUR_TD + day + TD_);
//					item.add(new Label(idName+String.valueOf(i),String.valueOf(day)));
//					day++;
//				} else {
////					sb
////							.append(_TD + _cA(base_url, curYear, curMonth, day)
////									+ TD_);
//					item.add(new Label(idName+String.valueOf(i),String.valueOf(day)));
//					day++;
//				}
				count++;
			}
			
		}
		
		AjaxLink<String> prevMonth = new AjaxLink<String>("prevMonth") {

			@Override
			public void onClick(AjaxRequestTarget target) {
//				_cA(base_url, cal.get(Calendar.YEAR), 
//					      cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)
			}
			
			
			
		};
		add(prevMonth);
		
		AjaxLink<String> nextMonth = new AjaxLink<String>("nextMonth") {

			@Override
			public void onClick(AjaxRequestTarget target) {
//				_cA(base_url, cal.get(Calendar.YEAR), 
//					      cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)
			}
			
			
			
		};
		add(nextMonth);
		
		Label currentMonth = new Label("currentMonth", new SimpleDateFormat("MMMM").format(date));
		add(currentMonth);
		
		
	}

	private String _cA(String base_url, int year, 
		    int month, int day)
		  {
		    String dayStr = 
		      (day < 10) ? "0" + day : String.valueOf(day);
		    String monthStr =
		      (month < 10) ? "0" + month : String.valueOf(month);

		    return "<a href=\"" + base_url + year + monthStr + dayStr
		         + "\">" + day + "</a>";
		  }

		  private String _cA(String base_url, int year, int month,
		    int day, String showText)
		  {
		    String dayStr = 
		      (day < 10) ? "0" + day : String.valueOf(day);
		    String monthStr =
		      (month < 10) ? "0" + month : String.valueOf(month);

		    return "<a href=\"" + base_url + year + monthStr + dayStr
		      + "\">" + showText + "</a>";
		  }
		  
	public abstract Map<Integer, Object> getMonthMap(int month, int year);  
	public abstract WebComponent getComponentWhenOccupied(String id, int day);
	public abstract WebComponent getComponentWhenOpen(String id, int day);
}
