package pl.priv.messaging.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class AllUserResultSetExtractorImpl implements ResultSetExtractor<Map<String, List<String>>>
{	
	@Override
	public Map<String, List<String>> extractData(ResultSet rs) throws SQLException, DataAccessException 
	{
		Map<String, List<String>> userDetailsMap = new LinkedHashMap<>();
		
		while(rs.next()) 
		{
			List<String> userDetailsList = new ArrayList<>();
			
			for(int i=1; i<=3; i++) 
			{
				userDetailsList.add(rs.getString(i));
			}
			
			userDetailsMap.put(rs.getString(1), userDetailsList);
		}
		
		return userDetailsMap;
	}
}
