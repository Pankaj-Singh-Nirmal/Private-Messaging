package pl.priv.messaging.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import pl.priv.messaging.model.UserDTO;

@Component
public class RegistrationPasswordValidator implements Validator
{
	@Override
	public boolean supports(Class<?> clazz) 
	{
		return UserDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) 
	{
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "errors.password-empty");
		
		String passwordUser = ((UserDTO) target).getPassword();
		if(passwordUser.length() > 70)
			errors.rejectValue("password", "errors.password-length");
	}
}
