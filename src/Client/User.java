package Client;
//Version sincronizada  con cifrado

//TODO: gestionar una session key porgrupo y cambiar la ui para que clientes puedan elegiir grupo, incluso crearlo

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
