package Client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class OutChannel extends Channel {

	public OutChannel(Socket s, MessageManager msgMan) {
		super(s, msgMan);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			synchronized (msgMan) {

				while (msgMan.getMsg() == null) {
					try {
						System.out.println("cliente output esperando a para enviar");
						msgMan.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("antes del if");
					if (msgMan.getMsg() != null) {
						System.out.println("entra en el if");
						msgMan.sendMessage(oos);
						msgMan.setMsg(null);
						msgMan.setSent(true);
					}
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

}
