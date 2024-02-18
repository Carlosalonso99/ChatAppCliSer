package Server;

import java.io.Serializable;
import java.net.Socket;


public class User implements Serializable{
	String name;


	public User(String name, Socket s) {
		super();

		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
