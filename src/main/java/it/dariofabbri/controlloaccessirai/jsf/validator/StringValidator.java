package it.dariofabbri.controlloaccessirai.jsf.validator;

import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value="stringValidator")
public class StringValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {

		String tmpBuffer;
		
		// Get the string value of the passed object.
		//
		String s = value.toString();

		// If a minimum length has been specified, check it.
		//
		tmpBuffer = component.getAttributes().get("minLength").toString();
		if(tmpBuffer != null) {
			int minLength = Integer.parseInt(tmpBuffer);
			if(s.length() < minLength) {
				FacesMessage message = new FacesMessage();
				message.setSummary(String.format("Minimo %d caratteri", minLength));
				message.setDetail(String.format("La lunghezza minima ammessa è %d caratteri.", minLength));
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			}
		}

		// If a maximum length has been specified, check it.
		//
		tmpBuffer = component.getAttributes().get("maxLength").toString();
		if(tmpBuffer != null) {
			int maxLength = Integer.parseInt(tmpBuffer);
			if(s.length() > maxLength) {
				FacesMessage message = new FacesMessage();
				message.setSummary(String.format("Massimo %d caratteri", maxLength));
				message.setDetail(String.format("La lunghezza massima ammessa è %d caratteri.", maxLength));
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			}
		}
		
		// If a regex pattern has been specified, apply it.
		//
		tmpBuffer = component.getAttributes().get("pattern").toString();
		if(tmpBuffer != null) {
			if(!Pattern.matches(tmpBuffer, s)) {
				FacesMessage message = new FacesMessage();
				message.setSummary("Formato errato");
				message.setDetail("Il dato immesso non rispetta il formato atteso");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			}
		}
	}

}
