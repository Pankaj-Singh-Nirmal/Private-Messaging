package pl.priv.messaging.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.priv.messaging.model.PrivateChatDTO;
import pl.priv.messaging.model.UserDTO;
import pl.priv.messaging.service.PrivateChatService;
import pl.priv.messaging.service.UserService;
import pl.priv.messaging.validators.RegistrationConfirmPasswordValidator;
import pl.priv.messaging.validators.RegistrationFirstNameValidator;
import pl.priv.messaging.validators.RegistrationLastNameValidator;
import pl.priv.messaging.validators.RegistrationPasswordValidator;
import pl.priv.messaging.validators.RegistrationUserIdValidator;

@Controller
@SessionAttributes("sessionUser")
public class UserController 
{
	@Autowired
	UserService userService;
	
	@Autowired
	PrivateChatService privateChatService;
	
	@Autowired
	RegistrationUserIdValidator registrationUserIdValidator;
	
	@Autowired
	RegistrationFirstNameValidator registrationFirstNameValidator;
	
	@Autowired
	RegistrationLastNameValidator registrationLastNameValidator;
	
	@Autowired
	RegistrationPasswordValidator registrationPasswordValidator;
	
	@Autowired
	RegistrationConfirmPasswordValidator registrationConfirmPasswordValidator;
	
	@GetMapping("/")
	public String loadRegisterUserPage(@ModelAttribute("userInfo") UserDTO userDTO)
	{
		return "registerUser-page";
	}
	
	@PostMapping("/processUserRegistration")
	public String processUserRegistrationPage(@Valid @ModelAttribute("userInfo") UserDTO userDTO,
											  BindingResult result, RedirectAttributes redirectAttributes)
	{
		if(result.hasErrors())
		{
			List<ObjectError> allErrors = result.getAllErrors();
			for(ObjectError err : allErrors) 
			{
				System.out.println(err);
			}
			return "registerUser-page";
		}
		
		String plainTextPassword = userDTO.getPassword();
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String bcryptPassword = encoder.encode(plainTextPassword);
		userDTO.setPassword(bcryptPassword);
		
		userService.insertUser(userDTO);
		userService.insertUserIntoUsersAndAuthorities(userDTO.getUserId(), userDTO.getPassword());
		
		redirectAttributes.addFlashAttribute("registrationSuccess", "Registration Success !");
		
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String loadLoginPage()
	{
		return "login-page";
	}
	
	@GetMapping("/privateChatUser")
	public String loadPrivateChatUserPage(@ModelAttribute("privateChat") PrivateChatDTO privateChatDTO, 
										  Model model, HttpSession session, UserDTO userDTO)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String userId = auth.getName();
		
		List<UserDTO> user = userService.selectUser(userId);
		
		for(UserDTO u : user) 
		{
			userDTO.setUserId(userId);
			userDTO.setFirstName(u.getFirstName());
			userDTO.setLastName(u.getLastName());
		}
		
		session.setAttribute("sessionUser", userDTO);
		
		userDTO = (UserDTO) session.getAttribute("sessionUser");
		
		Map<String, List<String>> allUserList = privateChatService.getAllUserList(userDTO.getUserId()); 
		
		String selectedUser = privateChatDTO.getSelectedUser();
		
		model.addAttribute("users", allUserList);
		
		Map<String, List<String>> privateChatData;
		int messageCount;
		
		if(!privateChatService.chatExist(userDTO.getUserId()) && selectedUser == null)
			return "emptyPrivateChatUser-page";
		
		if(!privateChatService.chatExist(userDTO.getUserId()) || selectedUser != null) 
		{
			String[] selectedUserDetails = selectedUser.split("-");
			
			model.addAttribute("selectedUserId", selectedUserDetails[0].trim())
				 .addAttribute("selectedUserName", selectedUserDetails[1].trim());
			
			privateChatService.markMessageAsRead(selectedUserDetails[0].trim(), userDTO.getUserId());
			
			privateChatData = privateChatService.getPrivateChatData(userDTO.getUserId(), 
																	selectedUserDetails[0].trim());
			
			String selectedUserId = selectedUserDetails[0].trim();
			messageCount = privateChatService.getMessageCountBetweenTwoUsers(userDTO.getUserId(), 
					 														 selectedUserId);
		}
		else
		{
			selectedUser = privateChatService.getLastAddedDataForUser(userDTO.getUserId());
			
			List<String> userDetails = privateChatService.getUserDetailsByUserId(selectedUser);
			
			model.addAttribute("selectedUserId", userDetails.get(0))
			 	 .addAttribute("selectedUserName", userDetails.get(1) + " " + userDetails.get(2));
			
			privateChatService.markMessageAsRead(userDetails.get(0), userDTO.getUserId());
			
			privateChatData = privateChatService.getPrivateChatData(userDTO.getUserId(), userDetails.get(0));
			
			messageCount = privateChatService.getMessageCountBetweenTwoUsers(userDTO.getUserId(), 
																			 userDetails.get(0));
		}
		
		Map<String, List<String>> recentUserData = privateChatService.getRecentUserData(userDTO.getUserId());
		
		model.addAttribute("chats", privateChatData)
			 .addAttribute("messageCount", messageCount)
			 .addAttribute("recentUserData", recentUserData);
		
		return "messages";
	}
	
	@PostMapping("/messageSentUser")
	public String loadMessageSentPrivateChatUserPage(@ModelAttribute("privateChat") PrivateChatDTO privateChatDTO, 
											 		 Model model, HttpSession session, RedirectAttributes redirectAttributes)
	{
		UserDTO userDTO = (UserDTO) session.getAttribute("sessionUser");
		
		Map<String, List<String>> allUserList = privateChatService.getAllUserList(userDTO.getUserId()); 
		
		String selectedUserId = privateChatDTO.getSelectedUser().split("-")[0];
		String selectedUserName = privateChatDTO.getSelectedUser().split("-")[1];
		
		privateChatService.markMessageAsRead(privateChatDTO.getSelectedUser().split("-")[0], 
				userDTO.getUserId());
		
		privateChatDTO.setSenderUserId(userDTO.getUserId());
		privateChatDTO.setReceiverUserId(selectedUserId);
		
		if(!privateChatDTO.getMessage().trim().isEmpty())
			privateChatService.insertData(privateChatDTO);
		
		Map<String, List<String>> privateChatData = privateChatService
				.getPrivateChatData(userDTO.getUserId(), selectedUserId);
		
		int messageCount = privateChatService.getMessageCountBetweenTwoUsers(userDTO.getUserId(), 
				 															 selectedUserId);
		
		Map<String, List<String>> recentUserData = privateChatService.getRecentUserData(userDTO.getUserId());
		
		redirectAttributes.addFlashAttribute("users", allUserList)
						  .addFlashAttribute("selectedUserId", selectedUserId)
						  .addFlashAttribute("selectedUserName", selectedUserName)
						  .addFlashAttribute("chats", privateChatData)
						  .addFlashAttribute("messageCount", messageCount)
						  .addFlashAttribute("recentUserData", recentUserData);
		
		return "redirect:/messages";
	}
	
	@GetMapping("/messages")
	public String loadMessageSentSuccessfullyUserPage(@ModelAttribute("privateChat") PrivateChatDTO privateChatDTO)
	{
		return "messages";
	}
	
	@InitBinder("userInfo")
	public void initBinderRegisterUserValidator(WebDataBinder binder) 
	{
		binder.addValidators(registrationUserIdValidator);
		binder.addValidators(registrationFirstNameValidator);
		binder.addValidators(registrationLastNameValidator);
		binder.addValidators(registrationPasswordValidator);
		binder.addValidators(registrationConfirmPasswordValidator);
	}
}
