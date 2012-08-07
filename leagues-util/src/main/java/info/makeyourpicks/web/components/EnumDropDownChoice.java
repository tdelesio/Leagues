package info.makeyourpicks.web.components;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.model.IModel;

public class EnumDropDownChoice<T extends Enum<T>> extends DropDownChoice<T> {
	 
	public EnumDropDownChoice(String id)
	{
		super(id);
		setChoiceRenderer(new EnumChoiceRenderer<T>(this));
	}
	
	public EnumDropDownChoice(String id, IModel<T> model) {
		super(id);
		setModel(model);
		setChoiceRenderer(new EnumChoiceRenderer<T>(this));
	}
 
	public EnumDropDownChoice(String id, IModel<T> model, EnumChoiceRenderer<T> choiceRenderer) {
		super(id);
		setModel(model);
		setChoiceRenderer(choiceRenderer);
	}
 
	@Override
	public List<? extends T> getChoices() {
		return Arrays.asList(getModelObject().getDeclaringClass().getEnumConstants());
	}
}
