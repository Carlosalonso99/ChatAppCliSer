package Client;

public class MessageManager {
	private Message msg;
	private volatile boolean sent = true;
	
	public MessageManager(Message msg) {
		super();
		this.msg = msg;
	}

	public Message getMsg() {
		return msg;
	}

	public void setMsg(Message msg) {
		this.msg = msg;
		sent = false;
	}

	public boolean isSent() {
		return sent;
	}

	public void setSent(boolean sent) {
		this.sent = sent;
	}
	
	
}
