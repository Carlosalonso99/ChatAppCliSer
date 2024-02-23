package Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MessageManager {
	private Message msg;
	private volatile boolean sent = true;
	private AESCipher c;
	
	public MessageManager(Message msg, AESCipher c) {
		super();
		this.msg = msg;
		this.c =c;
	}

	public Message getMsg() {
		return msg;
	}

	public synchronized void setMsg(Message msg) {
		this.msg = msg;
		setSent(false);
	}

	public boolean isSent() {
		return sent;
	}

	public void setSent(boolean sent) {
		this.sent = sent;
		this.notifyAll();
	}

	public AESCipher getC() {
		return c;
	}

	public void setC(AESCipher c) {
		this.c = c;
	}
	
	public void sendMessage(ObjectOutputStream oos) {
		c.sendMessage(oos, msg);
	}
	
	public Message reciveMessage(ObjectInputStream ois) {
		return c.recibeMessage(ois);
	}
	
	
	
}
