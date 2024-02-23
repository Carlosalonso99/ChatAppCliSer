package Server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;


/**
 * Esta clase sera el servidor,
 *  que almacenara los userManager,uno por grupo
 */
public class Server {
	static volatile MessageManager gp1 = new MessageManager();
	static volatile MessageManager gp2 = new MessageManager();
	static SecretKey sessionKey;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		KeyGenerator keygen;
		try {
			keygen = KeyGenerator.getInstance("AES");
			sessionKey = keygen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		try {
			ServerSocket ss = new ServerSocket();
			
			InetSocketAddress isa = new InetSocketAddress("localhost", 9999);
			
			ss.bind(isa);
			int cont = 0;
			while(true) {
				Socket s = ss.accept();
				
				//Para recibir la publica del cliente
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				
				PublicKey pkclient = (PublicKey) ois.readObject();
				System.out.println("Recibi la publica del cliente");
				
				Cipher c = Cipher.getInstance("RSA");
				
				c.init(Cipher.ENCRYPT_MODE, pkclient);
				
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				
				byte [] sessionKeyEncriptada = c.doFinal(sessionKey.getEncoded());
				
				oos.writeObject(sessionKeyEncriptada);
				System.out.println("Envie la session key al cliente encriptada con su publica");
				
				
				
				if (cont < 2) {
				Thread in = new InChannel(s, gp1);
				in.start();
				Thread out = new OutChannel(s, gp1);
				out.start();
				}else {
					Thread in = new InChannel(s, gp2);
					in.start();
					Thread out = new OutChannel(s, gp2);
					out.start();
				}
				cont ++;
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	


	

}
