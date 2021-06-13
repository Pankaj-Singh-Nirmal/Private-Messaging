package pl.priv.messaging.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import pl.priv.messaging.model.PrivateChatDTO;

@Component
public class PrivateChatRowMapperImpl implements RowMapper<PrivateChatDTO>
{	
	@Override
	public PrivateChatDTO mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		PrivateChatDTO privateChatDTO = new PrivateChatDTO();
		
		privateChatDTO.setSenderUserId(rs.getString(2));
		privateChatDTO.setReceiverUserId(rs.getString(3));
		privateChatDTO.setMessage(rs.getString(4));
		privateChatDTO.setTimestamp(rs.getString(5));
		privateChatDTO.setMessageStatus(rs.getString(6));
		
		return privateChatDTO;
	}
}
