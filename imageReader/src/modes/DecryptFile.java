package modes;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class DecryptFile {
	
    /**
     * Use key from encryption to decrypt the file 
     */
//    public static void main(String inputFilePath, String outputFileFolder, String int bitsToDecode, String decryptedFile, SecretKey secKey) throws Exception {
    public static void main(String imageName, String outFolder, String outFile, String keyString) throws Exception { 
//    	byte[] inputBytes = Files.readAllBytes(new File(inputFilePath).toPath());
//        byte[] decryptedText = decryptText(inputBytes, secKey);
//        
//        
//        File file = new File(decryptedFile);
//        FileOutputStream fos = new FileOutputStream(decryptedFile);
//        try {
//            fos.write(decryptedText);
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
    public static byte[] decryptText(byte[] byteCipherText, SecretKey secKey) throws Exception {
        // AES defaults to AES/ECB/PKCS5Padding in Java 7
        Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aesCipher.init(Cipher.DECRYPT_MODE, secKey);
        System.out.println(byteCipherText.length);
        byte[] byteFinal = byteCipherText;
        if ( byteCipherText.length % 16 != 0){
        	byte[] byteRem = new byte[16 - (byteCipherText.length % 16)];
        	// create a destination array that is the size of the two arrays
        	byteFinal = new byte[byteCipherText.length + byteRem.length];

        	// copy ciphertext into start of destination (from pos 0, copy ciphertext.length bytes)
        	System.arraycopy(byteCipherText, 0, byteFinal, 0, byteCipherText.length);

        	// copy mac into end of destination (from pos ciphertext.length, copy mac.length bytes)
        	System.arraycopy(byteRem, 0, byteFinal, byteCipherText.length, byteRem.length);
        }
        System.out.println(byteFinal.length);
        byte[] bytePlainText = aesCipher.doFinal(byteFinal);
        return bytePlainText;
    }
}
