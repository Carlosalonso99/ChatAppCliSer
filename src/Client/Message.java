package Client;

import java.io.Serializable;

public class Message implements Serializable{

	private User user;
	private byte [] msg;
	
	public Message(User user, byte[] msg) {
		super();
		this.user = user;
		this.msg = msg;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public byte[] getMsg() {
		return msg;
	}

	public void setMsg(byte[] msg) {
		this.msg = msg;
	}
	
	
}
