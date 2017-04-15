package modes;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author liamdasilva, daxs3130@mylaurier.ca
 *
 */
public class Utilities {
	//the number of channels per pixel to encode (3 being RGB, 4 being RGBA)
	private static final int NUM_CHANNELS = 3;
	//the number of bytes an integer takes up (used to encode file length at start)
	private static final int INT_BYTES = 32;
	
	private static final String SIGNATUREHEADER = "0000000011111111";
	
	/**
	 * Creates a BufferedImage object from an image filename/path
	 * @param fileName the image filename
	 * @return the (BufferedImage) image handle
	 */
	private static BufferedImage getBufferedImageFromFile(String fileName){
		File origImage = new File(fileName);
		BufferedImage img = null;
		try {
			img = ImageIO.read(origImage);
			//BufferedImage newImage = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_RGB);
			//System.out.println("Height: "+img.getHeight()+" Width: "+img.getWidth());
			return img;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Determines whether a file will fit into the image using the specified number of bit encoding
	 * @param img the image to encode
	 * @param file the file to be encoded into the image
	 * @param bits the number of bits to use
	 * @return (boolean) true/false
	 */
	private static boolean fileWillFit(BufferedImage img, File file, int bits){
		int l =(int) file.length();
		int bytes = getMaxBytes(img,bits);
		return bytes>l;
	}
	
	/**
	 * Returns the maximum number of bytes that can be encoded in the image using the specified number of bits to encode
	 * @param img the image to encode
	 * @param bits the number of bits used to encode
	 * @return the number of bytes that can be encoded in the image
	 */
	private static int getMaxBytes(BufferedImage img,int bits){
		return (NUM_CHANNELS*img.getHeight()*img.getWidth()*bits)/8;
	}
	
	/**
	 * Returns the maximum number of bytes that can be encoded in the image using the specified number of bits to encode
	 * @param img the path/name of image to encode
	 * @param bits the number of bits used to encode
	 * @return the number of bytes that can be encoded in the image
	 */
	public static int getMaxBytes(String imageName,int bits){
		BufferedImage img = getBufferedImageFromFile(imageName);
		return (NUM_CHANNELS*img.getHeight()*img.getWidth()*bits)/8;
	}
	
	
	/**
	 * Encodes a file into an image with the lowest possible number of 'bits per byte' encoding
	 * @param imageName the name/path of the image
	 * @param fileName the name/path of the file to be encoded into the image
	 * @param outFile the name/path of the encoded image that will be written out
	 * @return (int) the number of bits used to encode the file. -1 if encoding failed
	 */
	public static int insertFileOptimally(String imageName, String fileName, String outFile){
		BufferedImage img = getBufferedImageFromFile(imageName);
		File file = new File(fileName);
		int i = 1;
		while(i<8 && !fileWillFit(img,file,i)){
			i++;
		}
		if(!(insertFileIntoImage(imageName,fileName,i,outFile)==-1)){
			return i;
		}else{
			return -1;
		}
	}
	
	/**
	 * Signs an image using one-bit encoding
	 * @param imageName the name/path of the image
	 * @param signature the signature to be encoded into the image
	 * @param outFile the name/path of the encoded image that will be written out
	 * @return (boolean) whether the signing was successful or not
	 * 
	 */
	public static boolean signImage(String imageName, String signature, String outFile){
		int bits = 1;
		if(signature.length()>100||signature.length()<=0){
			return false;
		}
		BufferedImage img = getBufferedImageFromFile(imageName);
		
		try {
			//convert signature to binary and attach marker to start of it
			String binString = SIGNATUREHEADER+toBinary(signature);
			IMGWriter imgTracker = new IMGWriter(img,bits);
			int counter = 0;
			for (int i=0;i<img.getHeight();i++){
				for (int j=0;j<img.getWidth();j++){
					String nextBits = binString.substring(counter, counter+1);
					imgTracker.insertBits(nextBits);
					counter=(counter+1)%binString.length();
					nextBits = binString.substring(counter, counter+1);
					imgTracker.insertBits(nextBits);
					counter=(counter+1)%binString.length();
					nextBits = binString.substring(counter, counter+1);
					imgTracker.insertBits(nextBits);
					counter=(counter+1)%binString.length();
				}
			}
			//get output file extension
			String[] array = imageName.split("\\.");
			String extension = array[array.length-1];
			ImageIO.write(imgTracker.getIMG(), extension, new File(outFile));
			return true;
				
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Gets a signature from an image
	 * @param imageName the name/path of the image
	 * @return (String) the signature extracted
	 * 
	 */
	public static String getSignature(String imageName){
		int bits = 1;
		BufferedImage img = getBufferedImageFromFile(imageName);
		
		IMGReader imgReader = new IMGReader(img,bits);
		String tempString = "";
		String string = "";
		boolean beginningFound = false;
		boolean signatureFound = false;
		int i =0;int j =0;
		while (i<img.getHeight()&&!signatureFound){
			while (j<img.getWidth()&&!signatureFound){
				tempString+=imgReader.readBits();
				if (!beginningFound){
					if(tempString.contains(SIGNATUREHEADER)){//is this beginning of signature?
						beginningFound = true;
						tempString="";
					}
				}else{
					if(tempString.contains(SIGNATUREHEADER)){//is the signature done?
						signatureFound = true;
						tempString=tempString.substring(0, tempString.length() - SIGNATUREHEADER.length());
						string = binaryToString(tempString);
					}
				}
				j++;
			}
			i++;
		}
		
		return string;
	}
	
    /**
     * Converts a string to a string of its binary representation
     * @param text the text to convert
     * @return (String) the binary string
     */
    private static String toBinary(String text) {
        StringBuilder sb = new StringBuilder();
        for (char character : text.toCharArray()) {
            sb.append(String.format("%8s", Integer.toBinaryString(character)).replace(' ', '0'));
        }
        return sb.toString();

    }
    
    /**
     * Converts a binary string to a string
     * @param text the text to convert
     * @return (String) the string
     */
    private static String binaryToString(String text) {
        String result = "";
    	if ((text.length()%8==0)){
    		int c = 0;
    		while (c<text.length()/8){
    			String charCode = text.substring(0, 8);
    			text = text.substring(8);
    			result += new Character((char)Integer.parseInt(charCode,2));
    		}
    		
    	}
    	return result;
    }
    
    /**
	 * Shifts the bits in each byte 'bitsToShift' places left
	 * @param imageName the name/path of the image
	 * @param bitsToShift the signature to be encoded into the image
	 * @param outFile the name/path of the image that will be written out
	 * @return (boolean) whether the shifting was successful or not
	 * 
	 */
	public static boolean shiftBits(String imageName,int bitsToShift, String outFile){
		if(bitsToShift>8||bitsToShift<0){
			return false;
		}
		BufferedImage img = getBufferedImageFromFile(imageName);
		
		try {
			for (int i=0;i<img.getHeight();i++){
				for (int j=0;j<img.getWidth();j++){
					Color curColor = new Color(img.getRGB(j, i));
					int r = (curColor.getRed()<<bitsToShift)%256;
					int g = (curColor.getGreen()<<bitsToShift)%256;
					int b = (curColor.getBlue()<<bitsToShift)%256;
					Color newc = new Color(r,g,b,curColor.getAlpha());
					img.setRGB(j, i, newc.getRGB());
				}
			}
			//get output file extension
			String[] array = imageName.split("\\.");
			String extension = array[array.length-1];
			ImageIO.write(img, extension, new File(outFile));
			return true;
				
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Shifts the bits in each byte 'bitsToShift' places left
	 * @param imageName the name/path of the image
	 * @param bitsToShift the signature to be encoded into the image
	 * @param outFile the name/path of the image that will be written out
	 * @return (boolean) whether the shifting was successful or not
	 * 
	 */
	public static boolean getLeastSigBits(String imageName,int bitsToGet, String outFile){
		if(bitsToGet>8||bitsToGet<=0){
			return false;
		}
		int intToAnd = (int)Math.pow(2, bitsToGet)-1;
		BufferedImage img = getBufferedImageFromFile(imageName);
		
		try {
			for (int i=0;i<img.getHeight();i++){
				for (int j=0;j<img.getWidth();j++){
					Color curColor = new Color(img.getRGB(j, i));
					int r = curColor.getRed();
					int g = curColor.getGreen();
					int b = curColor.getBlue();
					r = r&0b00000011;
					g = g&0b00000011;
					b = b&0b00000011;
					int average = (r+g+b)/3;
					int x = 0;
					if (average==0){
						x=0;
					}else if(average==1){
						x=85;
					}else if(average==2){
						x=170;
					}else if(average==3){
						x=255;
					}
					Color newc = new Color(x,x,x,curColor.getAlpha());
					img.setRGB(j, i, newc.getRGB());
				}
			}
			//get output file extension
			String[] array = imageName.split("\\.");
			String extension = array[array.length-1];
			System.out.println("Writing file...");
			ImageIO.write(img, extension, new File(outFile));
			System.out.println("Done");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
    
	
	/**
	 * Encodes a file into an image given the number of 'bits per byte' to use for encoding
	 * @param imageName the name/path of the image
	 * @param fileName the name/path of the file to be encoded into the image
	 * @param bits the number of 'bits per byte' to use for encoding
	 * @param outFile the name/path of the encoded image that will be written out
	 * @return (int) number of bytes successfully inserted. -1 if failure
	 * 
	 */
	public static int insertFileIntoImage(String imageName, String fileName, int bits, String outFile){
		if(bits>8||bits<=0){
			return -1;
		}
		BufferedImage img = getBufferedImageFromFile(imageName);
		File file = new File(fileName);
		boolean itFits = fileWillFit(img,file,bits);
		//System.out.println("Will the file fit in the image?: "+itFits);
		try {
			if(itFits){
				int length = (int) file.length();//file length in bytes
				
				//System.out.println("File Length: "+length);
				int numBytes = length+(bits-length%bits);//length of file in bytes rounded up to multiple of 'bits' variable
				int numBitBlocks = numBytes/bits;//number of bitblocks to be read in
				
				IMGWriter imgTracker = new IMGWriter(img,bits);
				
				FileInputStream in = new FileInputStream(fileName);
				
				StringBuilder sb = new StringBuilder();
				
				//insert length of file into image first
				//the number of bits used is equal to 32+(bits-32%bits)
				int ls = INT_BYTES+(bits-INT_BYTES%bits);//length of file length string
				String lengthString = String.format("%"+ls+"s", Integer.toBinaryString(length)).replace(' ', '0');
				for (int i=0;i<ls/bits;i++){
					String nextBits = lengthString.substring(i*bits, i*bits+bits);
					boolean success = imgTracker.insertBits(nextBits);
					if (!success){
						return -1;
					}
				}
				
				//insert bytes of file in bit blocks of size 8*bits
				for(int i=0;i<numBitBlocks;i++){
					sb = new StringBuilder();
					for(int j=0;j<bits;j++){
						int next = in.read();
						if (next==-1){
							sb.append("00000000"); //padding if necessary once final byte is reached
						}else{
							sb.append(String.format("%8s", Integer.toBinaryString(next)).replace(' ', '0'));
						}
					}
					String bitBlock = sb.toString();
//					System.out.println(bitBlock);
					for (int k=0;k<8;k++){
						//System.out.println(block.substring(k*bits, k*bits+bits));
						String nextBits = bitBlock.substring(k*bits, k*bits+bits);
						boolean success = imgTracker.insertBits(nextBits);
						if (!success){
							return -1;
						}
					}
				}
				
				//get output file extension
				String[] array = imageName.split("\\.");
				String extension = array[array.length-1];
				ImageIO.write(imgTracker.getIMG(), extension, new File(outFile));
				return length;
				
			}else{
				return -1;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	
	/**
	 * Extracts an encoded file from an image given the number of bits initially used to encode it
	 * @param imageName the name/path of the image
	 * @param bits the number of 'bits per byte' that was used for encoding
	 * @param outFile the name/path of the extracted file
	 * @return (int) number of bytes successfully read. -1 if failure
	 */
	public static int readFileFromImage(String imageName, int bits, String outFile){
		BufferedImage img = getBufferedImageFromFile(imageName);
		IMGReader imgReader = new IMGReader(img,bits);
		
		int ls = INT_BYTES+(bits-INT_BYTES%bits);//number of bits used for length of file
		int i = 0; String lengthString = "";
		while(i<ls){
			lengthString+=imgReader.readBits();
			i+=bits;
		}
		int numBytes = Integer.parseInt(lengthString, 2);
//		System.out.println(numBytes);
		int maxBytes = getMaxBytes(img,bits);
		
		//validation of numBytes
		if(numBytes <0 || numBytes > maxBytes){
			System.out.println("Faulty number of bits used");
			return -1;
		}
		
		int numBits = numBytes*8;
		i = 0;
		String string="";
		try {
			FileOutputStream file = new FileOutputStream(outFile);
			while(i<numBits){
				string+=imgReader.readBits();
				//write bits out one byte at a time 
				if (string.length()>=8){
					String substr = string.substring(0, 8);
					byte towrite =(byte) Integer.parseInt(substr, 2);
					file.write(towrite);
					string = string.substring(8);
				}
				i+=bits;
			}
			return numBytes;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}
}
