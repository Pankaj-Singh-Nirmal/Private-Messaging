package pl.priv.messaging.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import pl.priv.messaging.model.UserDTO;

@Component
public class RegistrationConfirmPasswordValidator implements Validator
{
	@Override
	public boolean supports(Class<?> clazz) 
	{
		return UserDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) 
	{
		String passwordUser = ((UserDTO) target).getPassword();
		String confirmPasswordUser = ((UserDTO) target).getConfirmPassword();
		
		if(!(passwordUser.equals(confirmPasswordUser)))
			errors.rejectValue("confirmPassword", "errors.password-mismatch");
	}
}
