package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class RSACipher {

	Cipher c;
	KeyPair keyPair ;
	
	public RSACipher() {
		try {
			//Genero las claves de RSA
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyPair = keyGen.generateKeyPair();
			//Haco mi Cipher en modo desencriptar con mi privada para poder desencriptar la clave de sesion del servidor
			c = Cipher.getInstance("RSA");
			c.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Metodo para enviar mi publica al servidor
	 * @param oos
	 */
	void sendPublic(ObjectOutputStream oos) {
		try {
			
			oos.writeObject(keyPair.getPublic());
			System.out.println("Se ha enviado la clave publica");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	SecretKeySpec recibeSessionKey(ObjectInputStream ois) {
		SecretKeySpec sks = null;
		try {
			//Leo la clave cifrada
			byte [] sessionKeyCifrada = (byte[]) ois.readObject();
			//La descifro
			byte [] sessionKeyDescifrada = c.doFinal(sessionKeyCifrada);
			
			//creo una clave de sesion con la clave
			
			sks = new SecretKeySpec(sessionKeyDescifrada, "AES");
			System.out.println("Tenemos la clave de sesion");
			
			
			//return Objecto chipher AES
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sks;
	}
}
