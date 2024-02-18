package Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import Client.Message;

public class OutChannel extends Channel {

	public OutChannel(Socket s, MessageManager group) {

		super(s, group);

	}

	@Override
	public void run() {
		super.run();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

			while (true) {
				if (!group.isSent()) {
					List<Message> msgs = group.getMessages();
					Message msg = msgs.get(msgs.size() - 1);
					oos.writeObject(msg);
					oos.flush();
					group.setSent(true);
				}
			}
		} catch (SocketException e) {
			System.err.println("Socket cerrado.");
			try {
				s.close();
				group.getSockets().remove(s);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
