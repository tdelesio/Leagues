package info.makeyourpicks.web.components;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.AbstractReadOnlyModel;

public class CssClassModifier extends AttributeModifier {

	public static final int HomeAway=0;
	public static final int EvenOdd=1;
	
	private static Map<Integer, String>trueConditions = new HashMap<Integer, String>();
	private static Map<Integer, String>falseConditions = new HashMap<Integer, String>();
	
	static 
	{
		trueConditions.put(HomeAway, "home");
		trueConditions.put(EvenOdd, "even");
		
		falseConditions.put(HomeAway, "away");
		falseConditions.put(EvenOdd, "odd");
		
		
	};
	
	public CssClassModifier(final int key, final boolean condition)
	{
		super("class", true, new AbstractReadOnlyModel()
            {
                public Object getObject()
                {
                    return (condition) ? (String)trueConditions.get(key) : (String)falseConditions.get(key);
                }
            });
	}
	
	public CssClassModifier(final int key, final boolean condition, final String prefix)
	{
		super("class", true, new AbstractReadOnlyModel()
            {
                public Object getObject()
                {
                    return (condition) ? prefix+" "+(String)trueConditions.get(key) : prefix+" "+(String)falseConditions.get(key);
                }
            });
	}
}
