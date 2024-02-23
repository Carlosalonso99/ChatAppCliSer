package Server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

import Client.Message;

/**
 * Conexion es un hilo que gestiona la escucha activa de los mensajes del
 * usuario asociado al socket se queda leyendo hasta que lee algo y entonces
 */
public class InChannel extends Channel {

	public InChannel(Socket s, MessageManager group) {
		super(s, group);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// Se une al grupo
		super.run();
		joinGroup();

		try (ObjectInputStream ois = new ObjectInputStream(s.getInputStream())) {
	        while (true) {

	            try {
	            	System.out.println("esperando mensaje");
	                byte [] msg = readMsg(ois);


	                if (msg != null) {
	                    addMessageToGroup(msg);
	                }
	            } catch (EOFException e) {
	                System.out.println("Cliente desconectado: " + s.getRemoteSocketAddress());
	                break; // Salir del bucle si el cliente se desconecta
	            } catch (ClassNotFoundException e) {
	                System.out.println("Clase no encontrada: " + e.getMessage());
	            } catch (IOException e) {
	                System.out.println("Error al leer el mensaje: " + e.getMessage());
	                break; // Salir del bucle si ocurre un error de IO diferente
	            }
	        }
	    } catch (SocketException e) {
	        System.out.println("Conexión reseteada por el cliente");
	    } catch (IOException e) {
	        System.out.println("Error al obtener el flujo de entrada: " + e.getMessage());
	    } finally {
	        try {
	            s.close();
	        } catch (IOException e) {
	            System.out.println("Error al cerrar el socket: " + e.getMessage());
	        }
	        leaveGroup();
	    }

	}

	private void leaveGroup() {
		// TODO Auto-generated method stub
		group.getSockets().remove(s);
	}

	private void addMessageToGroup(byte[] msg) {
		synchronized (group) {
			this.group.addMessage(msg, s);
		}
		
	}

	private byte [] readMsg(ObjectInputStream ois) throws IOException, ClassNotFoundException {
	    return  (byte[]) ois.readObject(); // EOFException será capturado en el método run
	}

	private void joinGroup() {

		group.addSocket(s);
	}

}
