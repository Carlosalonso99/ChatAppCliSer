package Client;

import javax.swing.JTextArea;

public class Drawer extends Thread {
	private MessageManager messMan;
	private JTextArea chatArea;
	private User user;

	public Drawer(MessageManager messMan, JTextArea chatArea, User user) {
		super();
		this.messMan = messMan;
		this.chatArea = chatArea;
		this.user = user;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		synchronized (messMan) {

			while (messMan.isSent()) {
				try {
					System.out.println("drawer esperando");
					messMan.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("drawer antes if");
				if (!messMan.isSent()) {
					System.out.println("drawer en el if");
					Message msg = messMan.getMsg();
					if (!msg.getUser().getName().equals(user.getName())) {
						chatArea.append(msg.getUser().getName() + ": " + new String(msg.getMsg()) + "\n");
					}
					messMan.setSent(true);
				}
			}
		}
	}

}
