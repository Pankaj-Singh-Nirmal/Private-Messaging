package pl.priv.messaging.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import pl.priv.messaging.service.CustomDateTimeFormatService;
import pl.priv.messaging.service.PrivateChatService;
import pl.priv.messaging.serviceImpl.CustomDateTimeFormatServiceImpl;
import pl.priv.messaging.serviceImpl.PrivateChatServiceImpl;

@Component
public class PrivateChatResultSetExtractorImpl implements ResultSetExtractor<Map<String, List<String>>>
{
	CustomDateTimeFormatService customDateTimeFormatService = new CustomDateTimeFormatServiceImpl();
	
	PrivateChatService privateChatService = new PrivateChatServiceImpl();
	
	@Override
	public Map<String, List<String>> extractData(ResultSet rs) throws SQLException, DataAccessException 
	{
		Map<String, List<String>> privateChatMap = new LinkedHashMap<>();
		
		while(rs.next()) 
		{
			List<String> privateChatList = new ArrayList<>();
			
			for(int i=2; i<=5; i++) 
			{
				if(i==5) 
				{
					String timestamp = rs.getString(i);
					timestamp = timestamp.substring(0, timestamp.length()-2); //remove .0 from the timestamp
					
					String[] splitTimestamp = timestamp.split(" ");
					String dateAsString = splitTimestamp[0];
					String timeAsString = splitTimestamp[1];
					
					String formattedDate = customDateTimeFormatService.getFormattedDate(dateAsString);
					String formattedTime = customDateTimeFormatService.getFormattedTime(timeAsString);
					
					if(dateAsString.equals(LocalDate.now().toString()))
						formattedDate = "Today";
					else if(dateAsString.equals(LocalDate.now().minusDays(1).toString()))
						formattedDate = "Yesterday";
					
					String customizedDateTime = formattedTime + ", " + formattedDate;
					
					privateChatList.add(customizedDateTime);
				}
				else
					privateChatList.add(rs.getString(i));
			}
			
			privateChatMap.put(rs.getString(1), privateChatList);
		}
		
		return privateChatMap;
	}
}
