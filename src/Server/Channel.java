package Server;

import java.net.Socket;

public class Channel extends Thread{
	
	protected Socket s;
	protected MessageManager group;
	
	public Channel(Socket s, MessageManager group) {

		super();
		this.s = s;
		this.group = group;
	}

}
