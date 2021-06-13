package pl.priv.messaging.service;

import java.util.List;

import pl.priv.messaging.model.UserDTO;

public interface UserService 
{
	void insertUser(UserDTO userDTO);
	void insertUserIntoUsersAndAuthorities(String username, String password);
	List<UserDTO> selectUser(String userId);
	Boolean userIdExists(String userId);
	Boolean passwordMatch(String userId, String password);
}
