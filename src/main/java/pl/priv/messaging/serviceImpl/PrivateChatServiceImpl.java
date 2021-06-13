package pl.priv.messaging.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.priv.messaging.dao.PrivateChatDao;
import pl.priv.messaging.model.PrivateChatDTO;
import pl.priv.messaging.service.PrivateChatService;

@Service
public class PrivateChatServiceImpl  implements PrivateChatService
{
	@Autowired
	PrivateChatDao privateChatDao;
	
	@Autowired
	PrivateChatService privateChatService;

	@Override
	public Map<String, List<String>> getAllUserList(String userId) 
	{
		return privateChatDao.getAllUserList(userId);
	}
	
	@Override
	public boolean chatExist(String userId) 
	{
		return privateChatDao.chatExist(userId);
	}
	
	@Override
	public Map<String, List<String>> getPrivateChatData(String userId1, String userId2) 
	{
		return privateChatDao.getPrivateChatData(userId1, userId2);
	}

	@Override
	public void insertData(PrivateChatDTO privateChatDTO) 
	{
		privateChatDao.insertData(privateChatDTO);
	}
	
	@Override
	public void deleteUserIdFromPrivateChat(String userId) 
	{
		privateChatDao.deleteUserIdFromPrivateChat(userId);
	}
	
	@Override
	public String getLastAddedDataForUser(String userId) 
	{
		return privateChatDao.getLastAddedDataForUser(userId);
	}

	@Override
	public Map<String, List<String>> getRecentUserListForUser(String userId) 
	{
		return privateChatDao.getRecentUserListForUser(userId);
	}
	
	@Override
	public int getMessageCountBetweenTwoUsers(String userId1, String userId2) 
	{
		return privateChatDao.getMessageCountBetweenTwoUsers(userId1, userId2);
	}
	
	@Override
	public List<String> getUserDetailsByUserId(String userId) 
	{
		return privateChatDao.getUserDetailsByUserId(userId);
	}
	
	@Override
	public void markMessageAsRead(String senderUserId, String receiverUserId) 
	{
		privateChatDao.markMessageAsRead(senderUserId, receiverUserId);
	}
	
	@Override
	public int getUnreadMessageReceivedCountForRecentUserList(String senderUserId, String receiverUserId) 
	{
		return privateChatDao.getUnreadMessageReceivedCountForRecentUserList(senderUserId, receiverUserId);
	}
	
	@Override
	public Map<String, List<String>> getRecentUserData(String loggedInUserId) 
	{
		Map<String, List<String>> recentUserMap = privateChatService.getRecentUserListForUser(loggedInUserId);
		Map<String, List<String>> recentUserData = new LinkedHashMap<>();
		
		for(Map.Entry<String,List<String>> entry : recentUserMap.entrySet()) 
		{
			int unreadMessage = privateChatService
					.getUnreadMessageReceivedCountForRecentUserList(entry.getKey(), loggedInUserId);
			
			List<String> userDetails = privateChatService.getUserDetailsByUserId(entry.getKey());
			
			userDetails.add(entry.getValue().get(1));
			userDetails.add(entry.getValue().get(2));
			userDetails.add(Integer.toString(unreadMessage));
			
			recentUserData.put(entry.getKey(), userDetails);
		}
		
		return recentUserData;
	}
}
