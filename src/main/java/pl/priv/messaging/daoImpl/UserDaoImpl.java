package pl.priv.messaging.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pl.priv.messaging.dao.UserDao;
import pl.priv.messaging.model.UserDTO;

@Repository
public class UserDaoImpl implements UserDao 
{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	RowMapper<UserDTO> rowMapper;
	
	@Autowired
	UserDTO userDTO;
	
	@Override
	public void insertUser(UserDTO userDTO) 
	{
		String sql = "INSERT INTO user_details VALUES(?, ?, ?, ?)";
		Object[] args = {
							userDTO.getUserId().trim(),
							userDTO.getFirstName().trim(), 
							userDTO.getLastName().trim(), 
							userDTO.getPassword()
						};
		
		int numberOfRowsInserted = jdbcTemplate.update(sql, args);
		
		System.out.println(numberOfRowsInserted + " row(s) updated");
	}
	
	@Override
	public void insertUserIntoUsersAndAuthorities(String username, String password) 
	{	
		String sql_1 = "INSERT INTO users(username, password) values(?, ?)";
		Object[] args_1 = {username, password};
		
		int numberOfRowsInserted = jdbcTemplate.update(sql_1, args_1);
		
		String sql_2 = "INSERT INTO authorities values(?, 'ROLE_user');";
		Object[] args_2 = {username};
		
		numberOfRowsInserted = jdbcTemplate.update(sql_2, args_2);
		
		System.out.println(numberOfRowsInserted + " row(s) updated");
	}
	
	@Override
	public List<UserDTO> selectUser(String userId) 
	{
		String sql = "SELECT * FROM user_details WHERE user_id=?";
		String args = userId;
		List<UserDTO> user = this.jdbcTemplate.query(sql, rowMapper, args);
		
		return user;
	}
	
	@Override
	public Boolean userIdExists(String userId) 
	{
		Boolean userIdExists = false;
		String sql = "SELECT * FROM user_details";
		
		List<UserDTO> users = this.jdbcTemplate.query(sql, rowMapper);
		
		for(UserDTO u : users) 
		{
			if(u.getUserId().equals(userId))
				userIdExists = true;
		}
		
		return userIdExists;
	}
	
	@Override
	public Boolean passwordMatch(String userId, String password)  
	{
		Boolean passwordMatch = false;
		String sql = "SELECT * FROM user_details";
		
		List<UserDTO> users = this.jdbcTemplate.query(sql, rowMapper);
		
		for(UserDTO u : users) 
		{
			if(u.getUserId().equals(userId) && u.getPassword().equals(password))
				passwordMatch = true;
		}
		
		return passwordMatch;
	}
}
