package pl.priv.messaging.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import pl.priv.messaging.model.UserDTO;

@Component
public class RegistrationLastNameValidator implements Validator
{
	@Override
	public boolean supports(Class<?> clazz) 
	{
		return UserDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) 
	{
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "errors.lastName-empty");
		
		String lastNameUser = ((UserDTO) target).getLastName();
		if(lastNameUser.length() > 45)
			errors.rejectValue("lastName", "errors.lastName-length");
	}
}
