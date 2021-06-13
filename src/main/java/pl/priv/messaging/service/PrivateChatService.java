package pl.priv.messaging.service;

import java.util.List;
import java.util.Map;

import pl.priv.messaging.model.PrivateChatDTO;

public interface PrivateChatService 
{
	Map<String, List<String>> getAllUserList(String userId);
	boolean chatExist(String userId);
	Map<String, List<String>> getPrivateChatData(String userId1, String userId2);
	void insertData(PrivateChatDTO privateChatDTO);
	void deleteUserIdFromPrivateChat(String userId);
	String getLastAddedDataForUser(String userId);
	Map<String, List<String>> getRecentUserListForUser(String userId);
	int getMessageCountBetweenTwoUsers(String userId1, String userId2);
	List<String> getUserDetailsByUserId(String userId);
	void markMessageAsRead(String senderUserId, String receiverUserId);
	int getUnreadMessageReceivedCountForRecentUserList(String senderUserId, String receiverUserId);
	Map<String, List<String>> getRecentUserData(String loggedInUserId);
}
