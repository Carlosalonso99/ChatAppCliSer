package Client;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AESCipher {

	Cipher encrypter;
	Cipher decrypter;
	SecretKeySpec sks;
	
	public AESCipher(SecretKeySpec sks) {
		this.sks = sks;
		try {
			encrypter = Cipher.getInstance("AES");
			decrypter = Cipher.getInstance("AES");
			
			encrypter.init(Cipher.ENCRYPT_MODE, sks);
			decrypter.init(Cipher.DECRYPT_MODE, sks);
			
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
	Message recibeMessage(ObjectInputStream ois) {
		Message objetoDeserializado = null;
		try {
			//recibo mis bytes encriptados
			byte[] msgEncriptado = (byte[]) ois.readObject();
			//los desencripto
			byte[] msgDesencriptado = decrypter.doFinal(msgEncriptado);
			
			//Deserializacion de una array de bytes
			//creo un input de bytearray con los bytes de mi msg
			ByteArrayInputStream bais = new ByteArrayInputStream(msgDesencriptado);
			//creo un input de objeto con my stream de byte array
            ObjectInputStream ois2 = new ObjectInputStream(bais);
            //leo mi objeto msg de la entrada del oos
            objetoDeserializado = (Message) ois2.readObject();
			

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
		return objetoDeserializado;
	}
	
	void sendMessage(ObjectOutputStream oos, Message msg) {
		//Para serializar mi mensaje
		//Creo un output de array de bytes
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try {
			//Creo un output de object con mi output de bytes
			ObjectOutputStream oos2 = new ObjectOutputStream(baos);
			
			//Escribo mi objeto en mi output
			oos2.writeObject(msg);
			oos2.flush();
			
			//Recojo de mi output de arrays mi objeto serializado
			byte[] bytesMsg = baos.toByteArray();
			
			byte[] msgEncriptado = encrypter.doFinal(bytesMsg);
			oos.writeObject(msgEncriptado);
			oos.flush();
			
			
					
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
	}
	
	
	
}

































