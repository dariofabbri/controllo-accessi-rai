package it.dariofabbri.controlloaccessirai.jsf.validator;

import it.dariofabbri.controlloaccessirai.util.ValidationUtil;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value="matricolaValidator")
public class MatricolaValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {

		// Get the string value of the passed object.
		//
		String s = value.toString();

		// Check if it is a valid integer.
		//
		if(!ValidationUtil.isInteger(s)) {
			FacesMessage message = new FacesMessage();
			message.setDetail("Matricola non valida");
			message.setSummary("La matricola inserita non rappresenta un numero valido");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}

}
