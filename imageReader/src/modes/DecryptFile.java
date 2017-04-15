package modes;

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

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class DecryptFile {
	
    /**
     * Use key from encryption to decrypt the file 
     */
    public static void main(String inputFilePath, String decryptedFile, String keyString) throws Exception { 
        
    	byte[] hashedKey = hashKey(keyString);
    	SecretKey secKey = new SecretKeySpec(hashedKey, 0, hashedKey.length, "AES");
    	SecretKeySpec secKeySpec = new SecretKeySpec(hashedKey, "AES");
    	
    	System.out.println("AES Key (Hex Form): " + bytesToHex(secKey.getEncoded()));
    
    	byte[] plainText = decryptText(inputFilePath, decryptedFile, secKey, secKeySpec);
        
//        File file = new File(decryptedFile);
//        FileOutputStream fos = new FileOutputStream(decryptedFile);
//        try {
//            fos.write(plainText);
//        } finally {
//            fos.close();
//        }            
    }
	
	/**
     * Decrypts encrypted byte array using the key used for encryption.
     * @param byteCipherText
     * @param secKey
     * @return
     * @throws Exception
     */
    public static byte[] decryptText(String inputFilePath, String decryptedFile, SecretKey secKey, SecretKeySpec secKeySpec) throws Exception {
    	byte[] iv = {
    				0,0,0,0,
    				0,0,0,0,
    				0,0,0,0,
    				0,0,0,0
    				};
    	IvParameterSpec ivspec = new IvParameterSpec(iv);
    	
    	byte[] cipherText = Files.readAllBytes(new File(inputFilePath).toPath());

        Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        aesCipher.init(Cipher.DECRYPT_MODE, secKey, ivspec);
        aesCipher.init(Cipher.DECRYPT_MODE, secKeySpec, ivspec);
        System.out.println(cipherText.length);
        
//        byte[] bytePlainText = aesCipher.doFinal(cipherText);
        byte[] buf = new byte[1024];
        try {
//        	decryptStream(new FileInputStream(inputFilePath), new FileOutputStream(decryptedFile), aesCipher, 
//        			buf);
        	decryptStream(new FileInputStream(inputFilePath), new FileOutputStream(decryptedFile), aesCipher, 
        			cipherText);
        } catch (java.io.IOException e) {
        }
        
        return buf;
    }
    
    public static void decryptStream(InputStream in, OutputStream out, Cipher dcipher, byte[] buf) {
        try {

            in = new CipherInputStream(in, dcipher);

            // Read in the decrypted bytes and write the cleartext to out 
            int numRead = 0;


            while ((numRead = in.read(buf)) >= 0) {
                out.write(buf, 0, numRead);

            }
            out.close();


        } catch (java.io.IOException e) {
        }
    }
    
    private static String bytesToHex(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash);
    }
    
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
    
   //attempt to pad 
//  if ( cipherText.length % 16 != 0){
//	byte[] byteRem = new byte[16 - (cipherText.length % 16)];
//	// create a destination array that is the size of the two arrays
//	byteFinal = new byte[cipherText.length + byteRem.length];
//
//	// copy ciphertext into start of destination (from pos 0, copy ciphertext.length bytes)
//	System.arraycopy(cipherText, 0, byteFinal, 0, cipherText.length);
//
//	// copy mac into end of destination (from pos ciphertext.length, copy mac.length bytes)
//	System.arraycopy(byteRem, 0, byteFinal, cipherText.length, byteRem.length);
//}
}
