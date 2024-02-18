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
			while (true) {
				if (!msgMan.isSent()) {
					oos.writeObject(msgMan.getMsg());
					oos.flush();
					msgMan.setSent(true);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

}
