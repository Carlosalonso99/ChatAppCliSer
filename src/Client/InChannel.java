package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

public class InChannel extends Channel {

	public InChannel(Socket s, MessageManager msgMan) {
		super(s, msgMan);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
	    super.run();
	    ObjectInputStream ois = null;
	    try {
	        ois = new ObjectInputStream(this.s.getInputStream());
	        while (true) {
	            Message msg = msgMan.reciveMessage(ois);
	            System.out.println(new String(msg.getMsg()));
	            if (msg != null) {
	                msgMan.setMsg(msg);
	            }
	        }
	    } catch (SocketException e) {
	        System.err.println("Socket cerrado.");
	        // Aquí puedes manejar la reconexión si es necesario o cerrar la aplicación de manera ordenada
	    } catch (IOException e) {
	        e.printStackTrace();
	        //Para gestionar cuando el cliente se desconecta
	    } finally {
	    	try {
				ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        if (ois != null) {
	            try {
	                ois.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        try {
	            if (this.s != null && !this.s.isClosed()) {
	                this.s.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}


}
