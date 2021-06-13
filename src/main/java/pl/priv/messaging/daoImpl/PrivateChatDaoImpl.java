package pl.priv.messaging.daoImpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pl.priv.messaging.dao.PrivateChatDao;
import pl.priv.messaging.model.PrivateChatDTO;

@Repository
public class PrivateChatDaoImpl implements PrivateChatDao 
{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	RowMapper<PrivateChatDTO> rowMapper;
	
	@Autowired
	PrivateChatDTO privateChatDTO;
	
	@Override
	public Map<String, List<String>> getAllUserList(String userId) 
	{
		String sql = "SELECT * FROM user_details WHERE NOT user_id=?";
		String args = userId;
		
		Map<String, List<String>> userMap = this.jdbcTemplate.query
													(sql, new AllUserResultSetExtractorImpl() , args);
		
		return userMap;
	}
	
	@Override
	public boolean chatExist(String userId) 
	{
		String sql = "SELECT count(*) FROM private_chat WHERE receiver_user_id=? OR sender_user_id=?";
		Object[] args = {userId, userId};
		
		int count = this.jdbcTemplate.queryForObject(sql, Integer.class, args);
		
		if(count < 1)
			return false;
		
		return true;
	}
	
	@Override
	public Map<String, List<String>> getPrivateChatData(String userId1, String userId2) 
	{
		String sql = "SELECT * FROM private_chat WHERE (sender_user_id=? AND receiver_user_id=?) "
				   + "OR (sender_user_id=? AND receiver_user_id=?)";
		
		Object[] args = {userId1, userId2, userId2, userId1};
		
		Map<String, List<String>> privateChatList = this.jdbcTemplate.query
													(sql, new PrivateChatResultSetExtractorImpl(), args);
		
		return privateChatList;
	}
	
	@Override
	public void insertData(PrivateChatDTO privateChatDTO) 
	{
		String sql = "INSERT INTO private_chat(sender_user_id, receiver_user_id, message, timestamp) "
				   + "VALUES(?, ?, ?, now())";
		Object[] args = {
							privateChatDTO.getSenderUserId(),
							privateChatDTO.getReceiverUserId(),
							privateChatDTO.getMessage()
						};
		
		int numberOfRowsInserted = jdbcTemplate.update(sql, args);
		
		System.out.println(numberOfRowsInserted + " row(s) updated");
	}
	
	@Override
	public void deleteUserIdFromPrivateChat(String userId)
	{
		String sql = "DELETE FROM private_chat WHERE sender_user_id=? OR receiver_user_id=?";
		Object[] args = {userId, userId};
		
		int numberOfRowsUpdated = jdbcTemplate.update(sql, args);
		
		System.out.println(numberOfRowsUpdated + " row(s) updated");
	}
	
	@Override
	public String getLastAddedDataForUser(String userId) 
	{
		String sql = "SELECT * FROM private_chat WHERE receiver_user_id=? OR sender_user_id=? "
				   + "ORDER BY sl_no DESC LIMIT 1";
		Object[] args = {userId, userId};
		
		List<PrivateChatDTO> lastAddedRow = this.jdbcTemplate.query(sql, rowMapper, args);
		
		String recentUserId;
		
		if(lastAddedRow.get(0).getSenderUserId().equals(userId))
			recentUserId = lastAddedRow.get(0).getReceiverUserId();
		else
			recentUserId = lastAddedRow.get(0).getSenderUserId();
		
		return recentUserId;
	}
	
	@Override
	public Map<String, List<String>> getRecentUserListForUser(String userId) 
	{
		String sql = "SELECT * FROM private_chat WHERE sl_no IN"
				   + "(" 
				   + "		SELECT MAX(sl_no) as sl_no FROM private_chat " 
				   + "		WHERE receiver_user_id=? OR sender_user_id=? " 
				   + "		GROUP BY receiver_user_id, sender_user_id"
				   + ")" 
				   + " ORDER BY sl_no DESC";
		Object[] args = {userId, userId};
		
		List<PrivateChatDTO> recentUserDetailsList = this.jdbcTemplate.query(sql, rowMapper, args);
		
		Map<String, List<String>> recentUserMap = new LinkedHashMap<>();
		
		for(int i=0; i<recentUserDetailsList.size(); i++) 
		{
			List<String> recentUserList = new ArrayList<>();
			
			if(recentUserDetailsList.get(i).getSenderUserId().equals(userId) && 
			  !recentUserMap.containsKey(recentUserDetailsList.get(i).getReceiverUserId())) 
			{
				recentUserList.add(recentUserDetailsList.get(i).getReceiverUserId());
				recentUserList.add(recentUserDetailsList.get(i).getMessageStatus());
				recentUserList.add(recentUserDetailsList.get(i).getSenderUserId());
				recentUserMap.put(recentUserDetailsList.get(i).getReceiverUserId(), recentUserList);
			}
			
			else if(!recentUserDetailsList.get(i).getSenderUserId().equals(userId) && 
					!recentUserMap.containsKey(recentUserDetailsList.get(i).getSenderUserId())) 
			{
				recentUserList.add(recentUserDetailsList.get(i).getSenderUserId());
				recentUserList.add(recentUserDetailsList.get(i).getMessageStatus());
				recentUserList.add(recentUserDetailsList.get(i).getSenderUserId());
				recentUserMap.put(recentUserDetailsList.get(i).getSenderUserId(), recentUserList);
			}
		}
		
		return recentUserMap;
	}
	
	@Override
	public int getMessageCountBetweenTwoUsers(String userId1, String userId2) 
	{
		String sql = "SELECT count(*) FROM private_chat WHERE (sender_user_id=? AND receiver_user_id=?) "
				   + "OR (sender_user_id=? AND receiver_user_id=?)";
		
		Object[] args = {userId1, userId2, userId2, userId1};
		
		int messageCount = this.jdbcTemplate.queryForObject(sql, Integer.class, args);
		
		return messageCount;
	}
	
	@Override
	public List<String> getUserDetailsByUserId(String userId) 
	{	
		String sql = "SELECT user_id, first_name, last_name FROM user_details WHERE user_id=? ";
		
		Object[] args = {userId};
		
		List<List<String>> userDetails = this.jdbcTemplate.query(sql, 
														   new GetUserDetailsByUserIdRowMapperImpl(), 
														   args);
		
		return userDetails.get(0);
	}
	
	@Override
	public void markMessageAsRead(String senderUserId, String receiverUserId) 
	{
		String sql = "UPDATE private_chat " 
				   + "SET message_status='Read' " 
				   + "WHERE sender_user_id=? AND receiver_user_id=? ";
		Object[] args = {senderUserId, receiverUserId};
		
		int numberOfRowsUpdated = jdbcTemplate.update(sql, args);
		
		System.out.println(numberOfRowsUpdated + " row(s) updated");
	}
	
	@Override
	public int getUnreadMessageReceivedCountForRecentUserList(String senderUserId, String receiverUserId) 
	{
		String sql = "SELECT COUNT(*) FROM private_chat " 
				   + "WHERE sender_user_id=? "
				   + "AND receiver_user_id=? "
				   + "AND message_status='Unread' ";
		
		Object[] args = {senderUserId, receiverUserId};
		
		int receivedMessageCount = this.jdbcTemplate.queryForObject(sql, Integer.class, args);
		
		return receivedMessageCount;
	}
}
