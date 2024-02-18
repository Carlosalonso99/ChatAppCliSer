package Client;

import java.net.Socket;

public class Channel extends Thread{

	protected Socket s;
	protected MessageManager msgMan;
	
	
	public Channel(Socket s, MessageManager msgMan) {
		super();
		this.s = s;
		this.msgMan = msgMan;
	}
}
