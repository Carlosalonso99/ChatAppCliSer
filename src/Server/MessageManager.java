package Server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import Client.Message;

/**
 * El la clase que gestiona los grupos Contiene los sockets de los
 * usuarios(Cliente) de este grupo
 */

//TODO: Cambiar lista de bytes [] a lista de mensajes, Mensaje : User, msg
public class MessageManager {
	private String name;
	private List<Socket> sockets;
	private List<byte[]> messages;
	private volatile boolean sent;
	private volatile int sentTo = 0;



	public MessageManager(/*String name*/) {
		super();
		this.name = "Grupo prueba";
		this.sockets = new ArrayList<Socket>();
		this.messages = new ArrayList<byte[]>();
		this.sent = true;
	}

	public List<Socket> getSockets() {
		return sockets;
	}

	public void setSocket(List<Socket> sockets) {
		this.sockets = sockets;
	}

	public List<byte[]> getMessages() {
		return messages;
	}

	public void setMessages(List<byte[]> messages) {
		this.messages = messages;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSent() {
		return sent;
	}

	public synchronized void setSent(boolean sent) {
		this.sent = sent;
	}

	public int getSentTo() {
		return sentTo;
	}

	public void addSocket(Socket s) {
		this.sockets.add(s);
	}

	/**
	 * Metodo sincronizado que añade a la lista y envia a los demas usuarios
	 * 
	 * @param msg
	 * @param s
	 */
	public synchronized void addMessage(byte[] msg, Socket s) {
		// TODO Auto-generated method stub
		messages.add(msg);
		setSent(false);
		System.out.println("cambio a flase en recibes");
		this.notifyAll();
	}


	public void setSockets(List<Socket> sockets) {
		this.sockets = sockets;
	}

	public synchronized boolean addSentTo() {
		// TODO Auto-generated method stub
		sentTo += 1;
		System.out.println();
		if(sentTo == sockets.size()){
			sentTo = 0;
			sent = true;
			this.notifyAll();
			return true;
		}else {
			return false;
		}
			
	}


}
