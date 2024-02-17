package Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class OutChannel extends Channel {

	private Socket s;
	private MessageManager group;

	
	

	public OutChannel(Socket s, MessageManager group) {

		super(s, group);

	}


	@Override
	public void run() {
		super.run();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			
			while(true) {
				if(!group.isSent()) {
				List<byte[]> msgs = group.getMessages();
				oos.writeObject(msgs.get(msgs.size() - 1));
				group.setSent(true);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


	
	
}
