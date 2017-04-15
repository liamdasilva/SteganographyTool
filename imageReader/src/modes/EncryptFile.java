package modes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class EncryptFile {
		  
    /**
     * 1. Generate a plain text for encryption
     * 2. Get a secret key (printed in hexadecimal form). In actual use this must
     * by encrypted and kept safe. The same key is required for decryption.
     * 3.
     */
    public static void main(String inputFilePath, String encryptedFile, String keyString) throws Exception {

    	byte[] hashedKey = hashKey(keyString); 
    	SecretKey secKey = new SecretKeySpec(hashedKey, 0, hashedKey.length, "AES");
    	SecretKeySpec secKeySpec = new SecretKeySpec(hashedKey, "AES");
    	System.out.println("AES Key (Hex Form): " + bytesToHex(secKey.getEncoded()));
    	
        byte[] cipherText = encryptText(inputFilePath, encryptedFile, secKey, secKeySpec);
        
//	    File file = new File(encryptedFile);
//        FileOutputStream fos = new FileOutputStream(encryptedFile);
//        try {
//            fos.write(cipherText);
//        } finally {
//            fos.close();
//        }
    }
     
    /**
     * Encrypts inputFile in AES using the secret key
     * @param plainText
     * @param secKey
     * @return
     * @throws Exception
     */
    public static byte[] encryptText(String inputFilePath, String encryptedFile, SecretKey secKey, SecretKeySpec secKeySpec) throws Exception{
    	byte[] iv = {
				0,0,0,0,
				0,0,0,0,
				0,0,0,0,
				0,0,0,0
				};
    	IvParameterSpec ivspec = new IvParameterSpec(iv);
    	
    	byte[] inputBytes = Files.readAllBytes(new File(inputFilePath).toPath());
    	Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aesCipher.init(Cipher.ENCRYPT_MODE, secKeySpec, ivspec);
//        aesCipher.init(Cipher.ENCRYPT_MODE, secKey, ivspec);
        System.out.println(inputBytes.length);
//        byte[] byteCipherText = aesCipher.doFinal(inputBytes);
        try {
            encryptStream(new FileInputStream(inputFilePath), new FileOutputStream(encryptedFile), aesCipher);
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        
        return inputBytes;
    }
    
    public static void encryptStream(InputStream in, OutputStream out, Cipher aesCipher) {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            out = new CipherOutputStream(out, aesCipher);

            // read the cleartext and write it to out
            int numRead = 0;
            while ((numRead = in.read(buf)) >= 0) {
                out.write(buf, 0, numRead);

            }
            bOut.writeTo(out);
            out.close();
            bOut.reset();

        } catch (java.io.IOException e) {
        }

    }
    
     
    /**
     * Convert a binary byte array into readable hex form
     * @param hash
     * @return
     */
    private static String  bytesToHex(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash);
    }
    
//  public static SecretKey getSecretEncryptionKey() throws Exception{
//	  KeyGenerator generator = KeyGenerator.getInstance("AES");
//	  generator.init(128); // The AES key bit size
//	  SecretKey secKey = generator.generateKey();
//	  return secKey;
//  }
    
    public static byte[] hashKey(String keyString){
	    byte[] key = null; 
	    MessageDigest sha = null;
		try {
			key= (keyString).getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    key = sha.digest(key);
	    key = Arrays.copyOf(key, 16); // use only first 128 bit
	    return key;
    }

    
}
