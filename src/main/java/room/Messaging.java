package room;

public class Messaging {
	
	private String messageSender;
	private String messageContent;
	
	public Messaging() {
		
	}
	public Messaging(String messageSender, String messageContent) {
		super();
		this.messageSender = messageSender;
		this.messageContent = messageContent;
	}
	public String getMessageSender() {
		return messageSender;
	}
	public void setMessageSender(String messageSender) {
		this.messageSender = messageSender;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

}
