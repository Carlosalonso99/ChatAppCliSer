package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Conexion es un hilo que gestiona la escucha activa de los mensajes del
 * usuario asociado al socket se queda leyendo hasta que lee algo y entonces
 */
public class InChannel extends Channel {


	public InChannel(Socket s, MessageManager group) {
		super(s, group);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// Se une al grupo
		super.run();
		joinGroup();

		while (true) {


			byte [] msg = readMsg();
			
			if(msg != null) {
				addMessageToGroup(msg);
			}

		}

	}


	private void addMessageToGroup(byte[] msg) {
		this.group.addMessage(msg, s);
	}



	private byte[] readMsg() {

		byte [] msg = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			msg = (byte[]) ois.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}

	private void joinGroup() {

		group.addSocket(s);
	}

}
