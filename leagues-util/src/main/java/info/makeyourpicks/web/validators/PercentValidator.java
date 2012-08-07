package info.makeyourpicks.web.validators;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.ValidationError;

public class PercentValidator extends AbstractFormValidator
{
	public PercentValidator(FormComponent<?> formComponent1,
			FormComponent<?> formComponent2, FormComponent<?> formComponent3,
			FormComponent<?> formComponent4, FormComponent<?> formComponent5)
	{
		components = new FormComponent[] { formComponent1, formComponent2, formComponent3, formComponent4, formComponent5 };
	}
	
	private final FormComponent<?>[] components;
	
	public FormComponent<?>[] getDependentFormComponents() {
		return components;
	}

	public void validate(Form<?> form) {
		final FormComponent<?> formComponent1 = components[0];
		final FormComponent<?> formComponent2 = components[1];
		final FormComponent<?> formComponent3 = components[2];
		final FormComponent<?> formComponent4 = components[3];
		final FormComponent<?> formComponent5 = components[4];
		
		int value1 = Integer.parseInt(formComponent1.getInput());
		int value2 = Integer.parseInt(formComponent2.getInput());
		int value3 = Integer.parseInt(formComponent3.getInput());
		int value4 = Integer.parseInt(formComponent4.getInput());
		int value5 = Integer.parseInt(formComponent5.getInput());
		
		int sum = value1+value2+value3+value4+value5;
		if (sum != 100)
		{
			ValidationError error = new ValidationError();
			error.setMessage("Your percents is "+sum+".  1st, 2nd, 3rd, 4th, and 5th percents must equal 100.");
			
			formComponent1.error((IValidationError)error);
		}
	}


//	@Override
//	protected Map<String, Object> variablesMap() {
//		// TODO Auto-generated method stub
//		Map<String, Object> map = super.variablesMap();
//		map.put("", "");
//		return map;
//	}
	
	
	
}
