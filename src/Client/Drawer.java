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
		
		while (true) {
			if (!messMan.isSent()) {
				Message msg = messMan.getMsg();
				if(!msg.getUser().getName().equals(user.getName())) {
				chatArea.append(msg.getUser().getName() + ": " + new String(msg.getMsg()) + "\n");
				}
				messMan.setSent(true);
			}
		}
	}

}
