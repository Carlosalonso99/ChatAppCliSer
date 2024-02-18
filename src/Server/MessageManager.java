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
	private List<Message> messages;
	private volatile boolean sent;

	public MessageManager(/*String name*/) {
		super();
		this.name = "Grupo prueba";
		this.sockets = new ArrayList<Socket>();
		this.messages = new ArrayList<Message>();
		this.sent = true;
	}

	public List<Socket> getSockets() {
		return sockets;
	}

	public void setSocket(List<Socket> sockets) {
		this.sockets = sockets;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
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

	public void setSent(boolean sent) {
		this.sent = sent;
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
	public synchronized void addMessage(Message msg, Socket s) {
		// TODO Auto-generated method stub
		messages.add(msg);
		setSent(false);
	}



}
