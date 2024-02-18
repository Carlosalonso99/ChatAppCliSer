package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

import javax.swing.JButton;

import javax.swing.*;
import java.awt.*;

/**
 * Esta interfaz la ha diseñado chatGPT
 */
public class UIClient extends JFrame {
	private JTextArea chatArea;
	private JTextField messageField;
	private Client client = new Client();
	private User user;

	public UIClient() {

		client.getConexion();

		setTitle("Chat Sencillo");
		setSize(400, 300);
		


		getContentPane().setLayout(new BorderLayout());

		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Usamos FlowLayout para alinear los componentes
																		// a la izquierda
		JLabel lblNombre = new JLabel("Nombre"); // Crea la etiqueta
		JTextField textField = new JTextField(20); // Crea el cuadro de texto con un ancho predeterminado
		JButton btnNombre = new JButton("Conectar");

		topPanel.add(lblNombre); // Añade la etiqueta al panel
		topPanel.add(textField); // Añade el cuadro de texto al panel
		topPanel.add(btnNombre);
		getContentPane().add(topPanel, BorderLayout.NORTH);
		// JTextArea para mostrar el historial de mensajes
		chatArea = new JTextArea();
		chatArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(chatArea);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		btnNombre.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				client.setName(textField.getText());
				user = new User(client.getName(), client.getS());
				chatArea.append("Te has conectado como : " + user.getName() + "\n");
				Thread drawer = new Drawer(client.getReciber(), chatArea, user);
				drawer.start();
			}
		});

		// JTextField para que el usuario escriba los mensajes
		messageField = new JTextField();
		getContentPane().add(messageField, BorderLayout.SOUTH);

		// JButton para enviar los mensajes
		JButton sendButton = new JButton("Enviar");
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});
		getContentPane().add(sendButton, BorderLayout.EAST);
		this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (client != null) {
                    client.closeConnection(); 
                }
                System.exit(0); 
            }
        });
	}

	private void sendMessage() {
		String message = messageField.getText();
		if (user != null) {
			if (!message.isEmpty()) {
				chatArea.append("Tú: " + message + "\n");
				messageField.setText("");

				Message msg = new Message(user, message.getBytes());
				client.getSender().setMsg(msg);
			}
		} else {
			chatArea.append("!!!Debes conectarte con un nombre \n");
		}

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new UIClient().setVisible(true);

			}
		});
	}
}
