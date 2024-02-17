package Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Esta clase sera el servidor,
 *  que almacenara los userManager,uno por grupo
 */
public class Server {
	static MessageManager gp1 = new MessageManager();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			ServerSocket ss = new ServerSocket();
			
			InetSocketAddress isa = new InetSocketAddress("localhost", 9999);
			
			ss.bind(isa);
			
			while(true) {
				Socket s = ss.accept();
				Thread in = new InChannel(s, gp1);
				in.start();
				Thread out = new OutChannel(s, gp1);
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	


	

}
