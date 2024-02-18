package Client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;







public class Client {

	private Socket s = new Socket();
	private String name = "Carlos";
	private volatile MessageManager reciber = new MessageManager(null);
	private volatile MessageManager sender = new MessageManager(null);
	Thread in = new InChannel(s, reciber);
	Thread out = new OutChannel(s, sender);

	public Client() {
		super();

	}


	 void getConexion() {
		// TODO Auto-generated method stub
		InetSocketAddress isa = new InetSocketAddress("localhost", 9999);
		try {
			s.connect(isa);
			//Abrimos los hilos de lectura y escritura
			openChannels(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public MessageManager getReciber() {
		return reciber;
	}

	public void setReciber(MessageManager reciber) {
		this.reciber = reciber;
	}

	public MessageManager getSender() {
		return sender;
	}

	public void setSender(MessageManager sender) {
		this.sender = sender;
	}

	public String getName() {
		return name;
	}

	public Socket getS() {
		return s;
	}

	public void setS(Socket s) {
		this.s = s;
	}
	
	public void openChannels(Socket s) {
		in.start();
		out.start();
	}

	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
	}


	public void addMessage(Message message) {
		// TODO Auto-generated method stub
		
		sender.setMsg(message);
	}


	public void closeConnection() {
		// TODO Auto-generated method stub
		try {
			s.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	


}
