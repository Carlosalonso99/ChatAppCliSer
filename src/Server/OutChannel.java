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
			synchronized (group) {

				while (group.isSent()) {
					try {
							System.out.println("OUT CHANNEL ESPERANDO MENSAJE");
						group.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("server antes de if");
					if (!group.isSent()) {
						System.out.println("server despues de if");
						List<byte[]> msgs = group.getMessages();
						byte[] msg = msgs.get(msgs.size() - 1);
						oos.writeObject(msg);
						oos.flush();

						if(group.addSentTo()) {

							System.out.println("esnviado a todos ya");
						}else {
							System.out.println("msg enviado");
							try {
								group.wait();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
							
						
						
					}
				}
				System.out.println("salio" + group.isSent());
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
