package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.crypto.spec.SecretKeySpec;







public class Client {

	private Socket s = new Socket();
	private String name = "Carlos";
	private RSACipher c = new RSACipher();
	private volatile MessageManager reciber ;
	private volatile MessageManager sender;
	

	public Client() {
		super();

	}


	 void getConexion() {
		// TODO Auto-generated method stub
		InetSocketAddress isa = new InetSocketAddress("localhost", 9999);
		try {
			s.connect(isa);
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			//Envio la publica al servidor antes de establecer la conexion
			c.sendPublic(oos);
			System.out.println("Envie mipublica al server");
			
			ObjectInputStream ois = new ObjectInputStream(this.s.getInputStream());
			//Recibimos la clave de sesion encriptada
		    SecretKeySpec sks =  c.recibeSessionKey(ois);
		    System.out.println("Recibi la sesion key del server" + sks.getFormat());
		    //Creamos el cipher de sesion para darselo a los msgManages
		    AESCipher c = new AESCipher(sks);
		    
		    reciber = new MessageManager(null, c);
		    sender = new MessageManager(null, c);
		 
		    
		    
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
		Thread in = new InChannel(s, reciber);
		Thread out = new OutChannel(s, sender);
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
