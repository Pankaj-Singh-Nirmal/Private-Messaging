package pl.priv.messaging.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import pl.priv.messaging.model.UserDTO;

@Component
public class UserRowMapperImpl implements RowMapper<UserDTO>
{	
	@Override
	public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		UserDTO userDTO = new UserDTO();
		
		userDTO.setUserId(rs.getString(1));
		userDTO.setFirstName(rs.getString(2));
		userDTO.setLastName(rs.getString(3));
		userDTO.setPassword(rs.getString(4));
		
		return userDTO;
	}
}
