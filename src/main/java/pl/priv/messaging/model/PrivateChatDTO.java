package pl.priv.messaging.model;

import org.springframework.stereotype.Component;

@Component
public class PrivateChatDTO
{
	private String senderUserId;
	private String receiverUserId;
	private String message;
	private String timestamp;
	private String selectedUser;
	private String messageStatus;
	
	public String getSenderUserId() {
		return senderUserId;
	}

	public String getReceiverUserId() {
		return receiverUserId;
	}

	public String getMessage() {
		return message;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setSenderUserId(String senderUserId) {
		this.senderUserId = senderUserId;
	}

	public void setReceiverUserId(String receiverUserId) {
		this.receiverUserId = receiverUserId;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(String selectedUser) {
		this.selectedUser = selectedUser;
	}

	public String getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}

	@Override
	public String toString() {
		return "PrivateChatsDTO ["
			 + "senderUserId=" + senderUserId 
			 + ", receiverUserId=" + receiverUserId 
			 + ", message=" + message 
			 + ", timestamp=" + timestamp
			 + ", selectedUser=" + selectedUser
			 + ", messageStatus=" + messageStatus
			 + "]";
	}
}
