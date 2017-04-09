package imageReader;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

/**Used code taken from: 
 * https://www.quickprogrammingtips.com/java/how-to-encrypt-and-decrypt-data-in-java-using-aes-algorithm.html 
 */


public class EncryptFile {
		  
    /**
     * 1. Generate a plain text for encryption
     * 2. Get a secret key (printed in hexadecimal form). In actual use this must
     * by encrypted and kept safe. The same key is required for decryption.
     * 3.
     */
    public static SecretKey main(String inputFilePath, String outputFileFolder, String encryptedFile) throws Exception {

    	SecretKey secKey = getSecretEncryptionKey();
        byte[] cipherText = encryptText(inputFilePath, secKey);
        
        System.out.println("AES Key (Hex Form):" + bytesToHex(secKey.getEncoded()));
        
	    File file = new File(encryptedFile);
        FileOutputStream fos = new FileOutputStream(encryptedFile);
        try {
            fos.write(cipherText);
        } finally {
            fos.close();
        }
        return secKey;
    }
     
    public static SecretKey getSecretEncryptionKey() throws Exception{
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128); // The AES key bit size
        SecretKey secKey = generator.generateKey();
        return secKey;
    }
     
    /**
     * Encrypts inputFile in AES using the secret key
     * @param plainText
     * @param secKey
     * @return
     * @throws Exception
     */
    public static byte[] encryptText(String inputFilePath,SecretKey secKey) throws Exception{
        // AES defaults to AES/ECB/PKCS5Padding in Java 7
    	byte[] inputBytes = Files.readAllBytes(new File(inputFilePath).toPath());
    	Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
        byte[] byteCipherText = aesCipher.doFinal(inputBytes);
        return byteCipherText;
    }
     
    /**
     * Convert a binary byte array into readable hex form
     * @param hash
     * @return
     */
    private static String  bytesToHex(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash);
    }
}
