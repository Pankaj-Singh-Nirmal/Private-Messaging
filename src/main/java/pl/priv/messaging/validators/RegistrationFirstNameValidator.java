package pl.priv.messaging.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import pl.priv.messaging.model.UserDTO;

@Component
public class RegistrationFirstNameValidator implements Validator
{
	@Override
	public boolean supports(Class<?> clazz) 
	{
		return UserDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) 
	{
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "errors.firstName-empty");
		
		String firstNameUser = ((UserDTO) target).getFirstName();
		if(firstNameUser.length() > 45)
			errors.rejectValue("firstName", "errors.firstName-length");
	}
}
