package pl.priv.messaging.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class GetUserDetailsByUserIdRowMapperImpl implements RowMapper<List<String>>
{	
	@Override
	public List<String> mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		List<String> userDetails = new ArrayList<>();
		
		userDetails.add(rs.getString(1));
		userDetails.add(rs.getString(2));
		userDetails.add(rs.getString(3));
		
		return userDetails;
	}
}
