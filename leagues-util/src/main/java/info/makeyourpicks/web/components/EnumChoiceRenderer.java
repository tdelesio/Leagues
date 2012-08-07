package info.makeyourpicks.web.components;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.IChoiceRenderer;

public class EnumChoiceRenderer<T extends Enum<T>> implements IChoiceRenderer<T> {
	 
	/** The Component used a the root of the I18N search process */
	private final Component resourceProvider;
 
	public EnumChoiceRenderer(final Component resourceProvider) {
		this.resourceProvider = resourceProvider;
	}
 
	public Object getDisplayValue(final T value) {
//		final String key = EnumMessageKeyProvider.getMessageKey(value);
//		return resourceProvider.getString(key);
		return value.name();
	}
 
	public String getIdValue(final T object, final int index) {
		final Enum<?> enumValue = object;
		return enumValue.name();
	}
}
