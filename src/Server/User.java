package Server;

import java.net.Socket;

/**
 * La clase usuario sera la encargadade mostrar los mensajes recibitos
 * y enviar los mensajes escritos
 */
public class User {
	String name;
	int id;
	Socket s;

	public User(int id, Socket s, String name) {
		super();
		this.id = id;
		this.s = s;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Socket getS() {
		return s;
	}

	public void setS(Socket s) {
		this.s = s;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Muestra el mensaje y lo envia el mensaje
	 * @param msg
	 */
	public void sendMessage(String msg) {

	}
	/**
	 * Recibe el mensaje y lo muestra
	 * @param msg
	 */
	public void reciveMessage(String msg) {

	}

}
